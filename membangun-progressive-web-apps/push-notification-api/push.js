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

const pushSubscription = {
    "endpoint": "https://fcm.googleapis.com/fcm/send/eOqCZyYWu5k:APA91bEs4ow1OnLv1UHYsiaQNI36X_bZs5mD0smn9wJItlc0vS2UtxmBBuRujAT-y8lWFoNtrB0YfLsnx5EklCMGCTxXHb7VaGQmsC-4h1mMfgIn7yM3T_l14MLXSRvDKy0MyRPweLxe",
    "keys": {
        "p256dh": 'BMqB+JiRIXZ1+d8STo3kdrTdRHkKmFjpsCxkRjaZGh9fVoX/RMMyvpS1OAKZgtZbCyxJnmhXipHn1whYKxXNOJY=',
        "auth": 'DWH9T0nQjWRolGSk3O4dNw=='
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