const CACHE_NAME = "galeriku-v1";

const scripts = [
    "materialize.min.js",
    "scripts.js"
].map(script => `/js/${script}`);

const css = [
    "materialize.min.css",
    "main.css"
].map(css => `/css/${css}`);

const imageAssets = [
    "pp.png",
    "icon-512x512.png",
    "gambar1.jpg",
    "gambar2.jpg",
    "gambar3.jpg",
    "gambar4.jpg",
].map(image => `/assets/${image}`);

const urlsToCache = [
    "/",
    "/manifest.json",
    "/index.html",
    "/nav-top.html",
    "/nav-side.html",
    "pages/beranda.html",
    "/pages/galeriku.html",
    "/pages/login.html",
    "/pages/register.html",
    ...imageAssets,
    ...css,
    ...scripts
];


// mendaftarkan file di cache
self.addEventListener("install", function(e){
    e.waitUntil(
        caches.open(CACHE_NAME).then(function(cache){
            return cache.addAll(urlsToCache); // memasukan semua cache
        })
    );
});  

// handel request, jika request assets ada di dalam cache, maka akan mengambil dari cache
// namun apabila tidak ada di dalam cache maka request akan diteruskan ke server
self.addEventListener("fetch", function(e){
    e.respondWith(
        caches
            .match(e.request, { cacheName: CACHE_NAME})
            .then(function(response){
                if(response){
                    return response; // memuat assets dari cache
                }

                return fetch(e.request); // memuat assets dari server
            })
    );  
});

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