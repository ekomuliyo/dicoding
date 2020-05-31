const CACHE_NAME = "cache-v1";

const urlsToCache = [
    '/', 
    '/index.html',
    '/js/main.js',
    '/img/logo.png'
];

self.addEventListener('install', function(e) {
    console.log('ServiceWorker: Menginstal...');

    e.waitUntil(
        caches.open(CACHE_NAME)
        .then(cache => {
            console.log('ServiceWorker: Membuka cache...');
            return cache.addAll(urlsToCache);
        })
    );
});

self.addEventListener('fetch', function(e) {
    e.respondWith(
        caches.match(e.request)
        .then(response => {
            console.log('ServiceWorker: Menarik data' + e.request.url);
            if(response){
                console.log('ServiceWorker: Gunakan aset dari cache: ' + response.url);
                return response;
            }

            console.log('ServiceWorker: Memuat aset dari server: ' + e.request.url);

            const fetchRequest = e.request.clone();

            return fetch(fetchRequest)
                .then(response => {
                    if(!response || response.status !== 200){
                        return response
                    }

                    const responseToCache = response.clone();
                    caches.open(CACHE_NAME)
                        .then(cache => {
                            cache.put(e.request, responseToCache);
                        });
                    
                    return response;
                })
        })
    )
});

self.addEventListener('activate', function(e) {
    console.log('Aktivasi service worker berhasil!');

    e.waitUntil(
        caches.keys()
            .then(cacheNames => {
                return Promise.all(
                    cacheNames.map(cacheName => {
                        if(cacheName !== CACHE_NAME && cacheName.startsWith('cache')) {
                            return caches.delete(cacheName);
                        }
                    })
                )
            })
    )
});