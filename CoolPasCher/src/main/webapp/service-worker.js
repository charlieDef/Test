var cacheName = 'GMD Demo PWA';
var filesToCache = [
    'Index.html',
    'manifest.json',
    'service-worker.js',
      'mboaonline.css',
    'js/jssor.slider-27.1.0.min.js',
    'js/jquery-3.2.1.min.js',
    'mboaonline/mboaonline.nocache.js',
    'ckeditor/ckeditor.js',
    'js/prebaalmd.js',
    'css/responsiveslides.css',
    'css/demo.css',
    'js/responsiveslides.min.js',
    'img/pbal_144x144.png',
    'img/pbal_512x367.png'
];

self.addEventListener('install', function(e) {
    console.log('[ServiceWorker] Install');
    e.waitUntil(
        caches.open(cacheName).then(function(cache) {
            console.log('[ServiceWorker] Caching app shell');
            return cache.addAll(filesToCache);
        })
    );
});


self.addEventListener('activate', function(e) {
    console.log('[ServiceWorker] Activate');
    e.waitUntil(
        caches.keys().then(function(keyList) {
            return Promise.all(keyList.map(function(key) {
                console.log('[ServiceWorker] Removing old cache', key);
                if (key !== cacheName) {
                    return caches.delete(key);
                }
            }));
        })
    );
});

self.addEventListener('fetch', function(e) {
    console.log('[ServiceWorker] Fetch', e.request.url);
    e.respondWith(
        caches.match(e.request).then(function(response) {
            return response || fetch(e.request);
        })
    );
});