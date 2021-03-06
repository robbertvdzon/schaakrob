package com.vdzon.java.robotimpl

import com.pi4j.io.i2c.I2CBus
import com.pi4j.io.i2c.I2CDevice
import com.pi4j.io.i2c.I2CFactory
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException
import com.vdzon.java.BerekenVersnelling
import com.vdzon.java.robitapi.RobotAansturing
import java.io.IOException
import java.io.PrintWriter
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
        try {
            val i2c = I2CFactory.getInstance(I2CBus.BUS_1)
            arm1 = i2c.getDevice(ARM1)
            arm2 = i2c.getDevice(ARM2)
            arm3 = i2c.getDevice(ARM3)
//            display = i2c.getDevice(DISPLAY)
        } catch (e: UnsupportedBusNumberException) {
            println("ERROR, UnsupportedBusNumberException in init")
        } catch (e: IOException) {
            println("ERROR IOException in init:" + e.message)
        }

        val updateDisplayThread = Thread(Runnable { startDisplayThread() })
        updateDisplayThread.start()


    }

    override fun movetoVlak(vlak: String) {
        println("move to vlak $vlak")
        val posA8 = getA8()
        val posH1 = getH1()
        val xa = posA8!!.split(",".toRegex()).toTypedArray()[1].toInt()
        val xh = posH1!!.split(",".toRegex()).toTypedArray()[1].toInt()
        val y8 = posA8!!.split(",".toRegex()).toTypedArray()[0].toInt()
        val y1 = posH1!!.split(",".toRegex()).toTypedArray()[0].toInt()
        val xDelta = (xa - xh) / 7
        val yDelta = (y8 - y1) / 7
        println("xDelta=$xDelta")
        println("yDelta=$yDelta")
        val letter = vlak.toUpperCase()[0]
        val cijfer = vlak.toUpperCase()[1]
        println("letter=$letter")
        println("cijfer=$cijfer")
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
        if (cijfer == '8') y = y8
        if (cijfer == '7') y = y8 - yDelta * 1
        if (cijfer == '6') y = y8 - yDelta * 2
        if (cijfer == '5') y = y8 - yDelta * 3
        if (cijfer == '4') y = y8 - yDelta * 4
        if (cijfer == '3') y = y8 - yDelta * 5
        if (cijfer == '2') y = y8 - yDelta * 6
        if (cijfer == '1') y = y1
        if (y > 18500) y = 18500
        if (y < 100) y = 100
        if (x > 15000) x = 15000
        if (x < 100) x = 100
        moveto(y, x)
    }

    override fun moveto(x: Int, y: Int) {
        println("move to pos $x,$y")
        calcDelays(x, y)
        gotoPos(arm1, x, formattedDelayFactor1)
        gotoPos(arm2, y, formattedDelayFactor2)
        waitUntilReady(100)
    }

    override fun homeVert() {
        home(arm1)
    }

    override fun homeHor() {
        home(arm2)
    }

    override fun sleep() {
        println("sleeping")
        moveto(100, 100)
        try {
            arm1!!.write("^X0000000000000000".toByteArray())
            arm2!!.write("^X0000000000000000".toByteArray())
        } catch (e: IOException) {
            e.printStackTrace()
        }
        waitUntilReady(50)
    }

    fun gotoPos(arm: I2CDevice?, pos: Int, vertraging: String) {
        try {
            println("gotopos:" + pos + " to " + arm!!.address + " vertraging:" + vertraging)
            val formattedPos = String.format("%06d", pos)
            val command = "^M$formattedPos$vertraging"
            println("command:" + command + " to " + arm.address)
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
    }

    override fun clamp() {
        println("clamp")
        try {
            arm3!!.write("^C0000000000000000".toByteArray())
            Thread.sleep(400)
        } catch (e: Exception) {
            e.printStackTrace()
        }

//    waitUntilReady(100);
    }

    override fun release() {
        println("release")
        try {
            arm3!!.write("^R0000000000000000".toByteArray())
            Thread.sleep(400)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //    waitUntilReady(100);
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

    override fun setA8(pos: String) {
        saveToFile("/home/pi/a8.data", pos)
    }

    override fun getH1(): String? {
        return loadFile("/home/pi/h1.data")
    }

    override fun setH1(pos: String) {
        saveToFile("/home/pi/h1.data", pos)
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

    override fun startDisplayThread(){

            println("Strting up the MCP23017 based 16x2 LCD Example")
            val bus = I2CFactory.getInstance(I2CBus.BUS_1) //
            I2CLcdDisplay.dev = bus.getDevice(DISPLAY) //Address for MCp23017 change if A0,A1,A2 are connected to diff potenrial
            I2CLcdDisplay.dev.write(0x01, 0x00.toByte()) //Initialized PORT B of MCP23017 to use as ouput.
            val lcd = I2CLcdDisplay()
            lcd.initDisplay() //LCD Initialization Routine
            lcd.lcd_byte(0x01, I2CLcdDisplay.LCD_CMD) //LCD Clear Command
            lcd.lcd_byte(0x02, I2CLcdDisplay.LCD_CMD) //LCD Home Command
            lcd.write("WeArGenius")
            lcd.setCursorPosition(1, 0)
            lcd.write("weargenius.in")
            Thread.sleep(2000)
            while (true) {
                println("Write 1")
                lcd.lcd_byte(0x01, I2CLcdDisplay.LCD_CMD) //LCD Clear
                lcd.setCursorPosition(1, 0)
                lcd.write("Embedded")
                Thread.sleep(3000)
                println("Write 2")
                lcd.lcd_byte(0x01, I2CLcdDisplay.LCD_CMD) //LCD Clear
                lcd.setCursorPosition(1, 0)
                lcd.write("Home Automation")
                Thread.sleep(3000)
                println("Write 3")
                lcd.lcd_byte(0x01, I2CLcdDisplay.LCD_CMD) //LCD Clear
                lcd.setCursorPosition(1, 0)
                lcd.write("IOT")
                Thread.sleep(3000)
                println("Write 4")
                lcd.lcd_byte(0x01, I2CLcdDisplay.LCD_CMD) //LCD Clear
                lcd.setCursorPosition(1, 0)
                lcd.write("Programming")
                Thread.sleep(3000)
            }
        }
//
//        while (true) {
//            println("status")
//            try {
//                val arm1Status = arm1!!.read()
//                val arm2Status = arm2!!.read()
//                val arm3Status = arm3!!.read()
//                allReady = arm1Status == 1 && arm2Status == 1 && arm3Status != 2 // arm3 : alleen checken dat hij niet aan het moven is
////                status1Label.text = getStatusString(arm1Status)
////                status2Label.text = getStatusString(arm2Status)
////                status3Label.text = getArm3StatusString(arm3Status)
////                status4Label.text = "ready:$allReady"
//                Thread.sleep(300)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }

    private fun home(arm: I2CDevice?) {
        try {
            arm?.write("^H0000000000600000".toByteArray())
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
        println("pulses1=$pulses1")
        println("pulses2=$pulses2")
        val delays = BerekenVersnelling.calcDelays(pulses1, pulses2)
        var delayFactor1: Double = if (pulses1 == 0) 1.0 else delays!!.delay1
        var delayFactor2: Double = if (pulses2 == 0) 1.0 else delays!!.delay2
        if (delayFactor1 > 9999) delayFactor1 = 9999.0
        if (delayFactor2 > 9999) delayFactor2 = 9999.0

        // speedup 2x
        val snelheid: Double = getSnelheid()?.toDoubleOrNull()?:2.0
        print("snelheid:"+snelheid)

//    delayFactor1 = delayFactor1/2;
//    delayFactor2 = delayFactor2/2;
        delayFactor1 = delayFactor1 * snelheid
        delayFactor2 = delayFactor2 * snelheid
        //    delayFactor1 = delayFactor1*3;
//    delayFactor2 = delayFactor2*3;
        println("delayFactor1a=$delayFactor1")
        println("delayFactor2a=$delayFactor2")
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
            val arm1Status = arm1!!.read()
            val arm2Status = arm2!!.read()
            val arm3Status = arm3!!.read()
            allReady = arm1Status == 1 && arm2Status == 1 && arm3Status != 2 // arm3 : alleen checken dat hij niet aan het moven is
            Thread.sleep(10)
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
        Thread(Runnable { runOnce(text) }).start()
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
                            println("moveto:$row")
                            println("ss:" + row.trim { it <= ' ' }.substring(1, 3))
                            movetoVlak(row.trim { it <= ' ' }.substring(1, 3))
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
