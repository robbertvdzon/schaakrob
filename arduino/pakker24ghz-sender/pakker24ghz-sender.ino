/*

---
i2c protocol:
byte 0 : altijd ^
byte 1 : command (H = home, M = move)
byte 2 : to_pos (6 lang, max 999999)
byte 3 : to_pos 
byte 4 : to_pos 
byte 5 : to_pos 
byte 6 : to_pos 
byte 7 : to_pos 
byte 8: vertragings_factor (in percentage, dus 0135 = 1,35 keer zo snel, dus alle delays vermenigvuldigen met 1,35)
byte 9: vertragings_factor
byte 10: vertragings_factor
byte 11: vertragings_factor

states:
0: homing needed
1: ready
2: moving
3: homing
4: error (out of range)

send: state + pos

*/

#include <Wire.h>// kan dit weg?
#include <SPI.h> // kan dit weg?
//#include <nRF24L01.h>// kan dit weg?
#include <RF24.h>  // for documentation: https://nrf24.github.io/RF24/classRF24.html

#define NR_OF_BYTES_TO_READ 12

#define READY 1
#define GRABBING 2
#define RELEASING 3

// #define HOME_SPEED 120
//
// #define magneetPin 3
// #define pullMagneetPin 5
// #define magneet2PinA 4
// #define magneet2PinB 2
#define beepPin 9

// #define adressPin1 10
// #define adressPin2 11

char command;
// int requestedPos;
// int vertraginsfactor;
char number[50];
int state = READY;
int readPos = 0;
int SLAVE_ADDRESS = 5;

RF24 radio(7, 8); // CE, CSN
const byte address[6] = "00002";

// int lastButton1 = 0;
// int lastButton2 = 0;

void setup() {

  TCCR2B = TCCR2B & B11111000 | B00000001; // kan dit weg?

//   pinMode(pullMagneetPin, OUTPUT);
//   pinMode(magneetPin, OUTPUT);
//   pinMode(magneet2PinB, OUTPUT);
//   pinMode(magneet2PinA, OUTPUT);
  pinMode(beepPin, OUTPUT);


  pinMode(adressPin1, INPUT);
  pinMode(adressPin1, INPUT);

  boolean addr1 = digitalRead(adressPin1);
  boolean addr2 = digitalRead(adressPin2);

//  if (addr1) SLAVE_ADDRESS = SLAVE_ADDRESS+1;
//  if (addr2) SLAVE_ADDRESS = SLAVE_ADDRESS+2;


  Serial.begin(9600);
  Serial.println("Starting pakker");
  Serial.print("Slave on adress:");
  Serial.println(SLAVE_ADDRESS);

  Wire.begin(SLAVE_ADDRESS); // OUDE LIB? REMOVE?
  Wire.onReceive(receiveData); // OUDE LIB? REMOVE?
  Wire.onRequest(sendData); // OUDE LIB? REMOVE?

  // show that we have been restarted
  bootSeq();


  digitalWrite(beepPin, LOW);
//   analogWrite(magneetPin, 0);
//   analogWrite(pullMagneetPin, 0);
  
  
// new
//   pinMode(4, INPUT);
//   pinMode(5, INPUT);
  radio.begin();
  radio.openWritingPipe(address);
  radio.setPALevel(RF24_PA_MIN);
  radio.stopListening();

  Serial.println("radio ready");
}

void loop() {
      
  processCommand();
 // loop2();
}
/*
void loop2() {
  int button1 =  digitalRead(4);
  int button2 =  digitalRead(5);
  if (button1!=lastButton1){
    lastButton1 = button1;
    if (button1==0){
      const char text[] = "zet1";
      radio.write(&text, sizeof(text));
      Serial.println("zet1");
    }
    else{
      const char text[] = "pak1";
      radio.write(&text, sizeof(text));
      Serial.println("pak1");
    }
  }
  if (button2!=lastButton2){
    lastButton2 = button2;
    if (button2==0){
      const char text[] = "zet2";
      radio.write(&text, sizeof(text));
      Serial.println("zet2");
    }
    else{
      const char text[] = "pak2";
      radio.write(&text, sizeof(text));
      Serial.println("pak2");
    }
    

  }
}
*/
//
// void sendData(){
//    Wire.write(state);
// }
//
// void receiveData(int byteCount){
//   while(Wire.available()) {
//     processCharRead(Wire.read());
//   }
// }

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

  char buffer[7];

  buffer[0] = number[2];
  buffer[1] = number[3];
  buffer[2] = number[4];
  buffer[3] = number[5];
  buffer[4] = number[6];
  buffer[5] = number[7];
  buffer[6] = '\0';
  int toPos = atoi(buffer);

  buffer[0] = number[8];
  buffer[1] = number[9];
  buffer[2] = number[10];
  buffer[3] = number[11];
  buffer[4] = '\0';
  vertraginsfactor = atoi(buffer);
  if (vertraginsfactor<10){
    vertraginsfactor = 10;
  }


  requestedPos = toPos;
  command = number[1];

  Serial.print("- cmd:");Serial.println(command);
  Serial.print("- pos:");Serial.println(requestedPos);
}

void processCommand(){
 if (command == 'C') clamp1();
 if (command == 'R') release1();
//  if (command == 'H') hold();
//  if (command == 'D') drop();
//  if (command == 'A') activateMagnet();
//  if (command == 'I') deactivateMagnet();

 if (command == 'W') clamp2();
 if (command == 'E') release2();
}

// void hold(){
//   Serial.print("clamp");
//   analogWrite(pullMagneetPin, 255);
//   command = '-';
// }
//
// void drop(){
//   Serial.print("clamp");
//   analogWrite(pullMagneetPin, 0);
//   command = '-';
// }
//
// void activateMagnet(){
//   Serial.print("clamp");
//   analogWrite(magneetPin, 255);
//   command = '-';
// }
//
// void deactivateMagnet(){
//   Serial.print("clamp");
//   analogWrite(magneetPin, 0);
//   command = '-';
// }

void clamp1(){
  state = GRABBING;
  const char text[] = "pak1";
  radio.writeBlocking(&text, sizeof(text), 1000);
  radio.txStandBy(1000);
                                              // Blocks only until user timeout or success. Data flushed on fail.
  Serial.println("pak1");
  state = READY;
  command = '-';
}

void release1(){
  state = RELEASING;

  const char text[] = "zet1";
  radio.writeBlocking(&text, sizeof(text), 1000);
  radio.txStandBy(1000);
  Serial.println("zet1");
  state = READY;
  command = '-';
}

void clamp2(){
  state = GRABBING;
  const char text[] = "pak2";
  radio.writeBlocking(&text, sizeof(text), 1000);
  radio.txStandBy(1000);
  Serial.println("pak2");
  state = READY;
  command = '-';
}

void release2(){
  state = RELEASING;

  const char text[] = "zet2";
  radio.writeBlocking(&text, sizeof(text), 1000);
  radio.txStandBy(1000);
  Serial.println("zet2");
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
