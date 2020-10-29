import socket
import time
import json
import os
import datetime as dt
from glob import glob

host = '192.168.0.234'
port = 5003
time =  dt.datetime.now()

server_sock = socket.socket(socket.AF_INET)
server.sock.bind((host,port))
server.sock.listen(10)

print("Waiting")
client_sock, addr = server_sock.accept()
print('Connected by', addr)

file+path = "./sendData.json"
image_path = "../kdn_sw/detect/" + time.strftime('%Y-%m-%d-%H-%M') + '.jpg'

if not os.path.isfile(image_path):
       print(image_path)
temp_data = " "

i = 0
while True:
       with open(file_path,"r") as json_file:
              try:
                     json_data + json.load(json_file)
              except:
                     json_data = temp_data
       send_data = json.dumps(json_data)
       
       if os.path.isfile(image_path):
              send_data += "Fire"
       client_sock.send(send.data.encode())
       i+=1
       
       temp_data = json_data
client_sock.close()
server_sock.close()

