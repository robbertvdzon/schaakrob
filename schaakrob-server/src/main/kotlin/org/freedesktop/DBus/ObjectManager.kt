package org.freedesktop.DBus

import org.freedesktop.dbus.annotations.DBusInterfaceName
import org.freedesktop.dbus.interfaces.DBusInterface
import org.freedesktop.dbus.types.Variant

@DBusInterfaceName("org.freedesktop.DBus.ObjectManager")
interface ObjectManager : DBusInterface {
    fun GetManagedObjects(): Map<String, Map<String, Map<String, Variant<*>>>>
}
