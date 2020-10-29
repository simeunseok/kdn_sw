import json
from influxdb import InfluxDBCliet
from time import sleep

def get_influxdb(database_name, host = 'localhost', port = 8086):
    client = InfluxDBClient(host, port)
    
    try:
        client.switch_database(database_name)
        print('switch ' + database_name)
    except Exception as e:
        print('switch except')
        print(e)
        return None
    return client

file_path = "./sendData.json"

client = get_influxdb(database_name = 'FIRE', host = '192.168.0.234', port = 8086)

while True:
    rs = client.query('select * from Arduino group by * order by desc limit 1')
    rawDict = rs.raw
    
    with open(file_path, 'w') as json_file:
        json.dump(rawDict, json_file, indent='|t')
        
        
        
        