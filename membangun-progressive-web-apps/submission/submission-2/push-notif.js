const webPush = require('web-push');

const vapidKeys = {
    "publicKey": "BCP0PM0tys6OL7w-HJKrnv1bvHY-JV5r5nEk1PsOhCNtLbrzfrc1ZQ0heTLn-EpUI7wTDiDdWN9ZtEt_BU2wJ2g",
    "privateKey": "PzqwgEV-Ts-ZhQJ2eQOA9DVhHfKK6qUbyphIdLAdV9k"
}

webPush.setVapidDetails(
    'mailto:ekomuliyo@gmail.com',
    vapidKeys.publicKey,
    vapidKeys.privateKey
)
// console.log(endPoint);

const pushSubscription = {
    "endpoint": "https://fcm.googleapis.com/fcm/send/ecLpbiWJBI4:APA91bE-KVnrxj-vbDCcJVik4XXL-vmP_fy2FgKl1f6xdgn8ghRp6djJpArz_0PsEG0YEtI092YlWmUFgQ-bARyG3Y5ntr_KZn_IwOrgj_cU1TwSLhdM6S9yV7pd-mv6ezCDj8ZJDkXq",
    "keys": {
        "p256dh": 'BGVCWy6eKB2/mXuNf/PT9KnAun9OxXIxZTfVurMlESPaveoivKauscJkBqBaFa13b4KkY77BgXl0mxjqv845PQE=',
        "auth": 'QzDkzQiZw+c+7GikHl0fdA=='
    }
}

const payload = 'Selamat! ini adalah isi pesan notifikasi melalui push notification api';

const options = {
    gcmAPIKey: '755200111688',
    TTL: 60
}

webPush.sendNotification(
    pushSubscription,
    payload,
    options
)