/*

verschil met v1:
tijdens homen: motor altijd aan
tijdens niet-homen: bij homesensor==1 : zet motor in error


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

#define NR_OF_BYTES_TO_READ 12

#define HOMING_NEEDED 0
#define READY 1
#define MOVING 2
#define HOMING 3
#define IN_ERROR 4
#define GOING_TO_SLEEP 5
#define SLEEPING 6

#define HOME_SPEED 120

#define dirPin 3
#define stepPin 4 
#define stepsPerRevolution 2000
#define arm1SensorPin 6
#define topSensorPin 8
#define enableMotorPin 5
#define errorPin 9
#define adressPin1 10
#define adressPin2 11

char command;
int requestedPos;
int vertraginsfactor; 

char number[50];
int state = HOMING_NEEDED;
int readPos = 0;
int currentPos = 0;
int arm1State = 0;
int currentDir = -1;
int newDir = 0;
int turn = 0;
int homeFinished = 0;
bool error = 0;

int SLAVE_ADDRESS = 5;

// onderstaande 3 consts in gegenereerd in java
static const int delayList[] = {412,302,250,218,196,179,166,156,147,140,133,128,123,118,114,111,107,104,102,99,97,94,92,90,89,87,85,84,82,81,80,78,77,76,75,74,73,72,71,70,69,68,68,67,66,65,65,64,63,63,62,61,61,60,60,59,59,58,58,57,57,56,56,55,55,54,54,54,53,53,52,52,52,51,51,51,50,50,50,49,49,49,49,48,48,48,47,47,47,47,46,46,46,46,45,45,45,45,44,44,44,44,44,43,43,43,43,42,42,42,42,42,42,41,41,41,41,41,40,40,40,40,40,40};
static const int delayArraySize = 124;
static const int indexSteps = 20;
void setup() {

  pinMode(stepPin, OUTPUT);
  pinMode(dirPin, OUTPUT);
  pinMode(enableMotorPin, OUTPUT);
  pinMode(errorPin, OUTPUT);

  pinMode(arm1SensorPin, INPUT);
  pinMode(topSensorPin, INPUT);
  pinMode(adressPin1, INPUT);
  pinMode(adressPin1, INPUT);

  boolean addr1 = digitalRead(adressPin1);
  boolean addr2 = digitalRead(adressPin2);

  if (addr1) SLAVE_ADDRESS = SLAVE_ADDRESS+1;
  if (addr2) SLAVE_ADDRESS = SLAVE_ADDRESS+2;


  Serial.begin(9600);
  Serial.print("Slave on adress:");
  Serial.println(SLAVE_ADDRESS);

  Wire.begin(SLAVE_ADDRESS);
  Wire.onReceive(receiveData);
  Wire.onRequest(sendData);

  // show that we have been restarted
  bootSeq();

  // reset the pins
  digitalWrite(dirPin, LOW);
  digitalWrite(stepPin, LOW);
  digitalWrite(enableMotorPin, LOW);
  digitalWrite(errorPin, LOW);


}

void loop() {
  processCommand();
  checkError();
}

void sendData(){
  if (error){
    Wire.write(IN_ERROR);
  }
  else{
    Wire.write(state);
  }
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
  Serial.print("- vertraginsfactor:");Serial.println(vertraginsfactor);
  Serial.print("- currentPos:");Serial.println(currentPos);
}

void processCommand(){
 if (command == 'H') home1();
 if (command == 'M') move();
 if (command == 'X') sleeping();
}

void home1(){
  state = HOMING;
  Serial.println("home");
  Serial.print("cmd:");Serial.println(command);
  Serial.print("pos:");Serial.println(requestedPos);
  home();
  state = READY;
  command = '-';
}

void move(){

  if (state == HOMING_NEEDED || state == SLEEPING){
    home1();
  }

  Serial.println("Start moving");
  if (state!=GOING_TO_SLEEP){
    state = MOVING;
  }

  if (requestedPos>currentPos){
    moveUp(requestedPos);
  }
  else{
    moveDown(requestedPos);
  }

  if (state!=GOING_TO_SLEEP){
    state = READY;
  }
  command = '-';

}

void checkError(){

  if (state == HOMING) return;
  if (state == GOING_TO_SLEEP) return;
  if (state == SLEEPING) return;

  boolean homeSensorOn = digitalRead(arm1SensorPin)==1;
  boolean topSensorOn = digitalRead(topSensorPin)==1;

  if (!homeSensorOn && !topSensorOn){
    if (error){
      Serial.println("Error fixed");
      error = false;
    }
  }
  else if (!error){
    Serial.print("Entering error mode while in state:");
    Serial.println(state);
    error = true;
    state = HOMING_NEEDED;
    digitalWrite(enableMotorPin, HIGH);
    command = '-';
    errorMessage();
  }

}

void moveUp(int reqPos){
  digitalWrite(enableMotorPin, LOW);
  digitalWrite(dirPin, HIGH);
  long startTime = millis();
  moveNrSteps(reqPos - currentPos, +1);
  long totalTime = millis() - startTime;
}

void moveDown(int reqPos){
  digitalWrite(enableMotorPin, LOW);
  digitalWrite(dirPin, LOW);
  moveNrSteps(currentPos - reqPos, -1);
}

void moveNrSteps(int totalSteps, int direction){
  long halfway = totalSteps/2;
  int delayIndex = 0;
  int remainingDelayIndex = 0;
  int remainingSteps;
  double delay = 0;
  double calculatedDelay = 0;

  for (int i = 0; i < totalSteps; i++) {
    remainingSteps = totalSteps - i;
    delayIndex = i/indexSteps;
    remainingDelayIndex = remainingSteps/indexSteps;
    if (i==0 || i%indexSteps==0){
      if (i<halfway && delayIndex<delayArraySize) delay = delayList[delayIndex];
      if (i>halfway && remainingDelayIndex<delayArraySize) delay = delayList[remainingDelayIndex];
      float tmp = delay;
      tmp = tmp * vertraginsfactor;
      tmp = tmp / 100;
      calculatedDelay = (int) tmp;
    }
    pulse(stepPin, calculatedDelay); // verreken vertraging!
    currentPos+=direction;
  }
}

void home() {
  digitalWrite(enableMotorPin, LOW);
  error = false;

  arm1State = digitalRead(arm1SensorPin); 
  Serial.println("\t start homing");

  // move down until high   
  Serial.println("\t move fast down until high");
  digitalWrite(dirPin, LOW);
  while (!digitalRead(arm1SensorPin)){
    pulse(stepPin,HOME_SPEED);
  }

  // move up until not high   
  Serial.println("\t move slow up until not high");
  digitalWrite(dirPin, HIGH);
  while (digitalRead(arm1SensorPin)){
    pulse(stepPin,HOME_SPEED);
  }

  Serial.println("\t move klein stukje omhoog");
    for (int i = 0; i < 100; i++) {
      pulse(stepPin,HOME_SPEED);
  }
  
  Serial.println("\t homing finished");
  currentPos = 00;


}


void sleeping() {
  state = GOING_TO_SLEEP;
  digitalWrite(enableMotorPin, LOW);
  error = false;

  arm1State = digitalRead(arm1SensorPin);
  Serial.println("\t start homing");

  // move down until high
  Serial.println("\t move fast down until high");
  digitalWrite(dirPin, LOW);
  while (!digitalRead(arm1SensorPin)){
    pulse(stepPin,HOME_SPEED);
  }

  digitalWrite(enableMotorPin, HIGH);
  state = SLEEPING;
  command = '-';  
}

void bootSeq(){
    beep();
}

void errorMessage(){
  beepLong();
}

void beepLong(){
    digitalWrite(errorPin, HIGH);
    delay(300);
    digitalWrite(errorPin, LOW);
    delay(100);
}

void beep(){
    digitalWrite(errorPin, HIGH);
    delay(100);
    digitalWrite(errorPin, LOW);
    delay(100);
}


void pulse(int pin, long delaytime){
    checkError();
    if (!error){
      digitalWrite(pin, HIGH);
      delayMicroseconds(delaytime);
      digitalWrite(pin, LOW);
      delayMicroseconds(delaytime);
    }
}
