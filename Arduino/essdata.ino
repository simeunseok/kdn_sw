#include <DHT.h>
#include <ArduinoJson.h>
#include <SparkFunTSL2561.h>
#include <Wire.h>
SFE_TSL2561 light;

#define DHTPIN 2
#define MQ2PIN A0
#define MQ7PIN A1
#define DHTTYPE DHT22
boolean gain;    
unsigned int ms; 

DHT dht(DHTPIN, DHTTYPE);


void setup() {
  pinMode(MQ7PIN, INPUT);
  pinMode(MQ2PIN, INPUT);
  Serial.begin(9600);
  dht.begin();
  light.begin();
  gain = 0;
  unsigned char time =1;
  light.setTiming(gain,time,ms);
  light.setPowerUp();
}

const char json[] = "";

void loop() {
  double mq7 = analogRead(MQ7PIN);
  double mq2 = analogRead(MQ2PIN);
  double humi = dht.readHumidity();
  double temp = dht.readTemperature();
  unsigned int data0,data1;
  light.getData(data0,data1);
  double lux;
  boolean good;
  good = light.getLux(gain,ms,data0,data1,lux);
  
  DynamicJsonBuffer JsonBuffer;
  JsonObject & Json = JsonBuffer.createObject();
{ 
    Json["ID"] = "KDN_RASP2";
    Json["HUMI"] = humi;
    Json["TEMP"] = temp;
    Json["GAS"] = mq2;
    Json["CO"] = mq7;
    Json["FULL"] = data0;
    Json["IR"] = data1;
    Json["LUX"] = lux;  
    delay(5000);
    String d;
    Json.printTo(d);
    Serial.println(d);
  }


  }
  
