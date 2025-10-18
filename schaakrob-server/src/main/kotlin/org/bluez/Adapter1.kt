package org.bluez

import org.freedesktop.dbus.annotations.DBusInterfaceName
import org.freedesktop.dbus.interfaces.DBusInterface

@DBusInterfaceName("org.bluez.Adapter1")
interface Adapter1 : DBusInterface {
    fun StartDiscovery()
    fun StopDiscovery()
}