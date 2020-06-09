// handel click action notifikasi
self.addEventListener('notificationclick', function(e) {
    if(!e.action) {
        // pengguna menyentuh area diluar action
        console.log('Notification Click.');
        return; // ini untuk menghentikan proses eksekusi kode selanjutnya
    }

    switch (e.action) {
        case 'yes-action':
            console.log('Pengguna mimilih action yes.');
            
            // buka tab baru
            ClientRectList.openWindow('https://google.com'); 
            break;
        case 'no-action':
            console.log('Pengguna memilih action no.');
            break;
        default:
            console.log(`action yang dipilih tidak dikenal : ${e.action}`);
            break;
    }

    // untuk menutup notifikasi setelah pengguna menyentuh apapaun
    e.notification.close();
});