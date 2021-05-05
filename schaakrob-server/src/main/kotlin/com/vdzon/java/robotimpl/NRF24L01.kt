package com.vdzon.java.robotimpl

import java.lang.Runnable
import kotlin.jvm.Volatile
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ExecutorService
import com.pi4j.wiringpi.Gpio
import java.util.concurrent.Executors
import com.pi4j.wiringpi.GpioUtil
import java.util.concurrent.TimeUnit
import java.lang.InterruptedException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/*
 * #%L
 * **********************************************************************
 * ORGANIZATION  :  Pi4J
 * PROJECT       :  Pi4J :: Device Abstractions
 * FILENAME      :  NRF24L01.java
 *
 * This file is part of the Pi4J project. More information about
 * this project can be found here:  http://www.pi4j.com/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2017 Pi4J
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 *
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
/**
 * NRF24L01.java is the driver for nodic nRF24L01 seriser 2.4G RF chip. see http://www.nordicsemi.com/eng/Products/2.4GHz-RF/nRF24L01P
 * it is simplex communication, but it abstract the chip as duplex. It owns one data listener for receiving.
 * this app mocked SPI bus for communication implementation.
 *
 * this class is designed as singleton pattern since hardware is monopolized.
 *
 * <h1>Sample Usage</h1>
 * <pre>
 * NRF24L01 nrf=NRF24L01.getInstance();
 * nrf.start();//there will be thread daemon up, if your system does not have other user thread, setup one for dummy
 * nrf.setReceiverListener(.....);
 *
 * <br></br>somewhere to send
 * nrf.send(.....);
 *
 * <br></br>somewhere to shutdown
 * nrf.shutdown();
</pre> *
 *
 * RF/GPIO table:<br></br>
 *
 *  * GPIO_21 LED bubble
 *  * GPIO_2 IRQ
 *  * GPIO_12 MISO
 *  * GPIO_11 CE
 *  * GPIO_13 MOSI
 *  * GPIO_14 SCLK
 *  * GPIO_0 CSN
 *
 *
 * @author Alex maoanapex88@163.com
 */
class NRF24L01 private constructor() : IRegister, Runnable {
    /**
     * default local listening chanel 96
     */
    private var localRFChanel = 96

    /**
     * default local RF address which is one byte length n 5
     */
    private var localRFAddress = intArrayOf(53, 69, 149, 231, 231)

    /**
     * one flag marking thread running
     */
    @Volatile
    private var running = false

    /**
     * the IRQ watching thread handler
     */
    private var irqWatchThread: Thread? = null

    /**
     * the attached receive listener, it is empty listener by default.
     * empty listener will only print received data byte array to STDOUT.
     */
    private var listener: ReceiveListener = EmptyReceiveListener()

    /**
     * one blocking queue for send data, when sending data, data first will
     * be queued and then wait NRF send period to pull data and send it out.
     */
    private val fifo: BlockingQueue<DataPackage> = LinkedBlockingQueue(16)

    /**
     * one thread pool for processing data listener, all received data callback will be invoked from
     * thread and not thread safe.
     */
    private var executorService: ExecutorService? = null

    companion object {
        /**
         * one led bubble, GPIO 21, if working, it will light up
         */
        private const val LED = 21

        /**
         * irq GPIO_2
         */
        private const val IRQ = 2

        /**
         * miso GPIO_12
         */
        private const val MISO = 9 //12;

        /**
         * ce GPIO_11
         */
        private const val CE = 17 //11

        /**
         * mosi GPIO_13
         */
        private const val MOSI = 10 //13;

        /**
         * sclk GPIO_14
         */
        private const val SCLK = 11 //14;

        /**
         * csn GPIO_0
         */
        private const val CSN = 8 //0;

        /**
         * default data width 16, NRF support 32 in max
         */
        private const val RECEIVE_DATA_WIDTH: Short = 16
        /**
         * @return return the singleton instance
         */
        /**
         * the singleton instance
         */
        val instance = NRF24L01()

        init {
        }
    }

    /**
     * setRxMode is used to config RF channel and address via SPI command.
     * it uses chanel 0 as receive address and same as send
     * hardcode to 250kbps 0dBm, then enable RX_DR irq; block TX_DS+MAX_RT; enable CRC powerup; in receive mode
     * @param rfChannel from 0-128
     * @param addrWidth fixed to 5
     * @param rxAddr one array length of 5 representing the address
     */
    private fun setRxMode(rfChannel: Int, addrWidth: Int, rxAddr: IntArray) {
        Gpio.digitalWrite(CE, 0)
        writeRegister(IRegister.W_REGISTER + IRegister.SETUP_AW, addrWidth - 2) // set address width
        writeBuffer(
            IRegister.W_REGISTER + IRegister.RX_ADDR_P0,
            rxAddr,
            addrWidth
        ) // use channel 0, same address for receive
        writeRegister(IRegister.W_REGISTER + IRegister.RF_CH, rfChannel) // RF setup
        writeRegister(
            IRegister.W_REGISTER + IRegister.RX_PW_P0,
            RECEIVE_DATA_WIDTH.toInt()
        ) // channel 0 same data width
        writeRegister(IRegister.W_REGISTER + IRegister.RF_SETUP, 0x27) // inital 250Kbps, power 0dBm
        // (+22dBm with PA), LNA?
        writeRegister(IRegister.W_REGISTER + IRegister.STATUS, 0x7f) // clear RX_DR,TX_DS,MAX_RT flags
        writeRegister(
            IRegister.W_REGISTER + IRegister.CONFIG,
            0x3f
        ) // enable RX_DR irq, block TX_DS+MAX_RT, enable CRC powerup, in receive mode
    }

    /**
     * send SPI command to init device and power up, clean up read/write buffer
     * then light up LED bubble on GPIO_21
     */
    private fun init() {
        println("init 1"); 
        Gpio.digitalWrite(CE, 0)
        println("init 2"); 
        Gpio.digitalWrite(CSN, 1)
        println("init 3"); 
        Gpio.digitalWrite(SCLK, 0)
        println("init 4"); 
        writeRegister(IRegister.W_REGISTER + IRegister.EN_AA, 0x01)
        println("init 5"); 
        writeRegister(IRegister.W_REGISTER + IRegister.EN_RXADDR, 0x01) // enable channel 0
        println("init 6"); 
        writeRegister(IRegister.W_REGISTER + IRegister.SETUP_RETR, 0x1f) // set auto retry delay 500us, retry 15 times
        println("init 7"); 
        writeRegister(IRegister.W_REGISTER + IRegister.STATUS, 0x7e) // clear RX_DR,TX_DS,MAX_RT flags
        println("init 8"); 
        writeRegister(
            IRegister.W_REGISTER + IRegister.CONFIG,
            0x7e
        ) // enable RX_DR irq, block TX_DS+MAX_RT, enable CRC powerup, in receive mode PTX
        println("init 9"); 
        flushTx()
        println("init 10"); 
        flushRx()
        println("init 11"); 
        Gpio.digitalWrite(LED, 1)
        println("init 12"); 
    }

    /**
     * write given byte data to SPI protocol, see https://en.wikipedia.org/wiki/SPI
     * @param spiData
     * @return 0 if succeed
     */
    private fun spiReadWrite(spiData: Int): Int {
        var spiData = spiData
        for (i in 0..7) {
            if (0x80 and spiData == 0x80) Gpio.digitalWrite(MOSI, 1) else Gpio.digitalWrite(MOSI, 0)
            spiData = spiData shl 1
            Gpio.digitalWrite(SCLK, 1)
            if (Gpio.digitalRead(MISO) == 1) spiData = spiData or 0x01
            Gpio.digitalWrite(SCLK, 0)
        }
        return spiData and 0xff
    }

    private fun writeRegister(regAddr: Int, writeData: Int): Int {
        Gpio.digitalWrite(CSN, 0)
        val `val` = spiReadWrite(regAddr)
        spiReadWrite(writeData)
        Gpio.digitalWrite(CSN, 1)
        return `val`
    }

    private fun readRegister(regAddr: Int): Int {
        Gpio.digitalWrite(CSN, 0)
        spiReadWrite(regAddr)
        val `val` = spiReadWrite(0x00)
        Gpio.digitalWrite(CSN, 1)
        return `val`
    }

    private fun writeBuffer(regAddr: Int, txData: IntArray, dataLen: Int): Int {
        Gpio.digitalWrite(CSN, 0)
        val `val` = spiReadWrite(regAddr)
        for (i in 0 until dataLen) {
            spiReadWrite(txData[i])
        }
        Gpio.digitalWrite(CSN, 1)
        return `val`
    }

    private fun readBuffer(regAddr: Int, rxData: IntArray, dataLen: Int): Int {
        Gpio.digitalWrite(CSN, 0)
        val `val` = spiReadWrite(regAddr)
        for (i in 0 until dataLen) {
            rxData[i] = spiReadWrite(0)
        }
        Gpio.digitalWrite(CSN, 1)
        return `val`
    }

    private fun flushRx() {
        Gpio.digitalWrite(CSN, 0)
        spiReadWrite(IRegister.FLUSH_RX.toInt())
        Gpio.digitalWrite(CSN, 1)
    }

    private fun flushTx() {
        Gpio.digitalWrite(CSN, 0)
        spiReadWrite(IRegister.FLUSH_TX.toInt())
        Gpio.digitalWrite(CSN, 1)
    }// RX FIFO is not null, data in FIFO// clear FIFO

    // read FIFO
    private val isDataAvaid: Boolean
        private get() {
            var status: Int
            if (Gpio.digitalRead(IRQ) == 0) {
                status = readRegister(IRegister.R_REGISTER + IRegister.STATUS)
                if (status and 0x40 == 0x40) {
                    // read FIFO
                    status = readRegister(IRegister.R_REGISTER + IRegister.FIFO_STATUS)
                    if (status and 0x01 == 0x01) {
                        writeRegister(IRegister.W_REGISTER + IRegister.STATUS, 0x40)
                        // clear FIFO
                    } else {
                        // RX FIFO is not null, data in FIFO
                        return true
                    }
                }
            }
            return false
        }

    private fun nrfGetOneDataPacket(): IntArray {
        val dataBuffer = IntArray(RECEIVE_DATA_WIDTH.toInt())
        readBuffer(IRegister.R_RX_PAYLOAD.toInt(), dataBuffer, RECEIVE_DATA_WIDTH.toInt())
        return dataBuffer
    }

    /**
     * method to start the NRF main loop, you must call start after you get this class as singleton.
     * duplicated calling of start will take no effect. there is one running flag to avoid duplicated start.
     *
     * start method will start IRQ watch thread and listener thread pool.
     */
    fun start() {
        if (running) {
            System.err.println("It is already started, call start do nothing")
            return
        }
        running = true
        irqWatchThread = Thread(this, "NRF24L01+ Daemon")
        irqWatchThread!!.priority = Thread.MAX_PRIORITY //in high priority
        irqWatchThread!!.isDaemon = true //daemon
        irqWatchThread!!.start()
        executorService = Executors.newCachedThreadPool()
    }

    /**
     * You must call shutdown explicitly when you shutdown your application. otherwise, hardware pins will
     * be blocked.
     *
     * shutdown will release all GPIO and unexport them.
     */
    fun shutdown() {
        running = false
        executorService!!.shutdown()
        Gpio.digitalWrite(LED, 0)
        Gpio.digitalWrite(CSN, 0)
        Gpio.digitalWrite(SCLK, 0)
        Gpio.digitalWrite(MOSI, 0)
        Gpio.digitalWrite(CE, 0)
        Gpio.digitalWrite(MISO, 0)
        Gpio.digitalWrite(IRQ, 0)
        Gpio.digitalWrite(LED, 0)
        GpioUtil.unexport(CSN)
        GpioUtil.unexport(SCLK)
        GpioUtil.unexport(MOSI)
        GpioUtil.unexport(CE)
        GpioUtil.unexport(MISO)
        GpioUtil.unexport(IRQ)
        GpioUtil.unexport(LED)
    }

    /**
     * send is public interface to end user, send method does not really send data out, it will push
     * data package to FIFO send queue,then waiting its period to send out.
     *
     * @param rfChannel according to RF data sheet, can be value 0-127
     * @param rfPower the power for send, it is enum data, can be 1, 2, 3
     * @param maxRetry max retry times, it can be integer value less then 9
     * @param addrWidth it is fixed to 5
     * @param txAddr, one byte array of 5 which it RF address
     * @param dataWidth your sending data width, i.e. txData width
     * @param txData the byte array to send
     */
    fun send(
        rfChannel: Int,
        rfPower: Int,
        maxRetry: Int,
        addrWidth: Int,
        txAddr: IntArray,
        dataWidth: Int,
        txData: IntArray
    ) {
        fifo.add(DataPackage(rfChannel, rfPower, maxRetry, addrWidth, txAddr, dataWidth, txData))
    }

    /**
     * run is the core main loop of RF, it is one infinitely loop with one running flag.
     * In loop body, it will check if any data valid, if valid, then will receive data and then
     * pass to listener callback.
     *
     * then loop will check sending block queue if any data, if any send data is queue, if will take one
     * and the send it out.
     *
     * at last step, it will switch back to listen mode
     */
    override fun run() {
        while (running) {
            /*receive*/
            while (isDataAvaid) {
                val data = nrfGetOneDataPacket()
                executorService!!.execute { listener.dataReceived(data) }
            }

            /*read FIFO for data send*/try {
                val pkg = fifo.poll(25, TimeUnit.MILLISECONDS) ?: continue
                val retry = nrfSendData(
                    pkg.chanel,
                    pkg.power,
                    pkg.maxRetry,
                    pkg.addrWidth,
                    pkg.txAddr,
                    pkg.dataWidth,
                    pkg.txData
                )
                pkg.sentTimestamp = System.currentTimeMillis()
                pkg.retry = retry
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } finally {
                setRxMode(localRFChanel, 5, localRFAddress)
            }
        }
    }

    /**
     * send data out
     * @param rfChannel
     * @param rfPower can be 1,2,3,4 enum  250Kbps+-18dBm, 250Kbps+-12dBm, 250Kbps+-6dBm, 250Kbps+0dBm
     * @param maxRetry how many software retry, it is NOT chipset retry!
     * @param addrWidth it is 5
     * @param txAddr 5 bytes of RF data address
     * @param dataWidth your data length
     * @param txData your data payload
     * @return result of send, >=253 for failed
     */
    private fun nrfSendData(
        rfChannel: Int,
        rfPower: Int,
        maxRetry: Int,
        addrWidth: Int,
        txAddr: IntArray,
        dataWidth: Int,
        txData: IntArray
    ): Int {
        var ret = 0
        var retryCnt = 0
        // check params
        if (rfChannel > 125 || rfPower == 0 || rfPower > 4 || maxRetry > 9 || addrWidth < 3 || addrWidth > 5 || dataWidth == 0 || dataWidth > 32) {
            return 253
        }
        Gpio.digitalWrite(CE, 0)
        writeRegister(IRegister.W_REGISTER + IRegister.SETUP_AW, addrWidth - 2)
        writeBuffer(IRegister.W_REGISTER + IRegister.TX_ADDR, txAddr, addrWidth)
        writeBuffer(IRegister.W_REGISTER + IRegister.RX_ADDR_P0, txAddr, addrWidth)
        if (rfPower == 1) {
            writeRegister(IRegister.W_REGISTER + IRegister.RF_SETUP, 0x21) // 250Kbps -18dBm
        } else if (rfPower == 2) {
            writeRegister(IRegister.W_REGISTER + IRegister.RF_SETUP, 0x23) // 250Kbps -12dBm
        } else if (rfPower == 3) {
            writeRegister(IRegister.W_REGISTER + IRegister.RF_SETUP, 0x25) // 250Kbps -6dBm
        } else if (rfPower == 4) {
            writeRegister(IRegister.W_REGISTER + IRegister.RF_SETUP, 0x27) // 250Kbps 0dBm
        }
        // (+22dBm with PA),
        // LNA?
        writeRegister(IRegister.W_REGISTER + IRegister.RF_CH, rfChannel)
        writeRegister(IRegister.W_REGISTER + IRegister.STATUS, 0x7f) // clear RX_DR,TX_DS,MAX_RT flags
        writeRegister(IRegister.W_REGISTER + IRegister.CONFIG, 0x4e) // block RX_DR irq
        // irq TX_DS,MAX_RT，CRC enable PTX power on
        retryCnt = 0
        while (retryCnt <= maxRetry) {
            writeBuffer(IRegister.W_TX_PAYLOAD.toInt(), txData, dataWidth) //write
            Gpio.digitalWrite(CE, 1)
            while (Gpio.digitalRead(IRQ) == 1) {
            }
            Gpio.digitalWrite(CE, 0) // must wait IRQ then set CE!
            ret = checkSendStatus()
            if (ret <= 15) {
                ret += 16 * retryCnt
                break
            }
            retryCnt++
        }
        return ret
    }

    private fun checkSendStatus(): Int {
        val status = readRegister(IRegister.R_REGISTER + IRegister.STATUS)
        //LOGGER.debug("status={0}", status);
        return if (status and 0x20 == 0x20) {
            writeRegister(IRegister.W_REGISTER + IRegister.STATUS, 0x7f)
            readRegister(IRegister.R_REGISTER + IRegister.OBSERVE_TX) and 0x0f
        } else if (status and 0x10 == 0x10) {
            writeRegister(IRegister.W_REGISTER + IRegister.STATUS, 0x7f)
            flushTx()
            255
        } else {
            252
        }
    }

    /**
     * set other chanel and address for this chip
     * @param chanel
     * @param addr
     */
    fun setLocalAddress(chanel: Int, addr: IntArray) {
        localRFChanel = chanel
        localRFAddress = addr
        setRxMode(localRFChanel, localRFAddress.size, localRFAddress)
    }

    /**
     * set your business data listener
     * @param l ReceiveListener which is callback function on data received
     */
    fun setReceiveListener(l: ReceiveListener) {
        listener = l
    }

    /**
     * remove ReceiveListener, class will use EmptyReceiveListener which will only print byte recevied to stdout
     */
    fun removeReceiveListener() {
        listener = EmptyReceiveListener()
    }

    /**
     * one default data listener, it is empty, just print out bytes in console
     */
    private inner class EmptyReceiveListener : ReceiveListener {
        private val df: DateFormat = SimpleDateFormat("hh:mm:ss - ")
        override fun dataReceived(data: IntArray) {
            print(df.format(Date()))
            for (i in data.indices) {
                print(data[i] and 0x00ff)
                print(", ")
            }
            println()
        }
    }

    /**
     * the data package structure. DataPackage holds the data bytes will send out including
     * its chanel, power, target address and etc. all also DataPackage will calculate the latency
     */
    inner class DataPackage(//send to chanel
        val chanel: Int, // power
        val power: Int, //maxtry
        val maxRetry: Int, val addrWidth: Int, val txAddr: IntArray, val dataWidth: Int, //payload
        val txData: IntArray
    ) {
        /**
         * @return　how many time of retry for sending data out.
         */
        var retry //result of times retry
                = 0
        val createTimestamp //time to create data package
                : Long
        var sentTimestamp //time send out
                : Long = 0

        /**
         * the latency
         * @return the latency the time escaped from data was queued to final send out
         */
        val letency: Int
            get() = (sentTimestamp - createTimestamp).toInt()

        init {
            createTimestamp = System.currentTimeMillis()
        }
    }

    /**
     * the default constructor will do steps as follows,
     *
     *  * provision CSN as output
     *  * provision SCLK as output
     *  * provision MOSI as output
     *  * provision CE as output
     *  * provision MISO as output, then pull up resistor buit-in
     *  * provision IRQ as INPUT, then pull up resistor buit-in
     *  * provision LED as output, then pull up resistor buit-in
     *  * do init and then go into listen mode
     *
     */
    init {

        Gpio.wiringPiSetup()
        /*CSN*/
        GpioUtil.export(CSN, GpioUtil.DIRECTION_OUT)
        Gpio.pinMode(CSN, Gpio.OUTPUT)
        println("init x 3"); 

        /*SCLK*/GpioUtil.export(SCLK, GpioUtil.DIRECTION_OUT)
        Gpio.pinMode(SCLK, Gpio.OUTPUT)
        println("init x 4");

        /*mosi*/GpioUtil.export(MOSI, GpioUtil.DIRECTION_OUT)
        Gpio.pinMode(MOSI, Gpio.OUTPUT)

        println("init x 5");
        /*ce*/GpioUtil.export(CE, GpioUtil.DIRECTION_OUT)
        Gpio.pinMode(CE, Gpio.OUTPUT)

        println("init x 6");
        /*miso*/GpioUtil.export(MISO, GpioUtil.DIRECTION_IN)
        Gpio.pinMode(MISO, Gpio.OUTPUT)
        Gpio.pullUpDnControl(MISO, Gpio.PUD_UP)
        println("init x 7");

        /*irq*/GpioUtil.export(IRQ, GpioUtil.DIRECTION_IN)
        Gpio.pinMode(MISO, Gpio.INPUT)
        Gpio.pullUpDnControl(IRQ, Gpio.PUD_UP)
        println("init x 8");

//        /LEDED light*/GpioUtil.export(LED, GpioUtil.DIRECTION_OUT)
//        Gpio.pinMode(LED, Gpio.OUTPUT)
//        Gpio.pullUpDnControl(LED, Gpio.PUD_UP)
//        println("init x 9");

        init()
        println("init x 10");
        setRxMode(localRFChanel, 5, localRFAddress)
        println("init x 11");
    }
}