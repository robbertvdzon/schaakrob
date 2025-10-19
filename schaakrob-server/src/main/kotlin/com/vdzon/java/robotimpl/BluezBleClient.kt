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
import org.freedesktop.dbus.exceptions.NotConnected
import org.freedesktop.dbus.exceptions.FatalDBusException

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
    fun write(mac: String, data: ByteArray, timeoutMs: Long = 20000): Boolean {
        val lowercaseRx = rxCharUuid.lowercase()
        val adapterPath = "/org/bluez/$adapter"

        var attempt = 1
        while (attempt <= 2) {
            var bus: DBusConnection? = null
            var adapterObj: Adapter1? = null
            var dev: Device1? = null
            try {
                bus = ensureConn()

                // Resolve or discover the device path
                var devicePath = findDevicePathByAddress(bus, mac)
                if (devicePath == null) {
                    // Start discovery to let BlueZ create the device object
                    adapterObj = bus.getRemoteObject("org.bluez", adapterPath, Adapter1::class.java)
                    try { adapterObj.StartDiscovery() } catch (_: Exception) {}
                    val stopAt = System.currentTimeMillis() + timeoutMs
                    while (devicePath == null && System.currentTimeMillis() < stopAt) {
                        Thread.sleep(200)
                        devicePath = findDevicePathByAddress(bus, mac)
                    }
                    if (adapterObj != null) { try { adapterObj.StopDiscovery() } catch (_: Exception) {} }
                    if (devicePath == null) {
                        log.error("BlueZ: Device with address $mac not found (discovery timeout)")
                        return false
                    }
                }

                dev = bus.getRemoteObject("org.bluez", devicePath, Device1::class.java)
                val objMgr = bus.getRemoteObject("org.bluez", "/", ObjectManager::class.java)

                // Always attempt to connect (idempotent if already connected)
                try { dev.Connect() } catch (_: Exception) {}
                Thread.sleep(200) // small backoff before polling properties

                // Wait (best-effort) for services to resolve; tolerate slow resolve
                val stopAt = System.currentTimeMillis() + timeoutMs
                var resolved = false
                var connected = false
                while (System.currentTimeMillis() < stopAt) {
                    try {
                        val managed = objMgr.GetManagedObjects()
                        val devEntry = managed.entries.firstOrNull { it.key.path == devicePath }
                        if (devEntry != null) {
                            val devIfaces = devEntry.value["org.bluez.Device1"]
                            connected = ((devIfaces?.get("Connected") as? Variant<*>)?.value as? Boolean) ?: false
                            resolved = ((devIfaces?.get("ServicesResolved") as? Variant<*>)?.value as? Boolean) ?: false
                            if (connected && resolved) break
                        }
                    } catch (ex: Exception) {
                        log.error("BlueZ: Failed to read Device1 props from ObjectManager: ${ex.message}", ex)
                    }
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
                // Use Write Without Response to reduce timing sensitivities on peripheral
                val options = hashMapOf<String, Variant<*>>("type" to Variant("command"))
                gatt.WriteValue(data.toList(), options)
                return true
            } catch (e: Exception) {
                val recoverable = (e is NotConnected) || (e is FatalDBusException)
                log.error("BlueZ write failed to $mac (attempt $attempt): ${e.message}", e)
                if (recoverable && attempt < 2) {
                    // Reset D-Bus connection and retry once
                    try { conn?.close() } catch (_: Exception) {}
                    conn = null
                    try { Thread.sleep(150) } catch (_: Exception) {}
                    attempt++
                    continue
                }
                return false
            } finally {
                // Ensure discovery is stopped and connection is closed for Option A (connect-write-disconnect)
                if (adapterObj != null) {
                    try { adapterObj.StopDiscovery() } catch (_: Exception) {}
                }
                if (dev != null) {
                    try { dev.Disconnect() } catch (_: Exception) {}
                }
            }
        }
        return false
    }

    override fun close() {
        try { conn?.close() } catch (_: Exception) {}
        conn = null
    }
}
