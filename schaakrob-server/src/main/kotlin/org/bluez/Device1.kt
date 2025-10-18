package org.bluez

import org.freedesktop.dbus.annotations.DBusInterfaceName
import org.freedesktop.dbus.interfaces.DBusInterface

@DBusInterfaceName("org.bluez.Device1")
interface Device1 : DBusInterface {
    fun Connect()
    fun Disconnect()
}