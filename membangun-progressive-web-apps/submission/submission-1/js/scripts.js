// cek support web worker
if("serviceWorker" in navigator){
  window.addEventListener('load', function(){
      navigator.serviceWorker
          .register("/service-worker.js")
          .then(function(){
              console.log('berhasil menambahkan service worker');
          }).catch(function(){
              console.log('gagal menambahkan service worker');
          });
  });
}
else{
  alert('browser tidak mendukung service worker');
}

document.addEventListener('DOMContentLoaded', function() {
  // initial menu sidenav
  const elementSideNav = document.querySelectorAll('.sidenav');
  M.Sidenav.init(elementSideNav);
  // initial parallax
  const elementParallax = document.querySelectorAll('.parallax');
  M.Parallax.init(elementParallax);

  // load menu dari file nav-side.html dan nav-top.html
  loadNavTop();
  loadNavSide();

  // mengatur url root ke halaman home
  const page = window.location.hash.substr(1);
  if(page === "") page = "beranda";
  loadPage(page); 


});

loadNavTop = () => {
  let xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function(){
    if(this.readyState == 4){
      if(this.status != 200) return;

      // memuat menu topnav dari file nav-bar.html
      document.querySelectorAll('.topnav').forEach(function(element){
        element.innerHTML = xhr.responseText;
      });

      // Mendaftarkan event listener click pada setiap menu
      document.querySelectorAll(".topnav a").forEach(function (e){
        e.addEventListener("click", function(event){
          page = event.target.getAttribute("href").substr(1);
          loadPage(page);
      });
  });

    }
  }

  xhr.open('GET', 'nav-top.html', true);
  xhr.send();
}

loadNavSide = () => {
  let xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function(){
    if(this.readyState == 4){
      if(this.status != 200) return;

      // memuat menu topnav dari file nav-bar.html
      document.querySelectorAll('.sidenav').forEach(function(element){
        element.innerHTML = xhr.responseText;
      });

      // Mendaftarkan event listener click pada setiap menu
      document.querySelectorAll(".sidenav a").forEach(function (e){
        e.addEventListener("click", function(event){

          // memuat halaman sesuai content
          page = event.target.getAttribute("href").substr(1);
          loadPage(page);

          // tutup side ketika setelah di click
          let sidenav = document.querySelector(".sidenav");
          M.Sidenav.getInstance(sidenav).close();



      });
  });

    }
  }

  xhr.open('GET', 'nav-side.html', true);
  xhr.send();
}

// fungsi ini untuk memuat content
loadPage = (page) => {
  let xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function (){
    if(this.readyState == 4){
      let contents = document.getElementById("contents");
      if(this.status == 200){
        contents.innerHTML = xhr.responseText
      }else if (this.status == 404){
        contents.innerHTML = `<div class="card-panel white-text #c62828 red darken-1 center">Halaman tidak ditemukan!</div>`;
      }else{
        contents.innerHTML = `<div class="card-panel white-text #c62828 red darken-1 center">Ups. halaman tidak dapat diakses!</div>`;
      }
    }
  }

  xhr.open("GET", `pages/${page}.html`, true);
  xhr.send();
}
