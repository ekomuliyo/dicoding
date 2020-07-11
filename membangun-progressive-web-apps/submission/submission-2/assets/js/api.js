const baseUrl = 'https://api.football-data.org/v2';
const headers = { headers: { 'X-Auth-Token': 'f6d767e7cb71480bb41416a5e73c753f'} };

const status = (response) => {
    if(response.status !== 200) {
        console.log('Error : ' + response.status);
        return Promise.reject(new Error(response.statusText));
    }
    else{
        return Promise.resolve(response);
    }
}

const json = (response) => {
    return response.json();
}

const error = (err) => {
    console.log('Error : ' + err);
}

const getKlasemen = (idLeague) => {

    // handel agar request data api cache terlebih dahulu
    // setelah itu baru request ke api server
    if('caches' in window) {
        caches.match(`${baseUrl}/competitions/${idLeague}/standings`)
            .then(response => {
                if(response) {
                    response.json()
                        .then(data => {
                            let tableTr = '';
                            data.standings[0].table.forEach((klub, index) => {
                                tableTr += `
                                    <tr>
                                        <td>${++index}</td>
                                        <td>
                                            <a href="./klub.html?id=${klub.team.id}">
                                            <img src="${klub.team.crestUrl}" width="17px" alt="Logo Klub ${klub.team.name}"> &nbsp; ${klub.team.name}
                                            </a>
                                        </td>
                                        <td>${klub.playedGames}</td>
                                        <td>${klub.won}</td>
                                        <td>${klub.draw}</td>
                                        <td>${klub.lost}</td>
                                        <td>${klub.points}</td>
                                    </tr>
                                `;
                            });
                    
                            document.getElementById('tbody-klasemen').innerHTML = tableTr;
                
                            document.getElementById('progress-bar').style.display = 'none';
                        })
                }
            })
    }

    fetch(`${baseUrl}/competitions/${idLeague}/standings`, headers)
        .then(status)
        .then(json)
        .then(data => {
            let tableTr = '';
            data.standings[0].table.forEach((klub, index) => {
                tableTr += `
                    <tr>
                        <td>${++index}</td>
                        <td>
                            <a href="./klub.html?id=${klub.team.id}">
                            <img src="${klub.team.crestUrl}" width="17px" alt="Logo Klub ${klub.team.name}"> &nbsp; ${klub.team.name}
                            </a>
                        </td>
                        <td>${klub.playedGames}</td>
                        <td>${klub.won}</td>
                        <td>${klub.draw}</td>
                        <td>${klub.lost}</td>
                        <td>${klub.points}</td>
                    </tr>
                `;
            });
    
            document.getElementById('tbody-klasemen').innerHTML = tableTr;

            document.getElementById('progress-bar').style.display = 'none';
        })
}

const getPertandingan = (idLeague, date) => {

    // handel agar request data api cache terlebih dahulu
    // setelah itu baru request ke api server
    if('caches' in window) {
        caches.match(`${baseUrl}/competitions/${idLeague}/matches?status=SCHEDULED&dateFrom=${date}&dateTo=${date}`)
            .then(response => {
                if(response) {
                    response.json()
                        .then(data => {
                            let tableTr = '';
                            if(data.matches.length > 0) {
                                data.matches.forEach(pertandingan => {
                                    const convertDate = new Date(pertandingan.utcDate);
                                    
                                    hari = convertDate.getDay();
                                    switch(hari) {
                                        case 0: hari = "Minggu"; break;
                                        case 1: hari = "Senin"; break;
                                        case 2: hari = "Selasa"; break;
                                        case 3: hari = "Rabu"; break;
                                        case 4: hari = "Kamis"; break;
                                        case 5: hari = "Jum'at"; break;
                                        case 6: hari = "Sabtu"; break;
                                    }
                    
                                    const tanggal = `${convertDate.getDate()}/${convertDate.getMonth()}/${convertDate.getFullYear()}` ;
                                    const jam = convertDate.getHours();
                                    const menit = convertDate.getMinutes();
                                    tableTr += `
                                        <tr>
                                            <td>${hari}, ${tanggal} </br> ${jam < 10 ? `0${jam}` : jam} : ${menit < 10 ? `0${menit}` : menit}</td>
                                            <td class="center-align">${pertandingan.homeTeam.name}</td>
                                            <td class="center-align">-</td>
                                            <td class="center-align">${pertandingan.awayTeam.name}</td>
                                        </tr>
                                    `;
                                });
                            }
                            else {
                                tableTr = `<tr>
                                    <td colspan="4">Tidak Ada Jadwal Pertandingan!</td>
                                </tr>`
                            }
                    
                            document.getElementById('tbody-pertandingan').innerHTML = tableTr;
                            document.getElementById('progress-bar').style.display = 'none';
                        })
                }
            })
    }

    fetch(`${baseUrl}/competitions/${idLeague}/matches?status=SCHEDULED&dateFrom=${date}&dateTo=${date}`, headers)
        .then(status)
        .then(json)
        .then(data => {
            let tableTr = '';
            if(data.matches.length > 0) {
                data.matches.forEach(pertandingan => {
                    const convertDate = new Date(pertandingan.utcDate);
                    
                    hari = convertDate.getDay();
                    switch(hari) {
                        case 0: hari = "Minggu"; break;
                        case 1: hari = "Senin"; break;
                        case 2: hari = "Selasa"; break;
                        case 3: hari = "Rabu"; break;
                        case 4: hari = "Kamis"; break;
                        case 5: hari = "Jum'at"; break;
                        case 6: hari = "Sabtu"; break;
                    }

                    const tanggal = `${convertDate.getDate()}/${convertDate.getMonth()}/${convertDate.getFullYear()}` ;
                    const jam = convertDate.getHours();
                    const menit = convertDate.getMinutes();
                    tableTr += `
                        <tr>
                            <td>${hari}, ${tanggal} </br> ${jam < 10 ? `0${jam}` : jam} : ${menit < 10 ? `0${menit}` : menit}</td>
                            <td class="center-align">${pertandingan.homeTeam.name}</td>
                            <td class="center-align">-</td>
                            <td class="center-align">${pertandingan.awayTeam.name}</td>
                        </tr>
                    `;
                });        
            }
            else {
                tableTr = `<tr>
                    <td colspan="4">Tidak Ada Jadwal Pertandingan!</td>
                </tr>`
            }

            document.getElementById('tbody-pertandingan').innerHTML = tableTr;
            document.getElementById('progress-bar').style.display = 'none';
        });
    
    
}

const getKlubById = () => {

    return new Promise((resolve) => {

        document.getElementById('progress-bar').style.display = 'block';

        // ambil nilai id
        const urlParams = new URLSearchParams(window.location.search);
        const idTeam = urlParams.get('id');

        // handel request ke cache api terlebih dahulu,
        // setelah itu baru diteruskan ke server
        if('caches' in window) {
            caches.match(`${baseUrl}/teams/${idTeam}`)
                .then(response => {
                    if(response) {
                        response.json()
                            .then(data => {
                                const klubHtml = `
                                    <div class="col s12 m7">
                                    <div class="card">
                                        <div class="card-content">
                                            <div class="row">
                                                <div class="col s12 m12 l12 center-align">
                                                    <img src="${data.crestUrl}" style="max-width: 150px;" alt="Logo Klub ${data.shortName}">
                                                </div>
                                                <div class="col s12 m12 l12 center-align">
                                                    <h4>${data.shortName}</h4>
                                                </div>
                                                <div class="col s3 m3 l3">
                                                    <span>Nama</span>
                                                </div>
                                                <div class="col s9 m9 l9 left-align">
                                                    <span>: ${data.name}</span>
                                                </div>
                                                <div class="col s3 m3 l3">
                                                    <span>Alamat</span>
                                                </div>
                                                <div class="col s9 m9 l9 left-align">
                                                    <span>: ${data.address}</span>
                                                </div>
                                                <div class="col s3 m3 l3">
                                                    <span>Berdiri</span>
                                                </div>
                                                <div class="col s9 m9 l9 left-align">
                                                    <span>: ${data.founded}</span>
                                                </div>
                                                <div class="col s3 m3 l3">
                                                    <span>Stadion</span>
                                                </div>
                                                <div class="col s9 m9 l9 left-align">
                                                    <span>: ${data.venue}</span>
                                                </div>
                                                <div class="col s3 m3 l3">
                                                    <span>Website</span>
                                                </div>
                                                <div class="col s9 m9 l9 left-align">
                                                    <span>: <a href="${data.website}" target="_blank">${data.website}</a></span>
                                                </div>
                                                <div class="col s3 m3 l3">
                                                    <span>Email</span>
                                                </div>
                                                <div class="col s9 m9 l9 left-align">
                                                    <span>: ${data.email ? data.email : '-'}</span>
                                                </div>
                                                <div class="col s12 m12 l12">
                                                    <h5>Pemain Tim</h5>
                                                    <table class="highlight">
                                                        <thead>
                                                            <tr>
                                                                <th class="center-align">Nama</th>
                                                                <th class="center-align">Posisi</th>
                                                                <th class="center-align">Asal Negara</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            ${data.squad.map(squad => {
                                                                if(squad.role === "PLAYER") {
                                                                    return `
                                                                    <tr>
                                                                        <td>${squad.name}</td>
                                                                        <td class="center-align">${squad.position}</td>
                                                                        <td class="center-align">${squad.nationality}</td>
                                                                    </tr>`
                                                                }
                                                            }).join('')}
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                `;
                                        
                                document.getElementById('body-content').innerHTML = klubHtml;
                
                                document.getElementById('progress-bar').style.display = 'none';
                                
                                // console.log(data);
                                // kirim object parsing json dalam bentuk promise resolve agar bisa disimpan indexedDd
                                resolve(data);
                            });
                    }
                });
        }

        fetch(`${baseUrl}/teams/${idTeam}`, headers)
            .then(status)
            .then(json)
            .then(data => {
                const klubHtml = `
                    <div class="col s12 m7">
                    <div class="card">
                        <div class="card-content">
                            <div class="row">
                                <div class="col s12 m12 l12 center-align">
                                    <img src="${data.crestUrl}" style="max-width: 150px;" alt="Logo Klub ${data.shortName}">
                                </div>
                                <div class="col s12 m12 l12 center-align">
                                    <h4>${data.shortName}</h4>
                                </div>
                                <div class="col s3 m3 l3">
                                    <span>Nama</span>
                                </div>
                                <div class="col s9 m9 l9 left-align">
                                    <span>: ${data.name}</span>
                                </div>
                                <div class="col s3 m3 l3">
                                    <span>Alamat</span>
                                </div>
                                <div class="col s9 m9 l9 left-align">
                                    <span>: ${data.address}</span>
                                </div>
                                <div class="col s3 m3 l3">
                                    <span>Berdiri</span>
                                </div>
                                <div class="col s9 m9 l9 left-align">
                                    <span>: ${data.founded}</span>
                                </div>
                                <div class="col s3 m3 l3">
                                    <span>Stadion</span>
                                </div>
                                <div class="col s9 m9 l9 left-align">
                                    <span>: ${data.venue}</span>
                                </div>
                                <div class="col s3 m3 l3">
                                    <span>Website</span>
                                </div>
                                <div class="col s9 m9 l9 left-align">
                                    <span>: <a href="${data.website}" target="_blank">${data.website}</a></span>
                                </div>
                                <div class="col s3 m3 l3">
                                    <span>Email</span>
                                </div>
                                <div class="col s9 m9 l9 left-align">
                                    <span>: ${data.email ? data.email : '-'}</span>
                                </div>
                                <div class="col s12 m12 l12">
                                    <h5>Pemain Tim</h5>
                                    <table class="highlight">
                                        <thead>
                                            <tr>
                                                <th class="center-align">Nama</th>
                                                <th class="center-align">Posisi</th>
                                                <th class="center-align">Asal Negara</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            ${data.squad.map(squad => {
                                                if(squad.role === "PLAYER") {
                                                    return `
                                                    <tr>
                                                        <td>${squad.name}</td>
                                                        <td class="center-align">${squad.position}</td>
                                                        <td class="center-align">${squad.nationality}</td>
                                                    </tr>`
                                                }
                                            }).join('')}
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                `;
                        
                document.getElementById('body-content').innerHTML = klubHtml;

                document.getElementById('progress-bar').style.display = 'none';
                
                // console.log(data);
                // kirim object parsing json dalam bentuk promise resolve agar bisa disimpan indexedDd
                resolve(data);

            })
    })
}
 
const getAllKlubSaved = () => {
    getAll().then(klubs => {
        
        let klubHtml = '';
        if(klubs.length > 0) {
            klubs.forEach(klub => {
                klubHtml += `
                    <div class="col s12 m7">
                        <div class="card">
                            <a href="./klub.html?id=${klub.id}&saved=true">
                            <div class="card-content">
                                <div class="row">
                                        <div class="col s12 m12 l12 center-align">
                                            <img src="${klub.crestUrl}" style="max-width: 150px;" alt="Logo Klub ${klub.shortName}">
                                        </div>
                                        <div class="col s12 m12 l12 center-align">
                                            <h4>${klub.shortName}</h4>
                                        </div>
                                        <div class="col s3 m3 l3">
                                            <span>Nama</span>
                                        </div>
                                        <div class="col s9 m9 l9 left-align">
                                            <span>: ${klub.name}</span>
                                        </div>
                                        <div class="col s3 m3 l3">
                                            <span>Alamat</span>
                                        </div>
                                        <div class="col s9 m9 l9 left-align">
                                            <span>: ${klub.address}</span>
                                        </div>
                                        <div class="col s3 m3 l3">
                                            <span>Berdiri</span>
                                        </div>
                                        <div class="col s9 m9 l9 left-align">
                                            <span>: ${klub.founded}</span>
                                        </div>
                                        <div class="col s3 m3 l3">
                                            <span>Stadion</span>
                                        </div>
                                        <div class="col s9 m9 l9 left-align">
                                            <span>: ${klub.venue}</span>
                                        </div>
                                        <div class="col s3 m3 l3">
                                            <span>Website</span>
                                        </div>
                                        <div class="col s9 m9 l9 left-align">
                                            <span>: <a href="${klub.website}" target="_blank">${klub.website}</a></span>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </div>
                `;

            });    
        }
        else {
            klubHtml = `
                <div class="container">
                    <div class="row">
                        <div class="col s12 m12 l12 center-align">
                            <h5>Tidak Ada Klub Tersimpan :)</h5>
                        </div>
                    </div>
                </div>
            `
        }

        document.getElementById('body-content').innerHTML = klubHtml;
    })
}

const getSavedKlubById = (id) => {
    const urlParams = new URLSearchParams(window.location.search);
    const idKlub = urlParams.get('id');

    document.getElementById('progress-bar').style.display = 'block';

    getById(idKlub).then(klub => {
        const klubHtml = `
            <div class="col s12 m7">
            <div class="card">
                <div class="card-content">
                    <div class="row">
                        <div class="col s12 m12 l12 center-align">
                            <img src="${klub.crestUrl}" style="max-width: 150px;" alt="Logo Klub ${klub.shortName}">
                        </div>
                        <div class="col s12 m12 l12 center-align">
                            <h4>${klub.shortName}</h4>
                        </div>
                        <div class="col s3 m3 l3">
                            <span>Nama</span>
                        </div>
                        <div class="col s9 m9 l9 left-align">
                            <span>: ${klub.name}</span>
                        </div>
                        <div class="col s3 m3 l3">
                            <span>Alamat</span>
                        </div>
                        <div class="col s9 m9 l9 left-align">
                            <span>: ${klub.address}</span>
                        </div>
                        <div class="col s3 m3 l3">
                            <span>Berdiri</span>
                        </div>
                        <div class="col s9 m9 l9 left-align">
                            <span>: ${klub.founded}</span>
                        </div>
                        <div class="col s3 m3 l3">
                            <span>Stadion</span>
                        </div>
                        <div class="col s9 m9 l9 left-align">
                            <span>: ${klub.venue}</span>
                        </div>
                        <div class="col s3 m3 l3">
                            <span>Website</span>
                        </div>
                        <div class="col s9 m9 l9 left-align">
                            <span>: <a href="${klub.website}" target="_blank">${klub.website}</a></span>
                        </div>
                        <div class="col s3 m3 l3">
                            <span>Email</span>
                        </div>
                        <div class="col s9 m9 l9 left-align">
                            <span>: ${klub.email ? klub.email : '-'}</span>
                        </div>
                        <div class="col s12 m12 l12">
                            <h5>Pemain Tim</h5>
                            <table class="highlight">
                                <thead>
                                    <tr>
                                        <th class="center-align">Nama</th>
                                        <th class="center-align">Posisi</th>
                                        <th class="center-align">Asal Negara</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    ${klub.squad.map(squad => {
                                        if(squad.role === "PLAYER") {
                                            return `
                                            <tr>
                                                <td>${squad.name}</td>
                                                <td class="center-align">${squad.position}</td>
                                                <td class="center-align">${squad.nationality}</td>
                                            </tr>`
                                        }
                                    }).join('')}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        `;
                
        document.getElementById('body-content').innerHTML = klubHtml;

        document.getElementById('progress-bar').style.display = 'none';
        
    })
}