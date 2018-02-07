#include <Adafruit_NeoPixel.h>
#include <Wire.h>

#define PIN 6
#define NUMPIXELS 30

Adafruit_NeoPixel pixels = Adafruit_NeoPixel(NUMPIXELS, PIN, NEO_GRB + NEO_KHZ800);

#define BLUE pixels.Color(0,0,255)
#define RED pixels.Color(255,0,0)


//Blue = 1
//Red  = 2
int allianceColor;

//Autonomous = 3
//Operator control = 4
//Disabled = 5
int stage;

void setup() {
  pixels.begin();
  Wire.begin(4);                // join i2c bus with address #8
  Wire.onReceive(receiveEvent); // register event
  Serial.begin(9600);           // start serial for output
}

void flashingTeam(int loops, uint32_t team){
  for(int k = 0; k <= loops; k++)
  {
    for(int i = 0; i <= NUMPIXELS; i++)
    {
      pixels.setPixelColor(i, pixels.Color(255,255,255));
    }
     pixels.show();
     delay(20);
      for(int j = 0; j <= NUMPIXELS; j++)
      {
        pixels.setPixelColor(j, team);
      }
      pixels.show();
  }
}

void christmasTree(int loops)
{
  for(int i = 0; i <= loops; i++)
  { 
    int rp = (int) (random(10,25));
  for(int j = 0; j <= rp; j++){
    int r = (int) (random(NUMPIXELS -1));
    int rr = (int) (random(170));
    int rg = (int) (random(170));
    int rb = (int) (random(170));
    pixels.setPixelColor(r, pixels.Color(rr,rg,rb));
    pixels.setPixelColor(r+1, pixels.Color(rr,rg,rb));
  }
  delay(500);
  pixels.show();
  }
}

void sharpness(uint32_t color, double percent)
{
  int red = (color>>16)&0x0ff;
  int green=(color>>8) &0x0ff;
  int blue= (color)    &0x0ff;
  for(int i = 0; i <= NUMPIXELS; i++){
    pixels.setPixelColor(i, pixels.Color(red * percent, green * percent, blue * percent));
  }
  pixels.show();
  delay(25);
}

void breathing(int loops){

  for(int j = 0; j < loops; j++){
    double pIncrease = .05;
    double pCurrent = 0;
    boolean inOrOut = false;
    uint32_t color = pixels.Color(235,59 ,3 );
    for(int i = 0; i < (1/pIncrease) * 2; i++){
      sharpness(color, pCurrent);
  
      if(!inOrOut){
        pCurrent += pIncrease;
      }else{
        pCurrent -= pIncrease;
      }
      if(pCurrent >= 1){
        inOrOut = true;
      }
    }
  }
}    
        
void sharpness(uint32_t color, double percent){
  uint32_t nColor = pixels.Color(0,0,0);
  for(int i = 0; i <= NUMPIXELS; i++){
    nColor = pixels.Color((int)(255 * percent), 0, 0);
    pixels.setPixelColor(i, nColor);
  }
  pixels.show();
}

void lightShow(){
  christmasTree(10);
  breathing(5);
  feakout(10);
    for(int i = 0; i < 2; i++){
  bounceChaseOpposite(BLUE, pixels.Color(0,0,50),32);
  bounceChaseOpposite(RED, pixels.Color(50,0,0), 32);
  }
  rainbowCycle(4);
  pixelRun(3, pixels.Color(50, 0, 0), RED, 25, 3);
  rainbowCycle(2);
  pixelRun(3, pixels.Color(0, 0, 50), BLUE, 25, 3);
  for(int i = 0; i < 2; i++){
  rainbowCycle(0);
  }

}


void solidColor(uint32_t color){
  for (int i = 0; i <= NUMPIXELS; i++){
    pixels.setPixelColor(i, color);
  }
  pixels.show();
}

void pixelRun(int running_pixels, uint32_t base_color, uint32_t moving_color, uint8_t wait, int loops) {
  solidColor(base_color);
  for (int j = 1; j <= loops; j++){
    
    for (int k = 0; k <= NUMPIXELS; k++){
      for (int l = 0; l < running_pixels; l++){
        if(k + l <= NUMPIXELS){
          pixels.setPixelColor(k + l, moving_color);
        } else {
          pixels.setPixelColor((k+l)%NUMPIXELS-1, moving_color);
        }
      }

      if(k-1 < 0){
        pixels.setPixelColor(NUMPIXELS, base_color);
      } else {
        pixels.setPixelColor(k-1, base_color);
      }
        pixels.show();
        delay(wait);
      }
    }
}

void bounceChaseIn(uint32_t bounce, uint32_t base, int wait){
  solidColor(base);
  for(int right = 0, left = NUMPIXELS; right <= NUMPIXELS/2; right++, left--){
    pixels.setPixelColor(right, bounce);
    pixels.setPixelColor(left, bounce);
    pixels.show();
    delay(wait);
    pixels.setPixelColor(right, base);
    pixels.setPixelColor(left, base);
  }
}
void bounceChaseOut(uint32_t bounce, uint32_t base, int wait) {
  solidColor(base);
  for(int right = NUMPIXELS/2-1, left = NUMPIXELS/2+1; right != 0; right--, left++){
    pixels.setPixelColor(right, bounce);
    pixels.setPixelColor(left, bounce);
    pixels.show();
    delay(wait);
    pixels.setPixelColor(right, base);
    pixels.setPixelColor(left, base);
  }
}

void bounceChase(uint32_t bounce, uint32_t base, int wait) {
  bounceChaseIn(bounce,base,wait);
  bounceChaseOut(bounce,base,wait);
}

void bounceChaseOpposite(uint32_t bounce, uint32_t base, int wait) {
  bounceChaseOut(bounce,base,wait);
  bounceChaseIn(bounce,base,wait);
}

// Input a value 0 to 255 to get a color value.
// The colours are a transition r - g - b - back to r.
uint32_t Wheel(byte WheelPos) {
  WheelPos = 255 - WheelPos;
  if(WheelPos < 85) {
    return pixels.Color(255 - WheelPos * 3, 0, WheelPos * 3);
  }
  if(WheelPos < 170) {
    WheelPos -= 85;
    return pixels.Color(0, WheelPos * 3, 255 - WheelPos * 3);
  }
  WheelPos -= 170;
  return pixels.Color(WheelPos * 3, 255 - WheelPos * 3, 0);
}

void rainbowCycle(uint8_t wait) {
  uint16_t i, j;

  for(j=0; j<256*5; j++) { // 5 cycles of all colors on wheel
    for(i=0; i< NUMPIXELS; i++) {
      pixels.setPixelColor(i, Wheel(((i * 256 / pixels.numPixels()) + j) & 255));
    }
    pixels.show();
    delay(wait);
  }
}
#define RAINBOW_SEIZURE rainbowCycle(0)

void loop() {
  //lightShow();
flashingTeam(10,RED);
  //christmasTree(10);
  //breathing();
  //For testing purposes.  

  //pixelRun(3, pixels.Color(50,0,0), pixels.Color(255,0,0), 25, 5);
}

// function that executes whenever data is received from master
// this function is registered as an event, see setup()
void receiveEvent(int howMany) {
  
int input = Wire.read();
  //If the input defines an alliance
  if (input == 1) {
    allianceColor = input;
    solidColor(BLUE);
  } else if (input == 2){
    allianceColor = input;
    solidColor(RED); 
  } else if (input == 3){
    pixelRun(1, pixels.Color(0,0,50), BLUE, 25, 1);
  } else if (input == 4) {
    pixelRun(1, pixels.Color(50,0,0), RED, 25, 1);
  } else if (input == 5) {
    rainbowCycle(4);
  } else if (input == 6) {
    rainbowCycle(2);
  } else if (input == 7) {
    RAINBOW_SEIZURE;
  }
}
