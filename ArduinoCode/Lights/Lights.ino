#include <Adafruit_NeoPixel.h>
#include <Wire.h>

#define PIN 6
#define NUMPIXELS 30

Adafruit_NeoPixel pixels = Adafruit_NeoPixel(NUMPIXELS, PIN, NEO_GRB + NEO_KHZ800);


//Blue = 1
//Red  = 2
int allianceColor;

void setup() {
  pixels.begin();
  Wire.begin(4);                // join i2c bus with address #8
  Wire.onReceive(receiveEvent); // register event
  Serial.begin(9600);           // start serial for output
}

void loop() {
  delay(100);
}

// function that executes whenever data is received from master
// this function is registered as an event, see setup()
void receiveEvent(int howMany) {
int input = Wire.read();

  //If the input defines an alliance
  if (input <= 2) {
    setAlliance(input)
  }

}

void setAlliance(int alliance){
  allianceColor = alliance;
}

