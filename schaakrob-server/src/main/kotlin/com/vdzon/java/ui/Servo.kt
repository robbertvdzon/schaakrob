package com.vdzon.java.ui;

import com.pi4j.gpio.extension.pca.PCA9685GpioProvider;
import com.pi4j.gpio.extension.pca.PCA9685Pin;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;

import java.math.BigDecimal;

public class Servo {

  static final int STUUR_TIME_PER_STEP_IN_MSEC = 2;
  private static final int SERVO_DURATION_MIN = 900;
  private static final int SERVO_DURATION_NEUTRAL = 1500;
  private static final int SERVO_DURATION_MAX = 2100;
  private PCA9685GpioProvider provider = null;
  private boolean busy = false;
  private ServoRequest currentRequest = null;


  public Servo() {
    runThread();
  }

  private void init(){
    try {
      BigDecimal frequency = new BigDecimal("48.828");
      BigDecimal frequencyCorrectionFactor = new BigDecimal("1.0578");
      I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_1);
      provider = new PCA9685GpioProvider(bus, 0x40, frequency, frequencyCorrectionFactor);
      GpioPinPwmOutput[] myOutputs = provisionPwmOutputs(provider);
      provider.reset();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private synchronized void clearRequest(){
    currentRequest = null;
  }

  public synchronized void setRequest(int startPos, int eindPos, long time){
    com.vdzon.java.ui.ServoRequest servoRequest = new com.vdzon.java.ui.ServoRequest();
    servoRequest.startPos = startPos;
    servoRequest.eindPos = eindPos;
    servoRequest.time = time;
    currentRequest = servoRequest;
  }


  private synchronized com.vdzon.java.ui.ServoRequest getRequest(){
    return currentRequest;
  }


  public void runThread(){
    Thread thread = new Thread(() -> {
      busy = true;
      try {
        System.out.println("Start init in thread");
        init();
        while (true) {
          com.vdzon.java.ui.ServoRequest request = getRequest();
          if (request==null){
            sleep(5);
          }
          else{
            clearRequest();
            moveTo(request.startPos, request.eindPos, request.time);
          }
        }
      }
      catch (Exception ex){
        ex.printStackTrace();
      }
      busy = false;
    });
    thread.start();

  }


  private void moveTo(int oldPos, int newPos, long time) {
    int startPos = Math.max(oldPos, 900);
    int eindPos = Math.min(newPos, 2100);
    if (startPos==eindPos) return;
    int step = eindPos > startPos ? 1 : -1;
    int totalSteps = Math.abs(eindPos - startPos);
    int skipSteps = 1;
    long extraDelay = 0;
    long totalTime = totalSteps * STUUR_TIME_PER_STEP_IN_MSEC;
    if (totalTime > time) {
      skipSteps = (int) Math.ceil(totalTime / time);
    }
    int realSteps = totalSteps / skipSteps;
    long totalTimeWithSkip = realSteps * STUUR_TIME_PER_STEP_IN_MSEC;
    long timeDiff = time - totalTimeWithSkip;
    extraDelay = timeDiff / realSteps;
    System.out.println("startPos="+startPos);
    System.out.println("eindPos="+eindPos);
    System.out.println("step="+step);
    System.out.println("skipSteps="+skipSteps);
    System.out.println("extraDelay="+extraDelay);
    for (int p = startPos; p != eindPos; p += step) {
      if (p % skipSteps == 0) {
        try {
          provider.setPwm(PCA9685Pin.PWM_00, p);
        }
        catch (Exception ex){
          System.out.println("ERROR writing "+p+" : "+ ex.getMessage());
        }
        if (extraDelay > 0) {
          sleep(extraDelay);
        }
      }
    }
  }

  private static void sleep(long time) {
    try {
      Thread.sleep(time);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static GpioPinPwmOutput[] provisionPwmOutputs(final PCA9685GpioProvider gpioProvider) {
    GpioController gpio = GpioFactory.getInstance();
    GpioPinPwmOutput myOutputs[] = {
        gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_00, "Pulse 00"),
        gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_01, "Pulse 01"),
        gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_02, "Pulse 02"),
        gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_03, "Pulse 03"),
        gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_04, "Pulse 04"),
        gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_05, "Pulse 05"),
        gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_06, "Pulse 06"),
        gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_07, "Pulse 07"),
        gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_08, "Pulse 08"),
        gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_09, "Pulse 09"),
        gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_10, "Always ON"),
        gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_11, "Always OFF"),
        gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_12, "Servo pulse MIN"),
        gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_13, "Servo pulse NEUTRAL"),
        gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_14, "Servo pulse MAX"),
        gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_15, "not used")};
    return myOutputs;
  }

  public void home() {
    provider.setPwm(PCA9685Pin.PWM_00, 900);
  }
}
