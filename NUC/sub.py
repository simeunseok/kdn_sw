var mqtt = require('mqtt');
const fs = require('fs');
var client = mqtt.connect('mqtt://192.168.0.234:1883');
const Influx = require('influx');
const clientInflux = new Influx.InfluxDB({
        host: '192.168.0.234',
        database: 'FIRE',
        schema: [
                {
                        measurement: 'Arduino',
                        fields: {
                                Humidity: Influx.FieldType.FLOAT,
                                Temperature: Influx.FieldType.FLOAT,
                                GAS: Influx.FieldType.FLOAT,
                                CO: Influx.FieldType.FLOAT,
                                Full: Influx.FieldType.FLOAT,
                                Ir: Influx.FieldType.FLOAT,
                                Lux: Influx.FieldType.FLOAT
                        },
                        tags: [
                                'ID'
                        ]
                }
        ]
})

client.on('connect', () => {
        console.log('connected to server');
        client.subscribe('topic_sub');
        console.log('topic_sub');
});

client.on('close', () => {
        console.log('Disconnected from server');
});

clientInflux.getDatabaseNames()
        .then(names => {
                if(!names.includes('FIRE')){
                        cONSOLE.LOG("FIRE is created")
                        return clientInflux.createDatabase('FIRE');
                        }
})

clientInflux.getMeasurements()
        .then(names =>
                  console.log('hi ' + names.join(', ')));

client.on('message', (Topic, message) => {
        console.log(message, toString() );
        try{
        data = jSON.parse(message, toString())
        console.log(data)
        if(data, ID){
                CONSOLE.LOG('get data');
                var temp = data.TEMP;
                var humi = data.HUMI;
                var gas = data.GAS;
                var co = data.CO;
                var full = data.FULL;
                var ir = data.IR;
                var lux = data.LUX;
                

                clientInflux.writePoints([
                     {
                            measurement: 'Arduino',
                            tags: {
                                   ID: data.ID
                            },
                            fields:{
                                   Humidity : humi,
                                   Temperature: temp,
                                   Gas: gas,
                                   Co: co,
                                   Full: full,
                                   Ir: ir,
                                   LUX: lux
                            },
                     }
              ], {
                     database: 'FIRE'
              })
       }
       }
        catch(err){
              console.log("I did not het the correct JSON valus.");
       }
});

