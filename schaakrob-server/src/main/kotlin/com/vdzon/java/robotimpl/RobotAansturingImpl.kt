package com.vdzon.java.robotimpl

import com.pi4j.component.lcd.impl.I2CLcdDisplay
import com.pi4j.io.gpio.GpioFactory
import com.pi4j.io.gpio.PinPullResistance
import com.pi4j.io.gpio.RaspiPin
import com.pi4j.io.gpio.event.GpioPinListenerDigital
import com.pi4j.io.i2c.I2CBus
import com.pi4j.io.i2c.I2CDevice
import com.pi4j.io.i2c.I2CFactory
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException
import com.vdzon.java.BerekenVersnelling
import com.vdzon.java.Lock
import com.vdzon.java.robitapi.RobotAansturing
import com.vdzon.java.schaakspel.Schaakspel
import de.pi3g.pi.oled.Font
import de.pi3g.pi.oled.OLEDDisplay
import org.slf4j.LoggerFactory
import java.io.IOException
import java.io.PrintWriter
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.UnknownHostException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.function.Consumer
import kotlin.concurrent.thread


private val log = LoggerFactory.getLogger(RobotAansturingImpl::class.java)

class RobotAansturingImpl() : RobotAansturing {

    private var schaakspel: Schaakspel? = null // todo: deze injecteren

    var lastPos1 = 0
    var lastPos2 = 0
    var formattedDelayFactor1 = "0050"
    var formattedDelayFactor2 = "0050"
    private var allReady = false
    private var allSleeping = false
    private var arm1: I2CDevice? = null
    private var arm2: I2CDevice? = null
    private var lastMovement = 0L
    private var arm1AtHome = false
    private var arm2AtHome = false
    private var currentLoopThread: Thread? = null

    // BLE Pakker settings
    private val bleServiceUuid = "6E400001-B5A3-F393-E0A9-E50E24DCCA9E"
    private val bleRxCharUuid = "6E400002-B5A3-F393-E0A9-E50E24DCCA9E" // write characteristic (for reference)
    private val bleDeviceName = "NanoESP32-Pakker"
    private val bleHandle = "0x000b" // GATT handle used by gatttool for RX characteristic
    private val bleMacAddress: String? by lazy { resolveBleMac() }

    private fun resolveBleMac(): String? {
        return "E4:B0:63:AD:D2:AD"
    }

    private fun bleEncodeHex(text: String): String {
        return text.toByteArray(Charsets.UTF_8).joinToString("") { String.format("%02x", (it.toInt() and 0xFF)) }
    }


    private val dbusClient: BluezBleClient by lazy { BluezBleClient() }

    private fun bleWrite(command: String): Boolean {
        val mac = bleMacAddress
        if (mac.isNullOrBlank()) {
            log.warn("BLE MAC for Pakker not configured.")
            return false
        }
        // Use BlueZ D-Bus API only
        return try {
            log.info("Write using BlueZ D-Bus: '$command'")
            val ok = dbusClient.write(mac, command.toByteArray(Charsets.UTF_8))
            if (!ok) {
                log.warn("BlueZ D-Bus write failed for '$command'")
            }
            ok
        } catch (e: Exception) {
            log.error("BlueZ D-Bus exception for '$command': ${e.message}", e)
            false
        }
    }
//
//    private fun bleWriteGatttool(command: String): Boolean {
//        val mac = bleMacAddress
//        if (mac.isNullOrBlank()) return false
//        return try {
//            val hex = bleEncodeHex(command)
//            val shellCmd = "gatttool -b $mac --char-write-req -a $bleHandle -n $hex"
//            val pb = ProcessBuilder("bash", "-lc", shellCmd)
//            pb.redirectErrorStream(true)
//            val proc = pb.start()
//            val output = StringBuilder()
//            val reader = BufferedReader(InputStreamReader(proc.inputStream))
//            val readerThread = Thread {
//                try {
//                    var line: String?
//                    while (reader.readLine().also { line = it } != null) {
//                        output.appendLine(line)
//                    }
//                } catch (_: Exception) {
//                } finally {
//                    try { reader.close() } catch (_: Exception) {}
//                }
//            }
//            readerThread.start()
//            val exit = proc.waitFor()
//            readerThread.join(500)
//            val out = output.toString().trim()
//            if (exit != 0) {
//                log.error("gatttool exited with code $exit while sending '$command' to $mac (handle $bleHandle). Output=[$out]")
//            } else {
//                if (out.contains("failed", ignoreCase = true) || out.contains("error", ignoreCase = true)) {
//                    log.warn("gatttool reported potential issue while sending '$command': $out")
//                } else if (out.isNotEmpty()) {
//                    log.debug("gatttool output for '$command': $out")
//                }
//            }
//            exit == 0
//        } catch (e: Exception) {
//            log.error("Failed to send BLE command '$command' via gatttool: ${e.message}", e)
//            false
//        }
//    }

    private fun bleConnectAndNotify() {
        val mac = bleMacAddress
        if (mac.isNullOrBlank()) {
            log.info("BLE MAC not set; skipping initial BLE connect to Pakker")
            return
        }
        try {
            // Stuur een 'connected' bericht via BlueZ D-Bus BLE client
            bleWrite("connected")
        } catch (e: Exception) {
            log.warn("Initial BLE notify failed: ${e.message}")
        }
    }

    fun init() {
        if (arm1 != null) {
            return
        }

        var initialized = false
        while (!initialized ) {
            val display = Display(this)
            display.startDisplay()

            try {
                log.info("Open devices")
                val i2c = I2CFactory.getInstance(I2CBus.BUS_1)
                arm1 = i2c.getDevice(ARM1)
                arm2 = i2c.getDevice(ARM2)
                // test connectoe
                arm1!!.readI2c("arm1")
                arm2!!.readI2c("arm2")



                initialized = true
            } catch (e: UnsupportedBusNumberException) {
                log.info("ERROR, UnsupportedBusNumberException in init")
                Thread.sleep(2000)
            } catch (e: IOException) {
                log.info("ERROR IOException in init:" + e.message)
                Thread.sleep(2000)
            }
        }

        log.info("Devices found")

        // Try to connect to BLE Pakker at startup and send 'connected'
        thread { bleConnectAndNotify() }

        thread {
            println("start check sleep thread")
            while (true){
//                val res = arm1!!.readI2c("arm1")
//                println("arm1 state="+res)
//                Thread.sleep(1000)
                Thread.sleep(3000)
                val timeout = System.currentTimeMillis() - 1000*20 // 20 seconds
                val bothHome = bothArmsAtHome()
                val hasTimeout = lastMovement<timeout
                if (bothHome && hasTimeout){
                    println("automatically sleep!")
                    sleep()
                }
            }
        }




    }

    fun setSchaakspel(schaakspel: Schaakspel){
        this.schaakspel = schaakspel
    }

    fun bothArmsAtHome(): Boolean = arm1AtHome && arm2AtHome

    fun updateLastMovement(){
        lastMovement = System.currentTimeMillis()
    }

    override fun movetoVlak(vlak: String, arm: Int) {
        updateLastMovement()
        log.info("start: move to "+vlak)
        // check if homeing is needed
        if (homeingNeeded()){
            homeHor()
            homeVert()
            waitUntilReady(100)
        }


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
        if (arm==1) y = y+(getPakkerHoogte()?.toIntOrNull()?:0)

        moveto(y, x)
        log.info("done: move to "+vlak)

    }

    override fun moveto(x: Int, y: Int) {
        updateLastMovement()
        calcDelays(x, y)
        gotoPos(arm1, x, formattedDelayFactor1)
        gotoPos(arm2, y, formattedDelayFactor2)
        waitUntilReady(100)
    }

    override fun homeVert() {
        updateLastMovement()
        home(arm1)
        arm2AtHome = true
    }

    override fun homeHor() {
        updateLastMovement()
        home(arm2)
        arm1AtHome = true
    }

    override fun sleep() {
        arm1AtHome = false
        arm2AtHome = false
        log.info("sleeping")
        try {
            val snelheid: Double = getSnelheid()?.toDoubleOrNull()?:2.0
            val delay = 100*snelheid;
            val formattedDelayFactor = String.format("%04d", delay.toInt())
            arm1!!.writeI2c(("^X000000"+formattedDelayFactor).toByteArray(),"arm1")
            arm2!!.writeI2c(("^X000000"+formattedDelayFactor).toByteArray(),"arm2")
        } catch (e: IOException) {
            e.printStackTrace()
        }
        waitUntilSleeping(50)
        lastPos2 = 0
        lastPos1 = 0
    }

    fun gotoPos(arm: I2CDevice?, pos: Int, vertraging: String) {
        arm1AtHome == false
        arm2AtHome == false
        try {
            val formattedPos = String.format("%06d", pos)
            val command = "^M$formattedPos$vertraging"
            arm?.writeI2c(command.toByteArray(), armName(arm))
            if (arm === arm1) {
                lastPos1 = pos
            }
            if (arm === arm2) {
                lastPos2 = pos
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun clamp1() {
        log.info("BLE: pak1")
        bleWrite("pak1")
        Thread.sleep(4000)// voor nu: wacht 2 seconden, moet later via een callback
        log.info("pak1 klaar")
    }

    override fun release1() {
        log.info("BLE: zet1")
        bleWrite("zet1")
        Thread.sleep(4000)// voor nu: wacht 2 seconden, moet later via een callback
        log.info("zet1 klaar")
    }

    override fun clamp2() {
        log.info("BLE: pak2")
        bleWrite("pak2")
        Thread.sleep(4000)// voor nu: wacht 2 seconden, moet later via een callback
        log.info("pak2 klaar")
    }

    override fun release2() {
        log.info("BLE: zet2")
        bleWrite("zet2")
        Thread.sleep(4000)// voor nu: wacht 2 seconden, moet later via een callback
        log.info("zet2 klaar")
    }


    override fun bootsound() {
        log.info("bootsound")
        arm1!!.writeI2c("^B0000000000000000".toByteArray(),"arm1")
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

    override fun getPakkerHoogte(): String? {
        return loadFile("/home/pi/pakkerhoogte.data")
    }

    override fun setPakkerHoogte(pakkerhoogte: String) {
        saveToFile("/home/pi/pakkerhoogte.data", pakkerhoogte)
    }

    override fun getSnelheid(): String? {
        return loadFile("/home/pi/snelheid.data")
    }

    override fun setSnelheid(snelheid: String) {
        saveToFile("/home/pi/snelheid.data", snelheid)
    }

//    override fun getDelayNaPak(): String? {
//        return loadFile("/home/pi/pakdelay.data")
//    }
//
//    override fun setDelayNaPak(delay: String) {
//        saveToFile("/home/pi/pakdelay.data", delay)
//    }
//
//    override fun getDelayNaZet(): String? {
//        return loadFile("/home/pi/zetdelay.data")
//    }
//
//    override fun setDelayNaZet(delay: String) {
//        saveToFile("/home/pi/zetdelay.data", delay)
//    }

    override fun getDemoString(): String? {
        return loadFile("/home/pi/loop.data")
    }

    override fun setDemoString(demoString: String) {
        saveToFile("/home/pi/loop.data", demoString)
    }

    override fun runDemoOnce() {
        schaakspel?.startAutoPlay()
    }

    override fun runDemoLoop() {
        startLoop(getDemoString())
    }

    override fun stopDemo() {
        schaakspel?.stopAutoplay()
    }

    override fun resetBoard() {
        schaakspel?.reset()
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
        log.info("Start display thread")
        var lcd:I2CLcdDisplay? = null
        while (lcd==null ) {
            try{
                lcd = I2CLcdDisplay(2, 16,I2CBus.BUS_1, 0x38, 3, 0, 1, 2, 7, 6, 5, 4)
                lcd.setCursorHome()
                lcd.clear();
            } catch (e: Exception) {
                log.info("Error loading display:"+e.message)
                Thread.sleep(3000)
            }
        }
        log.info("Display found")

        lcd.setCursorHome()
        lcd.clear();
        Thread.sleep(1000);
        val inetAddress = localHostLANAddress()
        val ipAdress = inetAddress.hostAddress
        lcd.write(0, ipAdress)

    }

    private fun home(arm: I2CDevice?) {
        try {
            arm?.writeI2c("^H0000000000600000".toByteArray(), armName(arm))
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

    private fun armName(arm: I2CDevice?)=
        when (arm){
            arm1 -> "arm1"
            arm2 -> "arm2"
            else -> "?"
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
            val arm1Status = arm1!!.readI2c("arm1")
            val arm2Status = arm2!!.readI2c("arm2")
            allReady = arm1Status == 1 && arm2Status == 1
            allSleeping = arm1Status == 6 && arm2Status == 6
        } catch (e: Exception) {
            e.printStackTrace()
            allReady = false
            allSleeping = false
        }
    }

    private fun stopLoop() {
        if (currentLoopThread != null) {
            currentLoopThread=null
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
                    if (row != null && !row.startsWith("#") && currentLoopThread!=null) {// check currentLoopThread: als die null is, dan is gevraagd op de demo te stoppen
                        if (row.trim { it <= ' ' }.startsWith("@")) {
                            val regel = row.trim().replace("@","")
                            val (vlak, armStr) = regel.split(" ")
                            val arm = armStr.toInt()
                            movetoVlak(vlak, arm)
                        } else if (row.trim { it <= ' ' }.startsWith("pak 0")) {
                            clamp1()
                        } else if (row.trim { it <= ' ' }.startsWith("pak 1")) {
                            clamp2()
                        } else if (row.trim { it <= ' ' }.startsWith("zet 0")) {
                            release1()
                        } else if (row.trim { it <= ' ' }.startsWith("zet 1")) {
                            release2()
                        } else if (row.trim { it <= ' ' }.startsWith("sleep")) {
                            sleep()
                        } else if (row.trim { it <= ' ' }.startsWith("home")) {
                            homeHor()
                            homeVert()
                            waitUntilReady(100)
                        }
                    }
                }
        )
    }

    private fun homeingNeeded(): Boolean{
        val arm1Status = arm1!!.readI2c("arm1")
        val arm2Status = arm2!!.readI2c("arm2")
        val arm1SleepingOrHomingNeeded = arm1Status == 9 || arm1Status == 6
        val arm2SleepingOrHomingNeeded = arm2Status == 9 || arm2Status == 6
        val res = arm1SleepingOrHomingNeeded || arm2SleepingOrHomingNeeded
        println("Homing needed: $arm1Status / $arm2Status : $res")
        return res
    }


    private fun waitUntilSleeping(initialDelay: Int) {
        sleep(initialDelay)
        udateStatus()
        println("Wait until ready")
        while (!allSleeping) {
            sleep(10)
            udateStatus()
        }
        println("All ready")
    }

    private fun waitUntilReady(initialDelay: Int) {
        sleep(initialDelay)
        udateStatus()
        println("Wait until ready")
        while (!allReady) {
            sleep(10)
            udateStatus()
        }
        println("All ready")
    }

    private fun sleep(initialDelay: Int) {
        try {
            Thread.sleep(initialDelay.toLong())
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val ARM1 = 0x6
        private const val ARM2 = 0x7
    }

    init {
        try {
            init()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

private fun I2CDevice.readI2c(devicename: String): Int {
    var result:Int = -1;
    Lock.lock()
    var succeeded = false
    var tryCount = 0
    while (!succeeded && tryCount<20) {
        try {
            result = read()
            if (result!=0) {
//                log.info("status of 0 not accepted for "+devicename+", retry read status")
                succeeded = true
            }
        }
        catch (e:Exception){
            tryCount++;
            log.info("read failed $tryCount times for $devicename: ${e.message}")
            sleep(100)
        }
    }
    Lock.unlock()
    return result

}

private fun I2CDevice.writeI2c(toByteArray: ByteArray, devicename: String) {
    Lock.lock()
    var succeeded = false
    var tryCount = 0
    while (!succeeded && tryCount<20) {
        try {
            write(toByteArray)
            succeeded = true
        }
        catch (e:Exception){
            tryCount++;
            log.info("write failed $tryCount times for $devicename: ${e.message}")
            sleep(100)
        }
    }
    Lock.unlock()
}

private fun sleep(time:Long){
    try{
        Thread.sleep(time)
    }
    catch (e:Exception){

    }

}
