package com.vdzon.java.robotimpl.rf

interface ReceiveListener {
    /**
     * @param data data bytes arrived
     */
    fun dataReceived(data: IntArray)
}