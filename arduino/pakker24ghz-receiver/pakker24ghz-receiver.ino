#include <Arduino.h>
#include <ArduinoBLE.h>              // <-- gebruik ArduinoBLE i.p.v. BLEDevice.h
#include <Wire.h>
#include <Adafruit_PWMServoDriver.h>

// --------- Pins ----------
#define MAGNET1_PIN 2
#define MAGNET2_PIN 3
#define BUZZER_PIN  4
#define SWITCH_PIN  5

const int MAGNET_OFF  = 0;     // duty 0..255
const int MAGNET_ON   = 255;
const int MAGNET_HOLD = 220;

// --------- Servo driver ----------
Adafruit_PWMServoDriver pwm = Adafruit_PWMServoDriver(0x40);
const int SERVOMIN = 120;
const int SERVOMAX = 620;
const int SERVO_MIDDLE = (SERVOMAX - SERVOMIN) / 2 + SERVOMIN;
const int PULSES_DOWN_ARM1 = 220;
const int PULSES_DOWN_ARM2 = 185;

// --------- Timers ----------
const unsigned long TIMEOUT_MAGNET = 20000;
long lastGrapTime1 = -1;
long lastGrapTime2 = -1;

// --------- BLE (ArduinoBLE) ----------
const char* DEVICE_NAME = "NanoESP32-Pakker";
BLEService service("6E400001-B5A3-F393-E0A9-E50E24DCCA9E"); // NUS-achtig
BLEStringCharacteristic rxChar(
  "6E400002-B5A3-F393-E0A9-E50E24DCCA9E",
  BLEWrite | BLEWriteWithoutResponse,
  32
);

// ======= Helpers =======
void setupMagnetPwm() {
  pinMode(MAGNET1_PIN, OUTPUT);
  pinMode(MAGNET2_PIN, OUTPUT);
  analogWrite(MAGNET1_PIN, MAGNET_OFF);
  analogWrite(MAGNET2_PIN, MAGNET_OFF);
}

void magnetWrite(int magnetIndex, int duty /*0..255*/) {
  duty = constrain(duty, 0, 255);
  if (magnetIndex == 1) analogWrite(MAGNET1_PIN, duty);
  else if (magnetIndex == 2) analogWrite(MAGNET2_PIN, duty);
}

// Buzzer met tone()/noTone()
void setupBuzzer() {
  pinMode(BUZZER_PIN, OUTPUT);
  noTone(BUZZER_PIN);
}

void buzzerTone(double freqHz) { tone(BUZZER_PIN, (unsigned int)freqHz); }
void buzzerOff() { noTone(BUZZER_PIN); }

// 2 s: 1 s sweep omhoog, 1 s omlaag
void beepMelody2s() {
  const int steps = 50;
  const double fStart = 500.0, fEnd = 2000.0;
  for (int i = 0; i <= steps; i++) {
    double f = fStart + (fEnd - fStart) * (double)i / steps;
    buzzerTone(f); delay(1000 / steps);
  }
  for (int i = 0; i <= steps; i++) {
    double f = fEnd - (fEnd - fStart) * (double)i / steps;
    buzzerTone(f); delay(1000 / steps);
  }
  buzzerOff();
}

void beepShort() { buzzerTone(1500); delay(80); buzzerOff(); delay(120); }

// ======= Acties =======
void pak1() {
  pwm.setPWM(0, 0, SERVO_MIDDLE - PULSES_DOWN_ARM1);
  pwm.setPWM(1, 0, SERVO_MIDDLE + PULSES_DOWN_ARM1);
  delay(200);
  magnetWrite(1, MAGNET_ON);
  delay(400);
  pwm.setPWM(0, 0, SERVO_MIDDLE);
  pwm.setPWM(1, 0, SERVO_MIDDLE);
  delay(200);
  magnetWrite(1, MAGNET_HOLD);
  lastGrapTime1 = millis();
}

void zet1() {
  pwm.setPWM(0, 0, SERVO_MIDDLE - PULSES_DOWN_ARM1);
  pwm.setPWM(1, 0, SERVO_MIDDLE + PULSES_DOWN_ARM1);
  delay(200);
  magnetWrite(1, MAGNET_OFF);
  pwm.setPWM(0, 0, SERVO_MIDDLE);
  pwm.setPWM(1, 0, SERVO_MIDDLE);
  lastGrapTime1 = -1;
}

void pak2() {
  pwm.setPWM(2, 0, SERVO_MIDDLE - PULSES_DOWN_ARM2);
  pwm.setPWM(3, 0, SERVO_MIDDLE + PULSES_DOWN_ARM2);
  delay(200);
  magnetWrite(2, MAGNET_ON);
  delay(400);
  pwm.setPWM(2, 0, SERVO_MIDDLE);
  pwm.setPWM(3, 0, SERVO_MIDDLE);
  delay(200);
  magnetWrite(2, MAGNET_HOLD);
  lastGrapTime2 = millis();
}

void zet2() {
  pwm.setPWM(2, 0, SERVO_MIDDLE - PULSES_DOWN_ARM2);
  pwm.setPWM(3, 0, SERVO_MIDDLE + PULSES_DOWN_ARM2);
  delay(200);
  magnetWrite(2, MAGNET_OFF);
  pwm.setPWM(2, 0, SERVO_MIDDLE);
  pwm.setPWM(3, 0, SERVO_MIDDLE);
  lastGrapTime2 = -1;
}

void testloop() {
  Serial.println("testloop start");
  pak1(); delay(300); zet1();
  delay(500);
  pak2(); delay(300); zet2();
}

// ======= Setup / Loop =======
bool oldButtonState = true;

void setup() {
  Serial.begin(115200);
  pinMode(SWITCH_PIN, INPUT_PULLUP);

  setupMagnetPwm();
  setupBuzzer();

  Wire.begin();
  pwm.begin();
  pwm.setPWMFreq(60);
  pwm.setPWM(0, 0, SERVO_MIDDLE);
  pwm.setPWM(1, 0, SERVO_MIDDLE);
  pwm.setPWM(2, 0, SERVO_MIDDLE);
  pwm.setPWM(3, 0, SERVO_MIDDLE);

  beepShort();

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
}

void handleCommand(const String& in) {
  String s = in;
  s.trim(); s.toLowerCase();
  Serial.print("BLE cmd: "); Serial.println(s);

  if (s == "pak1")      pak1();
  else if (s == "pak2") pak2();
  else if (s == "zet1") zet1();
  else if (s == "zet2") zet2();
  else if (s == "beep") beepMelody2s();
  else if (s == "test") testloop();
}

void loop() {
  // Verbindingen afhandelen (ArduinoBLE pattern)
  BLEDevice central = BLE.central();

  // Wachten op writes: wanneer de central iets naar rxChar schrijft:
  if (rxChar.written()) {
    handleCommand(rxChar.value());   // rxChar.value() geeft String
  }

  // Knop
  bool buttonState = digitalRead(SWITCH_PIN);
  if (!buttonState && oldButtonState) {
    Serial.println("button pressed");
    beepShort();
    testloop();
  }
  oldButtonState = buttonState;

  // magneet time-outs
  unsigned long now = millis();
  if (lastGrapTime1 != -1 && (now - lastGrapTime1) > TIMEOUT_MAGNET) {
    magnetWrite(1, MAGNET_OFF);
    lastGrapTime1 = -1;
  }
  if (lastGrapTime2 != -1 && (now - lastGrapTime2) > TIMEOUT_MAGNET) {
    magnetWrite(2, MAGNET_OFF);
    lastGrapTime2 = -1;
  }

  delay(1);
}
