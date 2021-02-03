package com.vdzon.java.ui;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;
import com.vdzon.java.BerekenVersnelling;
import com.vdzon.java.Delays;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class MyPanelOld extends JPanel {

  static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

  private boolean allReady = false;
  private static int ARM1 = 0x8;//was 5
  private static int ARM2 = 0x6;// was 7
  private static int ARM3 = 0x5;// was 8
  int lastPos1 = 0;
  int lastPos2 = 0;
  int lastPos3 = 0;

  String formattedDelayFactor1 = "0050";
  String formattedDelayFactor2 = "0050";

  private I2CDevice arm1 = null;
  private I2CDevice arm2 = null;
  private I2CDevice arm3 = null;
  private Thread currentLoopThread = null;
  JTextField vertragingTextfield;
  final JTextField tfArm1 = new JTextField();
  final JTextField tfArm2 = new JTextField();
  JFrame mainFrame = null;
  JLabel statusLabel = new JLabel("status");

  public MyPanelOld() {
    System.out.println("Starting");
    init();

    mainFrame = new JFrame("Schaakrobot v1.6");

    mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    mainFrame.getContentPane().add(this);
    mainFrame.pack();
    mainFrame.setLocationByPlatform(true);

    JButton b = new JButton("Restart & Update");
    b.setBounds(5, 20, 200, 40);
    b.addActionListener(e -> updateAndRestart());
    mainFrame.add(b);

    JButton bExit = new JButton("Restart");
    bExit.setBounds(210, 20, 200, 40);
    bExit.addActionListener(e -> System.exit(0));
    mainFrame.add(bExit);


    vertragingTextfield = new JTextField();
    vertragingTextfield.setBounds(410, 20, 100, 40);
    vertragingTextfield.setText("0100");
    mainFrame.add(vertragingTextfield);


    {
      JButton bHome = new JButton("Home 1");
      bHome.setBounds(5, 70, 200, 40);
      bHome.addActionListener(e -> home(arm1));
      mainFrame.add(bHome);
    }

    {
      JButton bHome = new JButton("Home 2");
      bHome.setBounds(210, 70, 200, 40);
      bHome.addActionListener(e -> home(arm2));
      mainFrame.add(bHome);
    }

    {
      JButton bHome = new JButton("Naar rust pos");
      bHome.setBounds(415, 70, 200, 40);
      bHome.addActionListener(e -> naarRustPos());
      mainFrame.add(bHome);
    }

    {
      tfArm1.setBounds(5, 120, 100, 40);
      tfArm1.setText("0");
      mainFrame.add(tfArm1);
      {
        JButton button = new JButton("Goto");
        button.setBounds(210, 120, 100, 40);
        button.addActionListener(e -> gotoPos(tfArm1, arm1, 0));
        mainFrame.add(button);
      }
      {
        JButton button = new JButton("-1000");
        button.setBounds(315, 120, 100, 40);
        button.addActionListener(e -> gotoPos(tfArm1, arm1, -1000));
        mainFrame.add(button);
      }
      {
        JButton button = new JButton("+1000");
        button.setBounds(420, 120, 100, 40);
        button.addActionListener(e -> gotoPos(tfArm1, arm1, +1000));
        mainFrame.add(button);
      }
      {
        JButton button = new JButton("-5000");
        button.setBounds(525, 120, 100, 40);
        button.addActionListener(e -> gotoPos(tfArm1, arm1, -5000));
        mainFrame.add(button);
      }
      {
        JButton button = new JButton("+5000");
        button.setBounds(630, 120, 100, 40);
        button.addActionListener(e -> gotoPos(tfArm1, arm1, +5000));
        mainFrame.add(button);
      }
    }
    {
      tfArm2.setBounds(5, 170, 100, 40);
      tfArm2.setText("0");
      mainFrame.add(tfArm2);
      {
        JButton button = new JButton("Goto");
        button.setBounds(210, 170, 100, 40);
        button.addActionListener(e -> gotoPos(tfArm2, arm2, 0));
        mainFrame.add(button);
      }
      {
        JButton button = new JButton("-1000");
        button.setBounds(315, 170, 100, 40);
        button.addActionListener(e -> gotoPos(tfArm2, arm2, -1000));
        mainFrame.add(button);
      }
      {
        JButton button = new JButton("+1000");
        button.setBounds(420, 170, 100, 40);
        button.addActionListener(e -> gotoPos(tfArm2, arm2, +1000));
        mainFrame.add(button);
      }
      {
        JButton button = new JButton("-5000");
        button.setBounds(525, 170, 100, 40);
        button.addActionListener(e -> gotoPos(tfArm2, arm2, -5000));
        mainFrame.add(button);
      }
      {
        JButton button = new JButton("+5000");
        button.setBounds(630, 170, 100, 40);
        button.addActionListener(e -> gotoPos(tfArm2, arm2, +5000));
        mainFrame.add(button);
      }
    }
    {
      final JTextField tf = new JTextField();
      tf.setBounds(5, 220, 100, 40);
      tf.setText("90");
      mainFrame.add(tf);
      {
        JButton button = new JButton("Goto");
        button.setBounds(210, 220, 100, 40);
        button.addActionListener(e -> gotoPosArm3(tf, 0, 1000));
        mainFrame.add(button);
      }
      {
        JButton button = new JButton("-10");
        button.setBounds(315, 220, 100, 40);
        button.addActionListener(e -> gotoPosArm3(tf, -10, 1000));
        mainFrame.add(button);
      }
      {
        JButton button = new JButton("+10");
        button.setBounds(420, 220, 100, 40);
        button.addActionListener(e -> gotoPosArm3(tf, +10, 1000));
        mainFrame.add(button);
      }
      {
        JButton button = new JButton("50");
        button.setBounds(525, 220, 100, 40);
        button.addActionListener(e -> gotoPosArm3Abs(tf, 50));
        mainFrame.add(button);
      }
      {
        JButton button = new JButton("100");
        button.setBounds(630, 220, 100, 40);
        button.addActionListener(e -> gotoPosArm3Abs(tf, 100));
        mainFrame.add(button);
      }
    }

    {
      JButton button = new JButton("p");
      button.setBounds(120, 120, 40, 40);
      button.addActionListener(e -> clamp());
      mainFrame.add(button);
    }
    {
      JButton button = new JButton("z");
      button.setBounds(165, 120, 40, 40);
      button.addActionListener(e -> release());
      mainFrame.add(button);
    }
    {
      JButton button = new JButton("sleep");
      button.setBounds(525, 320, 100, 40);
      button.addActionListener(e -> sleeping());
      mainFrame.add(button);
    }
    {
      JButton button = new JButton("status");
      button.setBounds(525, 370, 100, 40);
      button.addActionListener(e -> status());
      mainFrame.add(button);

      statusLabel.setBounds(630, 370, 100, 40);
      mainFrame.add(statusLabel);
    }


    {
      JTextArea textArea = new JTextArea(5, 20);
      JScrollPane scrollPane = new JScrollPane(textArea);
      scrollPane.setBounds(5, 270, 300, 80);
      textArea.setEditable(true);
      mainFrame.add(scrollPane);
      textArea.setText(loadFile());
      {
        JButton button = new JButton("save");
        button.setBounds(315, 260, 100, 20);
        button.addActionListener(e -> saveToFile(textArea.getText()));
        mainFrame.add(button);
      }
      {
        JButton button = new JButton("run once");
        button.setBounds(315, 285, 100, 20);
        button.addActionListener(e -> runOnceInThread(textArea.getText()));
        mainFrame.add(button);
      }
      {
        JButton button = new JButton("loop");
        button.setBounds(315, 370, 100, 40);
        button.addActionListener(e -> startLoop(textArea.getText()));
        mainFrame.add(button);
      }
      {
        JButton button = new JButton("stop");
        button.setBounds(420, 370, 100, 40);
        button.addActionListener(e -> stopLoop());
        mainFrame.add(button);
      }
    }

    mainFrame.setLayout(null);

    mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    mainFrame.setVisible(true);
    startReadStatus();
  }

  private void clamp(){
    System.out.println("clamp");
    try {
        arm3.write("^C0000000000000000".getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void release(){
    System.out.println("release");
    try {
        arm3.write("^R0000000000000000".getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void sleeping(){
    System.out.println("sleeping");
    try {
        arm1.write("^X0000000000000000".getBytes());
        arm2.write("^X0000000000000000".getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void startReadStatus(){
    currentLoopThread = new Thread(() -> status());
    currentLoopThread.start();

  }

  private String getStatusString(int status){
    if (status==0) return "HN";
    if (status==1) return "RE";
    if (status==2) return "MO";
    if (status==3) return "HO";
    if (status==4) return "ER";
    return "??";
  }

  private String getArm3StatusString(int status){
    if (status==0) return "RE";
    if (status==1) return "RE";
    if (status==2) return "MO";
    if (status==3) return "HO";
    if (status==4) return "ER";
    return "??";
  }


    private void status(){
    while(true) {
      System.out.println("status");
      try {
        int arm1Status = arm1.read();
        int arm2Status = arm2.read();
        int arm3Status = arm3.read();
        allReady = arm1Status==1 && arm2Status==1 && arm3Status!=2; // arm3 : alleen checken dat hij niet aan het moven is
        statusLabel.setText(getStatusString(arm1Status) + "-" + getStatusString(arm2Status) + "-" + getArm3StatusString(arm3Status)+":"+allReady);
        Thread.sleep(10);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }


  private void stopLoop() {
    if (currentLoopThread != null) {
      currentLoopThread.stop();
    }
  }

  private void startLoop(String text) {
    currentLoopThread = new Thread(() -> runInLoop(text));
    currentLoopThread.start();
  }

  private void runOnceInThread(String text) {
    new Thread(() -> runOnce(text)).start();
  }

  private void runInLoop(String text) {
    while (true) {
      runOnce(text);
    }
  }

  private void runOnce(String text) {
    String[] split = text.split("#");

    Arrays.asList(split).forEach(
        row -> {
          if (row != null && !row.startsWith("#")) {
            waitUntilReady(100);

            if (row.trim().startsWith("pak")){
              clamp();
//              try {
//                Thread.sleep(300);
//              }
//              catch (Exception ex){
//                ex.printStackTrace();
//              }
            }
            else if (row.trim().startsWith("zet")){
              release();
//              try {
//                Thread.sleep(300);
//              }
//              catch (Exception ex){
//                ex.printStackTrace();
//              }

            }
            else if (row.trim().startsWith("sleep")){
              sleeping();
//              try {
//                Thread.sleep(300);
//              }
//              catch (Exception ex){
//                ex.printStackTrace();
//              }

            }
            else {
              String[] splitWords = row.split(",");
              if (splitWords.length >= 3) {
                String posArm1 = splitWords[0].trim();
                String posArm2 = splitWords[1].trim();
                try {
                  int pos1 = Integer.parseInt(posArm1);
                  int pos2 = Integer.parseInt(posArm2);
//                  long totalTime = calcDelays(pos1, pos2);

                  formattedDelayFactor1 = vertragingTextfield.getText();
                  formattedDelayFactor2 = vertragingTextfield.getText();

//                  System.out.println("totalTime=" + totalTime);
                  System.out.println("formattedDelayFactor1=" + formattedDelayFactor1);
                  System.out.println("formattedDelayFactor2=" + formattedDelayFactor2);

                  gotoPos(arm1, pos1, formattedDelayFactor1);
                  gotoPos(arm2, pos2, formattedDelayFactor2);
                } catch (Exception ex) {
                  ex.printStackTrace();

                }
              }
            }

          }
        }
    );
  }

  private void waitUntilReady(int initialDelay) {
    sleep(initialDelay);
    while (!allReady){
      sleep(10);
    }

  }

  private void sleep(int initialDelay) {
    try {
      Thread.sleep(initialDelay);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void fullscreen(){
    device.setFullScreenWindow(mainFrame);
  }

  public long calcDelays(int pos1, int pos2) {
    int pulses1 = Math.abs(pos1 - lastPos1);
    int pulses2 = Math.abs(pos2 - lastPos2);

    Delays delays = BerekenVersnelling.calcDelays(pulses1, pulses2);

    double delayFactor1 = pulses1 == 0 ? 1  : delays.delay1;
    double delayFactor2 = pulses2 == 0 ? 1  : delays.delay2;

    if (delayFactor1>9999) delayFactor1 = 9999;
    if (delayFactor2>9999) delayFactor2 = 9999;

    formattedDelayFactor1 = String.format("%04d", (int)delayFactor1);
    formattedDelayFactor2 = String.format("%04d", (int)delayFactor2);
    return delays.totalTime;
  }


  private void saveToFile(String text) {
    Path path = Paths.get("/home/pi/loop.data");
    byte[] strToBytes = text.getBytes();
    try {
      Files.write(path, strToBytes);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String loadFile() {
    try {
      return new String(Files.readAllBytes(Paths.get("/home/pi/loop.data")));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "123";
  }

  public void init() {
    if (arm1 != null) { return; }
    try {
      I2CBus i2c = I2CFactory.getInstance(I2CBus.BUS_1);
      arm1 = i2c.getDevice(ARM1);
      arm2 = i2c.getDevice(ARM2);
      arm3 = i2c.getDevice(ARM3);
    } catch (UnsupportedBusNumberException e) {
      System.out.println("ERROR, UnsupportedBusNumberException in init");
    } catch (IOException e) {
      System.out.println("ERROR IOException in init:" + e.getMessage());
    }

  }

  private void gotoPos(JTextField tf, I2CDevice arm, int increment) {
    int pos = Integer.parseInt(tf.getText());
    int newPos = pos + increment;
    tf.setText("" + newPos);
    gotoPos(arm, newPos);
  }

  private void gotoPosAbs(JTextField tf, I2CDevice arm, int newPos) {
    tf.setText("" + newPos);
    gotoPos(arm, newPos);
  }

  private void gotoPosArm3(JTextField tf, int increment, long time) {
    int pos = Integer.parseInt(tf.getText());
    int newPos = pos + increment;
    tf.setText("" + newPos);
    gotoPosArm3(newPos, time);
  }

  private void gotoPosArm3Abs(JTextField tf, int pos) {
    tf.setText("" + pos);
    gotoPosArm3(pos, 1);
  }

  public void gotoPosArm3(int pos, long delay) {
    try {
      String formattedPos = String.format("%06d", pos);
      String time = String.format("%04d", delay);
      String command = "^S" + formattedPos + time;
      System.out.println("command:"+command);
      arm3.write(command.getBytes());
      lastPos3 = pos;
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      arm3.write("^S0000000000600000".getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }

    lastPos3 = pos;
  }


  public void gotoPos(I2CDevice arm, int pos) {
    gotoPos(arm, pos, vertragingTextfield.getText());
  }

  public void gotoPos(I2CDevice arm, int pos, String vertraging) {
    try {
      String formattedPos = String.format("%06d", pos);
      String command = "^M" + formattedPos + vertraging;
      System.out.println("command:"+command);
      if (arm != null) { arm.write(command.getBytes()); }
      if (arm == arm1) { lastPos1 = pos; }
      if (arm == arm2) { lastPos2 = pos; }

    } catch (IOException e) {
      e.printStackTrace();
    }


  }

  private void home(I2CDevice arm) {
    try {
      if (arm != null) { arm.write("^H0000000000600000".getBytes()); }
      if (arm == arm1) {
        tfArm1.setText("0");
        lastPos1 = 0;
      }
      if (arm == arm2) {
        tfArm2.setText("0");
        lastPos2 = 0;
      }



    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void naarRustPos() {
    gotoPosAbs(tfArm1, arm1,100);
    gotoPosAbs(tfArm2, arm2,100);
  }

  private void updateAndRestart() {
    try {
      PrintWriter writer = new PrintWriter("/tmp/rebuildui", "UTF-8");
      writer.close();
      System.exit(0);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
