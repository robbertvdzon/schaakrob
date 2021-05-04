package com.vdzon.java.robotimpl.rf;

public interface ReceiveListener {

    /**
     * @param data data bytes arrived
     */
    void dataReceived(int[] data);

}