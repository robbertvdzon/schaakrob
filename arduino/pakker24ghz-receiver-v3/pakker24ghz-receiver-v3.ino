
#include <Arduino.h>

#include <Adafruit_PWMServoDriver.h>
#include <WiFi.h>
#include <WebServer.h>
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

// WiFi configuration
const char* WIFI_SSID = "robbertkarenziggo";
const char* WIFI_PASSWORD = "Robbert12345!";
IPAddress local_IP(192, 168, 178, 10);
IPAddress gateway(192, 168, 178, 1);
IPAddress subnet(255, 255, 255, 0);

WebServer server(80);
volatile bool isBusy = false;

Adafruit_PWMServoDriver pwm= Adafruit_PWMServoDriver(0x40);

// Forward declarations for action functions so we can reference them in server handlers
void pak1();
void pak2();
void zet1();
void zet2();
void connectedBeep();
void beep();
void testloop();

// Helper to run an action if not busy; else reply "buzy"
void handleAction(void (*action)()) {
  if (isBusy) {
    server.send(200, "text/plain", "buzy");
    return;
  }
  isBusy = true;
  action();
  isBusy = false;
  server.send(200, "text/plain", "ok");
}

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
  Serial.println("START (WiFi REST: /pak1 /pak2 /zet1 /zet2 /connected /status)");

  // Connect to WiFi with static IP
  WiFi.mode(WIFI_STA);
  if (!WiFi.config(local_IP, gateway, subnet)) {
    Serial.println("WiFi config failed");
  }
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to WiFi ");
  Serial.print(WIFI_SSID);
  int tries = 0;
  while (WiFi.status() != WL_CONNECTED && tries < 100) { // ~10 seconds
    shortDelay(100);
    Serial.print(".");
    tries++;
  }
  Serial.println();
  if (WiFi.status() == WL_CONNECTED) {
    Serial.print("WiFi connected. IP: ");
    Serial.println(WiFi.localIP());
  } else {
    Serial.println("WiFi NOT connected yet; continuing and web server will start anyway.");
  }

  // REST endpoints
  server.on("/pak1", [](){ handleAction(pak1); });
  server.on("/pak2", [](){ handleAction(pak2); });
  server.on("/zet1", [](){ handleAction(zet1); });
  server.on("/zet2", [](){ handleAction(zet2); });
  server.on("/connected", [](){ handleAction(connectedBeep); });
  server.on("/status", [](){ server.send(200, "text/plain", isBusy ? "buzy" : "idle"); });
  server.onNotFound([](){ server.send(404, "text/plain", "not found"); });
  server.begin();
  Serial.println("HTTP server started");
}



unsigned long lastGrapTime1 = 0;
unsigned long lastGrapTime2 = 0;
unsigned long currentTime = 0;

boolean oldButtonState = true;



void shortDelay(unsigned long ms){
  unsigned long end = millis() + ms;
  while ((long)(end - millis()) > 0) {
    // keep the web server responsive during delays
    server.handleClient();
    delay(1);
  }
}

void loop() {
  // keep server responsive
  server.handleClient();

  boolean buttonState = digitalRead(switchpin);
  if (!buttonState &&  oldButtonState){
    Serial.println("pressed");
    beep();
    testloop();
  }
  oldButtonState = buttonState;

  currentTime = millis();
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
}


void pak1(){
       pwm.setPWM(0, 0, SERVO_MIDDLE - PULSES_DOWN_ARM1 );
       pwm.setPWM(1, 0, SERVO_MIDDLE + PULSES_DOWN_ARM1 );
       shortDelay(200);// was 200
       analogWrite(2, MAGNET_ON);
       shortDelay(400);// was 400
       pwm.setPWM(0, 0, SERVO_MIDDLE);
       pwm.setPWM(1, 0, SERVO_MIDDLE);
       shortDelay(200);// was 200
       analogWrite(2, MAGNET_HOLD);
       lastGrapTime1 = millis();
}

void pak2(){
       pwm.setPWM(2, 0, SERVO_MIDDLE - PULSES_DOWN_ARM2 );
       pwm.setPWM(3, 0, SERVO_MIDDLE + PULSES_DOWN_ARM2 );
       shortDelay(200);
       analogWrite(3, MAGNET_ON);
       shortDelay(400);
       pwm.setPWM(2, 0, SERVO_MIDDLE);
       pwm.setPWM(3, 0, SERVO_MIDDLE);
       shortDelay(200);
       analogWrite(3, MAGNET_HOLD);
       lastGrapTime2 = millis();
}

void zet1(){
       pwm.setPWM(0, 0, SERVO_MIDDLE - PULSES_DOWN_ARM1 );
       pwm.setPWM(1, 0, SERVO_MIDDLE + PULSES_DOWN_ARM1 );
       shortDelay(200);// was 200
       analogWrite(2, MAGNET_OFF);
       shortDelay(10);// was 0
       pwm.setPWM(0, 0, SERVO_MIDDLE);
       pwm.setPWM(1, 0, SERVO_MIDDLE);
       shortDelay(200);// was 0
       lastGrapTime1 = -1;
}

void zet2(){
       pwm.setPWM(2, 0, SERVO_MIDDLE - PULSES_DOWN_ARM2 );
       pwm.setPWM(3, 0, SERVO_MIDDLE + PULSES_DOWN_ARM2 );
       shortDelay(200);
       analogWrite(3, MAGNET_OFF);
       shortDelay(10);// was 0
       pwm.setPWM(2, 0, SERVO_MIDDLE);
       pwm.setPWM(3, 0, SERVO_MIDDLE);
       shortDelay(200);// was 0
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
