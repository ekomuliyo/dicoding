self.addEventListener('push', function(event) {
    let body;
    if(event.data) {
        body = event.data.text();
    }
    else {
        body = 'Push message no payload';
    }

    const options = {
        body: body,
        icon: 'img/nofification.png',
        vibrate: [100, 50, 100],
        data: {
            dateOfArrival: Date.now(),
            primaryKey: 1
        }
    }

    event.waitUntil(
        self.registration.showNotification('Push Notification', options)
    )
})