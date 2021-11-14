const Gpio = require('onoff').Gpio;
const pinRed = 13; // GPIO17 (pin11): red
const pinGreen = 19; // GPIO18 (pin12): green
const pinBlue = 26; // GPIO27 (pin13): blue

function turnOnLed() {
    const ledRed = new Gpio(pinRed, 'out');     
    const ledBlue = new Gpio(pinBlue,'out');
	const ledGreen = new Gpio(pinGreen,'out');
    console.log('light on!');
	ledRed.writeSync(1);
    ledBlue.writeSync(1);
    ledGreen.writeSync(1);
}

function turnOnPurple(){
    const ledRed = new Gpio(pinRed,'out');
    const ledBlue = new Gpio(pinBlue,'out');
    const ledGreen = new Gpio(pinGreen,'out');
    
    console.log('Purple on!');
	ledRed.writeSync(1);
    ledBlue.writeSync(1);
    ledGreen.writeSync(0);
}
function turnOnBlue(){
    const ledRed = new Gpio(pinRed,'out');
    const ledBlue = new Gpio(pinBlue,'out');
    const ledGreen = new Gpio(pinGreen,'out');
    
    console.log('blue on!');
	ledRed.writeSync(0);
    ledBlue.writeSync(1);
    ledGreen.writeSync(0);
}
function turnOnRed(){
    const ledRed = new Gpio(pinRed,'out');
    const ledBlue = new Gpio(pinBlue,'out');
    const ledGreen = new Gpio(pinGreen,'out');
    
    console.log('red on!');
	ledRed.writeSync(1);
    ledBlue.writeSync(0);
    ledGreen.writeSync(0);
}

function turnOnGreen(){
    const ledRed = new Gpio(pinRed,'out');
    const ledBlue = new Gpio(pinBlue,'out');
    const ledGreen = new Gpio(pinGreen,'out');
    
    console.log('green on!');
	ledRed.writeSync(0);
    ledBlue.writeSync(0);
    ledGreen.writeSync(1);
}
function turnOffAll() {
    const ledRed = new Gpio(pinRed, 'out');    
    const ledGreen = new Gpio(pinGreen, 'out');
    const ledBlue = new Gpio(pinBlue, 'out');

	console.log('All lights off!');
	ledRed.writeSync(0);
	ledGreen.writeSync(0);
	ledBlue.writeSync(0);
}

function turnonCyan(){
    const ledRed = new Gpio(pinRed,'out');
    const ledBlue = new Gpio(pinBlue,'out');
    const ledGreen = new Gpio(pinGreen,'out');
    
    console.log('cyan on!');
	ledRed.writeSync(0);
    ledBlue.writeSync(1);
    ledGreen.writeSync(1);
}

function turnonYellow(){
    const ledRed = new Gpio(pinRed,'out');
    const ledBlue = new Gpio(pinBlue,'out');
    const ledGreen = new Gpio(pinGreen,'out');
    
    console.log('cyan on!');
	ledRed.writeSync(1);
    ledBlue.writeSync(0);
    ledGreen.writeSync(1);
}
switch (process.argv[2]) {
    case '000':
        turnOffAll(); break;
    case "100":
        turnOnRed(); break;
    case '010':
        turnOnGreen(); break;
    case '001':
        turnOnBlue(); break;
    case '110':
        turnOnPurple();break;
    case '011':
        turnonCyan();break;
    case '101':
        turnonYellow();break;
    case '111':
        turnOnLed();break;
    default:
        console.log('Sorry, wrong command!');
}