package com.vdzon.java.robotimpl

interface IRegister {
    companion object {
        /*nRF24L01 register24 in total*/
        const val CONFIG: Short = 0x00 // config register
        const val EN_AA: Short = 0x01 // enable auto ack
        const val EN_RXADDR: Short = 0x02 // receive channel enable 0-5
        const val SETUP_AW: Short = 0x03 // setup address width 3-5
        const val SETUP_RETR: Short = 0x04 // setup auto retry
        const val RF_CH: Short = 0x05 // RF channel setup
        const val RF_SETUP: Short = 0x06 // RF register register
        const val STATUS: Short = 0x07 // status register
        const val OBSERVE_TX: Short = 0x08 // observe TX resiter
        const val CD: Short = 0x09 // Carrier
        const val RX_ADDR_P0: Short = 0x0a // data channel 0 address
        const val RX_ADDR_P1: Short = 0x0b // data channel 1 address
        const val RX_ADDR_P2: Short = 0x0c // data channel 2 address
        const val RX_ADDR_P3: Short = 0x0d // data channel 3 address
        const val RX_ADDR_P4: Short = 0x0e // data channel 4 address
        const val RX_ADDR_P5: Short = 0x0f // data channel 5 address
        const val TX_ADDR: Short = 0x10 // TX address
        const val RX_PW_P0: Short = 0x11 // P0 chanel data width setup
        const val RX_PW_P1: Short = 0x12 // P1 chanel data width setup
        const val RX_PW_P2: Short = 0x13 // P2 chanel data width setup
        const val RX_PW_P3: Short = 0x14 // P3 chanel data width setup
        const val RX_PW_P4: Short = 0x15 // P4 chanel data width setup
        const val RX_PW_P5: Short = 0x16 // P5 chanel data width setup
        const val FIFO_STATUS: Short = 0x17 // FIFO
        const val FEATURE: Short = 0x1D // Additional features register,

        // needed to enable the
        // additional
        // commands
        /*nRF24L01register operation commands*/
        const val R_REGISTER: Short = 0x00 // read register
        const val W_REGISTER: Short = 0x20 // write
        const val R_RX_PAYLOAD: Short = 0x61 // read rx payload
        const val W_TX_PAYLOAD: Short = 0xa0 // write rx payload
        const val FLUSH_TX: Short = 0xe1 // clear TXFIFO
        const val FLUSH_RX: Short = 0xe2 // clear RXFIFO
        const val REUSE_TX_PL: Short = 0xe3 // reuse last data package load
        const val NOP: Short = 0xff // NULL operation
    }
}