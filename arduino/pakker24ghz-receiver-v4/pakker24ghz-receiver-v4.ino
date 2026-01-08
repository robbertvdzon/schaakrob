#include <Arduino.h>
#include <WiFi.h>
#include <WebSocketsServer.h>
#define buzzerpin 2
#define switchpin 6
#define pull1 3//4
#define pull2 7
#define magnet1 5//3
#define magnet2 4


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

WebSocketsServer ws(81);   // WebSocket server op poort 81
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
void handleAction(void (*action)(), uint8_t num) {
  if (isBusy) {
    ws.sendTXT(num, "buzy");
    return;
  }
  isBusy = true;
  action();
  moveCount++;
  isBusy = false;
  // WebSocketsServer::sendTXT in sommige versies verwacht een niet-const String&,
  // dus we vermijden het doorgeven van een tijdelijke String.
  String payload = String(moveCount);
  ws.sendTXT(num, payload);
}

void setup() {
  Serial.begin(9600);

  pinMode(pull1, OUTPUT);
  pinMode(pull2, OUTPUT);
  pinMode(magnet1, OUTPUT);
  pinMode(magnet2, OUTPUT);
  pinMode(buzzerpin , OUTPUT);
  pinMode(switchpin , INPUT_PULLUP);

  // Configureer PWM frequentie voor de magneten (Nano ESP32 specifiek)
  // Dit voorkomt het piepende geluid door de frequentie boven het hoorbare gebied te zetten.
  ledcAttach(magnet1, 25000, 8); // 25 kHz, 8-bit resolutie
  ledcAttach(magnet2, 25000, 8);

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
  // Reduce latency by disabling WiFi modem sleep (avoids 100–300ms wake delays)
  WiFi.setSleep(false);
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

  // WebSocket server starten
  ws.begin();
  // Minimize TCP latency (disable Nagle) if supported by the library version
  ws.onEvent([](uint8_t num, WStype_t type, uint8_t * payload, size_t length){
    if (type == WStype_TEXT) {
      String cmd = String((char*)payload).substring(0, length);
      cmd.trim();
      if (cmd == "pak1") { handleAction(pak1, num); return; }
      if (cmd == "pak2") { handleAction(pak2, num); return; }
      if (cmd == "zet1") { handleAction(zet1, num); return; }
      if (cmd == "zet2") { handleAction(zet2, num); return; }
      if (cmd == "connected") { handleAction(connectedBeep, num); return; }
      if (cmd == "status") { ws.sendTXT(num, isBusy ? "buzy" : "idle"); return; }
      ws.sendTXT(num, "unknown");
    }
  });
  Serial.println("WebSocket server started");
}



unsigned long lastGrapTime1 = 0;
unsigned long lastGrapTime2 = 0;
unsigned long currentTime = 0;

boolean oldButtonState = true;



void shortDelay(unsigned long ms){
  unsigned long end = millis() + ms;
  while ((long)(end - millis()) > 0) {
    // keep the websocket server responsive during delays
    ws.loop();
    delay(1);
  }
}

void loop() {
  // keep websocket server responsive
  ws.loop();

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

