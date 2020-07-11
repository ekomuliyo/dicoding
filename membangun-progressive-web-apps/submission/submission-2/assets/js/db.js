
// membuat database
const dbPromiseKlub = idb.open('informasi-sepakbola', 1, function(upgradeDB) {
    const klubObjectStore = upgradeDB.createObjectStore('klubsepakbola', { keyPath: 'id'});
    klubObjectStore.createIndex('name', 'name', { unique: false});
});

// simpan klub
const saveClub = (klub) => {
    dbPromiseKlub.then(db => {
        const tx = db.transaction('klubsepakbola', 'readwrite');
        const store = tx.objectStore('klubsepakbola');
        store.put(klub);
        return tx.complete;
    })
    .then(() => {
        console.log('berhasil disimpan!');
    });
}

// mengambil semua data klub yang tersimpan di indexedDB
const getAll = () => {
    return new Promise(resolve => {
        dbPromiseKlub.then(db => {
            const tx = db.transaction('klubsepakbola', 'readonly');
            const store = tx.objectStore('klubsepakbola');
            return store.getAll(); // method untuk mengambil semua data
        })
        .then(klubs => {
            resolve(klubs);
        });
    });
}

// mengambil data by id
const getById = (id) => {
    return new Promise(resolve => {
        dbPromiseKlub.then(db => {
            const tx = db.transaction('klubsepakbola', 'readonly');
            const store = tx.objectStore('klubsepakbola');
            return store.get(parseInt(id));
        })
        .then(klub => {
            resolve(klub);
        });
    });
}

// menghapus data by id
const removeKlubById = (id) => {
    return new Promise(resolve => {
        dbPromiseKlub.then(db => {
            const tx = db.transaction('klubsepakbola', 'readwrite');
            const store = tx.objectStore('klubsepakbola');
            store.delete(parseInt(id));
            return tx.complete;
        })
        .then(() => {
            resolve('berhasil dihapus'); 
        });
    })
}