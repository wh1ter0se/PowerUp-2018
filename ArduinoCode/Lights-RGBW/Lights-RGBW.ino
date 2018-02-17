#include <Adafruit_NeoPixel.h>
#include <Wire.h>

#define PIN 6
#define NUMPIXELS 60

Adafruit_NeoPixel pixels = Adafruit_NeoPixel(NUMPIXELS, PIN, NEO_GRBW + NEO_KHZ800);

#define BLUE pixels.Color(0,0,255)
#define WHITE pixels.Color(0,0,0,255)
#define RED pixels.Color(255,0,0)
#define VIOLET pixels.Color(148,0,211)
#define INDIGO pixels.Color(75,0,130)
#define GREEN pixels.Color(0,255,0)
#define YELLOW pixels.Color(255,175,0)
#define ORANGE pixels.Color(255,69,0)


//Blue = 1
//Red  = 2
int allianceColor;

//Autonomous = 3
//Operator control = 4
//Disabled = 5
int stage;

void setup() {
  pixels.begin();
  //  Wire.begin(4);                // join i2c bus with address #8
  //  Wire.onReceive(receiveEvent); // register event
  //  Serial.begin(9600);           // start serial for output
  randomSeed(analogRead(0));

}

void wingz(uint8_t wait, int loops, int skipAmount, uint32_t bounce) {
  for(int i = 0; i <= NUMPIXELS; i++){
    pixels.setPixelColor(i, bounce);
    pixels.setPixelColor(i+skipAmount, bounce);
    pixels.show();
    if (i > 0){
      pixels.setPixelColor(i-1, pixels.Color(0,0,0,0));
      pixels.show();
    }
    pixels.setPixelColor(i+skipAmount-1, pixels.Color(0,0,0,0));
    pixels.show();
    delay(wait);
  }
}

void rgbChase(int running_pixels, uint32_t base_color, uint8_t wait, int loops) {
  solidColor(base_color);
  for (int j = 1; j <= loops; j++) {
    for (int k = 0; k <= NUMPIXELS; k++) {
      for (int l = 0; l < running_pixels; l++) {
        if (k + 3 <= NUMPIXELS) {
          pixels.setPixelColor(k + l, RED);
          pixels.setPixelColor(k + 2, GREEN);
          pixels.setPixelColor(k + 3, BLUE);
        } else {
          pixels.setPixelColor((k + l) % NUMPIXELS - 1, RED);
          pixels.setPixelColor((k + 2) % NUMPIXELS - 1, GREEN);
          pixels.setPixelColor((k + 3) % NUMPIXELS - 1, BLUE);
        }
      }

      if (k - 1 < 0) {
        pixels.setPixelColor(NUMPIXELS, base_color);
      } else {
        pixels.setPixelColor(k - 1, base_color);
      }
      pixels.show();
      delay(wait);
    }
  }
}

void whiteOverRainbow(uint8_t wait, uint8_t whiteSpeed, uint8_t whiteLength ) {

  if (whiteLength >= pixels.numPixels()) whiteLength = pixels.numPixels() - 1;

  int head = whiteLength - 1;
  int tail = 0;

  int loops = 3;
  int loopNum = 0;

  static unsigned long lastTime = 0;


  while (true) {
    for (int j = 0; j < 256; j++) {
      for (uint16_t i = 0; i < pixels.numPixels(); i++) {
        if ((i >= tail && i <= head) || (tail > head && i >= tail) || (tail > head && i <= head) ) {
          pixels.setPixelColor(i, pixels.Color(0, 0, 0, 255 ) );
        }
        else {
          pixels.setPixelColor(i, Wheel(((i * 256 / pixels.numPixels()) + j) & 255));
        }

      }

      if (millis() - lastTime > whiteSpeed) {
        head++;
        tail++;
        if (head == pixels.numPixels()) {
          loopNum++;
        }
        lastTime = millis();
      }

      if (loopNum == loops) return;

      head %= pixels.numPixels();
      tail %= pixels.numPixels();
      pixels.show();
      delay(wait);
    }
  }

}

void rainbowChase(int loops, int wait) {
  for (int i = 0; i < loops; i++)
  {
    buildingChase(50, 1, RED);
    buildingChase(50, 1, ORANGE);
    buildingChase(50, 1, YELLOW);
    buildingChase(50, 1, GREEN);
    buildingChase(50, 1, BLUE);
    buildingChase(50, 1, INDIGO);
    buildingChase(50, 1, VIOLET);
  }
}

void Merica(int loops) {
  for (int l = 0; l < loops; l++) {
    if (l % 2 == 0)
    {
      for (int i = 24; i < NUMPIXELS; i++) {
        if (i % 2 == 0) {
          pixels.setPixelColor(i, pixels.Color(0, 0, 0, 175));
        }
        else {
          pixels.setPixelColor(i, RED);
        }
      }
      pixels.show();
    }
  }
  for (int j = 0; j < loops; j++) {
    if (j % 2 == 0)
    {
      for (int i = 0; i < 24; i++) {
        if (i % 2 == 0) {
          pixels.setPixelColor(i, pixels.Color(0, 0, 0, 175));
        }
        else {
          pixels.setPixelColor(i, BLUE);
        }
      }
      pixels.show();
      delay(125);
    }
    else {
      {
        for (int i = 0; i < 24; i++) {
          if (i % 2 == 0) {
            pixels.setPixelColor(i, BLUE);
          }
          else {
            pixels.setPixelColor(i, pixels.Color(0, 0, 0, 175));
          }
        }
        pixels.show();
        delay(125);
      }
    }
  }
}


void redBlueChase(int loops) {
  for (int i = 0; i < loops; i++) {
    pixelRun(3, pixels.Color(50, 0, 0), RED, 25, 1);
    pixelRun(3, pixels.Color(0, 0, 50), BLUE, 25, 1);
  }
}

void flashingTeamOff(int loops, uint32_t team) {
  for (int k = 0; k <= loops; k++)
  {
    for (int i = 0; i <= NUMPIXELS; i++)
    {
      pixels.setPixelColor(i, pixels.Color(0, 0, 0, 0));
    }
    pixels.show();
    delay(500);
    for (int j = 0; j <= NUMPIXELS; j++)
    {
      pixels.setPixelColor(j, team);
    }
    pixels.show();
    delay(500);
  }
}

void buildingChase(int wait, int loops, uint32_t color) {
  for (int j = 0; j < loops; j++) {
    for (int i = 0; i <= NUMPIXELS; i++) {
      pixels.setPixelColor(i, color);
      pixels.show();
      delay(wait);
    }
  }
}


void flashingTeam(int loops, uint32_t team) {
  for (int k = 0; k <= loops; k++)
  {
    for (int i = 0; i <= NUMPIXELS; i++)
    {
      pixels.setPixelColor(i, pixels.Color(0, 0, 0, 255));
    }
    pixels.show();
    delay(500);
    for (int j = 0; j <= NUMPIXELS; j++)
    {
      pixels.setPixelColor(j, team);
    }
    pixels.show();
    delay(500);
  }
}

void christmasTree(int loops)
{
  for (int i = 0; i <= loops; i++)
  {
    int rp = (int) (random(10, 25));
    for (int j = 0; j <= rp; j++) {
      int r = (int) (random(NUMPIXELS - 1));
      int rr = (int) (random(170));
      int rg = (int) (random(170));
      int rb = (int) (random(170));
      pixels.setPixelColor(r, pixels.Color(rr, rg, rb));
      pixels.setPixelColor(r + 1, pixels.Color(rr, rg, rb));
    }
    delay(500);
    pixels.show();
  }
}

void RandomRapidSinglePixels(int loops) {
  for (int i = 0; i <= loops; i++)
  {
    for (int h = 0; h <= NUMPIXELS; h++) {
      pixels.setPixelColor(h, pixels.Color(0, 0, 0, 0));
      pixels.setPixelColor(h + 1, pixels.Color(0, 0, 0, 0));
    }
    for (int j = 0; j <= 10; j++) {
      pixels.setPixelColor((int)(random(NUMPIXELS - 1)), pixels.Color(0, 0, 0, 255));
      pixels.show();
    }
    delay(40);
  }
}

void sharpness(uint32_t color, double percent)
{
  int red = (color >> 16) & 0x0ff;
  int green = (color >> 8) & 0x0ff;
  int blue = (color)    & 0x0ff;
  for (int i = 0; i <= NUMPIXELS; i++) {
    pixels.setPixelColor(i, pixels.Color(red * percent, green * percent, blue * percent));
  }
  pixels.show();
  delay(25);
}

void breathing(int loops, uint32_t color) {

  for (int j = 0; j < loops; j++) {
    double pIncrease = .05;
    double pCurrent = 0;
    boolean inOrOut = false;
    for (int i = 0; i < (1 / pIncrease) * 2; i++) {
      sharpness(color, pCurrent);

      if (!inOrOut) {
        pCurrent += pIncrease;
      } else {
        pCurrent -= pIncrease;
      }
      if (pCurrent >= 1) {
        inOrOut = true;
      }
    }
  }
}


void lightShow() { //main
  Merica(35);
  RandomRapidSinglePixels(100);
  rgbChase(3, pixels.Color(0, 0, 0, 255), 30, 3);
  whiteOverRainbow(20, 35, 5);
  redBlueChase(5);
  flashingTeamOff(5, RED);
  flashingTeamOff(5, BLUE);
  buildingChase(3, 32, 5);
  christmasTree(10);
  breathing(2, BLUE);
  breathing(2, RED);
  flashingTeam(5, RED);
  flashingTeam(5, BLUE);
  for (int i = 0; i < 2; i++) {
    bounceChaseOpposite(BLUE, pixels.Color(0, 0, 50), 32);
    bounceChaseOpposite(RED, pixels.Color(50, 0, 0), 32);
  }
  //rainbowCycle(4);
  pixelRun(3, pixels.Color(50, 0, 0), RED, 25, 3);
  rainbowCycle(2);
  pixelRun(3, pixels.Color(0, 0, 50), BLUE, 25, 3);
  for (int i = 0; i < 2; i++) {
    rainbowCycle(0);
  }

}


void solidColor(uint32_t color) {
  for (int i = 0; i <= NUMPIXELS; i++) {
    pixels.setPixelColor(i, color);
  }
  pixels.show();
}

void pixelRun(int running_pixels, uint32_t base_color, uint32_t moving_color, uint8_t wait, int loops) {
  solidColor(base_color);
  for (int j = 1; j <= loops; j++) {

    for (int k = 0; k <= NUMPIXELS; k++) {
      for (int l = 0; l < running_pixels; l++) {
        if (k + l <= NUMPIXELS) {
          pixels.setPixelColor(k + l, moving_color);
        } else {
          pixels.setPixelColor((k + l) % NUMPIXELS - 1, moving_color);
        }
      }

      if (k - 1 < 0) {
        pixels.setPixelColor(NUMPIXELS, base_color);
      } else {
        pixels.setPixelColor(k - 1, base_color);
      }
      pixels.show();
      delay(wait);
    }
  }
}

void bounceChaseIn(uint32_t bounce, uint32_t base, int wait) {
  solidColor(base);
  for (int right = 0, left = NUMPIXELS; right <= NUMPIXELS / 2; right++, left--) {
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
  for (int right = NUMPIXELS / 2 - 1, left = NUMPIXELS / 2 + 1; right != 0; right--, left++) {
    pixels.setPixelColor(right, bounce);
    pixels.setPixelColor(left, bounce);
    pixels.show();
    delay(wait);
    pixels.setPixelColor(right, base);
    pixels.setPixelColor(left, base);
  }
}

void bounceChase(uint32_t bounce, uint32_t base, int wait) {
  bounceChaseIn(bounce, base, wait);
  bounceChaseOut(bounce, base, wait);
}

void bounceChaseOpposite(uint32_t bounce, uint32_t base, int wait) {
  bounceChaseOut(bounce, base, wait);
  bounceChaseIn(bounce, base, wait);
}

// Input a value 0 to 255 to get a color value.
// The colours are a transition r - g - b - back to r.
uint32_t Wheel(byte WheelPos) {
  WheelPos = 255 - WheelPos;
  if (WheelPos < 85) {
    return pixels.Color(255 - WheelPos * 3, 0, WheelPos * 3);
  }
  if (WheelPos < 170) {
    WheelPos -= 85;
    return pixels.Color(0, WheelPos * 3, 255 - WheelPos * 3);
  }
  WheelPos -= 170;
  return pixels.Color(WheelPos * 3, 255 - WheelPos * 3, 0);
}

void rainbowCycle(uint8_t wait) {
  uint16_t i, j;

  for (j = 0; j < 256 * 5; j++) { // 5 cycles of all colors on wheel
    for (i = 0; i < NUMPIXELS; i++) {
      pixels.setPixelColor(i, Wheel(((i * 256 / pixels.numPixels()) + j) & 255));
    }
    pixels.show();
    delay(wait);
  }
}
#define RAINBOW_SEIZURE rainbowCycle(0)

void loop() {
  RAINBOW_SEIZURE;
 // lightShow();
  //wingz(50, 5, 5, RED);
  //rainbowChase(1, 0);
  //rgbChase(3,pixels.Color(0,0,0,255),30,3);
  //whiteOverRainbow(20,75,5);
  //RandomRapidSinglePixels(10);
  //Merica(10);
  //rainbowCycle(0);
  //flashingTeam(10,RED);
  //christmasTree(10);
  //breathing();
  //For testing purposes.

  //pixelRun(3, pixels.Color(50,0,0), pixels.Color(255,0,0), 25, 5);
}

//// function that executes whenever data is received from master
//// this function is registered as an event, see setup()
//void receiveEvent(int howMany) {
//
//  int input = Wire.read();
//  //If the input defines an alliance
//  if (input == 1) {
//    allianceColor = input;
//    solidColor(BLUE);
//  } else if (input == 2) {
//    allianceColor = input;
//    solidColor(RED);
//  } else if (input == 3) {
//    pixelRun(1, pixels.Color(0, 0, 50), BLUE, 25, 1);
//  } else if (input == 4) {
//    pixelRun(1, pixels.Color(50, 0, 0), RED, 25, 1);
//  } else if (input == 5) {
//    rainbowCycle(4);
//  } else if (input == 6) {
//    rainbowCycle(2);
//  } else if (input == 7) {
//    RAINBOW_SEIZURE;
//  }
//}
