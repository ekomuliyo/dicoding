// Spreading Operator
// mengeluarkan isi dari sebuah array / iterable

const favorites = ["Seafood", "Salad", "Nugget", "Soup"];
// console.log(favorites); array
console.log(...favorites);

// mencari nilai terbesar dari sebuah array
const numbers = [100, 90, 120, 130, 140, 150];
console.log(Math.max(...numbers));

// menggabungkan 2 buah array
const favorites2 = ["Seafood", "Salad", "Nugget", "Soup"];
const others = ["Cake", "Pie", "Donut"];

const allFavorites = [...favorites2, ...others];
console.log(allFavorites);


// Rest Parameter
// mengambil atau menangkap nilai dari sebuah array / iterable

function sum(...numbers2){
    let result = 0;
    for(let n of numbers2){
        result += n;
    }
    return result;
}

console.log(sum(1, 2, 4, 6, 7, 10));

// Rest Parameter dengan desctructuring
const refrigerator = ["LG", 50, 2, "susu", "keju"];
const [manufacture, weight, door, ...items] = refrigerator;
console.log(manufacture);
console.log(weight);
console.log(door);
console.log(items);