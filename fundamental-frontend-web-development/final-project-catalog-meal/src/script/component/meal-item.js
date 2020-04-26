import ApiInteface from "../api/api-interface.js";
class MealItem extends HTMLElement{
    constructor(){
        super();
    }

    set meal(meal){
        this._meal = meal;
        this.render();
    }

    render(){
        this.innerHTML = '';
        this.classList.add('col-md-4');
        this.innerHTML = `
            <div class="card mt-4 bg-light" style="width: 18rem;">
                <img class="card-img-top" src="${this._meal.strMealThumb}" alt="Card image cap">
                <div class="card-body">
                <h5 class="card-title">${this._meal.strMeal}</h5>
                <p class="card-text">${this._meal.strInstructions}</p>
                </div>
            </div>
        `;
    }

}

customElements.define('meal-item', MealItem);