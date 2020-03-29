// Destructuring Object

const profile = {
    firstName: "Eko",
    lastName: "Muliyo",
    age: 23
}

const {firstName, lastName, age} = profile;

console.log(firstName, lastName, age);
console.log(age);

const profile2 = {
    firstName2: "Andi",
    lastName2: "Pratama",
    age2: 21
}

let firstName2 = "Dimas";
let age2 = 20;

// inisialisasi nilai baru dari object desctruction
({firstName2, age2} = profile2);

console.log(firstName2);
console.log(age2);


// Default values
const profile3 = {
    firstName3: "Budi",
    lastName3: "Sudarsana",
    age3: 19
}

const {firstName3, age3, isMale = false} = profile3;
console.log(firstName3, age3, isMale);

// Local variabel / alias variabel desctrution

const profile4 = {
    firstName4: "Riko",
    lastName4: "Saputra",
    age4: 18
}

const {firstName4: localFirstName, lastName4: localLastName, age4: localAge} = profile4;
console.log(localFirstName);
console.log(localLastName);
console.log(localAge);

// Destructuring Array
const favorites = ["Seafood", "Salad", "Nugget", "Soup"];
const [firstFood, secondFood, thirdFood, fourthFood] = favorites;
console.log(firstFood);
console.log(secondFood);
console.log(thirdFood);
console.log(fourthFood);

// mengambil nilai tertentu
const [, , thirdFood2, ] = favorites
console.log(thirdFood2);

// inisialisasi nilai destructuring pada arrray
const favorites2 = ["Football", "Futsall", "Badminton", "Berenang"];
let myHobbies = "Membaca";
let herHobbies = "Nonton Film";

console.log(myHobbies);
console.log(herHobbies);

// swap variabel dengan desctructuring assigment
let a = 1;
let b = 2;
console.log("Sebelum swap");
console.log("Nilai a : " + a);
console.log("Nilai b : " + b);

[a, b] = [b, a];

console.log("Setelah swap");
console.log("Nilai a : " + a);
console.log("Nilai b : " + b);

// default value
const favorites3 = ["Football"];
const [myHobbies2, herHobbies2 = "Gaming"] = favorites3;
console.log(myHobbies2);
console.log(herHobbies2);
