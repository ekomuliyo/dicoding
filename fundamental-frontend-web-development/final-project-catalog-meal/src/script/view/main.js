import ApiInterface from "../api/api-interface.js";
import "../component/progress-bar.js";
import "../component/nav-bar.js";
import "../component/meal-list.js";

const main = () => {
    const navbarElement = document.querySelector('nav-bar');
    const mealListElement = document.querySelector('meal-list');
    const progressBarElement = document.querySelector('progress-bar');

    
    const onButtonSearchClicked = async (event) => {
        try {
            event.preventDefault();

            // menampilkan progress
            progressBarElement.hidden = '';

            // prosess ambil data
            const results = await ApiInterface.searchMeal(navbarElement.valueSearch);

            // menghilangkan progress
            progressBarElement.hidden = 'hidden';

            renderResult(results);
        } catch (error) {
            fallbackResult(error);
            progressBarElement.hidden = 'hidden';

        }
    }

    navbarElement.clickEvent = onButtonSearchClicked;
    
    const renderResult = results => {
        mealListElement.meals = results;
    }

    const fallbackResult = message => {
        mealListElement.renderError(message);
    }
}

export default main;