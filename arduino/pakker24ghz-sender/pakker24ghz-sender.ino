/*
* Arduino Wireless Communication Tutorial
*     Example 1 - Transmitter Code
*
* by Dejan Nedelkovski, www.HowToMechatronics.com
*
* Library: TMRh20/RF24, https://github.com/tmrh20/RF24/
*/
#include <SPI.h>
#include <nRF24L01.h>
#include <RF24.h>
RF24 radio(7, 8); // CE, CSN
const byte address[6] = "00001";

int lastButton1 = 0;
int lastButton2 = 0;

void setup() {
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
  int button1 =  digitalRead(4);
  int button2 =  digitalRead(5);
  if (button1!=lastButton1){
    lastButton1 = button1;
    if (button1==0){
      const char text[] = "zet1";
      radio.write(&text, sizeof(text));
    }
    else{
      const char text[] = "pak1";
      radio.write(&text, sizeof(text));
    }
  }
  if (button2!=lastButton2){
    lastButton2 = button2;
    if (button2==0){
      const char text[] = "zet2";
      radio.write(&text, sizeof(text));
    }
    else{
      const char text[] = "pak2";
      radio.write(&text, sizeof(text));
    }
    

  }
}
