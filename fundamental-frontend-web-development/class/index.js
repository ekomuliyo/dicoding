// // pendekatan OOP dengan cara biasa atau function declaration
// function Car(manufacture, color){
//     this.manufacture = manufacture;
//     this.color = color;
//     this.engineActive = false;
// }

// Car.prototype.startEngines = function(){
//     console.log('Mobil dinyalakan...');
//     this.engineActive = true;
// }

// Car.prototype.info = function(){
//     console.log("Manufacture: " + this.manufacture);
//     console.log("Color: " + this.color);
//     console.log("Engines: " + this.engineActive ? "Aktif" : "Inactive");
// }

// let car1 = new Car("Honda Jazz", "Red");
// car1.startEngines();
// car1.info();

// pendekatan OOP dengan menggunakan Class

class Car{
    
    constructor(manufacture, color){
        this.manufacture = manufacture;
        this.color = color;
        this.engineActive = false;
    }

    startEngines(){
        console.log('Mobil dinyalakan..');
        this.engineActive = true;
    }

    info(){
        console.log(`Manufacture : ${this.manufacture}`);
        console.log(`Color: ${this.color}`);
        console.log(`Engines: ${this.engineActive ? "Active" : "Inactive"} `);
    }
}


const car2 = new Car("Innova", "White");
car2.startEngines();
car2.info();

console.log();
// Menerapkan Setter/Getter
class Motor{
    constructor(merk, warna){
        this.merk = merk;
        this._warna = warna;
        this.mesin = false;
    }

    get warna(){
        return this._warna;
    }

    set warna(value){
        this._warna = value;
    }
}

const motor = new Motor("Jupiter Z", "Merah");
console.log(motor._warna);
motor._warna = "Putih";
console.log(motor.warna);


// Inheritance

class Vehicle{
    constructor(licensePlate, manufacture){
        this.licensePlate = licensePlate;
        this.manufacture = manufacture;
        this.engineActive = false;
    }

    startEngines(){
        console.log(`Mesin kendaraan ${this.licensePlate} dinyalakan!`);
    }

    info(){
        console.log(`Nomor Kendaraan : ${this.licensePlate}`);
        console.log(`Manufacture: ${this.manufacture}`);
        console.log(`Mesin: ${this.engineActive ? "Active" : "Inactive"}`);
    }

    parking(){
        console.log(`Kendaraan ${this.licensePlate} parkir!`);
    }
}

class Carr extends Vehicle{
    constructor(licensePlate, manufacture, wheels){
        super(licensePlate, manufacture);
        this.wheels = wheels;
    }

    droveOff(){
        console.log(`Kendaran ${this.licensePlate} melaju!`);
    }

    openDoor(){
        console.log(`Membuka pintu!`);
    }

    info(){
        super.info();
        console.log(`Jumlah roda: ${this.wheels}`);
    }
}

const car3 = new Carr("Sotgun", "Suzuki", 2);
car3.startEngines();
car3.info();

console.log()
// method static
// method yang hanya dapat diakses dari class-nya langsung tanpa perlu di instance kan atau keyword new
class VehicleFactory{
    static repair(vehicles){
        vehicles.forEach(vehicle => {
            console.log(`Kendaraan ${vehicle.licensePlate} sedang diperbaiki!`);
        });
    }
}

const vehicle1 = new Carr("Terios", "Daihatsu", 4);
const vehicle2 = new Carr("Yaris", "Toyota", 4);
const vehicle3 = new Carr("Honda Jazz", "Honda", 4);


VehicleFactory.repair([vehicle1, vehicle2, vehicle3]);