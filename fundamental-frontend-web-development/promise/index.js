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

// Chaining Promise
const state = {
    isCoffeMakerReady: true,
    seedStocks: {
        arabica: 250,
        robusta: 60,
        liberica: 80
    }
}

const getSeeds = (type, miligrams) => {
    return new Promise((resolve, rejected) => {
        if(state.seedStocks[type] >= miligrams){
            state.seedStocks[type] -= miligrams;
            resolve("Biji kopi didapatkan atau tersedia");
        }else{
            rejected("maaf stock habis");
        }
    });
}

const makeCoffe4 = seeds => {
    return new Promise((resolve, rejected) => {
        if(state.isCoffeMakerReady){
            resolve(`kopi berhasil dibuat ${seeds}`);
        }else{
            rejected("maaf mesin kopi tidak dapat digunakan");
        }
    });
}

const servingToTable = coffe => {
    return new Promise(resolve => {
        resolve(`pesanan Pesanan kopi ${coffe} selesai`);
    })
}

function reserveCoffe(type, miligrams){
    getSeeds(type, miligrams)
        .then(makeCoffe4(type)
            .then(resolveValueCoffe => {
                console.log(resolveValueCoffe)
            })
        )
        .then(servingToTable(type)
            .then(resolveValueServing => {
                console.log(resolveValueServing)
            })    
        )
        .then(resolveValue => {
            console.log(resolveValue);
        })
        .catch(rejectedReason => {
            console.log(rejectedReason);
        });
}

reserveCoffe("liberica", 80);


// Promise All
const arabicaOrder = () => {
    return new Promise(resolve => {
        setTimeout(() => {
            resolve("Kopi arabika selesai!");
        }, 4000);
    });
}

const robustaOrder = () => {
    return new Promise(resolve => {
        setTimeout(() => {
            resolve("Kopi robusta selesai!");
        }, 2000);
    });
}

const libericaOrder = () => {
    return new Promise(resolve => {
        setTimeout(() => {
            resolve("Kopi liberica selesai!");
        });
    }, 3000);
}

const promises = [arabicaOrder(), robustaOrder(), libericaOrder()];

Promise.all(promises)
    .then(resolvedValue => {
        console.log(resolvedValue);
    })

// Async Await adalah sebuah metode penulisan code dengan gaya synchronous namun pada kenyataanya asynchronous
async function makeCoffe5(){
    try{
        const coffe = await getCoffe();
        console.log(coffe);
    }catch(rejectedReason){
        console.log(rejectedReason);
    }
}

const getCoffe = () => {
    return new Promise((resolve, reject) => {
        const seeds = 5;
        setTimeout(() => {
            if(seeds >= 10){
                resolve("Coffe didapatkan!");
            }else{
                reject("kopi habis!");
            }
        }, 1000);
    });
}

makeCoffe5();

// Chaining Promise menggunakan async await
const state2 = {
    isCoffeMakerReady: true,
    seedStocks: {
        arabica: 250,
        robusta: 60,
        liberica: 80
    }
}

const getSeeds2 = (type, miligrams) => {
    return new Promise((resolve, rejected) => {
        if(state2.seedStocks[type] >= miligrams){
            state2.seedStocks[type] -= miligrams;
            resolve("Biji kopi didapatkan atau tersedia");
        }else{
            rejected("maaf stock habis");
        }
    });
}

const makeCoffe6 = seeds => {
    return new Promise((resolve, rejected) => {
        if(state2.isCoffeMakerReady){
            resolve(`kopi berhasil dibuat ${seeds}`);
        }else{
            rejected("maaf mesin kopi tidak dapat digunakan");
        }
    });
}

const servingToTable2 = coffe => {
    return new Promise(resolve => {
        resolve(`pesanan Pesanan kopi ${coffe} selesai dengan chain promise async await`);
    })
}

async function reserveCoffe2(type, miligrams){
    try {
        const seeds = await getSeeds2(type, miligrams);
        const coffee = await makeCoffe6(seeds);
        const result = await servingToTable2(coffee);
        console.log(result);
    } catch (rejectedReason) {
        console.log(rejectedReason);
    }
}

reserveCoffe2("liberica", 80);