import clubs from "./clubs.js"

class DataSource{

    // keyword static untuk pengakses method ini, ketika dipanggil dari class, tanpa membuat instance atau pakai keyword new
    static searchClub(keyword){
        return new Promise((resolve, reject) => {
            const filteredClubs = clubs.filter(club => club.name.toUpperCase().includes(keyword.toUpperCase()));
            if(filteredClubs.length){
                resolve(filteredClubs);
            }else{
                reject(`${keyword} is not found`);
            }
        });
    }
}

export default DataSource;