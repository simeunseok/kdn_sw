import numpy as np
import cv2
import datetime as dt
import io
from google.cloud import vision
from google.cloud.vision import types



while True:
    cap = cv2.VideoCapture("http://192.168.0.201:8081")
    td = dt.datetime.now()
    
    if td.second % 5 == 0:
        name = '/home/dnslab2/eunseok/kdn_sw/cctv/' + td.strftime('%H-%M-%S') +'.jpg'
    
        ret, frame = cap.read()
    
        if not ret:
            print("frame error")
            break
    
        cv2.imwrite(name, frame)    
        
        client = vision.ImageAnnotatorClient()
    
        with io.open(name, 'rb') as image_file:
            content = image_file.read()
    
        image = types.Image(content = content)
        response = client.label_detection(image = image)
        labels = response.label_annotations
    
        for label in labels:
            if label.description == 'Fire':
                print(td.strftime('%H-%M-%S') + " " + label.description + " = " + str(int(label.score*100)) + "%")
                dname = '/home/dnslab2/eunseok/kdn_sw/detect/' +td.strftime('%Y-%m-%d-%H-%M-%S') + '.jpg'
                cv2.imwrite(dname, frame)

cap.release()
cv2.destroyAllWindows()


