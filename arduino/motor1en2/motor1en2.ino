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

#define HOMING_NEEDED 9
#define READY 1
#define MOVING 2
#define HOMING 3
#define IN_ERROR 4
#define GOING_TO_SLEEP 5
#define SLEEPING 6
#define FATAL_ERROR 7

#define HOME_SPEED 120
#define HOME_SPEED_SLOW 240 //500

#define dirPin 3
#define stepPin 4 
#define stepPin2 5
#define stepsPerRevolution 2000
#define arm1SensorPin 6
#define arm2SensorPin 8
#define enableMotorPin 12
#define errorPin 9
#define adressPin1 10
#define fanPin 11
#define encoderPin1 2
#define encoderPin2 7

// vrije pinnen: 0,1,2,7 (eventueel pin 10 of 11)

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

int SLAVE_ADDRESS = 6;

// Parameters voor versnelling
float minDelay = 1000.0; // Start/stop vertraging (traag)
float maxDelay = 100.0;  // Minimale vertraging op topsnelheid (snel)
float accel = 2.0;       // Hoeveel microseconden er per stap van de delay afgaat

void setup() {

  pinMode(stepPin, OUTPUT);
  pinMode(stepPin2, OUTPUT);
  pinMode(dirPin, OUTPUT);
  pinMode(enableMotorPin, OUTPUT);
  pinMode(errorPin, OUTPUT);

  pinMode(arm1SensorPin, INPUT);
  pinMode(arm2SensorPin, INPUT);  
  pinMode(fanPin, OUTPUT);

  pinMode(adressPin1, INPUT);
//  pinMode(adressPin2, INPUT);

  pinMode(encoderPin1, INPUT);
  pinMode(encoderPin2, INPUT);

  
  digitalWrite(arm1SensorPin, HIGH); // turn on pull-up
  digitalWrite(arm2SensorPin, HIGH); // turn on pull-up
  digitalWrite(encoderPin1, HIGH); // turn on pull-up
  digitalWrite(encoderPin2, HIGH); // turn on pull-up


  boolean addr1 = digitalRead(adressPin1);
  if (addr1) SLAVE_ADDRESS = SLAVE_ADDRESS+1;


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
  digitalWrite(stepPin2, LOW);
  digitalWrite(enableMotorPin, HIGH);// motors uit bij starten
  digitalWrite(errorPin, LOW);

// fan aan
  digitalWrite(fanPin, HIGH);
  delay(3000);
  digitalWrite(fanPin, LOW);

// test home
//  home1(HOME_SPEED);  
   // testAll();


}

void testAll(){

  digitalWrite(enableMotorPin, LOW);
  error = false;
  digitalWrite(dirPin, LOW);



  beepLong();
  Serial.print("test move side1");
  for (int i = 0; i < 8000; i++) {
    pulse(stepPin, -1, 120);
  }
  beepLong();
  Serial.print("test move side2");
  for (int i = 0; i < 8000; i++) {
    pulse(-1, stepPin2, 120);
  }
  beepLong();

  Serial.print("read");
  while (true){
    boolean s1 = digitalRead(arm1SensorPin);
    boolean s2 = digitalRead(arm2SensorPin);
    boolean s3 = digitalRead(encoderPin1);
    boolean s4 = digitalRead(encoderPin2);
    Serial.print(s1);
    Serial.print(s2);
    Serial.print(s3);
    Serial.println(s4);
    delay(200);
  }



}

void loop() {
  /*
  int homeSensorOn = digitalRead(arm1SensorPin);
  int topSensorOn = digitalRead(arm2SensorPin);
  Serial.print(homeSensorOn);
  Serial.print(" ");
  Serial.print(topSensorOn);
  Serial.println("");
      delay(300);
      */

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
 if (command == 'H') home1(HOME_SPEED);
 if (command == 'M') move();
 if (command == 'X') sleeping();
 if (command == 'B') bootsound();
}

void home1(int homeSpeed){
  if (state == FATAL_ERROR) return;
  state = HOMING;
  Serial.println("home");
  Serial.print("cmd:");Serial.println(command);
  Serial.print("pos:");Serial.println(requestedPos);
  home(homeSpeed);
  state = READY;
  command = '-';
}

void move(){
  if (state == FATAL_ERROR) return;

  if (state == HOMING_NEEDED){
    home1(HOME_SPEED_SLOW);
  }
  if (state == SLEEPING){
    home1(HOME_SPEED_SLOW);
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
  if (state == FATAL_ERROR) return;

  boolean homeSensorOn = digitalRead(arm1SensorPin)==0 && currentPos>100;
  boolean topSensorOn = digitalRead(arm2SensorPin)==0 && currentPos>100;

  if (!homeSensorOn && !topSensorOn){
    if (error){
      Serial.println("Error fixed");
      error = false;
    }
  }
  else if (!error){
    Serial.print("Entering error mode while in state:");
    Serial.println(state);

    Serial.print("homeSensorOn=");
    Serial.println(homeSensorOn);
    Serial.print("topSensorOn=");
    Serial.println(topSensorOn);
    Serial.print("sensor1=");
    Serial.println(digitalRead(arm1SensorPin));
    Serial.print("sensor2=");
    Serial.println(digitalRead(arm2SensorPin));
    Serial.print("currentPos=");
    Serial.println(digitalRead(currentPos));


    error = true;
    state = HOMING_NEEDED;
    digitalWrite(enableMotorPin, HIGH);
    command = '-';
    errorMessage();
  }

}

void moveUp(int reqPos){
  Serial.println("MOVE UP");
  digitalWrite(enableMotorPin, LOW);
  digitalWrite(dirPin, HIGH);
  boolean succeeded = moveNrSteps(reqPos - currentPos, +1);
  if (!succeeded){
    Serial.println("HOME SLOW");
    state = HOMING;
    home(HOME_SPEED_SLOW);
    Serial.println("FINISHED HOME, MOVE AGAIN");
    state = MOVING;
    digitalWrite(dirPin, HIGH);
    succeeded = moveNrSteps(reqPos - currentPos, +1);
    if (!succeeded){
       Serial.println("FAILED AGAIN: FATAL ERROR");
       state = FATAL_ERROR;
    }
  }}

void moveDown(int reqPos){
  Serial.println("MOVE DOWN");
  digitalWrite(enableMotorPin, LOW);
  digitalWrite(dirPin, LOW);
  boolean succeeded = moveNrSteps(currentPos - reqPos, -1);
  if (!succeeded){
    Serial.println("HOME SLOW");
    state = HOMING;
    home(HOME_SPEED_SLOW);
    Serial.println("FINISHED HOME, MOVE AGAIN");
    state = MOVING;
    digitalWrite(dirPin, HIGH);
    succeeded = moveNrSteps(reqPos - currentPos, +1);
    if (!succeeded){
       Serial.println("FAILED AGAIN: FATAL ERROR");
       state = FATAL_ERROR;
    }
  }
}

bool moveNrSteps(int totalSteps, int direction){
  float currentDelay = minDelay; 
  int halfway = totalSteps / 2;
  int pulsesCounted1 = 0;
  int pulsesCounted2 = 0;
  int diffBetweenPulses = 0;
  bool lastEncodeSensorState1 = false;
  bool lastEncodeSensorState2 = false;

  for (int i = 0; i < totalSteps; i++) {
    bool currentEncodeSensorState1 = digitalRead(encoderPin1); // altijd sensor lezen (om de motor soepeler te laten lopen)
    bool currentEncodeSensorState2 = digitalRead(encoderPin2); // altijd sensor lezen (om de motor soepeler te laten lopen)
    if (i%30==0){
      if (currentEncodeSensorState1!=lastEncodeSensorState1){
        pulsesCounted1++;
        lastEncodeSensorState1=currentEncodeSensorState1;
      }
      if (currentEncodeSensorState2!=lastEncodeSensorState2){
        pulsesCounted2++;
        lastEncodeSensorState2=currentEncodeSensorState2;
      }
    }
    diffBetweenPulses = pulsesCounted1 - pulsesCounted2;

    if (diffBetweenPulses<-3 || diffBetweenPulses>3) {
         Serial.print("ERROR, too much difference: ");
         Serial.print(pulsesCounted1);
         Serial.print("/");
         Serial.println(pulsesCounted2);
         return false;// error status 
    }
    
    // 1. Bereken de delay voor de volgende stap
    if (i < halfway) {
      // Versnellen: verlaag de delay tot maxDelay bereikt is
      if (currentDelay > maxDelay) {
        currentDelay -= accel;
      }
    } else {
      // Vertragen: verhoog de delay weer richting minDelay
      if (currentDelay < minDelay) {
        currentDelay += accel;
      }
    }

    // 2. Beperk de delay binnen de grenzen
    if (currentDelay < maxDelay) currentDelay = maxDelay;
    if (currentDelay > minDelay) currentDelay = minDelay;

    // 3. Pas de vertragingsfactor toe
    long finalDelay = (long)((currentDelay * vertraginsfactor) / 100);

    // 4. Geef de puls
    pulse(stepPin, stepPin2, finalDelay);
    currentPos+=direction;
  }
  Serial.println("Move finished");
  Serial.print("Count1:");
  Serial.println(pulsesCounted1);
  Serial.print("Count2:");
  Serial.println(pulsesCounted2);
  analogWrite(errorPin, 0);          
  return true;
}

void home(int homeSpeed) {
  digitalWrite(enableMotorPin, LOW);
  error = false;

  arm1State = digitalRead(arm1SensorPin)==0;
  Serial.println("\t start homing");


// omhoog tot beide schakelaars uit zijn
  Serial.println("\t move slow up until not high");
  digitalWrite(dirPin, HIGH);
  int p1 = -1;
  int p2 = -1;
  while (digitalRead(arm1SensorPin)==0 || digitalRead(arm2SensorPin)==0){
    if (digitalRead(arm1SensorPin)==0) p1 = stepPin; else p1 = -1;
    if (digitalRead(arm2SensorPin)==0) p2 = stepPin2; else p2 = -1;
    pulse(p1, p2, homeSpeed);

  }

// omlaag tot beide schakelaars uit zijn
  Serial.println("\t move slow down until high");
  digitalWrite(dirPin, LOW);
  while ((!digitalRead(arm1SensorPin)==0) || (!digitalRead(arm2SensorPin)==0)){
    if (!digitalRead(arm1SensorPin)==0) p1 = stepPin; else p1 = -1;
    if (!digitalRead(arm2SensorPin)==0) p2 = stepPin2; else p2 = -1;
    pulse(p1, p2, homeSpeed);
  }

  Serial.println("\t homing finished");
  currentPos = 00;
  command = '-';

}


void sleeping() {
  if (state == FATAL_ERROR) return;
  state = GOING_TO_SLEEP;
  digitalWrite(enableMotorPin, LOW);
  error = false;

  arm1State = digitalRead(arm1SensorPin)==0;
  Serial.println("\t start sleeping");
  Serial.println(vertraginsfactor);

  // move down until high
  Serial.println("\t move fast down until high");
  digitalWrite(dirPin, LOW);

  float currentDelay = minDelay;
  int i = 0;
  while (!digitalRead(arm1SensorPin)==0){
    // Versnellen: verlaag de delay tot maxDelay bereikt is
    if (currentDelay > maxDelay) {
      currentDelay -= accel;
    }
    if (currentDelay < maxDelay) currentDelay = maxDelay;

    long finalDelay = (long)((currentDelay * vertraginsfactor) / 100);
    pulse(stepPin, stepPin2, finalDelay); // verreken vertraging!
    i++;
  }

  currentPos = 00;
  digitalWrite(enableMotorPin, HIGH);
  state = SLEEPING;
  command = '-';  
}

void bootsound(){
    beepLong();
    beepLong();
    command = '-';  
}

void bootSeq(){
    beep();
}

void errorMessage(){
  beepLong();
}

void beepLong(){
    analogWrite(errorPin, 150);
    delay(300);
    analogWrite(errorPin, 0);
    delay(100);
}

void beep(){
    analogWrite(errorPin, 5);
    delay(100);
    analogWrite(errorPin, 0);
    delay(100);
}


void pulse(int pin1, int pin2, long delaytime){
    checkError();
    if (!error){
      if (pin1>0) digitalWrite(pin1, HIGH);
      if (pin2>0) digitalWrite(pin2, HIGH);
      delayMicroseconds(delaytime);
      if (pin1>0) digitalWrite(pin1, LOW);
      if (pin2>0) digitalWrite(pin2, LOW);
      delayMicroseconds(delaytime);
    }
}
