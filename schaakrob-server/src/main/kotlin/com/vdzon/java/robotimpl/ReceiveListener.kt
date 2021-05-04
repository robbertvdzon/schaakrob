package com.vdzon.java.robotimpl

interface ReceiveListener {
    /**
     * @param data data bytes arrived
     */
    fun dataReceived(data: IntArray)
}