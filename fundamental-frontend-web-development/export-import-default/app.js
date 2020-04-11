import {coffeStock as stock, isCoffeMakerReady} from  "./state.js"

const displayStock = stock => {
    const coffeStockListElement = document.querySelector("#coffe-stock-list");
    for (const type in stock){
        const coffeStockItemElement = document.createElement("li");
        coffeStockItemElement.innerHTML =  `${type} : ${stock[type]}`
        coffeStockListElement.appendChild(coffeStockItemElement);
    }
}


const coffeOrder = (type, miligrams) => {
    return new Promise((resolve, reject) => {
        if(isCoffeMakerReady){
            if(stock[type] >= miligrams){
                resolve("kopi berhasil di pesan!")
            }else{
                reject("maaf kopi habis!");
            }
        }else{
            reject("maaf mesin kopi sedang rusak!")
        }
    });
}

const coffeOrderButtonEventHandler = async event => {
    const type = prompt("Kopi apa yang ingin anda pesan");
    const miligrams = prompt("Berapa miligrams");
    try {
        const result = await coffeOrder(type, miligrams);
        alert(result);
    } catch (rejectReason) {
        alert(rejectReason)
    }
}

const coffeOrderButtonEventElement = document.querySelector("#coffe-order-button");
coffeOrderButtonEventElement.addEventListener("click", coffeOrderButtonEventHandler);

displayStock(stock);