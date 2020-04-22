const xhrPost = new XMLHttpRequest();

xhrPost.onload = function(){
    console.log(this.responseText)
}

xhrPost.onerror = function(){
    console.log('something error!');
}

xhrPost.open("POST", "https://web-server-book-dicoding.appspot.com/add");

// menambahkan header
xhrPost.setRequestHeader("Content-Type", "application/json");
xhrPost.setRequestHeader("X-Auth-Token", "12345");

const book = {
    id: 100,
    title: "Baru",
    author: "Eko Muliyo"
}

xhrPost.send(JSON.stringify(book));


const xhrGet = new XMLHttpRequest();

xhrGet.onload = function(){
    const response = JSON.parse(this.responseText);
    response.books.forEach((book, index) => {
        document.body.innerText += `${index}. ${book.title}\n`
    })
}

xhrGet.onerror = function(){
    console.log('something error!');
}

xhrGet.open("GET", "https://web-server-book-dicoding.appspot.com/list");
xhrGet.send();