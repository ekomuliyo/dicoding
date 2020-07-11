const CACHE_NAME = 'informasi-sepakbola-v1';

const image = [
  'pp.png',
  'favicon-32x32.png',
  'icon-512x512.png',
  'icon-384x384.png',
  'icon-192x192.png',
  'icon-152x152.png',
  'icon-144x144.png',
  'icon-128x128.png',
  'logo-premier-league.webp',
  'logo-laliga.svg',
  'logo-seria.webp',
  'logo-bundesliga.webp',
  'logo-eredivisie.webp',
  'logo-ligue1.webp',
].map(image => `/assets/image/${image}`);

const css = [
  'materialize.min.css',
  'icon.css'
].map(css => `/assets/css/${css}`);

const js = [
  'api.js',
  'db.js',
  'idb.js',
  'materialize.min.js',
  'nav.js',
  'script.js'
].map(js => `/assets/js/${js}`);

const urlsToCache = [
  '/',
  '/manifest.json',
  '/index.html',
  '/klub.html',
  '/pages/klasemen.html',
  '/pages/pertandingan.html',
  '/pages/klub-tersimpan.html',
  ...image,
  ...css,
  ...js
];

// mendaftarkan file-file yang dimasukan di cache browser
self.addEventListener("install", function (event) {
  event.waitUntil(
    caches.open(CACHE_NAME).then(function (cache) {
      return cache.addAll(urlsToCache);
    })
  );
});

// ini adalah dari event fetch dari service worker, 
// yang akan dijalankan ketika browser melakukan request apapun
self.addEventListener("fetch", function (event) {

  // menjadikan cache secara dinamis
  const baseUrl = 'https://api.football-data.org/v2';

  if (event.request.url.indexOf(baseUrl) > -1) {
    event.respondWith(
      caches.open(CACHE_NAME)
      .then(cache => {
        return fetch(event.request)
          .then(response => {
            cache.put(event.request.url, response.clone());
            return response;
          })
      })
    );
  } else {
    event.respondWith(
      caches.match(event.request, {
        ignoreSearch: true
      })
      .then(response => {
        return response || fetch(event.request);
      })
    )
  }
});

// push notif api
self.addEventListener('push', function (event) {
  let body;
  if (event.data) {
    body = event.data.text();
  } else {
    body = 'Push message no payload';
  }

  const options = {
    body: body,
    icon: '/assets/image/favicon-32x32.png',
    vibrate: [100, 50, 100],
    data: {
      dateOfArrival: Date.now(),
      primaryKey: 1
    }
  }

  event.waitUntil(
    self.registration.showNotification('Ada Notifikasi Baru :)', options)
  )
})

// memeriksa service worker yang sedang aktif,
// dan membandingkan setiap service worker, apakah != service worker yang aktif, maka cache di delete
self.addEventListener("activate", function (event) {
  event.waitUntil(
    caches.keys().then(function (cacheNames) {
      return Promise.all(
        cacheNames.map(function (cacheName) {
          if (cacheName != CACHE_NAME) {
            console.log("ServiceWorker: cache " + cacheName + " dihapus");
            return caches.delete(cacheName);
          }
        })
      );
    })
  );
});