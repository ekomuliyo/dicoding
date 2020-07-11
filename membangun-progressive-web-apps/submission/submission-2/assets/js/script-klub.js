// Register service worker
if ("serviceWorker" in navigator) {
    window.addEventListener('load', function () {
        navigator.serviceWorker
            .register("/service-worker.js")
            .then(function () {
                console.log('Pendaftaran service worker berhasil');
            })
            .catch(function () {
                console.log('Pendaftaran service worker gagal');
            });
    });
} else {
    console.log('service worker tidak didukung pada browser ini');
}

document.addEventListener('DOMContentLoaded', function () {

    const urlParams = new URLSearchParams(window.location.search);
    const isFromSaved = urlParams.get('saved') 
    const idKlub = urlParams.get('id');
    const btnSave = document.getElementById('btnSave');
    const btnHapus = document.getElementById('btnHapus');

    if(isFromSaved) {
        // ambil data
        const klub = getSavedKlubById();

        // menghilangkan elemen FAB save dan
        // menampilkan elemen FAB hapus
        btnSave.style.display = 'none';
        btnHapus.style.display = 'block';

        // event klik btnHapus
        let stateToast = false;
        btnHapus.onclick = () => {
            if(!stateToast) {

                removeKlubById(idKlub);

                // tampilkan toast, dan disable tombol FAB
                M.toast({html: 'Berhasil Dihapus :(', classes: 'rounded valign-wrapper'});
                stateToast = true;

                setTimeout(() => {
                    window.history.back();
                }, 1000);
            }
        }
    } else {
        const klub = getKlubById();

        // menghilangkan elemen FAB hapus dan
        // menampilkan elemen FAB save
        btnSave.style.display = 'block';
        btnHapus.style.display = 'none';

        // event klik btnSave
        let stateToast = false;
        btnSave.onclick = () => {
            if(!stateToast){

                // lakukan penyimpanan data klub ke indexedDB
                klub.then(klub => {
                    saveClub(klub);
                })

                // tampilkan toast, dan disable tombol FAB
                M.toast({html: 'Berhasil Disimpan :)', classes: 'rounded valign-wrapper'});
                stateToast = true;

                setTimeout(() => {
                    window.history.back();
                }, 1000);
            }
        }
    }


});