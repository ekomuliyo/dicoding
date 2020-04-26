class NavBar extends HTMLElement{
    constructor(){
        super();
    }

    connectedCallback(){
        this.render();
    }

    set clickEvent(event){
        this._clickEvent = event;
        this.render();
    }

    get valueSearch(){
        return this.querySelector("#input-search").value;
    }

    render(){
        this.innerHTML = `

        <style>
            .row {
                justify-content: center;
                margin-left: auto;
                margin-right: auto;
                width: 75%;
            }
            .card-text {
                overflow: hidden;
                text-overflow: ellipsis;
                display: -webkit-box;
                -webkit-line-clamp: 3; /* number of lines to show */
                -webkit-box-orient: vertical;
              }
        </style>

        <nav class="navbar navbar-light bg-light">
            <a class="navbar-brand">
                <img src="./src/assets/food.png" width="30" height="30" class="d-inline-block align-top">
                Katalog Makananku
            </a>
            <form class="form-inline">
                <input class="form-control mr-sm-2" type="search" id="input-search" placeholder="Search" aria-label="Search">
                <a href="#" class="btn btn-outline-success my-2 my-sm-0" id="btn-search">Search</a>
            </form>
        </nav>
        `;

        this.querySelector("#btn-search").addEventListener("click", this._clickEvent);
    }
}

customElements.define("nav-bar", NavBar);