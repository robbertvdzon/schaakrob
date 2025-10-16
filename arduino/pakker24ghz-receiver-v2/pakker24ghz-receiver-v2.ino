
#include <Arduino.h>
#include <ArduinoBLE.h>   

#include <Adafruit_PWMServoDriver.h>
#define buzzerpin 4
#define switchpin 5


// RF24 radio(8,9); // CE, CSN
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

const char* DEVICE_NAME = "NanoESP32-Pakker";
BLEService service("6E400001-B5A3-F393-E0A9-E50E24DCCA9E"); // NUS-achtig
BLEStringCharacteristic rxChar(
  "6E400002-B5A3-F393-E0A9-E50E24DCCA9E",
  BLEWrite | BLEWriteWithoutResponse,
  32
);


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


  beep();

  // TCCR2B = TCCR2B & B11111000 | B00000001; // for PWM frequency of 31372.55 Hz, avoind zooming of the magnet

  // pwm.begin();
  // pwm.setPWMFreq(60);  // Analog servos run at ~60 Hz updates
  // pwm.setPWM(0, 0, SERVO_MIDDLE );
  // pwm.setPWM(1, 0, SERVO_MIDDLE );
  // pwm.setPWM(2, 0, SERVO_MIDDLE );
  // pwm.setPWM(3, 0, SERVO_MIDDLE );




  // if (!radio.begin()) {
  //   Serial.println(F("radio hardware is not responding!!"));
  //   errorbeep();
  //   while (1) {} // hold in infinite loop
  // }
  // radio.openReadingPipe(0, address);
  // radio.setChannel(108); // channel with less noise
  // radio.setPALevel(RF24_PA_LOW);
  // radio.setDataRate(RF24_250KBPS);
  // radio.setPayloadSize(sizeof("pak1"));
  // radio.setCRCLength(RF24_CRC_8);
  // radio.startListening();
  analogWrite(2, MAGNET_OFF);
  Serial.println("START");

    // --- BLE init (ArduinoBLE) ---
  if (!BLE.begin()) {
    Serial.println("BLE init failed"); while (1) { delay(1000); }
  }
  BLE.setLocalName(DEVICE_NAME);
  BLE.setAdvertisedService(service);
  service.addCharacteristic(rxChar);
  BLE.addService(service);
  BLE.advertise();

  Serial.println("START (BLE: pak1/pak2/zet1/zet2/beep/test)");

  errorbeep();

}

void handleCommand(const String& in) {
  String s = in;
  s.trim(); s.toLowerCase();
  Serial.print("BLE cmd: "); Serial.println(s);

  if (s == "pak1")      pak1();
  else if (s == "pak2") pak2();
  else if (s == "zet1") zet1();
  else if (s == "zet2") zet2();
  else if (s == "beep") errorbeep();
  else if (s == "test") testloop();
}


unsigned long lastGrapTime1 = 0;
unsigned long lastGrapTime2 = 0;
unsigned long currentTime = 0;

boolean oldButtonState = true;


void loop() {
  // Verbindingen afhandelen (ArduinoBLE pattern)
  BLEDevice central = BLE.central();

  // Wachten op writes: wanneer de central iets naar rxChar schrijft:
  if (rxChar.written()) {
    handleCommand(rxChar.value());   // rxChar.value() geeft String
  }

  // Knop
  // bool buttonState = digitalRead(SWITCH_PIN);
  // if (!buttonState && oldButtonState) {
  //   Serial.println("button pressed");
  //   beepShort();
  //   testloop();
  // }
  // oldButtonState = buttonState;

  // magneet time-outs
  // unsigned long now = millis();
  // if (lastGrapTime1 != -1 && (now - lastGrapTime1) > TIMEOUT_MAGNET) {
  //   magnetWrite(1, MAGNET_OFF);
  //   lastGrapTime1 = -1;
  // }
  // if (lastGrapTime2 != -1 && (now - lastGrapTime2) > TIMEOUT_MAGNET) {
  //   magnetWrite(2, MAGNET_OFF);
  //   lastGrapTime2 = -1;
  // }

  delay(1);
}


void loopOld() {
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


  // if (radio.available()) {
  //   char text[32] = "";
  //   radio.read(&text, sizeof(text));
  //   if(strcmp(text, "pak1") == 0){
  //     pak1();
  //   }
  //   if(strcmp(text, "zet1") == 0){
  //     zet1();
  //   }

  //   if(strcmp(text, "pak2") == 0){
  //     pak2();
  //   }

  //   if(strcmp(text, "zet2") == 0){
  //     zet2();
  //   }
  //   if(strcmp(text, "") != 0){
  //     Serial.println(text);
  //   }
  // }
}

void pak1(){
       pwm.setPWM(0, 0, SERVO_MIDDLE - PULSES_DOWN_ARM1 );
       pwm.setPWM(1, 0, SERVO_MIDDLE + PULSES_DOWN_ARM1 );
       delay(200);
       analogWrite(2, MAGNET_ON);
       delay(400);
       pwm.setPWM(0, 0, SERVO_MIDDLE);
       pwm.setPWM(1, 0, SERVO_MIDDLE);
       delay(200);
       analogWrite(2, MAGNET_HOLD);
       lastGrapTime1 = millis();
}

void pak2(){
       pwm.setPWM(2, 0, SERVO_MIDDLE - PULSES_DOWN_ARM2 );
       pwm.setPWM(3, 0, SERVO_MIDDLE + PULSES_DOWN_ARM2 );
       delay(200);
       analogWrite(3, MAGNET_ON);
       delay(400);
       pwm.setPWM(2, 0, SERVO_MIDDLE);
       pwm.setPWM(3, 0, SERVO_MIDDLE);
       delay(200);
       analogWrite(3, MAGNET_HOLD);
       lastGrapTime2 = millis();
}

void zet1(){
       pwm.setPWM(0, 0, SERVO_MIDDLE - PULSES_DOWN_ARM1 );
       pwm.setPWM(1, 0, SERVO_MIDDLE + PULSES_DOWN_ARM1 );
       delay(200);
       analogWrite(2, MAGNET_OFF);
       pwm.setPWM(0, 0, SERVO_MIDDLE);
       pwm.setPWM(1, 0, SERVO_MIDDLE);
       lastGrapTime1 = -1;
}

void zet2(){
       pwm.setPWM(2, 0, SERVO_MIDDLE - PULSES_DOWN_ARM2 );
       pwm.setPWM(3, 0, SERVO_MIDDLE + PULSES_DOWN_ARM2 );
       delay(200);
       analogWrite(3, MAGNET_OFF);
       pwm.setPWM(2, 0, SERVO_MIDDLE);
       pwm.setPWM(3, 0, SERVO_MIDDLE);
       lastGrapTime2 = -1;
}


void testloop(){
  Serial.println("testloop start");
  pak1();
  delay(300);
  zet1();
  delay(500);
  pak2();
  delay(300);
  zet2();

}


void errorbeep(){
  beep();
  beep();
  beep();
  beep();
}


void beep(){
    analogWrite(buzzerpin , 0);
    delay(200);
    analogWrite(buzzerpin , 255);
    delay(50);
}
