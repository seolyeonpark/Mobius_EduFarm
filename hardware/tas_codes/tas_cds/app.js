/**
 * - use tas sample created by I.-Y. Ahn, KETI
 */

var net = require('net');
var util = require('util');
var fs = require('fs');
var xml2js = require('xml2js');

var wdt = require('./wdt');

var useparentport = '';
var useparenthostname = '';

var upload_arr = [];
var download_arr = [];

var conf = {};

fs.readFile('conf.xml', 'utf-8', function (err, data) {
    if (err) {
        console.log("FATAL An error occurred trying to read in the file: " + err);
        console.log("error : set to default for configuration")
    }
    else {
        var parser = new xml2js.Parser({explicitArray: false});
        parser.parseString(data, function (err, result) {
            if (err) {
                console.log("Parsing An error occurred trying to read in the file: " + err);
                console.log("error : set to default for configuration")
            }
            else {
                var jsonString = JSON.stringify(result);
                conf = JSON.parse(jsonString)['m2m:conf'];

                useparenthostname = conf.tas.parenthostname;
                useparentport = conf.tas.parentport;

                if (conf.upload != null) {
                    if (conf.upload['ctname'] != null) {
                        upload_arr[0] = conf.upload;
                    }
                    else {
                        upload_arr = conf.upload;
                    }
                }

                if (conf.download != null) {
                    if (conf.download['ctname'] != null) {
                        download_arr[0] = conf.download;
                    }
                    else {
                        download_arr = conf.download;
                    }
                }
            }
        });
    }
});

var tas_state = 'init';
var upload_client = null;
var t_count = 0;
var tas_download_count = 0;

function on_receive(data) {
    if (tas_state == 'connect' || tas_state == 'reconnect' || tas_state == 'upload') {
        var data_arr = data.toString().split('<EOF>');
        if (data_arr.length >= 2) {
            for (var i = 0; i < data_arr.length - 1; i++) {
                var line = data_arr[i];
                var sink_str = util.format('%s', line.toString());
                var sink_obj = JSON.parse(sink_str);

                if (sink_obj.ctname == null || sink_obj.con == null) {
                    console.log('Received: data format mismatch');
                }
                else {
                    if (sink_obj.con == 'hello') {
                        console.log('Received: ' + line);

                        if (++tas_download_count >= download_arr.length) {
                            tas_state = 'upload';
                        }
                    }
                    else {
                        for (var j = 0; j < upload_arr.length; j++) {
                            if (upload_arr[j].ctname == sink_obj.ctname) {
                                console.log('ACK : ' + line + ' <----');
                                break;
                            }
                        }

                        for (j = 0; j < download_arr.length; j++) {
                            if (download_arr[j].ctname == sink_obj.ctname) {
                                g_down_buf = JSON.stringify({id: download_arr[i].id, con: sink_obj.con});
                                console.log(g_down_buf + ' <----');
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}

function tas_watchdog() {
    if (tas_state == 'init') {
        upload_client = new net.Socket();

        upload_client.on('data', on_receive);

        upload_client.on('error', function(err) {
            console.log(err);
            tas_state = 'reconnect';
        });

        upload_client.on('close', function() {
            console.log('Connection closed');
            upload_client.destroy();
            tas_state = 'reconnect';
        });

        if (upload_client) {
            console.log('tas init ok');
            tas_state = 'init_thing';
        }
    }
    else if (tas_state == 'init_thing') {
        
        tas_state = 'connect';
    }
    else if (tas_state == 'connect' || tas_state == 'reconnect') {
        upload_client.connect(useparentport, useparenthostname, function() {
            console.log('upload Connected');
            tas_download_count = 0;
            for (var i = 0; i < download_arr.length; i++) {
                console.log('download Connected - ' + download_arr[i].ctname + ' hello');
                var cin = {ctname: download_arr[i].ctname, con: 'hello'};
                upload_client.write(JSON.stringify(cin) + '<EOF>');
            }

            if (tas_download_count >= download_arr.length) {
                tas_state = 'upload';
            }
        });
    }
}

wdt.set_wdt(require('shortid').generate(), 3, tas_watchdog);

const spi = require('spi-device');
var rawValue1 = 0;
var rawValue2 = 0;
var rawValue3 = 0;
var rawValue4 = 0;

var dist1_0 = 0;
var dist1_1 = 0;

var dist2_0 = 0;
var dist2_1 = 0;

var dist3_0 = 0;
var dist3_1 = 0;

var dist4_0 = 0;
var dist4_1 = 0;

var light_update = false;
channel_num = ['0: ','1: ','2: ','3: '];

function plant_state(val) {
    if(val>300 && val<500) state = 'SoSo' 
    else if(val>=500) state = 'Dark'
    else if(val<=300) state = 'Shiny'
    
    return state;
}

setInterval(() => {

    //Sensor 1
    const mcp3008_1 = spi.open(0, 0, (err) => {
        const message = [
            {
            sendBuffer: Buffer.from([0x01, 0x80, 0x00]),  //Use MCP3008 Chanel 00
            receiveBuffer: Buffer.alloc(3),
            byteLength: 3,
            speedHz: 20000
        }
    ];
    
        if (err) throw err;
        
        
        mcp3008_1.transfer(message, (err, message) => {
            if (err) throw err;

            rawValue1 = ((message[0].receiveBuffer[1] & 0x03) << 8) + message[0].receiveBuffer[2] ;
            dist1_1 = rawValue1;
        });
    });

    //Sensor 2
    const mcp3008_2 = spi.open(0, 0, (err) => {
        const message = [
            {
            sendBuffer: Buffer.from([0x01, 0x90, 0x00]), //Use MCP3008 Chanel 01
            receiveBuffer: Buffer.alloc(3),
            byteLength: 3,
            speedHz: 20000
        }   
    ];
    
        if (err) throw err;
        
        
        mcp3008_2.transfer(message, (err, message) => {
            if (err) throw err;

            rawValue2 = ((message[0].receiveBuffer[1] & 0x03) << 8) + message[0].receiveBuffer[2] ;
            dist2_1 = rawValue2;
        });
    });
    
    const mcp3008_3 = spi.open(0, 0, (err) => {
        const message = [
            {
            sendBuffer: Buffer.from([0x01, 0xA0, 0x00]), //Use MCP3008 Chanel 03
            receiveBuffer: Buffer.alloc(3),             
            byteLength: 3,
            speedHz: 20000 
        }   
    ];
    
        if (err) throw err;
        
        
        mcp3008_3.transfer(message, (err, message) => {
            if (err) throw err;
            rawValue3 = ((message[0].receiveBuffer[1] & 0x03) << 8) + message[0].receiveBuffer[2] ;
            dist3_1 = rawValue3;
        });
    });

    const mcp3008_4 = spi.open(0, 0, (err) => {
        const message = [
            {
            sendBuffer: Buffer.from([0x01, 0xB0, 0x00]), //Use MCP3008 Chanel 04
            receiveBuffer: Buffer.alloc(3),              
            byteLength: 3,
            speedHz: 20000 
        }   
    ];
    
        if (err) throw err;
        
        
        mcp3008_4.transfer(message, (err, message) => {
            if (err) throw err;

            rawValue4 = ((message[0].receiveBuffer[1] & 0x03) << 8) + message[0].receiveBuffer[2] ;
            dist4_1 = rawValue4;
        });
    });



    send_data = [rawValue1,rawValue2,rawValue3,rawValue4];
    
    if (Math.abs(dist1_1 - dist1_0) > 100 || Math.abs(dist2_1 - dist2_0) > 100 ||Math.abs(dist3_1 - dist3_0) > 100 ||Math.abs(dist4_1 - dist4_0) > 100)
        light_update = true;
    else
        light_update = false;

     a =1;
    if (light_update) {
        if (tas_state=='upload') {
            for(var i = 0; i < upload_arr.length; i++) {
                if(upload_arr[i].id != "light") {
                    var cin = {ctname: upload_arr[i].ctname, con:send_data};
                    console.log("SEND : " + JSON.stringify(cin) + ' ---->')
                    upload_client.write(JSON.stringify(cin) + '<EOF>');
                    break;
                }
            }
        }
        dist1_0 = dist1_1;
        dist2_0 = dist2_1;
        dist3_0 = dist3_1;
        dist4_0 = dist4_1;
    }
}, 1000);
