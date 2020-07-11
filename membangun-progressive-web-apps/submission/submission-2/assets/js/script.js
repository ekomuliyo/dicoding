// Register service worker
if("serviceWorker" in navigator){
    window.addEventListener('load', function(){
        navigator.serviceWorker
            .register("/service-worker.js")
            .then(function(){
                console.log('Pendaftaran service worker berhasil');
            })
            .catch(function(){
                console.log('Pendaftaran service worker gagal');
            });
    });
    requestPermission();
}
else{
    console.log('service worker tidak didukung pada browser ini');
}

function urlBase64ToUint8Array(base64String) {
    const padding = '='.repeat((4 - base64String.length % 4) % 4);
    const base64 = (base64String + padding)
        .replace(/-/g, '+')
        .replace(/_/g, '/');
    const rawData = window.atob(base64);
    const outputArray = new Uint8Array(rawData.length);

    for (let index = 0; index < rawData.length; index++) {
        outputArray[index] = rawData.charCodeAt(index);
    }
    return outputArray;
}

function requestPermission() {
    if('Notification' in window) {
        Notification.requestPermission()
            .then(result => {
                if(result === 'denied') {
                    console.log('Fitur notifikasi tidak dizinkan!');
                    return;
                } else if(result === 'default') {
                    console.error('Pengguna menutup kotak dialog permintaan!');
                    return;
                }

                navigator.serviceWorker.ready.then(() => {
                    if(('PushManager' in window)) {
                        navigator.serviceWorker.getRegistration()
                            .then(registration => {
                                registration.pushManager.subscribe({
                                    userVisibleOnly: true,
                                    applicationServerKey: urlBase64ToUint8Array('BCP0PM0tys6OL7w-HJKrnv1bvHY-JV5r5nEk1PsOhCNtLbrzfrc1ZQ0heTLn-EpUI7wTDiDdWN9ZtEt_BU2wJ2g')
                                })
                                .then(subscribe => {
                                    console.log(`Berhasil melakukan subcribe dengan endpoint: ${subscribe.endpoint}`);
                                    
                                    console.log(`Berhasil melakukan subcribe dengan p256dh key : ${btoa(String.fromCharCode.apply(
                                        null, new Uint8Array(subscribe.getKey('p256dh'))
                                    ))}`);
    
                                    console.log(`Berhasil melakukan subcribe dengan auth key : ${btoa(String.fromCharCode.apply(
                                        null, new Uint8Array(subscribe.getKey('auth'))
                                    ))}`);
                                    
                                })
                                .catch(err => {
                                    console.error(`Tidak dapat melakukan subcribe ${err.message}`)
                                })
                            })
                    }
                })

        });
    }
}

tampilkan_klasemen = () => {
    document.getElementById('progress-bar').style.display = 'block';

    const idLeague = document.querySelector('#select-league-klasemen').value;

    if(idLeague !== ""){
        getKlasemen(idLeague);     
    }       
    else {
        alert('Silahkan pilih liga!');
        document.getElementById('progress-bar').style.display = 'none';
    }
}

tampilkan_pertandingan = () => {
    
    const idLeague = document.getElementById('select-league-pertandingan').value;
    const date = document.getElementById('datepicker').value;
    
    if(date === "") return;
    document.getElementById('progress-bar').style.display = 'block';

    getPertandingan(idLeague, date);

}
