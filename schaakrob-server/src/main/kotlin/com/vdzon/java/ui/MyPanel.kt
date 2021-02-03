package com.vdzon.java.ui

import com.pi4j.io.i2c.I2CBus
import com.pi4j.io.i2c.I2CDevice
import com.pi4j.io.i2c.I2CFactory
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException
import java.awt.Color
import java.awt.GraphicsEnvironment
import java.awt.event.ActionEvent
import java.io.IOException
import java.io.PrintWriter
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.UnknownHostException
import java.util.*
import javax.imageio.ImageIO
import javax.swing.*

class MyPanel : JPanel() {
    private var allReady = false
    var lastPos1 = 0
    var lastPos2 = 0
    var lastPos3 = 0
    var formattedDelayFactor1 = "0050"
    var formattedDelayFactor2 = "0050"
    private var arm1: I2CDevice? = null
    private var arm2: I2CDevice? = null
    private var arm3: I2CDevice? = null
    private var currentLoopThread: Thread? = null
    var vertragingTextfield: JTextField? = null
    val tfArm1 = JTextField()
    val tfArm2 = JTextField()
    var mainFrame: JFrame? = null
    var ipLabel = JLabel("ip")
    var status1Label = JLabel("status arm1")
    var status2Label = JLabel("status arm2")
    var status3Label = JLabel("status pakker")
    var status4Label = JLabel("ready")

    //
    //  private void clamp(){
    //    System.out.println("clamp");
    //    try {
    //        arm3.write("^C0000000000000000".getBytes());
    //    } catch (IOException e) {
    //      e.printStackTrace();
    //    }
    //  }
    //
    //  private void release(){
    //    System.out.println("release");
    //    try {
    //        arm3.write("^R0000000000000000".getBytes());
    //    } catch (IOException e) {
    //      e.printStackTrace();
    //    }
    //  }
    //
    //  private void sleeping(){
    //    System.out.println("sleeping");
    //    try {
    //        arm1.write("^X0000000000000000".getBytes());
    //        arm2.write("^X0000000000000000".getBytes());
    //    } catch (IOException e) {
    //      e.printStackTrace();
    //    }
    //  }
    fun startReadStatus() {
        try {
            val inetAddress = localHostLANAddress()
            ipLabel.text = inetAddress.hostAddress
        } catch (e: Exception) {
        }
        currentLoopThread = Thread(Runnable { status() })
        currentLoopThread!!.start()
    }

    private fun getStatusString(status: Int): String {
        if (status == 0) return "Homing needed"
        if (status == 1) return "Ready"
        if (status == 2) return "Moving"
        if (status == 3) return "Homing"
        if (status == 4) return "Error"
        if (status == 5) return "Going to sleep"
        return if (status == 6) "Sleeping" else "??"
    }

    private fun getArm3StatusString(status: Int): String {
        if (status == 1) return "Ready"
        if (status == 2) return "Grabbing"
        return if (status == 3) "Releasing" else "??"
    }

    private fun status() {
        while (true) {
            println("status")
            try {
                val arm1Status = arm1!!.read()
                val arm2Status = arm2!!.read()
                val arm3Status = arm3!!.read()
                allReady = arm1Status == 1 && arm2Status == 1 && arm3Status != 2 // arm3 : alleen checken dat hij niet aan het moven is
                status1Label.text = getStatusString(arm1Status)
                status2Label.text = getStatusString(arm2Status)
                status3Label.text = getArm3StatusString(arm3Status)
                status4Label.text = "ready:$allReady"
                Thread.sleep(300)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    //
    //  private void stopLoop() {
    //    if (currentLoopThread != null) {
    //      currentLoopThread.stop();
    //    }
    //  }
    //
    //  private void startLoop(String text) {
    //    currentLoopThread = new Thread(() -> runInLoop(text));
    //    currentLoopThread.start();
    //  }
    //
    //  private void runOnceInThread(String text) {
    //    new Thread(() -> runOnce(text)).start();
    //  }
    //
    //  private void runInLoop(String text) {
    //    while (true) {
    //      runOnce(text);
    //    }
    //  }
    //
    //  private void runOnce(String text) {
    //    String[] split = text.split("#");
    //
    //    Arrays.asList(split).forEach(
    //        row -> {
    //          if (row != null && !row.startsWith("#")) {
    //            waitUntilReady(100);
    //
    //            if (row.trim().startsWith("pak")){
    //              clamp();
    ////              try {
    ////                Thread.sleep(300);
    ////              }
    ////              catch (Exception ex){
    ////                ex.printStackTrace();
    ////              }
    //            }
    //            else if (row.trim().startsWith("zet")){
    //              release();
    ////              try {
    ////                Thread.sleep(300);
    ////              }
    ////              catch (Exception ex){
    ////                ex.printStackTrace();
    ////              }
    //
    //            }
    //            else if (row.trim().startsWith("sleep")){
    //              sleeping();
    ////              try {
    ////                Thread.sleep(300);
    ////              }
    ////              catch (Exception ex){
    ////                ex.printStackTrace();
    ////              }
    //
    //            }
    //            else {
    //              String[] splitWords = row.split(",");
    //              if (splitWords.length >= 3) {
    //                String posArm1 = splitWords[0].trim();
    //                String posArm2 = splitWords[1].trim();
    //                try {
    //                  int pos1 = Integer.parseInt(posArm1);
    //                  int pos2 = Integer.parseInt(posArm2);
    ////                  long totalTime = calcDelays(pos1, pos2);
    //
    //                  formattedDelayFactor1 = vertragingTextfield.getText();
    //                  formattedDelayFactor2 = vertragingTextfield.getText();
    //
    ////                  System.out.println("totalTime=" + totalTime);
    //                  System.out.println("formattedDelayFactor1=" + formattedDelayFactor1);
    //                  System.out.println("formattedDelayFactor2=" + formattedDelayFactor2);
    //
    //                  gotoPos(arm1, pos1, formattedDelayFactor1);
    //                  gotoPos(arm2, pos2, formattedDelayFactor2);
    //                } catch (Exception ex) {
    //                  ex.printStackTrace();
    //
    //                }
    //              }
    //            }
    //
    //          }
    //        }
    //    );
    //  }
    //
    //  private void waitUntilReady(int initialDelay) {
    //    sleep(initialDelay);
    //    while (!allReady){
    //      sleep(10);
    //    }
    //
    //  }
    //
    //  private void sleep(int initialDelay) {
    //    try {
    //      Thread.sleep(initialDelay);
    //    } catch (InterruptedException e) {
    //      e.printStackTrace();
    //    }
    //  }
    fun fullscreen() {
        device.fullScreenWindow = mainFrame
    }

    //
    //  public long calcDelays(int pos1, int pos2) {
    //    int pulses1 = Math.abs(pos1 - lastPos1);
    //    int pulses2 = Math.abs(pos2 - lastPos2);
    //
    //    Delays delays = BerekenVersnelling.calcDelays(pulses1, pulses2);
    //
    //    double delayFactor1 = pulses1 == 0 ? 1  : delays.delay1;
    //    double delayFactor2 = pulses2 == 0 ? 1  : delays.delay2;
    //
    //    if (delayFactor1>9999) delayFactor1 = 9999;
    //    if (delayFactor2>9999) delayFactor2 = 9999;
    //
    //    formattedDelayFactor1 = String.format("%04d", (int)delayFactor1);
    //    formattedDelayFactor2 = String.format("%04d", (int)delayFactor2);
    //    return delays.totalTime;
    //  }
    //
    //
    //  private void saveToFile(String text) {
    //    Path path = Paths.get("/home/pi/loop.data");
    //    byte[] strToBytes = text.getBytes();
    //    try {
    //      Files.write(path, strToBytes);
    //    } catch (IOException e) {
    //      e.printStackTrace();
    //    }
    //  }
    //
    //  private String loadFile() {
    //    try {
    //      return new String(Files.readAllBytes(Paths.get("/home/pi/loop.data")));
    //    } catch (IOException e) {
    //      e.printStackTrace();
    //    }
    //    return "123";
    //  }
    //
    fun initPanel() {
        if (arm1 != null) {
            return
        }
        try {
            val i2c = I2CFactory.getInstance(I2CBus.BUS_1)
            arm1 = i2c.getDevice(ARM1)
            arm2 = i2c.getDevice(ARM2)
            arm3 = i2c.getDevice(ARM3)
        } catch (e: UnsupportedBusNumberException) {
            println("ERROR, UnsupportedBusNumberException in init")
        } catch (e: IOException) {
            println("ERROR IOException in init:" + e.message)
        }
    }

    //
    //  private void gotoPos(JTextField tf, I2CDevice arm, int increment) {
    //    int pos = Integer.parseInt(tf.getText());
    //    int newPos = pos + increment;
    //    tf.setText("" + newPos);
    //    gotoPos(arm, newPos);
    //  }
    //
    //  private void gotoPosAbs(JTextField tf, I2CDevice arm, int newPos) {
    //    tf.setText("" + newPos);
    //    gotoPos(arm, newPos);
    //  }
    //
    //  private void gotoPosArm3(JTextField tf, int increment, long time) {
    //    int pos = Integer.parseInt(tf.getText());
    //    int newPos = pos + increment;
    //    tf.setText("" + newPos);
    //    gotoPosArm3(newPos, time);
    //  }
    //
    //  private void gotoPosArm3Abs(JTextField tf, int pos) {
    //    tf.setText("" + pos);
    //    gotoPosArm3(pos, 1);
    //  }
    //
    //  public void gotoPosArm3(int pos, long delay) {
    //    try {
    //      String formattedPos = String.format("%06d", pos);
    //      String time = String.format("%04d", delay);
    //      String command = "^S" + formattedPos + time;
    //      System.out.println("command:"+command);
    //      arm3.write(command.getBytes());
    //      lastPos3 = pos;
    //    } catch (IOException e) {
    //      e.printStackTrace();
    //    }
    //
    //    try {
    //      arm3.write("^S0000000000600000".getBytes());
    //    } catch (IOException e) {
    //      e.printStackTrace();
    //    }
    //
    //    lastPos3 = pos;
    //  }
    //
    //
    //  public void gotoPos(I2CDevice arm, int pos) {
    //    gotoPos(arm, pos, vertragingTextfield.getText());
    //  }
    //
    //  public void gotoPos(I2CDevice arm, int pos, String vertraging) {
    //    try {
    //      String formattedPos = String.format("%06d", pos);
    //      String command = "^M" + formattedPos + vertraging;
    //      System.out.println("command:"+command);
    //      if (arm != null) { arm.write(command.getBytes()); }
    //      if (arm == arm1) { lastPos1 = pos; }
    //      if (arm == arm2) { lastPos2 = pos; }
    //
    //    } catch (IOException e) {
    //      e.printStackTrace();
    //    }
    //
    //
    //  }
    //
    //  private void home(I2CDevice arm) {
    //    try {
    //      if (arm != null) { arm.write("^H0000000000600000".getBytes()); }
    //      if (arm == arm1) {
    //        tfArm1.setText("0");
    //        lastPos1 = 0;
    //      }
    //      if (arm == arm2) {
    //        tfArm2.setText("0");
    //        lastPos2 = 0;
    //      }
    //
    //
    //
    //    } catch (IOException e) {
    //      e.printStackTrace();
    //    }
    //  }
    //
    //  private void naarRustPos() {
    //    gotoPosAbs(tfArm1, arm1,100);
    //    gotoPosAbs(tfArm2, arm2,100);
    //  }
    private fun updateAndRestart() {
        try {
            val writer = PrintWriter("/tmp/rebuildui", "UTF-8")
            writer.close()
            System.exit(0)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        var device = GraphicsEnvironment.getLocalGraphicsEnvironment().screenDevices[0]
        private const val ARM1 = 0x8 //was 5
        private const val ARM2 = 0x6 // was 7
        private const val ARM3 = 0x5 // was 8// We did not find a site-local address, but we found some other non-loopback address.

        // Server might have a non-site-local address assigned to its NIC (or it might be running
        // IPv6 which deprecates the "site-local" concept).
        // Return this non-loopback candidate address...
        // At this point, we did not find a non-loopback address.
        // Fall back to returning whatever InetAddress.getLocalHost() returns...
// Found non-loopback address, but not necessarily site-local.
        // Store it as a candidate to be returned if site-local address is not subsequently found...
        // Note that we don't repeatedly assign non-loopback non-site-local addresses as candidates,
        // only the first. For subsequent iterations, candidate will be non-null.
// Found non-loopback site-local address. Return it immediately...// Iterate all IP addresses assigned to each card...
        // Iterate all NICs (network interface cards)...
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
    }
    init {
        println("Starting")
        initPanel()
        mainFrame = JFrame("Schaakrobot v1.6")
        mainFrame!!.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        mainFrame!!.contentPane.add(this)
        mainFrame!!.contentPane.background = Color.WHITE
        mainFrame!!.pack()
        mainFrame!!.isLocationByPlatform = true
        val b = JButton("Restart & Update")
        b.setBounds(65, 20, 200, 40)
        b.addActionListener { e: ActionEvent? -> updateAndRestart() }
        mainFrame!!.add(b)
        val bExit = JButton("Exit")
        bExit.setBounds(270, 20, 200, 40)
        bExit.addActionListener { e: ActionEvent? -> System.exit(0) }
        mainFrame!!.add(bExit)
        try {
            val read = ImageIO.read(javaClass.getResource("/logo.jpg"))
            val icon = ImageIcon(read)
            val imageLabel = JLabel(icon)
            imageLabel.setBounds(5, 20, 60, 60)
            mainFrame!!.add(imageLabel)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        ipLabel.setBounds(10, 70, 200, 40)
        mainFrame!!.add(ipLabel)
        status1Label.setBounds(10, 90, 100, 40)
        mainFrame!!.add(status1Label)
        status2Label.setBounds(10, 110, 100, 40)
        mainFrame!!.add(status2Label)
        status3Label.setBounds(10, 130, 100, 40)
        mainFrame!!.add(status3Label)
        status4Label.setBounds(10, 150, 100, 40)
        mainFrame!!.add(status4Label)
        mainFrame!!.layout = null
        mainFrame!!.extendedState = JFrame.MAXIMIZED_BOTH
        mainFrame!!.isVisible = true
        startReadStatus()
    }
}
