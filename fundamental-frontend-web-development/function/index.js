// regular function / function biasa
const upperiredNames = ["Dimas", "Widy", "Buchori"]
    .map(function(name){
        return name.toUpperCase();
    });

console.log(...upperiredNames);

// arrow function
const upperiredNames2 = ["Budi", "Andi", "Aan"]
    .map(name => name.toUpperCase());

console.log(...upperiredNames2);

// contoh regular function
// function declaration
function sayHello(greet){
    return `Hai selamat ${greet}`;
}
console.log(sayHello('Pagi'))

// function expression
const sayName = function (name){
    return `Hai ${name}`;
}

console.log(sayName('Eko'));

// contoh arrow function
// function expression
const sayHello2 = greet => `hai selamat ${greet}`;
const sayName2 = name => `hai ${name}`;

console.log(sayHello2('pagi'));
console.log(sayName2('eko'));

// arrow function tanpa parameter
const sayHello3 = () => "Selamat pagi semuanya!";
console.log(sayHello3());

// arrow function 2 parameter
const sayHello4 = (name, greet) => `${greet}, ${name}`;
console.log(sayHello4('Eko', 'Pagi'));

// arrow function dengan block body
const sayHello5 = language => {
    if(language.toUpperCase() === "INDONESIA"){
        return "Selamat Pagi!";
    }else{
        return "Good Morning!";
    }
}

console.log(sayHello5("England"));

// this keyword 

function People(name, age, hobby){
    this.name = name;
    this.age = age;
    this.hobby = hobby;
}

const programmer = new People("Eko", 23, ["Coding", "Reading Book"]);
console.log(programmer.name);
console.log(programmer.age);
console.log(programmer.hobby);

// penggunaan keyword new setelah nama fungsi, artinya kita membuat object dari fungsi tsb

// sama halnya dengan berikut
const programmer2 = {
    name: "Muliyo",
    age: 23,
    hobby: ["Coding", "Reading Book"]
}
console.log(programmer2.name);
console.log(programmer2.age);
console.log(programmer2.hobby);

// this pada arrow function
function People2(name, age, hobby){
    this.name = name;
    this.age = age;
    this.hobby = hobby;
}

// menambahkan prototype atau fungsi pada People2
People2.prototype.introMySelf = function(){
    // console.log(this);
    setTimeout(() => {
        // console.log(this);
        console.log(`Halo nama saya ${this.name}, umur ${this.age} dan hobi saya ${this.hobby}`);
    }, 300);
}

const programmer3 = new People2("Eko", 23, ["Coding", "Reading Book"]);
programmer3.introMySelf();

// this pada regular function
function People3(name, age, hobby){
    this.name = name;
    this.age = age;
    this.hobby = hobby;
}

People3.prototype.introMySelf = function(){
    console.log(this); // ini masih mengambil context this dari object People3
    setTimeout(function(){
        // console.log(this); // ini mengambil context this dari global atau context this dari function setTimeout()
        console.log(`Halo nama saya ${this.name}, umur ${this.age} dan hobi saya ${this.hobby}`);
    });
}

const programmer4 = new People3("andi", 21, ["Coding", "Reading Book"]);
programmer4.introMySelf();

// Default Value

// reguler function
function sayHello6(name = "Stranger", greet = "Hello"){
    console.log(`${greet}, ${name}`);
}

sayHello6("Eko", "Pagi");
sayHello6();

// arrow function
const sayHello7 = (name = "Stranger", greet = "Hello") => console.log(`${name}, ${greet}`);

sayHello7("Eko", "Siang");
sayHello7();

