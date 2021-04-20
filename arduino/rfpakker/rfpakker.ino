// Include RadioHead Amplitude Shift Keying Library
#include <RH_ASK.h>
// Include dependant SPI Library
#include <SPI.h>
#include <Wire.h>


#define magneetPin1 6
#define pullMagneetPin1 5
#define magneetPin2 4
#define pullMagneetPin2 3

// Create Amplitude Shift Keying Object
// default:RH_ASK (uint16_t speed=2000, uint8_t rxPin=11, uint8_t txPin=12, uint8_t pttPin=10, bool pttInverted=false)
// default RH_ASK rf_driver(2000, 11, 12, 10, false)
RH_ASK rf_driver(2000, 7);

void setup()
{
  pinMode(magneetPin1, OUTPUT);
  pinMode(pullMagneetPin1, OUTPUT);
  pinMode(magneetPin2, OUTPUT);
  pinMode(pullMagneetPin2, OUTPUT);



    digitalWrite(magneetPin1, HIGH);
    delay(100);
    digitalWrite(pullMagneetPin1, HIGH);
    delay(100);
    digitalWrite(magneetPin2, HIGH);
    delay(100);
    digitalWrite(pullMagneetPin2, HIGH);
    delay(100);
    digitalWrite(magneetPin1, LOW);
    delay(100);
    digitalWrite(pullMagneetPin1, LOW);
    delay(100);
    digitalWrite(magneetPin2, LOW);
    delay(100);
    digitalWrite(pullMagneetPin2, LOW);
    
    // Initialize ASK Object
    rf_driver.init();
    // Setup Serial Monitor
    Serial.begin(9600);
    Serial.print("Started!");


}

void loop()
{
    // Set buffer to size of expected message
    uint8_t buf[1];
    uint8_t buflen = sizeof(buf);
    // Check if received packet is correct size
    if (rf_driver.recv(buf, &buflen))
    {
      // Message received with valid checksum
      Serial.print("Message Received: ");
      Serial.println((char*)buf);

      if (buf[0]=='A'){
        digitalWrite(magneetPin1, HIGH);
        delay(200);
        digitalWrite(magneetPin1, LOW);
        delay(200);
        digitalWrite(magneetPin1, HIGH);
        delay(200);
        digitalWrite(magneetPin1, LOW);
      }
      else if (buf[0]=='B'){
        digitalWrite(magneetPin2, HIGH);
        delay(200);
        digitalWrite(magneetPin2, LOW);
        delay(200);
        digitalWrite(magneetPin2, HIGH);
        delay(200);
        digitalWrite(magneetPin2, LOW);
      }
      else if (buf[0]=='C'){
        digitalWrite(pullMagneetPin2, HIGH);
        delay(200);
        digitalWrite(pullMagneetPin2, LOW);
        delay(200);
        digitalWrite(pullMagneetPin2, HIGH);
        delay(200);
        digitalWrite(pullMagneetPin2, LOW);
      }
      else if (buf[0]=='D'){
        digitalWrite(pullMagneetPin2, HIGH);
        delay(200);
        digitalWrite(pullMagneetPin2, LOW);
        delay(200);
        digitalWrite(pullMagneetPin2, HIGH);
        delay(200);
        digitalWrite(pullMagneetPin2, LOW);
      }
      else{
        digitalWrite(magneetPin1, HIGH);
        digitalWrite(magneetPin2, HIGH);
        digitalWrite(pullMagneetPin1, HIGH);
        digitalWrite(pullMagneetPin2, HIGH);
        delay(200);
        digitalWrite(magneetPin1, LOW);
        digitalWrite(magneetPin2, LOW);
        digitalWrite(pullMagneetPin1, LOW);
        digitalWrite(pullMagneetPin2, LOW);
      }
    }
}
