const coffeStock = require('./state.js');

console.log(coffeStock);

const makeCoffe = (type, miligrams) => {
    if(coffeStock[type] >= miligrams){
        console.log("Kopi berhasil dibuat");
    }else{
        console.log("Biji kopi habis!");
    }
}

makeCoffe("robusta", 1000);