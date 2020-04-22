// contoh method GET
// promise
fetch("https://web-server-book-dicoding.appspot.com/list")
    .then(response => {
        return response.json()
    })
    .then(responseJson => {
        console.log(responseJson);
    })
    .catch(error => {
        console.log(error);
    });

// async / await
async function getBooks(){
    try {
        const response = await fetch("https://web-server-book-dicoding.appspot.com/list");
        const responseJson = response.json();
        console.log(responseJson);
    } catch (error) {
        console.log(error);
    }
}

getBooks();

// contoh method POST
fetch("https://web-server-book-dicoding.appspot.com/add", {
    method: "POST",
    headers: {
        "Content-Type": "application/json",
        "X-Auth-Token": "12345"
    },
    body: JSON.stringify({
        id: 100,
        title: "hai",
        author: "eko"
    })
});