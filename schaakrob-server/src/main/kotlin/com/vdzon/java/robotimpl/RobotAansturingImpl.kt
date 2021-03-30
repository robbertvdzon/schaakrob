package com.vdzon.java.robotimpl

import com.pi4j.component.lcd.impl.I2CLcdDisplay
import com.pi4j.io.i2c.I2CBus
import com.pi4j.io.i2c.I2CDevice
import com.pi4j.io.i2c.I2CFactory
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException
import com.vdzon.java.BerekenVersnelling
import com.vdzon.java.Lock
import com.vdzon.java.robitapi.RobotAansturing
import java.io.IOException
import java.io.PrintWriter
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.UnknownHostException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.function.Consumer


class RobotAansturingImpl : RobotAansturing {
    var lastPos1 = 0
    var lastPos2 = 0
    var formattedDelayFactor1 = "0050"
    var formattedDelayFactor2 = "0050"
    private var allReady = false
    private var arm1: I2CDevice? = null
    private var arm2: I2CDevice? = null
    private var arm3: I2CDevice? = null
//    private var display: I2CDevice? = null
    private var currentLoopThread: Thread? = null
    fun init() {
        if (arm1 != null) {
            return
        }

        var initialized = false
        while (!initialized ) {
            try {
                println("Open devices")
                val i2c = I2CFactory.getInstance(I2CBus.BUS_1)
                arm1 = i2c.getDevice(ARM1)
                arm2 = i2c.getDevice(ARM2)
                arm3 = i2c.getDevice(ARM3)
                // test connectoe
                Lock.lock()
                arm1!!.read()
                arm2!!.read()
                arm3!!.read()
                Lock.unlock()
                initialized = true
            } catch (e: UnsupportedBusNumberException) {
                println("ERROR, UnsupportedBusNumberException in init")
                Thread.sleep(2000)
            } catch (e: IOException) {
                println("ERROR IOException in init:" + e.message)
                Thread.sleep(2000)
            }
        }

        println("Devices found")
        val updateDisplayThread = Thread(Runnable { startDisplayThread() })
        updateDisplayThread.start()


    }

    override fun movetoVlak(vlak: String) {
        val posA8 = getA8()
        val posA11 = getA11()
        val posA21 = getA21()
        val posH1 = getH1()
        val posH10 = getH10()
        val posH20 = getH20()

        // calc delta van main vlakken
        val xa = posA8!!.split(",".toRegex()).toTypedArray()[1].toInt()
        val xh = posH1!!.split(",".toRegex()).toTypedArray()[1].toInt()
        val y8 = posA8!!.split(",".toRegex()).toTypedArray()[0].toInt()
        val y1 = posH1!!.split(",".toRegex()).toTypedArray()[0].toInt()
        val xDelta = (xa - xh) / 7
        val yDelta = (y8 - y1) / 7

        // calc delta van bovenste 2 rijen
        val qy21 = posA21!!.split(",".toRegex()).toTypedArray()[0].toInt()
        val qy20 = posH20!!.split(",".toRegex()).toTypedArray()[0].toInt()
//
        // calc delta van onderste 2 rijen
        val ry11 = posA11!!.split(",".toRegex()).toTypedArray()[0].toInt()
        val ry10 = posH10!!.split(",".toRegex()).toTypedArray()[0].toInt()

        val letter = vlak.toUpperCase()[0]
        val cijfer = vlak.substring(1)
        var x = xa
        if (letter == 'A') x = xa
        if (letter == 'B') x = xa - xDelta * 1
        if (letter == 'C') x = xa - xDelta * 2
        if (letter == 'D') x = xa - xDelta * 3
        if (letter == 'E') x = xa - xDelta * 4
        if (letter == 'F') x = xa - xDelta * 5
        if (letter == 'G') x = xa - xDelta * 6
        if (letter == 'H') x = xh
        var y = y8
        if (cijfer == "21") y = qy21
        if (cijfer == "20") y = qy20
        if (cijfer == "11") y = ry11
        if (cijfer == "10") y = ry10
        if (cijfer == "8") y = y8
        if (cijfer == "7") y = y8 - yDelta * 1
        if (cijfer == "6") y = y8 - yDelta * 2
        if (cijfer == "5") y = y8 - yDelta * 3
        if (cijfer == "4") y = y8 - yDelta * 4
        if (cijfer == "3") y = y8 - yDelta * 5
        if (cijfer == "2") y = y8 - yDelta * 6
        if (cijfer == "1") y = y1
        if (y > 21000) y = 21000
        if (y < 0) y = 0
        if (x > 13500) x = 13500
        if (x < 0) x = 0
        moveto(y, x)
    }

    override fun moveto(x: Int, y: Int) {
        calcDelays(x, y)
        gotoPos(arm1, x, formattedDelayFactor1)
        gotoPos(arm2, y, formattedDelayFactor2)
        waitUntilReady(20)
    }

    override fun homeVert() {
        home(arm1)
    }

    override fun homeHor() {
        home(arm2)
    }

    override fun sleep() {
        println("sleeping")
//        moveto(100, 100)
        Lock.lock()
        try {
            val snelheid: Double = getSnelheid()?.toDoubleOrNull()?:2.0
            val delay = 100*snelheid;
            val formattedDelayFactor = String.format("%04d", delay.toInt())
            arm1!!.write(("^X000000"+formattedDelayFactor).toByteArray())
            arm2!!.write(("^X000000"+formattedDelayFactor).toByteArray())
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Lock.unlock()
        waitUntilReady(50)
        lastPos2 = 0
        lastPos1 = 0
    }

    fun gotoPos(arm: I2CDevice?, pos: Int, vertraging: String) {
        Lock.lock()
        try {
            val formattedPos = String.format("%06d", pos)
            val command = "^M$formattedPos$vertraging"
            arm?.write(command.toByteArray())
            if (arm === arm1) {
                lastPos1 = pos
            }
            if (arm === arm2) {
                lastPos2 = pos
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Lock.unlock()
    }

    override fun clamp() {
        Lock.lock()
        try {
            arm3!!.write("^C0000000000000000".toByteArray())
//            Thread.sleep(400)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Lock.unlock()
    }

    override fun release() {
        Lock.lock()
        try {
            arm3!!.write("^R0000000000000000".toByteArray())
//            Thread.sleep(400)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Lock.unlock()
    }

    override fun hold() {
        Lock.lock()
        try {
            arm3!!.write("^H0000000000000000".toByteArray())
//            Thread.sleep(400)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Lock.unlock()
    }

    override fun drop() {
        Lock.lock()
        try {
            arm3!!.write("^D0000000000000000".toByteArray())
//            Thread.sleep(400)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Lock.unlock()
    }

    override fun activate() {
        Lock.lock()
        try {
            arm3!!.write("^A0000000000000000".toByteArray())
//            Thread.sleep(400)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Lock.unlock()
    }

    override fun deactivate() {
        Lock.lock()
        try {
            arm3!!.write("^I0000000000000000".toByteArray())
//            Thread.sleep(400)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Lock.unlock()
    }


    override fun rebuild() {
        try {
            val writer = PrintWriter("/tmp/rebuildui", "UTF-8")
            writer.close()
            System.exit(0)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun restart() {
        System.exit(0)
    }

    override fun getA8(): String? {
        return loadFile("/home/pi/a8.data")
    }

    override fun getA11(): String? {
        return loadFile("/home/pi/a11.data")
    }

    override fun getA21(): String? {
        return loadFile("/home/pi/a21.data")
    }

    override fun setA8(pos: String) {
        saveToFile("/home/pi/a8.data", pos)
    }

    override fun setA11(pos: String) {
        saveToFile("/home/pi/a11.data", pos)
    }

    override fun setA21(pos: String) {
        saveToFile("/home/pi/a21.data", pos)
    }

    override fun getH1(): String? {
        return loadFile("/home/pi/h1.data")
    }

    override fun getH10(): String? {
        return loadFile("/home/pi/h10.data")
    }

    override fun getH20(): String? {
        return loadFile("/home/pi/h20.data")
    }

    override fun setH1(pos: String) {
        saveToFile("/home/pi/h1.data", pos)
    }

    override fun setH10(pos: String) {
        saveToFile("/home/pi/h10.data", pos)
    }

    override fun setH20(pos: String) {
        saveToFile("/home/pi/h20.data", pos)
    }

    override fun getSnelheid(): String? {
        return loadFile("/home/pi/snelheid.data")
    }

    override fun setSnelheid(snelheid: String) {
        saveToFile("/home/pi/snelheid.data", snelheid)
    }

    override fun getDemoString(): String? {
        return loadFile("/home/pi/loop.data")
    }

    override fun setDemoString(demoString: String) {
        saveToFile("/home/pi/loop.data", demoString)
    }

    override fun runDemoOnce() {
        runOnceInThread(getDemoString())
    }

    override fun runDemoLoop() {
        startLoop(getDemoString())
    }

    override fun stopDemo() {
        stopLoop()
    }

    private fun getStatusString(status: Int): String {
        if (status == 0) return "HN"
        if (status == 1) return "RE"
        if (status == 2) return "MO"
        if (status == 3) return "HO"
        if (status == 4) return "ER"
        if (status == 5) return "GS"
        return if (status == 6) "SL" else "??"
    }

    private fun getArm3StatusString(status: Int): String {
        if (status == 1) return "RE"
        if (status == 2) return "GR"
        return if (status == 3) "RE" else "??"
    }

    fun localHostLANAddress(): InetAddress {
        try {
            var candidateAddress: InetAddress? = null
            // Iterate all NICs (network interface cards)...
            val ifaces: Enumeration<*> = NetworkInterface.getNetworkInterfaces()
            while (ifaces.hasMoreElements()) {
                val iface = ifaces.nextElement() as NetworkInterface
                // Iterate all IP addresses assigned to each card...
                val inetAddrs: Enumeration<*> = iface.inetAddresses
                while (inetAddrs.hasMoreElements()) {
                    val inetAddr = inetAddrs.nextElement() as InetAddress
                    if (!inetAddr.isLoopbackAddress) {
                        if (inetAddr.isSiteLocalAddress) {
                            // Found non-loopback site-local address. Return it immediately...
                            return inetAddr
                        } else if (candidateAddress == null) {
                            // Found non-loopback address, but not necessarily site-local.
                            // Store it as a candidate to be returned if site-local address is not subsequently found...
                            candidateAddress = inetAddr
                            // Note that we don't repeatedly assign non-loopback non-site-local addresses as candidates,
                            // only the first. For subsequent iterations, candidate will be non-null.
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                // We did not find a site-local address, but we found some other non-loopback address.
                // Server might have a non-site-local address assigned to its NIC (or it might be running
                // IPv6 which deprecates the "site-local" concept).
                // Return this non-loopback candidate address...
                return candidateAddress
            }
            // At this point, we did not find a non-loopback address.
            // Fall back to returning whatever InetAddress.getLocalHost() returns...
            val jdkSuppliedAddress = InetAddress.getLocalHost()
                    ?: throw UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.")
            return jdkSuppliedAddress
        } catch (e: Exception) {
            val unknownHostException = UnknownHostException("Failed to determine LAN address: $e")
            unknownHostException.initCause(e)
            throw unknownHostException
        }
    }

    override fun startDisplayThread() {
        println("Start display thread")
        var lcd:I2CLcdDisplay? = null
        while (lcd==null ) {
            try{
                lcd = I2CLcdDisplay(2, 16,I2CBus.BUS_1, 0x38, 3, 0, 1, 2, 7, 6, 5, 4)
                lcd.setCursorHome()
                lcd.clear();
            } catch (e: Exception) {
                println("Error loading display:"+e.message)
                Thread.sleep(3000)
            }
        }
        println("Display found")

        lcd.setCursorHome()
        lcd.clear();
        Thread.sleep(1000);
        val inetAddress = localHostLANAddress()
        val ipAdress = inetAddress.hostAddress
        lcd.write(0, ipAdress)
        var zandloperChar = "*"
        while (true) {
            try {
                zandloperChar = if (zandloperChar=="*") "o" else "*"
                Lock.lock()
                val arm1Status = arm1!!.read()
                val arm2Status = arm2!!.read()
                val arm3Status = arm3!!.read()
                Lock.unlock()
                allReady = arm1Status == 1 && arm2Status == 1 && arm3Status != 2 // arm3 : alleen checken dat hij niet aan het moven is
                val status = zandloperChar+" "+getStatusString(arm1Status) + "/" + getStatusString(arm2Status) + "/" + getArm3StatusString(arm3Status)
                Lock.lock()
                lcd.write(0, ipAdress)
                lcd.write(1, status)
                Lock.unlock()
                Thread.sleep(300)
            } catch (e: Exception) {
                println("Error updating display:"+e.message)
//                e.printStackTrace()
            }
        }
    }

    private fun home(arm: I2CDevice?) {
        try {
            Lock.lock()
            arm?.write("^H0000000000600000".toByteArray())
            Lock.unlock()
            if (arm === arm1) {
                lastPos1 = 0
            }
            if (arm === arm2) {
                lastPos2 = 0
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun calcDelays(pos1: Int, pos2: Int): Long {
        val pulses1 = Math.abs(pos1 - lastPos1)
        val pulses2 = Math.abs(pos2 - lastPos2)
        val delays = BerekenVersnelling.calcDelays(pulses1, pulses2)
        var delayFactor1: Double = if (pulses1 == 0) 1.0 else delays!!.delay1
        var delayFactor2: Double = if (pulses2 == 0) 1.0 else delays!!.delay2
        if (delayFactor1 > 9999) delayFactor1 = 9999.0
        if (delayFactor2 > 9999) delayFactor2 = 9999.0

        // speedup 2x
        val snelheid: Double = getSnelheid()?.toDoubleOrNull()?:2.0

        delayFactor1 = delayFactor1 * snelheid
        delayFactor2 = delayFactor2 * snelheid
        formattedDelayFactor1 = String.format("%04d", delayFactor1.toInt())
        formattedDelayFactor2 = String.format("%04d", delayFactor2.toInt())
        return delays!!.totalTime
    }

    private fun saveToFile(filename: String, text: String) {
        val path = Paths.get(filename)
        val strToBytes = text.toByteArray()
        try {
            Files.write(path, strToBytes)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun loadFile(filename: String): String {
        try {
            return String(Files.readAllBytes(Paths.get(filename)))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return "1000,1000"
    }

    private fun udateStatus() {
        try {
            Lock.lock()
            val arm1Status = arm1!!.read()
            val arm2Status = arm2!!.read()
            val arm3Status = arm3!!.read()
            Lock.unlock()
            allReady = arm1Status == 1 && arm2Status == 1 && arm3Status != 2 // arm3 : alleen checken dat hij niet aan het moven is
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun stopLoop() {
        if (currentLoopThread != null) {
            currentLoopThread!!.stop()
        }
    }

    private fun startLoop(text: String?) {
        currentLoopThread = Thread(Runnable { runInLoop(text) })
        currentLoopThread!!.start()
    }

    private fun runOnceInThread(text: String?) {
        currentLoopThread = Thread(Runnable { runOnce(text) })
        currentLoopThread!!.start()
    }

    private fun runInLoop(text: String?) {
        while (true) {
            runOnce(text)
        }
    }

    private fun runOnce(text: String?) {
        val split = text!!.split("#".toRegex()).toTypedArray()
        Arrays.asList(*split).forEach(
                Consumer { row: String? ->
                    if (row != null && !row.startsWith("#")) {
                        if (row.trim { it <= ' ' }.startsWith("@")) {
                            val vlak = row.trim().replace("@","")
                            movetoVlak(vlak)
                        }
                        if (row.trim { it <= ' ' }.startsWith("pak")) {
                            clamp()
                        } else if (row.trim { it <= ' ' }.startsWith("zet")) {
                            release()
                        } else if (row.trim { it <= ' ' }.startsWith("sleep")) {
                            sleep()
                        } else if (row.trim { it <= ' ' }.startsWith("home")) {
                            homeHor()
                            homeVert()
                            waitUntilReady(100)
                        } else {
                            val splitWords = row.split(",".toRegex()).toTypedArray()
                            if (splitWords.size >= 3) {
                                val posArm1 = splitWords[0].trim { it <= ' ' }
                                val posArm2 = splitWords[1].trim { it <= ' ' }
                                try {
                                    val pos1 = posArm1.toInt()
                                    val pos2 = posArm2.toInt()
                                    calcDelays(pos1, pos2)
                                    gotoPos(arm1, pos1, formattedDelayFactor1)
                                    gotoPos(arm2, pos2, formattedDelayFactor2)
                                } catch (ex: Exception) {
                                    ex.printStackTrace()
                                }
                            }
                        }
                    }
                }
        )
    }


    private fun waitUntilReady(initialDelay: Int) {
        sleep(initialDelay)
        udateStatus()
        while (!allReady) {
            sleep(10)
            udateStatus()
        }
//        sleep(200)// extra sleep, deze zou weg moeten kunnen
    }

    private fun sleep(initialDelay: Int) {
        try {
            Thread.sleep(initialDelay.toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val ARM1 = 0x8
        private const val ARM2 = 0x6
        private const val ARM3 = 0x5
        private const val DISPLAY = 0x38
    }

    init {
        try {
            init()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
