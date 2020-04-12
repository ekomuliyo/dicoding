let imageFigureElement = document.querySelector("image-figure");

// memeriksa apakah tidak ada imageFigureElement
if(!imageFigureElement){
    // membuat element dan langsung mengisi nilai atribute
    imageFigureElement = document.createElement("image-figure");
    imageFigureElement.setAttribute("src", "https://avatars0.githubusercontent.com/u/43437180?s=400&u=77297146261e9215b35d27ef9431776429c9807e&v=4");
    imageFigureElement.setAttribute("alt", "My Profile");
    imageFigureElement.setAttribute("caption", "Profil Saya");
    document.body.appendChild(imageFigureElement);
}

// // mengubah/menambah nilai atribute caption
// setTimeout(() => {
//    imageFigureElement.setAttribute("caption", "Gambar 1"); 
// }, 1000);

// // mengubah/menambah nilai atribute caption yang kedua
// setTimeout(() => {
//    imageFigureElement.setAttribute("caption", "Gambar 2"); 
// }, 1000);

// // menghapus imageFigureElement dari DOM
// setTimeout(() => {
//     imageFigureElement.remove();
// }, 3000);