package com.vdzon.java.robotimpl

import org.slf4j.LoggerFactory
import org.freedesktop.dbus.DBusPath
import org.freedesktop.dbus.connections.impl.DBusConnection
import org.freedesktop.dbus.interfaces.Properties
import org.freedesktop.dbus.types.Variant
import org.bluez.Device1
import org.bluez.GattCharacteristic1
import org.bluez.Adapter1
import org.freedesktop.DBus.ObjectManager

class BluezBleClient(
    private val adapter: String = "hci0",
    private val serviceUuid: String = "6e400001-b5a3-f393-e0a9-e50e24dcca9e",
    private val rxCharUuid: String = "6e400002-b5a3-f393-e0a9-e50e24dcca9e"
) : AutoCloseable {

    private val log = LoggerFactory.getLogger(BluezBleClient::class.java)
    private var conn: DBusConnection? = null

    private fun ensureConn(): DBusConnection {
        val existing = conn
        return if (existing != null) {
            try {
                if (existing.isConnected) existing else DBusConnection.getConnection(DBusConnection.DBusBusType.SYSTEM).also { conn = it }
            } catch (_: Exception) {
                DBusConnection.getConnection(DBusConnection.DBusBusType.SYSTEM).also { conn = it }
            }
        } else {
            DBusConnection.getConnection(DBusConnection.DBusBusType.SYSTEM).also { conn = it }
        }
    }

    private fun findDevicePathByAddress(bus: DBusConnection, mac: String): String? {
        val objMgr = bus.getRemoteObject("org.bluez", "/", ObjectManager::class.java)
        val managed = objMgr.GetManagedObjects()
        val suffix = "dev_" + mac.uppercase().replace(":", "_")
        // Match device object by deterministic path suffix and presence of Device1 interface
        val path: DBusPath? = managed.keys.firstOrNull { path ->
            val p = path.path
            p.startsWith("/org/bluez/$adapter/") && p.endsWith(suffix)
        }
        return path?.path
    }

    @Synchronized
    fun write(mac: String, data: ByteArray, timeoutMs: Long = 8000): Boolean {
        val lowercaseRx = rxCharUuid.lowercase()
        val bus = ensureConn()
        val adapterPath = "/org/bluez/$adapter"
        try {
            // Resolve or discover the device path
            var devicePath = findDevicePathByAddress(bus, mac)
            if (devicePath == null) {
                // Start discovery to let BlueZ create the device object
                val adapterObj = bus.getRemoteObject("org.bluez", adapterPath, Adapter1::class.java)
                try { adapterObj.StartDiscovery() } catch (_: Exception) {}
                val stopAt = System.currentTimeMillis() + timeoutMs
                while (devicePath == null && System.currentTimeMillis() < stopAt) {
                    Thread.sleep(200)
                    devicePath = findDevicePathByAddress(bus, mac)
                }
                try { adapterObj.StopDiscovery() } catch (_: Exception) {}
                if (devicePath == null) {
                    log.error("BlueZ: Device with address $mac not found (discovery timeout)")
                    return false
                }
            }

            val dev = bus.getRemoteObject("org.bluez", devicePath, Device1::class.java)
            val props = bus.getRemoteObject("org.bluez", devicePath, Properties::class.java)

            // Connect if needed
            var connected = try { (props.Get("org.bluez.Device1", "Connected") as Variant<*>).value as? Boolean } catch (_: Exception) { null } ?: false
            if (!connected) {
                dev.Connect()
            }

            // Wait (best-effort) for services to resolve; do not hard-abort if it takes long
            val stopAt = System.currentTimeMillis() + timeoutMs
            var resolved = false
            while (System.currentTimeMillis() < stopAt) {
                resolved = try { (props.Get("org.bluez.Device1", "ServicesResolved") as Variant<*>).value as? Boolean } catch (_: Exception) { null } ?: false
                connected = try { (props.Get("org.bluez.Device1", "Connected") as Variant<*>).value as? Boolean } catch (_: Exception) { null } ?: false
                if (resolved && connected) break
                Thread.sleep(100)
            }
            if (!resolved) {
                log.warn("BlueZ: Services not resolved for $mac; continue and poll for characteristic")
            }
            if (!connected) {
                log.error("BlueZ: Device $mac disconnected before write")
                return false
            }

            // Find RX characteristic path under this device, polling until it appears or timeout
            val objMgr = bus.getRemoteObject("org.bluez", "/", ObjectManager::class.java)
            var rxPath: String? = null
            val charStopAt = System.currentTimeMillis() + timeoutMs
            while (rxPath == null && System.currentTimeMillis() < charStopAt) {
                val managed = objMgr.GetManagedObjects()
                for (entry in managed.entries) {
                    val p = entry.key.path
                    if (!p.startsWith(devicePath)) continue
                    val ifaces = entry.value
                    if (!ifaces.containsKey("org.bluez.GattCharacteristic1")) continue
                    val uuidVar = ifaces["org.bluez.GattCharacteristic1"]?.get("UUID") as? Variant<*>
                    val uuid = (uuidVar?.value as? String)?.lowercase()
                    if (uuid == lowercaseRx) {
                        rxPath = entry.key.path
                        break
                    }
                }
                if (rxPath == null) Thread.sleep(100)
            }
            if (rxPath == null) {
                log.error("BlueZ: RX characteristic $rxCharUuid not found under $devicePath within timeout")
                return false
            }

            val gatt = bus.getRemoteObject("org.bluez", rxPath, GattCharacteristic1::class.java)
            val options = hashMapOf<String, Variant<*>>("type" to Variant("request"))
            gatt.WriteValue(data.toList(), options)
            return true
        } catch (e: Exception) {
            log.error("BlueZ write failed to $mac: ${e.message}", e)
            return false
        }
    }

    override fun close() {
        try { conn?.close() } catch (_: Exception) {}
        conn = null
    }
}
