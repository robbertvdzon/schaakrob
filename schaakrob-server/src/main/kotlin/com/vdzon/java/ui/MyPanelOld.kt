//package com.vdzon.java.ui
//
//import com.pi4j.io.i2c.I2CBus
//import com.pi4j.io.i2c.I2CDevice
//import com.pi4j.io.i2c.I2CFactory
//import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException
//import com.vdzon.java.BerekenVersnelling
//import java.awt.GraphicsEnvironment
//import java.awt.event.ActionEvent
//import java.io.IOException
//import java.io.PrintWriter
//import java.nio.file.Files
//import java.nio.file.Paths
//import java.util.*
//import java.util.function.Consumer
//import javax.swing.*
//
//class MyPanelOld : JPanel() {
//    private var allReady = false
//    var lastPos1 = 0
//    var lastPos2 = 0
//    var lastPos3 = 0
//    var formattedDelayFactor1 = "0050"
//    var formattedDelayFactor2 = "0050"
//    private var arm1: I2CDevice? = null
//    private var arm2: I2CDevice? = null
//    private var arm3: I2CDevice? = null
//    private var currentLoopThread: Thread? = null
//    var vertragingTextfield: JTextField
//    val tfArm1 = JTextField()
//    val tfArm2 = JTextField()
//    var mainFrame: JFrame? = null
//    var statusLabel = JLabel("status")
//    private fun clamp() {
//        println("clamp")
//        try {
//            arm3!!.write("^C0000000000000000".toByteArray())
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun release() {
//        println("release")
//        try {
//            arm3!!.write("^R0000000000000000".toByteArray())
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun sleeping() {
//        println("sleeping")
//        try {
//            arm1!!.write("^X0000000000000000".toByteArray())
//            arm2!!.write("^X0000000000000000".toByteArray())
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//
//    fun startReadStatus() {
//        currentLoopThread = Thread(Runnable { status() })
//        currentLoopThread!!.start()
//    }
//
//    private fun getStatusString(status: Int): String {
//        if (status == 0) return "HN"
//        if (status == 1) return "RE"
//        if (status == 2) return "MO"
//        if (status == 3) return "HO"
//        return if (status == 4) "ER" else "??"
//    }
//
//    private fun getArm3StatusString(status: Int): String {
//        if (status == 0) return "RE"
//        if (status == 1) return "RE"
//        if (status == 2) return "MO"
//        if (status == 3) return "HO"
//        return if (status == 4) "ER" else "??"
//    }
//
//    private fun status() {
//        while (true) {
//            println("status")
//            try {
//                val arm1Status = arm1!!.read()
//                val arm2Status = arm2!!.read()
//                val arm3Status = arm3!!.read()
//                allReady = arm1Status == 1 && arm2Status == 1 && arm3Status != 2 // arm3 : alleen checken dat hij niet aan het moven is
//                statusLabel.text = getStatusString(arm1Status) + "-" + getStatusString(arm2Status) + "-" + getArm3StatusString(arm3Status) + ":" + allReady
//                Thread.sleep(10)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//    private fun stopLoop() {
//        if (currentLoopThread != null) {
//            currentLoopThread!!.stop()
//        }
//    }
//
//    private fun startLoop(text: String) {
//        currentLoopThread = Thread(Runnable { runInLoop(text) })
//        currentLoopThread!!.start()
//    }
//
//    private fun runOnceInThread(text: String) {
//        Thread(Runnable { runOnce(text) }).start()
//    }
//
//    private fun runInLoop(text: String) {
//        while (true) {
//            runOnce(text)
//        }
//    }
//
//    private fun runOnce(text: String) {
//        val split = text.split("#".toRegex()).toTypedArray()
//        Arrays.asList(*split).forEach(
//                Consumer { row: String? ->
//                    if (row != null && !row.startsWith("#")) {
//                        waitUntilReady(100)
//                        if (row.trim { it <= ' ' }.startsWith("pak")) {
//                            clamp()
//                            //              try {
////                Thread.sleep(300);
////              }
////              catch (Exception ex){
////                ex.printStackTrace();
////              }
//                        } else if (row.trim { it <= ' ' }.startsWith("zet")) {
//                            release()
//                            //              try {
////                Thread.sleep(300);
////              }
////              catch (Exception ex){
////                ex.printStackTrace();
////              }
//                        } else if (row.trim { it <= ' ' }.startsWith("sleep")) {
//                            sleeping()
//                            //              try {
////                Thread.sleep(300);
////              }
////              catch (Exception ex){
////                ex.printStackTrace();
////              }
//                        } else {
//                            val splitWords = row.split(",".toRegex()).toTypedArray()
//                            if (splitWords.size >= 3) {
//                                val posArm1 = splitWords[0].trim { it <= ' ' }
//                                val posArm2 = splitWords[1].trim { it <= ' ' }
//                                try {
//                                    val pos1 = posArm1.toInt()
//                                    val pos2 = posArm2.toInt()
//                                    //                  long totalTime = calcDelays(pos1, pos2);
//                                    formattedDelayFactor1 = vertragingTextfield.text
//                                    formattedDelayFactor2 = vertragingTextfield.text
//
////                  System.out.println("totalTime=" + totalTime);
//                                    println("formattedDelayFactor1=$formattedDelayFactor1")
//                                    println("formattedDelayFactor2=$formattedDelayFactor2")
//                                    gotoPos(arm1, pos1, formattedDelayFactor1)
//                                    gotoPos(arm2, pos2, formattedDelayFactor2)
//                                } catch (ex: Exception) {
//                                    ex.printStackTrace()
//                                }
//                            }
//                        }
//                    }
//                }
//        )
//    }
//
//    private fun waitUntilReady(initialDelay: Int) {
//        sleep(initialDelay)
//        while (!allReady) {
//            sleep(10)
//        }
//    }
//
//    private fun sleep(initialDelay: Int) {
//        try {
//            Thread.sleep(initialDelay.toLong())
//        } catch (e: InterruptedException) {
//            e.printStackTrace()
//        }
//    }
//
//    fun fullscreen() {
//        device.fullScreenWindow = mainFrame
//    }
//
//    fun calcDelays(pos1: Int, pos2: Int): Long {
//        val pulses1 = Math.abs(pos1 - lastPos1)
//        val pulses2 = Math.abs(pos2 - lastPos2)
//        val delays = BerekenVersnelling.calcDelays(pulses1, pulses2)
//        var delayFactor1: Double = if (pulses1 == 0) 1 else delays!!.delay1
//        var delayFactor2: Double = if (pulses2 == 0) 1 else delays!!.delay2
//        if (delayFactor1 > 9999) delayFactor1 = 9999.0
//        if (delayFactor2 > 9999) delayFactor2 = 9999.0
//        formattedDelayFactor1 = String.format("%04d", delayFactor1.toInt())
//        formattedDelayFactor2 = String.format("%04d", delayFactor2.toInt())
//        return delays!!.totalTime
//    }
//
//    private fun saveToFile(text: String) {
//        val path = Paths.get("/home/pi/loop.data")
//        val strToBytes = text.toByteArray()
//        try {
//            Files.write(path, strToBytes)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun loadFile(): String {
//        try {
//            return String(Files.readAllBytes(Paths.get("/home/pi/loop.data")))
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        return "123"
//    }
//
//    fun init() {
//        if (arm1 != null) {
//            return
//        }
//        try {
//            val i2c = I2CFactory.getInstance(I2CBus.BUS_1)
//            arm1 = i2c.getDevice(ARM1)
//            arm2 = i2c.getDevice(ARM2)
//            arm3 = i2c.getDevice(ARM3)
//        } catch (e: UnsupportedBusNumberException) {
//            println("ERROR, UnsupportedBusNumberException in init")
//        } catch (e: IOException) {
//            println("ERROR IOException in init:" + e.message)
//        }
//    }
//
//    private fun gotoPos(tf: JTextField, arm: I2CDevice?, increment: Int) {
//        val pos = tf.text.toInt()
//        val newPos = pos + increment
//        tf.text = "" + newPos
//        gotoPos(arm, newPos)
//    }
//
//    private fun gotoPosAbs(tf: JTextField, arm: I2CDevice?, newPos: Int) {
//        tf.text = "" + newPos
//        gotoPos(arm, newPos)
//    }
//
//    private fun gotoPosArm3(tf: JTextField, increment: Int, time: Long) {
//        val pos = tf.text.toInt()
//        val newPos = pos + increment
//        tf.text = "" + newPos
//        gotoPosArm3(newPos, time)
//    }
//
//    private fun gotoPosArm3Abs(tf: JTextField, pos: Int) {
//        tf.text = "" + pos
//        gotoPosArm3(pos, 1)
//    }
//
//    fun gotoPosArm3(pos: Int, delay: Long) {
//        try {
//            val formattedPos = String.format("%06d", pos)
//            val time = String.format("%04d", delay)
//            val command = "^S$formattedPos$time"
//            println("command:$command")
//            arm3!!.write(command.toByteArray())
//            lastPos3 = pos
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        try {
//            arm3!!.write("^S0000000000600000".toByteArray())
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        lastPos3 = pos
//    }
//
//    @JvmOverloads
//    fun gotoPos(arm: I2CDevice?, pos: Int, vertraging: String = vertragingTextfield.text) {
//        try {
//            val formattedPos = String.format("%06d", pos)
//            val command = "^M$formattedPos$vertraging"
//            println("command:$command")
//            arm?.write(command.toByteArray())
//            if (arm === arm1) {
//                lastPos1 = pos
//            }
//            if (arm === arm2) {
//                lastPos2 = pos
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun home(arm: I2CDevice?) {
//        try {
//            arm?.write("^H0000000000600000".toByteArray())
//            if (arm === arm1) {
//                tfArm1.text = "0"
//                lastPos1 = 0
//            }
//            if (arm === arm2) {
//                tfArm2.text = "0"
//                lastPos2 = 0
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun naarRustPos() {
//        gotoPosAbs(tfArm1, arm1, 100)
//        gotoPosAbs(tfArm2, arm2, 100)
//    }
//
//    private fun updateAndRestart() {
//        try {
//            val writer = PrintWriter("/tmp/rebuildui", "UTF-8")
//            writer.close()
//            System.exit(0)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//
//    companion object {
//        var device = GraphicsEnvironment.getLocalGraphicsEnvironment().screenDevices[0]
//        private const val ARM1 = 0x8 //was 5
//        private const val ARM2 = 0x6 // was 7
//        private const val ARM3 = 0x5 // was 8
//    }
//
//    init {
//        println("Starting")
//        init()
//        mainFrame = JFrame("Schaakrobot v1.6")
//        mainFrame!!.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
//        mainFrame!!.contentPane.add(this)
//        mainFrame!!.pack()
//        mainFrame!!.isLocationByPlatform = true
//        val b = JButton("Restart & Update")
//        b.setBounds(5, 20, 200, 40)
//        b.addActionListener { e: ActionEvent? -> updateAndRestart() }
//        mainFrame!!.add(b)
//        val bExit = JButton("Restart")
//        bExit.setBounds(210, 20, 200, 40)
//        bExit.addActionListener { e: ActionEvent? -> System.exit(0) }
//        mainFrame!!.add(bExit)
//        vertragingTextfield = JTextField()
//        vertragingTextfield.setBounds(410, 20, 100, 40)
//        vertragingTextfield.text = "0100"
//        mainFrame.add(vertragingTextfield)
//        {
//            val bHome = JButton("Home 1")
//            bHome.setBounds(5, 70, 200, 40)
//            bHome.addActionListener { e: ActionEvent? -> home(arm1) }
//            mainFrame!!.add(bHome)
//        }
//        {
//            val bHome = JButton("Home 2")
//            bHome.setBounds(210, 70, 200, 40)
//            bHome.addActionListener { e: ActionEvent? -> home(arm2) }
//            mainFrame!!.add(bHome)
//        }
//        {
//            val bHome = JButton("Naar rust pos")
//            bHome.setBounds(415, 70, 200, 40)
//            bHome.addActionListener { e: ActionEvent? -> naarRustPos() }
//            mainFrame!!.add(bHome)
//        }
//        {
//            tfArm1.setBounds(5, 120, 100, 40)
//            tfArm1.text = "0"
//            mainFrame.add(tfArm1)
//            {
//                val button = JButton("Goto")
//                button.setBounds(210, 120, 100, 40)
//                button.addActionListener { e: ActionEvent? -> gotoPos(tfArm1, arm1, 0) }
//                mainFrame!!.add(button)
//            }
//            {
//                val button = JButton("-1000")
//                button.setBounds(315, 120, 100, 40)
//                button.addActionListener { e: ActionEvent? -> gotoPos(tfArm1, arm1, -1000) }
//                mainFrame!!.add(button)
//            }
//            {
//                val button = JButton("+1000")
//                button.setBounds(420, 120, 100, 40)
//                button.addActionListener { e: ActionEvent? -> gotoPos(tfArm1, arm1, +1000) }
//                mainFrame!!.add(button)
//            }
//            {
//                val button = JButton("-5000")
//                button.setBounds(525, 120, 100, 40)
//                button.addActionListener { e: ActionEvent? -> gotoPos(tfArm1, arm1, -5000) }
//                mainFrame!!.add(button)
//            }
//            {
//                val button = JButton("+5000")
//                button.setBounds(630, 120, 100, 40)
//                button.addActionListener { e: ActionEvent? -> gotoPos(tfArm1, arm1, +5000) }
//                mainFrame!!.add(button)
//            }
//        }
//        {
//            tfArm2.setBounds(5, 170, 100, 40)
//            tfArm2.text = "0"
//            mainFrame.add(tfArm2)
//            {
//                val button = JButton("Goto")
//                button.setBounds(210, 170, 100, 40)
//                button.addActionListener { e: ActionEvent? -> gotoPos(tfArm2, arm2, 0) }
//                mainFrame!!.add(button)
//            }
//            {
//                val button = JButton("-1000")
//                button.setBounds(315, 170, 100, 40)
//                button.addActionListener { e: ActionEvent? -> gotoPos(tfArm2, arm2, -1000) }
//                mainFrame!!.add(button)
//            }
//            {
//                val button = JButton("+1000")
//                button.setBounds(420, 170, 100, 40)
//                button.addActionListener { e: ActionEvent? -> gotoPos(tfArm2, arm2, +1000) }
//                mainFrame!!.add(button)
//            }
//            {
//                val button = JButton("-5000")
//                button.setBounds(525, 170, 100, 40)
//                button.addActionListener { e: ActionEvent? -> gotoPos(tfArm2, arm2, -5000) }
//                mainFrame!!.add(button)
//            }
//            {
//                val button = JButton("+5000")
//                button.setBounds(630, 170, 100, 40)
//                button.addActionListener { e: ActionEvent? -> gotoPos(tfArm2, arm2, +5000) }
//                mainFrame!!.add(button)
//            }
//        }
//        {
//            val tf = JTextField()
//            tf.setBounds(5, 220, 100, 40)
//            tf.text = "90"
//            mainFrame.add(tf)
//            {
//                val button = JButton("Goto")
//                button.setBounds(210, 220, 100, 40)
//                button.addActionListener { e: ActionEvent? -> gotoPosArm3(tf, 0, 1000) }
//                mainFrame!!.add(button)
//            }
//            {
//                val button = JButton("-10")
//                button.setBounds(315, 220, 100, 40)
//                button.addActionListener { e: ActionEvent? -> gotoPosArm3(tf, -10, 1000) }
//                mainFrame!!.add(button)
//            }
//            {
//                val button = JButton("+10")
//                button.setBounds(420, 220, 100, 40)
//                button.addActionListener { e: ActionEvent? -> gotoPosArm3(tf, +10, 1000) }
//                mainFrame!!.add(button)
//            }
//            {
//                val button = JButton("50")
//                button.setBounds(525, 220, 100, 40)
//                button.addActionListener { e: ActionEvent? -> gotoPosArm3Abs(tf, 50) }
//                mainFrame!!.add(button)
//            }
//            {
//                val button = JButton("100")
//                button.setBounds(630, 220, 100, 40)
//                button.addActionListener { e: ActionEvent? -> gotoPosArm3Abs(tf, 100) }
//                mainFrame!!.add(button)
//            }
//        }
//        {
//            val button = JButton("p")
//            button.setBounds(120, 120, 40, 40)
//            button.addActionListener { e: ActionEvent? -> clamp() }
//            mainFrame!!.add(button)
//        }
//        {
//            val button = JButton("z")
//            button.setBounds(165, 120, 40, 40)
//            button.addActionListener { e: ActionEvent? -> release() }
//            mainFrame!!.add(button)
//        }
//        {
//            val button = JButton("sleep")
//            button.setBounds(525, 320, 100, 40)
//            button.addActionListener { e: ActionEvent? -> sleeping() }
//            mainFrame!!.add(button)
//        }
//        {
//            val button = JButton("status")
//            button.setBounds(525, 370, 100, 40)
//            button.addActionListener { e: ActionEvent? -> status() }
//            mainFrame!!.add(button)
//            statusLabel.setBounds(630, 370, 100, 40)
//            mainFrame!!.add(statusLabel)
//        }
//        {
//            val textArea = JTextArea(5, 20)
//            val scrollPane = JScrollPane(textArea)
//            scrollPane.setBounds(5, 270, 300, 80)
//            textArea.isEditable = true
//            mainFrame!!.add(scrollPane)
//            textArea.setText(loadFile())
//            {
//                val button = JButton("save")
//                button.setBounds(315, 260, 100, 20)
//                button.addActionListener { e: ActionEvent? -> saveToFile(textArea.text) }
//                mainFrame!!.add(button)
//            }
//            {
//                val button = JButton("run once")
//                button.setBounds(315, 285, 100, 20)
//                button.addActionListener { e: ActionEvent? -> runOnceInThread(textArea.text) }
//                mainFrame!!.add(button)
//            }
//            {
//                val button = JButton("loop")
//                button.setBounds(315, 370, 100, 40)
//                button.addActionListener { e: ActionEvent? -> startLoop(textArea.text) }
//                mainFrame!!.add(button)
//            }
//            {
//                val button = JButton("stop")
//                button.setBounds(420, 370, 100, 40)
//                button.addActionListener { e: ActionEvent? -> stopLoop() }
//                mainFrame!!.add(button)
//            }
//        }
//        mainFrame!!.layout = null
//        mainFrame!!.extendedState = JFrame.MAXIMIZED_BOTH
//        mainFrame!!.isVisible = true
//        startReadStatus()
//    }
//}
