#include <Arduino.h>
#include <ArduinoBLE.h>

// Matcht jouw receiver:
const char* TARGET_NAME   = "NanoESP32-Pakker";
const char* SERVICE_UUID  = "6E400001-B5A3-F393-E0A9-E50E24DCCA9E";
const char* RX_CHAR_UUID  = "6E400002-B5A3-F393-E0A9-E50E24DCCA9E"; // Write / WriteNR

// Optioneel: knop om nogmaals te zenden
const int RESEND_BUTTON_PIN = 2;   // knop tussen pin en GND
bool sentOnce = false;

bool connectToReceiver(BLEDevice &peripheral) {
  Serial.println(F("Scanning for receiver..."));

  // Je kunt op naam of op service scannen. Naam is het makkelijkst:
  BLE.scan();  // algemene scan (werkt betrouwbaarder dan scanForName op sommige stacks)

  unsigned long t0 = millis();
  while (millis() - t0 < 15000) {     // 15s scan-venster, desnoods herhalen
    BLEDevice dev = BLE.available();
    if (dev) {
      // Debug
      Serial.print(F("Found: "));
      Serial.print(dev.address());
      Serial.print(F("  name="));
      Serial.println(dev.localName());

      if (dev.hasLocalName() && dev.localName() == TARGET_NAME) {
        BLE.stopScan();
        peripheral = dev;
        Serial.println(F("Receiver found by name."));
        break;
      }

      // Alternatief: op service-UUID matchen
      if (dev.hasAdvertisedServiceUuid() && dev.advertisedServiceUuid() == SERVICE_UUID) {
        BLE.stopScan();
        peripheral = dev;
        Serial.println(F("Receiver found by service UUID."));
        break;
      }
    }
  }

  if (!peripheral) {
    Serial.println(F("Receiver not found in scan window."));
    BLE.stopScan();
    return false;
  }

  Serial.print(F("Connecting to "));
  Serial.println(peripheral.address());
  if (!peripheral.connect()) {
    Serial.println(F("Connect failed."));
    return false;
  }
  Serial.println(F("Connected. Discovering attributes..."));

  if (!peripheral.discoverAttributes()) {
    Serial.println(F("Attribute discovery failed."));
    peripheral.disconnect();
    return false;
  }

  // Controleer dat service/characteristic bestaat
  if (!peripheral.hasService(SERVICE_UUID)) {
    Serial.println(F("Service not found on peripheral."));
    peripheral.disconnect();
    return false;
  }
  BLEService svc = peripheral.service(SERVICE_UUID);

  if (!svc.characteristic(RX_CHAR_UUID)) {
    Serial.println(F("RX characteristic not found."));
    peripheral.disconnect();
    return false;
  }

  Serial.println(F("Ready to write 'beep'."));
  return true;
}

bool sendCommandBeep(BLEDevice &peripheral) {
  BLEService svc = peripheral.service(SERVICE_UUID);
  BLECharacteristic rx = svc.characteristic(RX_CHAR_UUID);

  if (!rx) {
    Serial.println(F("Characteristic handle invalid."));
    return false;
  }

  bool canWrite = rx.canWrite();
  if (!canWrite) {
    Serial.println(F("Characteristic not writable."));
    return false;
  }

  // Stuur exact 'beep'
  bool ok = rx.writeValue("beep");   // je receiver leest BLEStringCharacteristic -> prima
  Serial.println(ok ? F("Sent: beep") : F("Write failed"));
  return ok;
}

void setup() {
  Serial.begin(115200);
  pinMode(RESEND_BUTTON_PIN, INPUT_PULLUP);

  if (!BLE.begin()) {
    Serial.println(F("BLE init failed"));
    while (1) delay(1000);
  }
  Serial.println(F("BLE central ready."));

  // Optioneel: central naam
  BLE.setLocalName("NanoESP32-Sender");
}

void loop() {
  static BLEDevice receiver;   // bewaar verbinding
  // (Her)verbinden wanneer niet verbonden en nog niet succesvol verzonden
  if (!receiver || !receiver.connected()) {
    receiver = BLEDevice(); // reset handle
    if (connectToReceiver(receiver)) {
      // Stuur meteen bij connect
      if (sendCommandBeep(receiver)) {
        sentOnce = true;
        // Je kunt hier desgewenst direct disconnecten:
        // receiver.disconnect();
      }
    } else {
      // even pauze, daarna opnieuw proberen
      delay(1000);
    }
  }

  // Handmatige resend via knop (active-low)
  if (receiver && receiver.connected()) {
    if (!digitalRead(RESEND_BUTTON_PIN)) {
      // simpele debounce
      delay(20);
      if (!digitalRead(RESEND_BUTTON_PIN)) {
        sendCommandBeep(receiver);
        // wacht tot losgelaten
        while (!digitalRead(RESEND_BUTTON_PIN)) delay(5);
      }
    }
  } else {
    // Niet verbonden: probeer straks opnieuw
    delay(200);
  }
}
