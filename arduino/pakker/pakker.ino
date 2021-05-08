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

#include <Wire.h>
#include <SPI.h>
#include <RH_ASK.h>
#include <nRF24L01.h>
#include <RF24.h>

#define NR_OF_BYTES_TO_READ 12

#define READY 1
#define GRABBING 2
#define RELEASING 3

#define HOME_SPEED 120

#define magneetPin 3
#define pullMagneetPin 5

#define beepPin 9

#define adressPin1 10
#define adressPin2 11

// default RH_ASK rf_driver(2000, 11, 12, 10, false)
RH_ASK rf_driver(2000, 11, 4);


RF24 radio(7, 8); // CE, CSN
const byte address[6] = "00002";

int lastButton1 = 0;
int lastButton2 = 0;

  
char command;
int requestedPos;
int vertraginsfactor;
char number[50];
int state = READY;
int readPos = 0;
int SLAVE_ADDRESS = 5;

void setup() {

  TCCR2B = TCCR2B & B11111000 | B00000001; // for PWM frequency of 31372.55 Hz

  pinMode(pullMagneetPin, OUTPUT);
  pinMode(magneetPin, OUTPUT);


  pinMode(beepPin, OUTPUT);


  pinMode(adressPin1, INPUT);
  pinMode(adressPin1, INPUT);

  boolean addr1 = digitalRead(adressPin1);
  boolean addr2 = digitalRead(adressPin2);

  if (addr1) SLAVE_ADDRESS = SLAVE_ADDRESS+1;
  if (addr2) SLAVE_ADDRESS = SLAVE_ADDRESS+2;


  Serial.begin(9600);
  Serial.println("Starting pakker");
  Serial.print("Slave on adress:");
  Serial.println(SLAVE_ADDRESS);

  Wire.begin(SLAVE_ADDRESS);
  Wire.onReceive(receiveData);
  Wire.onRequest(sendData);


  // Initialize ASK Object
  rf_driver.init();
    
  // show that we have been restarted
  bootSeq();


  digitalWrite(beepPin, LOW);
  analogWrite(magneetPin, 0);
  analogWrite(pullMagneetPin, 0);
  
  const char *msg = "D";
  rf_driver.send((uint8_t *)msg, strlen(msg));
  rf_driver.waitPacketSent();  

  // 24g
  pinMode(4, INPUT);
  pinMode(5, INPUT);
  Serial.begin(9600);
  radio.begin();
  radio.openWritingPipe(address);
  radio.setPALevel(RF24_PA_MIN);
  radio.stopListening();
  Serial.println("start");  

  

}

void loop() {
  processCommand();
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
 if (command == 'C') clamp();
 if (command == 'R') release();
 if (command == 'H') hold();
 if (command == 'D') drop();
 if (command == 'A') activateMagnet();
 if (command == 'I') deactivateMagnet();
 if (command == 'H') hold();
 if (command == 'D') drop();
 if (command == 'A') activateMagnet();
 if (command == 'I') deactivateMagnet();
}

void hold(){
  Serial.print("clamp");
  analogWrite(pullMagneetPin, 255);

      const char text[] = "pak1";
      radio.write(&text, sizeof(text));
      Serial.println("pak1");
  
  command = '-';      
}

void drop(){
  Serial.print("clamp");
  analogWrite(pullMagneetPin, 0);

      const char text[] = "zet1";
      radio.write(&text, sizeof(text));
      Serial.println("zet1");
        
  command = '-';      
}

void activateMagnet(){
  Serial.print("clamp");
  analogWrite(magneetPin, 255);
  command = '-';      
}

void deactivateMagnet(){
  Serial.print("clamp");
  analogWrite(magneetPin, 0);
  command = '-';      
}

void clamp(){
  state = GRABBING;
  Serial.print("clamp");
  analogWrite(magneetPin, 0);
  analogWrite(pullMagneetPin, 255); 
  delay(10);
  analogWrite(magneetPin, 255); 
  delay(100);
  analogWrite(pullMagneetPin, 0); 
  delay(20);
  analogWrite(magneetPin, 150); 
  Serial.println("RESET CURRENT COMMAND");
  delay(50);
  state = READY;
  command = '-';

  const char *msg = "A";
  rf_driver.send((uint8_t *)msg, strlen(msg));
  rf_driver.waitPacketSent();
}

void release(){
  state = RELEASING;
  Serial.print("release");
  analogWrite(pullMagneetPin, 255); 
  delay(40);
  analogWrite(magneetPin, 0);
  delay(120);
  analogWrite(pullMagneetPin, 0); 
  delay(50);
  Serial.println("RESET CURRENT COMMAND");
  state = READY;
  command = '-';

  const char *msg = "B";
  rf_driver.send((uint8_t *)msg, strlen(msg));
  rf_driver.waitPacketSent();
  
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
