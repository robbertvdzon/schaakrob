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

RF24 radio(7, 8); // CE, CSN
const byte address[6] = "00001";

const int SERVOMIN = 120; // this is the 'minimum' pulse length count (out of 4096)
const int SERVOMAX = 620; // this is the 'maximum' pulse length count (out of 4096)
const int SERVO_MIDDLE = (SERVOMAX-SERVOMIN)/2+SERVOMIN;
const int PULSES_DOWN = 200;

Adafruit_PWMServoDriver pwm= Adafruit_PWMServoDriver(0x40);

void setup() {
  Serial.begin(9600);


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
  Serial.println("START");

}



void loop() {
  if (radio.available()) {
    char text[32] = "";
    radio.read(&text, sizeof(text));
    if(strcmp(text, "pak1") == 0){
       digitalWrite(4, HIGH);
       pwm.setPWM(0, 0, SERVO_MIDDLE - PULSES_DOWN );
       pwm.setPWM(1, 0, SERVO_MIDDLE + PULSES_DOWN );
    }
    if(strcmp(text, "zet1") == 0){
       digitalWrite(4, LOW);
       pwm.setPWM(0, 0, SERVO_MIDDLE );
       pwm.setPWM(1, 0, SERVO_MIDDLE );
    }

    if(strcmp(text, "pak2") == 0){
       digitalWrite(4, HIGH);
       pwm.setPWM(2, 0, SERVO_MIDDLE - PULSES_DOWN );
       pwm.setPWM(3, 0, SERVO_MIDDLE + PULSES_DOWN );
    }
    if(strcmp(text, "zet2") == 0){
       digitalWrite(4, LOW);
       pwm.setPWM(2, 0, SERVO_MIDDLE );
       pwm.setPWM(3, 0, SERVO_MIDDLE );
    }    
    Serial.println(text);
  }
}
