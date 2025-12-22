#include <Arduino.h>
#include <WiFi.h>
#include <WebServer.h>
#define buzzerpin 7//5
#define switchpin 6//4
#define pull1 4//7
#define pull2 2
#define magnet1 5//6
#define magnet2 3


const byte address[6] = "00002";
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
int moveCount = 0;   // houdt aantal uitgevoerde moves bij

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
  moveCount++;            
  isBusy = false;
  server.send(200, "text/plain", String(moveCount));
}

void setup() {
  Serial.begin(9600);

  pinMode(pull1, OUTPUT);
  pinMode(pull2, OUTPUT);
  pinMode(magnet1, OUTPUT);
  pinMode(magnet2, OUTPUT);
  pinMode(buzzerpin , OUTPUT);
  pinMode(switchpin , INPUT_PULLUP);

  startBeep();

//alles uit
  analogWrite(magnet1, MAGNET_OFF);
  analogWrite(magnet2, MAGNET_OFF);
  digitalWrite(pull1, LOW);
  digitalWrite(pull2, LOW);
  digitalWrite(buzzerpin, LOW);





  // TCCR2B = TCCR2B & B11111000 | B00000001; // for PWM frequency of 31372.55 Hz, avoind zooming of the magnet

  Serial.println("Start wifi");

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
    beepLong();
  } else {
    Serial.println("WiFi NOT connected yet; continuing and web server will start anyway.");
    beepLong();
    beepLong();
    beepLong();
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
/*
 if (lastGrapTime1!=-1 && (currentTime-lastGrapTime1)>TIMEOUT_MAGNET){
    // drop piece
    analogWrite(magnet1, MAGNET_OFF);
    analogWrite(pull1, MAGNET_OFF);
    lastGrapTime1 = -1;
  }
  if (lastGrapTime2!=-1 && (currentTime-lastGrapTime2)>TIMEOUT_MAGNET){
    // drop piece
    analogWrite(magnet2, MAGNET_OFF);
    analogWrite(pull2, MAGNET_OFF);
    lastGrapTime2 = -1;
  }
  */
}


void pak1(){
       digitalWrite(pull1, HIGH);// arm 2
       analogWrite(magnet1, MAGNET_ON);// magneet 2
       shortDelay(100);
       digitalWrite(pull1, LOW);
       shortDelay(100);
       analogWrite(magnet1, MAGNET_HOLD);
       lastGrapTime2 = millis();
}

void pak2(){
       digitalWrite(pull2, HIGH);// arm 2
       analogWrite(magnet2, MAGNET_ON);// magneet 2
       shortDelay(100);
       digitalWrite(pull2, LOW);
       shortDelay(100);
       analogWrite(magnet2, MAGNET_HOLD);
       lastGrapTime2 = millis();
}

void zet1(){
       digitalWrite(pull1, HIGH);// arm 2
       shortDelay(30);
       analogWrite(magnet1, MAGNET_OFF);
       shortDelay(100);
       digitalWrite(pull1, LOW);
       lastGrapTime2 = -1;
}

void zet2(){
       digitalWrite(pull2, HIGH);// arm 2
       shortDelay(30);
       analogWrite(magnet2, MAGNET_OFF);
       shortDelay(100);
       digitalWrite(pull2, LOW);
       lastGrapTime2 = -1;
}


void testloop(){
  Serial.println("testloop start");
  pak1();
  shortDelay(1000);
  zet1();
  shortDelay(1000);
  pak2();
  shortDelay(1000);
  zet2();
  shortDelay(1000);
  Serial.println("testloop stop");

}


void beep(){
    digitalWrite(buzzerpin , HIGH);
    shortDelay(50);
    digitalWrite(buzzerpin , LOW);
    shortDelay(50);
}

void beepLong(){
    digitalWrite(buzzerpin , HIGH);
    shortDelay(500);
    digitalWrite(buzzerpin , LOW);
    shortDelay(500);
}

void connectedBeep(){
  beep();
  beep();
}

void startBeep() {
  beep();
  beep();
  beep();
  beep();
  beep();
  beep();
}

