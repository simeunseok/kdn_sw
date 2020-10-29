import serial
import json
import paho.mqtt.client as mqtt

ser = serial.Serial('/dev/ttyACM0', 9600, timeout=5);
if(ser.isOpen() == False):
    ser.open();

pub = mqtt.Client();
pub.connect("192.168.0.234", 1833);

while True:
    res = ser.readline();
    print(res);
    pub.publish("Danger", res);
    
    