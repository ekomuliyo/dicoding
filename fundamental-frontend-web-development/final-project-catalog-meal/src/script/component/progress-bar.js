class ProgressBar extends HTMLElement{
    constructor(){
        super();
    }

    connectedCallback(){
        this.render();
    }

    set hidden(hidden){
        this._hidden = hidden;
        this.render();
    }

    render(){
        this.innerHTML = `
            <style>
            #spinner {
                position: absolute;
                top: 45%;
                left: 45%;
                justify-content: center;
                margin-left: auto;
                margin-right: auto;
                align-content: center;
                border: 16px solid #f3f3f3;
                border-radius: 50%;
                border-top: 16px solid #3498db;
                width: 120px;
                height: 120px;
                -webkit-animation: spin 2s linear infinite; /* Safari */
                animation: spin 2s linear infinite;
              }
              
              /* Safari */
              @-webkit-keyframes spin {
                0% { -webkit-transform: rotate(0deg); }
                100% { -webkit-transform: rotate(360deg); }
              }
              
              @keyframes spin {
                0% { transform: rotate(0deg); }
                100% { transform: rotate(360deg); }
              }
            
            </style>

            <div id="spinner" ${this._hidden == undefined ? 'hidden' : `${this._hidden}`}></div>
        `;
    }
}

customElements.define('progress-bar', ProgressBar);