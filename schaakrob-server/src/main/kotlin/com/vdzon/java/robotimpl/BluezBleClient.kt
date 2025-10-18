package com.vdzon.java.robotimpl

import org.slf4j.LoggerFactory
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
        return conn ?: DBusConnection.getConnection(DBusConnection.DBusBusType.SYSTEM).also { conn = it }
    }

    private fun findDevicePathByAddress(bus: DBusConnection, mac: String): String? {
        val objMgr = bus.getRemoteObject("org.bluez", "/", ObjectManager::class.java)
        val managed = objMgr.GetManagedObjects()
        val targetAddr = mac.uppercase()
        return managed.entries.firstOrNull { (path, ifaces) ->
            path.startsWith("/org/bluez/$adapter/dev_") &&
            ifaces.containsKey("org.bluez.Device1") &&
            ((ifaces["org.bluez.Device1"]?.get("Address") as? Variant<*>)?.value as? String)?.equals(targetAddr, true) == true
        }?.key
    }

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
            val connected = try { (props.Get("org.bluez.Device1", "Connected") as Variant<*>).value as? Boolean } catch (_: Exception) { null } ?: false
            if (!connected) {
                dev.Connect()
            }

            // Wait for services resolved (best-effort)
            val stopAt = System.currentTimeMillis() + timeoutMs
            var resolved = try { (props.Get("org.bluez.Device1", "ServicesResolved") as Variant<*>).value as? Boolean } catch (_: Exception) { null } ?: false
            while (!resolved && System.currentTimeMillis() < stopAt) {
                Thread.sleep(100)
                resolved = try { (props.Get("org.bluez.Device1", "ServicesResolved") as Variant<*>).value as? Boolean } catch (_: Exception) { null } ?: false
            }
            if (!resolved) {
                log.warn("BlueZ: Services not resolved for $mac; proceeding to try write anyway")
            }

            // Find RX characteristic path under this device
            val objMgr = bus.getRemoteObject("org.bluez", "/", ObjectManager::class.java)
            val managed = objMgr.GetManagedObjects()
            val rxPath = managed.entries.firstOrNull { (path, ifaces) ->
                path.startsWith(devicePath) && ifaces.containsKey("org.bluez.GattCharacteristic1") &&
                        ((ifaces["org.bluez.GattCharacteristic1"]?.get("UUID") as? Variant<*>)?.value as? String)?.lowercase() == lowercaseRx
            }?.key
            if (rxPath == null) {
                log.error("BlueZ: RX characteristic $rxCharUuid not found under $devicePath")
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
