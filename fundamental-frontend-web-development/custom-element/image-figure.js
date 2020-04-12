/*  setiap class yang akan jadi component harus extends HTMLElement
    wajib menambahkan super() di constructor()
    lifecycle custom element ada 4
    1. connectedCallback() => akan dipanggil ketika element/component berhasil ditampilkan di DOM, biasanya digunakan untuk konfigurasi awal data, attribute
    2. disconnectedCallback() => akan dipanggil ketika element/component dikeluarkan dari DOM atau remove(), biasanya digunakan untuk membersihkan data 
    3. attributeChangedCallback() => akan dipanggil ketika ada perubahan attribute yang dipanggil dari fungsi static get observedAttributes(), biasanya digunakan untuk memuat ulang data, atribute, dll 
    4. adoptedCallback() => akan dipanggil ketika element/component dipindahkan pada document baru, jika kita menggunakan <iframe> maka callback ini akan dipanggil
*/ 
class ImageFigure extends HTMLElement{
    constructor(){
        super()
        console.log('Constrcuted!');
    }

    connectedCallback(){
        console.log('Connected!');
        this.src = this.getAttribute("src") || null;
        this.alt = this.getAttribute("alt") || null;
        this.caption = this.getAttribute("caption") || null;
        this.render();
    }

    // fungsi render akan dipanggil diawal dan ketika nilai di dalamnya ada perubahan
    render() {
        this.innerHTML = `
          <figure>
            <img src="${this.src}"
                alt="${this.alt}">
            <figcaption>${this.caption}</figcaption>
          </figure>
        `;
      }

    disconnectedCallback(){
        console.log('Disconnected!');
    }

    adoptedCallback(){
        console.log('Adopted!');
    }

    // name, attribute yang akan diubah
    // oldValue, nilai attribute sebelum diubah
    // newValue, nilai attribute baru
    attributeChangedCallback(name, oldValue, newValue){
        this[name] = newValue;
        this.render();
    }

    // digunakan untuk mengamati perubahan nilai atribute caption
    // apabila ingin mengamati lebih dari satu nilai bisa dengan
    // return ["cation", "title", "src"]
    static get observedAttributes(){
        return ["caption", "src", "alt"];
    }
}

// untuk menentukan nama element dan element yang akan dibuat
customElements.define("image-figure", ImageFigure);