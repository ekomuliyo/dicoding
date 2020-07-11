document.addEventListener("DOMContentLoaded", function() {
    // auto init materialize
    M.AutoInit();

    // Daftarkan event listener untuk setiap tautan menu
    document.querySelectorAll(".sidenav a, .topnav a").forEach(function(elm) {
        elm.addEventListener("click", function(event) {
        // Tutup sidenav
        var sidenav = document.querySelector(".sidenav");
        M.Sidenav.getInstance(sidenav).close();

        const page = event.target.getAttribute("href");
        if(page !== null) {
            // Muat konten halaman yang dipanggil
            loadPage(page.substr(1));
        }
        });
    });
    
    const loadPage = (page) => {

        const content = document.querySelector('#body-content');

        fetch(`pages/${page}.html`)
            .then(response => {

                // handel jika user klik menu klub tersimpan
                if(page === "klub-tersimpan") {
                    // tampilkan semua data yang tersimpan di indexedDB
                    getAllKlubSaved();
                }

                if(response.status == 200) {
                    response.text().then(responseText => {
                        content.innerHTML = responseText;
                        
                        // auto init materialize
                        M.AutoInit();

                        // custom date indonesia
                        const elemsDatepicker = document.querySelectorAll('.datepicker');
                        M.Datepicker.init(elemsDatepicker, {
                            format: 'yyyy-mm-dd',
                            autoClose: true
                        });
                    });
                }else if(response.status == 404) {
                    content.innerHTML = '<h2>404</h2> </br> <p>Halaman tidak ditemukan</p>'
                }

                if(page === "klasemen") {
                    setTimeout(() => {
                        tampilkan_klasemen();
                    }, 500);
                }
            })
            .catch(err => {
                console.error(err);
            })
    }

    // Load page content
    let page = window.location.hash.substr(1);
    if(page === "") page = "klasemen";
    loadPage(page);
});