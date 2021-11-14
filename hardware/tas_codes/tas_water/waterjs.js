/* TAS for RGB LED module (DFR0238)
 * Created by J. Yun, SCH Univ. (yun@sch.ac.kr)
 * Modify the tas_led sample developed by I.-Y. Ahn, KETI
*/
const Gpio = require('onoff').Gpio;
const motor = 12; // GPIO17 (pin11): red


function turnOnRed() {
    const ledRed = new Gpio(motor, 'out');     
	console.log('WaterMotor on!');
	ledRed.writeSync(1);
}


function turnOffAll() {
    const ledRed = new Gpio(motor, 'out');    

	console.log('WaterMotor off!');
	ledRed.writeSync(0);
}

switch (process.argv[2]) {
    case '0':
        turnOffAll(); break;
    case '1':
        turnOnRed(); break;
    default:
        // console.log('Sorry, wrong command!');
}