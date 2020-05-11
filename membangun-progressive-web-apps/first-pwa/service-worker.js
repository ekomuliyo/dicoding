const CACHE_NAME = "firstpwa-v3";
var urlsToCache = [
  "/",
  "icon.png",
  "/nav.html",
  "/index.html",
  "/pages/home.html",
  "/pages/about.html",
  "/pages/contact.html",
  "/css/materialize.min.css",
  "/js/materialize.min.js",
  "/js/nav.js"
];

// mendaftarkan file-file yang dimasukan di cache browser
self.addEventListener("install", function(event) {
  event.waitUntil(
    caches.open(CACHE_NAME).then(function(cache) {
      return cache.addAll(urlsToCache);
    })
  );
});

// ini adalah dari event fetch dari service worker, 
// yang akan dijalankan ketika browser melakukan request apapun
self.addEventListener("fetch", function(event) {
    event.respondWith(
      caches
        .match(event.request, { cacheName: CACHE_NAME })
        .then(function(response) {
          if (response) {
            console.log("ServiceWorker: Gunakan aset dari cache: ", response.url);
            return response;
          }
   
          console.log(
            "ServiceWorker: Memuat aset dari server: ",
            event.request.url
          );
          return fetch(event.request);
        })
    );
  });

// memeriksa service worker yang sedang aktif,
// dan membandingkan setiap service worker, apakah != service worker yang aktif, maka cache di delete
self.addEventListener("activate", function(event) {
event.waitUntil(
    caches.keys().then(function(cacheNames) {
    return Promise.all(
        cacheNames.map(function(cacheName) {
        if (cacheName != CACHE_NAME) {
            console.log("ServiceWorker: cache " + cacheName + " dihapus");
            return caches.delete(cacheName);
        }
        })
    );
    })
);
});