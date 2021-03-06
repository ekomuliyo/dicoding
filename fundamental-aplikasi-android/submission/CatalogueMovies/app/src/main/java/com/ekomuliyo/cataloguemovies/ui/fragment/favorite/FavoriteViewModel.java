package com.ekomuliyo.cataloguemovies.ui.fragment.favorite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ekomuliyo.cataloguemovies.model.Movie;

import java.util.ArrayList;

public class FavoriteViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Movie>> movieList = new MutableLiveData<>();

    public void setFavorite(ArrayList<Movie> movie){
        movieList.postValue(movie);
    }

    public LiveData<ArrayList<Movie>> getFavorite(){
        return movieList;
    }
}
