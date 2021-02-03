package com.vdzon.java.robotimpl;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;
import com.vdzon.java.BerekenVersnelling;
import com.vdzon.java.Delays;
import com.vdzon.java.robitapi.RobotAansturing;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;


public class RobotAansturingImpl implements RobotAansturing {
  private static int ARM1 = 0x8;//was 5
  private static int ARM2 = 0x6;// was 7
  private static int ARM3 = 0x5;// was 8
  int lastPos1 = 0;
  int lastPos2 = 0;

  String formattedDelayFactor1 = "0050";
  String formattedDelayFactor2 = "0050";
  private boolean allReady = false;

  private I2CDevice arm1 = null;
  private I2CDevice arm2 = null;
  private I2CDevice arm3 = null;
  private Thread currentLoopThread = null;

  public RobotAansturingImpl(){
    try {
      init();
    }
    catch (Exception e){
      e.printStackTrace();
    }
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


  @Override
  public void movetoVlak(String vlak) {
    System.out.println("move to vlak "+vlak);
    String posA8 = getA8();
    String posH1 = getH1();
    int xa = Integer.parseInt(posA8.split(",")[1]);
    int xh = Integer.parseInt(posH1.split(",")[1]);
    int y8 = Integer.parseInt(posA8.split(",")[0]);
    int y1 = Integer.parseInt(posH1.split(",")[0]);

    int xDelta = (xa-xh)/7;
    int yDelta = (y8-y1)/7;

    System.out.println("xDelta="+xDelta);
    System.out.println("yDelta="+yDelta);

    char letter = vlak.toUpperCase().charAt(0);
    char cijfer = vlak.toUpperCase().charAt(1);

    System.out.println("letter="+letter);
    System.out.println("cijfer="+cijfer);

    int x = xa;
    if (letter=='A') x = xa;
    if (letter=='B') x = xa-xDelta*1;
    if (letter=='C') x = xa-xDelta*2;
    if (letter=='D') x = xa-xDelta*3;
    if (letter=='E') x = xa-xDelta*4;
    if (letter=='F') x = xa-xDelta*5;
    if (letter=='G') x = xa-xDelta*6;
    if (letter=='H') x = xh;

    int y = y8;
    if (cijfer=='8') y = y8;
    if (cijfer=='7') y = y8-yDelta*1;
    if (cijfer=='6') y = y8-yDelta*2;
    if (cijfer=='5') y = y8-yDelta*3;
    if (cijfer=='4') y = y8-yDelta*4;
    if (cijfer=='3') y = y8-yDelta*5;
    if (cijfer=='2') y = y8-yDelta*6;
    if (cijfer=='1') y = y1;

    if (y>18500) y = 18500;
    if (y<100) y = 100;
    if (x>15000) x = 15000;
    if (x<100) x = 100;


    moveto(y,x);

  }

  @Override
  public void moveto(int x, int y) {
    System.out.println("move to pos "+x+","+y);

    calcDelays(x,y);
    gotoPos(arm1,x, formattedDelayFactor1);
    gotoPos(arm2,y, formattedDelayFactor2);
    waitUntilReady(100);
  }

  @Override
  public void homeVert() {
    home(arm1);

  }

  @Override
  public void homeHor() {
    home(arm2);

  }

  @Override
  public void sleep() {
    System.out.println("sleeping");
    moveto(100,100);
    try {
      arm1.write("^X0000000000000000".getBytes());
      arm2.write("^X0000000000000000".getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
    waitUntilReady(50);
  }

  public void gotoPos(I2CDevice arm, int pos, String vertraging) {
    try {
      System.out.println("gotopos:"+pos+" to "+arm.getAddress()+" vertraging:"+vertraging);
      String formattedPos = String.format("%06d", pos);
      String command = "^M" + formattedPos + vertraging;
      System.out.println("command:"+command+" to "+arm.getAddress());
      if (arm != null) { arm.write(command.getBytes()); }
      if (arm == arm1) { lastPos1 = pos; }
      if (arm == arm2) { lastPos2 = pos; }

    } catch (IOException e) {
      e.printStackTrace();
    }


  }


  @Override
  public void clamp(){
    System.out.println("clamp");
    try {
        arm3.write("^C0000000000000000".getBytes());
        Thread.sleep(400);
    } catch (Exception e) {
      e.printStackTrace();
    }

//    waitUntilReady(100);
  }

  @Override
  public void release(){
    System.out.println("release");
    try {
        arm3.write("^R0000000000000000".getBytes());
      Thread.sleep(400);
    } catch (Exception e) {
      e.printStackTrace();
    }
//    waitUntilReady(100);
  }

  @Override
  public void rebuild() {
    try {
      PrintWriter writer = new PrintWriter("/tmp/rebuildui", "UTF-8");
      writer.close();
      System.exit(0);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void restart() {
    System.exit(0);
  }

  @Override
  public String getA8() {
    return loadFile("/home/pi/a8.data");
  }

  @Override
  public void setA8(String pos) {
    saveToFile("/home/pi/a8.data", pos);
  }

  @Override
  public String getH1() {
    return loadFile("/home/pi/h1.data");
  }

  @Override
  public void setH1(String pos) {
    saveToFile("/home/pi/h1.data", pos);
  }

  @Override
  public String getDemoString() {
    return loadFile("/home/pi/loop.data");
  }

  @Override
  public void setDemoString(String demoString) {
    saveToFile("/home/pi/loop.data", demoString);
  }

  @Override
  public void runDemoOnce() {
    runOnceInThread(getDemoString());
  }

  @Override
  public void runDemoLoop() {
    startLoop(getDemoString());
  }

  @Override
  public void stopDemo() {
    stopLoop();
  }



  private void home(I2CDevice arm) {
    try {
      if (arm != null) { arm.write("^H0000000000600000".getBytes()); }
      if (arm == arm1) {
        lastPos1 = 0;
      }
      if (arm == arm2) {
        lastPos2 = 0;
      }



    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public long calcDelays(int pos1, int pos2) {
    int pulses1 = Math.abs(pos1 - lastPos1);
    int pulses2 = Math.abs(pos2 - lastPos2);

    System.out.println("pulses1="+pulses1);
    System.out.println("pulses2="+pulses2);

    Delays delays = BerekenVersnelling.calcDelays(pulses1, pulses2);

    double delayFactor1 = pulses1 == 0 ? 1  : delays.delay1;
    double delayFactor2 = pulses2 == 0 ? 1  : delays.delay2;

    if (delayFactor1>9999) delayFactor1 = 9999;
    if (delayFactor2>9999) delayFactor2 = 9999;

    // speedup 2x

//    delayFactor1 = delayFactor1/2;
//    delayFactor2 = delayFactor2/2;
    delayFactor1 = delayFactor1*2;
    delayFactor2 = delayFactor2*2;
//    delayFactor1 = delayFactor1*3;
//    delayFactor2 = delayFactor2*3;
    System.out.println("delayFactor1a="+delayFactor1);
    System.out.println("delayFactor2a="+delayFactor2);

    formattedDelayFactor1 = String.format("%04d", (int)delayFactor1);
    formattedDelayFactor2 = String.format("%04d", (int)delayFactor2);
    return delays.totalTime;
  }


  private void saveToFile(String filename, String text) {
    Path path = Paths.get(filename);
    byte[] strToBytes = text.getBytes();
    try {
      Files.write(path, strToBytes);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String loadFile(String filename) {
    try {
      return new String(Files.readAllBytes(Paths.get(filename)));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "1000,1000";
  }

  private void udateStatus(){
      try {
        int arm1Status = arm1.read();
        int arm2Status = arm2.read();
        int arm3Status = arm3.read();
        allReady = arm1Status==1 && arm2Status==1 && arm3Status!=2; // arm3 : alleen checken dat hij niet aan het moven is
        Thread.sleep(10);
      } catch (Exception e) {
        e.printStackTrace();
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

            if (row.trim().startsWith("@")){
              System.out.println("moveto:"+row);
              System.out.println("ss:"+row.trim().substring(1,3));
              movetoVlak(row.trim().substring(1,3));
            }
            if (row.trim().startsWith("pak")){
              clamp();
            }
            else if (row.trim().startsWith("zet")){
              release();
            }
            else if (row.trim().startsWith("sleep")){
              sleep();
            }
            else if (row.trim().startsWith("home")){
              homeHor();
              homeVert();
              waitUntilReady(100);
            }
            else {
              String[] splitWords = row.split(",");
              if (splitWords.length >= 3) {
                String posArm1 = splitWords[0].trim();
                String posArm2 = splitWords[1].trim();
                try {
                  int pos1 = Integer.parseInt(posArm1);
                  int pos2 = Integer.parseInt(posArm2);
                  calcDelays(pos1, pos2);
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
    udateStatus();
    while (!allReady){
      sleep(10);
      udateStatus();
    }

  }

  private void sleep(int initialDelay) {
    try {
      Thread.sleep(initialDelay);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
