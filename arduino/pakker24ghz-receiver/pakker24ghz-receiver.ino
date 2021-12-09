/*
* Arduino Wireless Communication Tutorial
*       Example 1 - Receiver Code
*
* by Dejan Nedelkovski, www.HowToMechatronics.com
*
* Library: TMRh20/RF24, https://github.com/tmrh20/RF24/
*/
#include <SPI.h>
#include <nRF24L01.h>
#include <RF24.h>
#include <Adafruit_PWMServoDriver.h>

RF24 radio(8,9); // CE, CSN
const byte address[6] = "00002";

const int SERVOMIN = 120; // this is the 'minimum' pulse length count (out of 4096)
const int SERVOMAX = 620; // this is the 'maximum' pulse length count (out of 4096)
const int SERVO_MIDDLE = (SERVOMAX-SERVOMIN)/2+SERVOMIN;
const int PULSES_DOWN = 190;
const int TIMEOUT_MAGNET = 20000;

const int MAGNET_OFF = 0;
const int MAGNET_ON = 255;
const int MAGNET_HOLD = 130;


Adafruit_PWMServoDriver pwm= Adafruit_PWMServoDriver(0x40);

void setup() {
  Serial.begin(9600);


  pinMode(2, OUTPUT);
  pinMode(3, OUTPUT);
  pinMode(4, OUTPUT);

  analogWrite(2, MAGNET_OFF);
  analogWrite(3, MAGNET_OFF);
  
  pwm.begin();
  pwm.setPWMFreq(60);  // Analog servos run at ~60 Hz updates
  pwm.setPWM(0, 0, SERVO_MIDDLE );
  pwm.setPWM(1, 0, SERVO_MIDDLE );
  pwm.setPWM(2, 0, SERVO_MIDDLE );
  pwm.setPWM(3, 0, SERVO_MIDDLE );

  radio.begin();
  radio.openReadingPipe(0, address);
  radio.setPALevel(RF24_PA_MIN);
  radio.startListening();
  analogWrite(2, MAGNET_OFF);  
  Serial.println("START");

// test loop, remove this code!
 pwm.setPWM(0, 0, SERVO_MIDDLE - PULSES_DOWN );
 pwm.setPWM(1, 0, SERVO_MIDDLE + PULSES_DOWN );
 delay(200);
 analogWrite(2, MAGNET_ON);
 pwm.setPWM(0, 0, SERVO_MIDDLE);
 pwm.setPWM(1, 0, SERVO_MIDDLE);
 delay(400);
 pwm.setPWM(0, 0, SERVO_MIDDLE - PULSES_DOWN );
 pwm.setPWM(1, 0, SERVO_MIDDLE + PULSES_DOWN );
 delay(200);
 analogWrite(2, MAGNET_OFF);
 pwm.setPWM(0, 0, SERVO_MIDDLE);
 pwm.setPWM(1, 0, SERVO_MIDDLE);

// wacht
 delay(1000);
// test loop, remove this code!
 pwm.setPWM(2, 0, SERVO_MIDDLE - PULSES_DOWN );
 pwm.setPWM(3, 0, SERVO_MIDDLE + PULSES_DOWN );
 delay(200);
 analogWrite(3, MAGNET_ON);
 pwm.setPWM(2, 0, SERVO_MIDDLE);
 pwm.setPWM(3, 0, SERVO_MIDDLE);
 delay(400);
 pwm.setPWM(2, 0, SERVO_MIDDLE - PULSES_DOWN );
 pwm.setPWM(3, 0, SERVO_MIDDLE + PULSES_DOWN );
 delay(200);
 analogWrite(3, MAGNET_OFF);
 pwm.setPWM(2, 0, SERVO_MIDDLE);
 pwm.setPWM(3, 0, SERVO_MIDDLE);



}

unsigned long lastGrapTime1 = 0;
unsigned long lastGrapTime2 = 0;
unsigned long currentTime = 0;

void loop() {
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


  if (radio.available()) {
    char text[32] = "";
    radio.read(&text, sizeof(text));
    if(strcmp(text, "pak1") == 0){
       pwm.setPWM(0, 0, SERVO_MIDDLE - PULSES_DOWN );
       pwm.setPWM(1, 0, SERVO_MIDDLE + PULSES_DOWN );
       delay(200);
       analogWrite(2, MAGNET_ON);
       delay(200);
       pwm.setPWM(0, 0, SERVO_MIDDLE);
       pwm.setPWM(1, 0, SERVO_MIDDLE);
       delay(200);
       analogWrite(2, MAGNET_HOLD);
       lastGrapTime1 = millis();
    }
    if(strcmp(text, "zet1") == 0){
       pwm.setPWM(0, 0, SERVO_MIDDLE - PULSES_DOWN );
       pwm.setPWM(1, 0, SERVO_MIDDLE + PULSES_DOWN );
       delay(200);
       analogWrite(2, MAGNET_OFF);
       pwm.setPWM(0, 0, SERVO_MIDDLE);
       pwm.setPWM(1, 0, SERVO_MIDDLE);
       lastGrapTime1 = -1;
    }

    if(strcmp(text, "pak2") == 0){
       pwm.setPWM(2, 0, SERVO_MIDDLE - PULSES_DOWN );
       pwm.setPWM(3, 0, SERVO_MIDDLE + PULSES_DOWN );
       delay(200);
       analogWrite(3, MAGNET_ON);
       delay(200);
       pwm.setPWM(2, 0, SERVO_MIDDLE);
       pwm.setPWM(3, 0, SERVO_MIDDLE);
       delay(200);
       analogWrite(3, MAGNET_HOLD);
       lastGrapTime2 = millis();
    }
    if(strcmp(text, "zet2") == 0){
       pwm.setPWM(2, 0, SERVO_MIDDLE - PULSES_DOWN );
       pwm.setPWM(3, 0, SERVO_MIDDLE + PULSES_DOWN );
       delay(200);
       analogWrite(3, MAGNET_OFF);
       pwm.setPWM(2, 0, SERVO_MIDDLE);
       pwm.setPWM(3, 0, SERVO_MIDDLE);
       lastGrapTime2 = -1;
    }
    if(strcmp(text, "") != 0){
      Serial.println(text);
    }
  }
}
