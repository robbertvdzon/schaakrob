/*

---
i2c protocol:
byte 0 : altijd ^
byte 1 : command
byte 2 : not used
byte 3 : not used
byte 4 : not used
byte 5 : not used
byte 6 : not used
byte 7 : not used

states:
0: initial
1: ready
2: grabbing
3: releaseing

*/

#include <Wire.h>
#include <RF24.h> // see https://github.com/tmrh20/RF24/

#define NR_OF_BYTES_TO_READ 12
#define READY 1
#define GRABBING 2
#define RELEASING 3
#define HOME_SPEED 120
#define beepPin 9


char command;
char number[50];
int state = READY;
int readPos = 0;
int SLAVE_ADDRESS = 5;

RF24 radio(7, 8); // CE, CSN
const byte address[6] = "00002";

void setup() {

  pinMode(beepPin, OUTPUT);

  Serial.begin(9600);
  Serial.println("Starting pakker");
  Serial.print("Slave on adress:");
  Serial.println(SLAVE_ADDRESS);

  Wire.begin(SLAVE_ADDRESS);
  Wire.onReceive(receiveData);
  Wire.onRequest(sendData);

  bootSeq();

  digitalWrite(beepPin, LOW);

  radio.begin();
  radio.openWritingPipe(address);
  radio.setPALevel(RF24_PA_MIN);
  radio.stopListening();
  Serial.println("radio ready");
}

void loop() {    
  processCommand();
}

void sendData(){
   Wire.write(state);
}

void receiveData(int byteCount){
  while(Wire.available()) {
    processCharRead(Wire.read());
  }
}

void processCharRead(char c){
  if (c == '^'){
    readPos = 0;
  }

  if (readPos < NR_OF_BYTES_TO_READ){
    number[readPos] = c;
    number[readPos+1] = '\0';
  }

  readPos ++;
  if (readPos == NR_OF_BYTES_TO_READ){
    parseCommand();
  }
}


void parseCommand(){
  Serial.print("parseCommand:");
  Serial.println(number);
  command = number[1];

  Serial.print("- cmd:");Serial.println(command);
}

void processCommand(){
 if (command == 'C') clamp1();
 if (command == 'R') release1();
 if (command == 'W') clamp2();
 if (command == 'E') release2();
}

void clamp1(){
  state = GRABBING;
  Serial.println("grabbing arm 1");
  const char text[] = "pak1";
  bool result = radio.write(&text, sizeof(text));
  if (result){
    Serial.println("grabbed");
  }
  else{
    Serial.println("grabbing failed");
  }
  state = READY;
  command = '-';
}

void release1(){
  state = RELEASING;
  Serial.println("releasing arm 1");
  const char text[] = "zet1";
  bool result = radio.write(&text, sizeof(text));
  if (result){
    Serial.println("released");
  }
  else{
    Serial.println("releasing failed");
  }
  state = READY;
  command = '-';
}

void clamp2(){
  state = GRABBING;
  Serial.println("grabbing arm 2");
  const char text[] = "pak2";
  bool result = radio.write(&text, sizeof(text));
  if (result){
    Serial.println("grabbed");
  }
  else{
    Serial.println("grabbing failed");
  }

  state = READY;
  command = '-';
}

void release2(){
  state = RELEASING;

  Serial.println("releasing arm 2");
  const char text[] = "zet2";
  bool result = radio.write(&text, sizeof(text));
  if (result){
    Serial.println("released");
  }
  else{
    Serial.println("releasing failed");
  }
  state = READY;
  command = '-';
}

void bootSeq(){
  beep();
  beep();
  command = '-';    
}

void beep(){
    digitalWrite(beepPin, HIGH);
    delay(100);
    digitalWrite(beepPin, LOW);
    delay(100);
}
