
#include <Arduino.h>

#include <Adafruit_PWMServoDriver.h>
#define buzzerpin 4
#define switchpin 5


const byte address[6] = "00002";

const int SERVOMIN = 120; // this is the 'minimum' pulse length count (out of 4096)
const int SERVOMAX = 620; // this is the 'maximum' pulse length count (out of 4096)
const int SERVO_MIDDLE = (SERVOMAX-SERVOMIN)/2+SERVOMIN;
const int PULSES_DOWN_ARM1 = 220;
const int PULSES_DOWN_ARM2 = 185;
const int TIMEOUT_MAGNET = 20000;

const int MAGNET_OFF = 0;
const int MAGNET_ON = 255;
const int MAGNET_HOLD = 220;


Adafruit_PWMServoDriver pwm= Adafruit_PWMServoDriver(0x40);

void setup() {
  Serial.begin(9600);

  pinMode(2, OUTPUT);
  pinMode(3, OUTPUT);
  pinMode(4, OUTPUT);

  pinMode(buzzerpin , OUTPUT);
  pinMode(switchpin , INPUT_PULLUP);

  analogWrite(2, MAGNET_OFF);
  analogWrite(3, MAGNET_OFF);


  // TCCR2B = TCCR2B & B11111000 | B00000001; // for PWM frequency of 31372.55 Hz, avoind zooming of the magnet

  pwm.begin();
  pwm.setPWMFreq(60);  // Analog servos run at ~60 Hz updates
  pwm.setPWM(0, 0, SERVO_MIDDLE );
  pwm.setPWM(1, 0, SERVO_MIDDLE );
  pwm.setPWM(2, 0, SERVO_MIDDLE );
  pwm.setPWM(3, 0, SERVO_MIDDLE );

  analogWrite(2, MAGNET_OFF);
  Serial.println("START");

  startBeep();
  Serial.println("START (BLE: pak1/pak2/zet1/zet2/beep/test)");
}



unsigned long lastGrapTime1 = 0;
unsigned long lastGrapTime2 = 0;
unsigned long currentTime = 0;

boolean oldButtonState = true;



void shortDelay(unsigned long ms){
  unsigned long end = millis() + ms;
  while ((long)(end - millis()) > 0) {
    // handle any wifi call's if needed
    delay(1);
  }
}

void loop() {
  boolean buttonState = digitalRead(switchpin);
  if (!buttonState &&  oldButtonState){
    Serial.println("pressed");
    beep();
    testloop();
  }
  oldButtonState = buttonState;




  currentTime =millis();
  if (lastGrapTime1!=-1 && (currentTime-lastGrapTime1)>TIMEOUT_MAGNET){
    // drop piece
    analogWrite(2, MAGNET_OFF);
    lastGrapTime1 = -1;
  }
  if (lastGrapTime2!=-1 && (currentTime-lastGrapTime2)>TIMEOUT_MAGNET){
    // drop piece
    analogWrite(3, MAGNET_OFF);
    lastGrapTime2 = -1;
  }


void pak1(){
       pwm.setPWM(0, 0, SERVO_MIDDLE - PULSES_DOWN_ARM1 );
       pwm.setPWM(1, 0, SERVO_MIDDLE + PULSES_DOWN_ARM1 );
       shortDelay(500);// was 200
       analogWrite(2, MAGNET_ON);
       shortDelay(1000);// was 400
       pwm.setPWM(0, 0, SERVO_MIDDLE);
       pwm.setPWM(1, 0, SERVO_MIDDLE);
       shortDelay(800);// was 200
       analogWrite(2, MAGNET_HOLD);
       lastGrapTime1 = millis();
}

void pak2(){
       pwm.setPWM(2, 0, SERVO_MIDDLE - PULSES_DOWN_ARM2 );
       pwm.setPWM(3, 0, SERVO_MIDDLE + PULSES_DOWN_ARM2 );
       shortDelay(500);
       analogWrite(3, MAGNET_ON);
       shortDelay(1000);
       pwm.setPWM(2, 0, SERVO_MIDDLE);
       pwm.setPWM(3, 0, SERVO_MIDDLE);
       shortDelay(800);
       analogWrite(3, MAGNET_HOLD);
       lastGrapTime2 = millis();
}

void zet1(){
       pwm.setPWM(0, 0, SERVO_MIDDLE - PULSES_DOWN_ARM1 );
       pwm.setPWM(1, 0, SERVO_MIDDLE + PULSES_DOWN_ARM1 );
       shortDelay(800);// was 200
       analogWrite(2, MAGNET_OFF);
       shortDelay(500);// was 0
       pwm.setPWM(0, 0, SERVO_MIDDLE);
       pwm.setPWM(1, 0, SERVO_MIDDLE);
       lastGrapTime1 = -1;
}

void zet2(){
       pwm.setPWM(2, 0, SERVO_MIDDLE - PULSES_DOWN_ARM2 );
       pwm.setPWM(3, 0, SERVO_MIDDLE + PULSES_DOWN_ARM2 );
       shortDelay(800);
       analogWrite(3, MAGNET_OFF);
       shortDelay(500);// was 0
       pwm.setPWM(2, 0, SERVO_MIDDLE);
       pwm.setPWM(3, 0, SERVO_MIDDLE);
       lastGrapTime2 = -1;
}


void testloop(){
  Serial.println("testloop start");
  pak1();
  shortDelay(300);
  zet1();
  shortDelay(500);
  pak2();
  shortDelay(300);
  zet2();

}


void beep(){
    analogWrite(buzzerpin , 0);
    shortDelay(200);
    analogWrite(buzzerpin , 255);
    shortDelay(50);
}

void connectedBeep(){
  beepTime(5,500);
  shortDelay(50);
  beepTime(5,100);
  shortDelay(50);
  beepTime(5,100);
}

void startBeep() {
  beep();
  beep();
  beep();
}


void beepTime(int delayTime, int totalTime) {
  int beepCount = totalTime / delayTime;
  for (int i = 0; i < beepCount; i++) {
    analogWrite(buzzerpin, (i % 2 == 0) ? 255 : 0);
    shortDelay(delayTime);
  }
  analogWrite(buzzerpin, 255);
}
