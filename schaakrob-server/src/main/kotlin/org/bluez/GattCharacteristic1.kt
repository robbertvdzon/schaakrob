package org.bluez

import org.freedesktop.dbus.annotations.DBusInterfaceName
import org.freedesktop.dbus.interfaces.DBusInterface
import org.freedesktop.dbus.types.Variant

@DBusInterfaceName("org.bluez.GattCharacteristic1")
interface GattCharacteristic1 : DBusInterface {
    fun WriteValue(value: List<Byte>, options: Map<String, Variant<*>>)
    fun ReadValue(options: Map<String, Variant<*>>): List<Byte>
    fun StartNotify()
    fun StopNotify()
}