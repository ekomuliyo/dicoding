// Promise (Janji), sesuai artinya promise adalah sebuah proses yang berjalan secara asyncronous dengan kondisi berikut
// Pending(proses), Fulfilled(terpenuhi), Rejected(gagal terpenuhi)

const executorFunction = (resolve, reject) => {
    const isCoffeMakerReady = true;
    if(isCoffeMakerReady){
        resolve("Kopi berhasil dibuat"); // kondisi promise berhasil
    }else{
        reject("Mesin kopi tidak bisa digunakan!"); // kondisi promise gagal
    }
}

const makeCoffe = new Promise(executorFunction);
console.log(makeCoffe);


console.log();
// onFulfilled and onRejected
const executorFunction2 = (resolve, reject) =>{
    const isCoffeMakerReady = false;
    if(isCoffeMakerReady){
        resolve("Kopi berhasil dibuat");
    }else{
        reject("Mesin kopi tidak dapat digunakan!");
    }
}

const handlerSuccess = resolvedValue => {
    console.log(resolvedValue);
}

const handlerRejected = rejectedValue => {
    console.log(rejectedValue);
}

const makeCoffe2 = new Promise(executorFunction2);
makeCoffe2.then(handlerSuccess, handlerRejected);

console.log();
// onRejected with catch
const executorFunction3 = (resolve, reject) => {
    const isCoffeMakerReady = true;
    if(isCoffeMakerReady){
        resolve("Kopi berhasil dibuat");
    }else{
        reject("Mesin kopi tidak dapat digunakan!");
    }
}

const handlerSuccess2 = resolvedValue => {
    console.log(resolvedValue);
}

const handlerRejected2 = rejectedValue => {
    console.log(rejectedValue);
}

const makeCoffe3 = new Promise(executorFunction3);
makeCoffe3
    .then(handlerSuccess2) // method then ini dipanggil ketika kondisi promise terpenuhi (fulfilled)
    .catch(handlerRejected2)  // method catch ini dipanggil ketika kondisi promise gagal / atau tidak terpeuhni (rejected)