package com.ekomuliyo.cataloguemovies.ui.fragment.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ekomuliyo.cataloguemovies.api.Api;
import com.ekomuliyo.cataloguemovies.api.ApiRetrofit;
import com.ekomuliyo.cataloguemovies.model.Movie;
import com.ekomuliyo.cataloguemovies.model.MovieResponse;
import com.ekomuliyo.cataloguemovies.ui.activity.MainActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Movie>> moviesList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Movie>> tvShowsList = new MutableLiveData<>();

    private Api api = ApiRetrofit.getRetrofit().create(Api.class);



    public void setMoviesOrTvShows(final String type){
        Call<MovieResponse> responseCall = api.getMoviesOrTvShows(type);
        responseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, retrofit2.Response<MovieResponse> response) {
                try {
                    if (type.equalsIgnoreCase("movie")){
                        ArrayList<Movie> movies = response.body().getResult();
                        for (Movie movie : movies){
                            movie.setType(type);

                            StringBuilder stringBuilder = new StringBuilder(String.valueOf(movie.getGenre_ids()));
                            stringBuilder.deleteCharAt(0);
                            stringBuilder.deleteCharAt(stringBuilder.length() - 1);

                            movie.setGenre_id(String.valueOf(stringBuilder));
                        }
                        moviesList.postValue(movies);
                    }else{
                        ArrayList<Movie> movies = response.body().getResult();
                        for (Movie movie : movies){
                            movie.setType(type);

                            StringBuilder stringBuilder = new StringBuilder(String.valueOf(movie.getGenre_ids()));
                            stringBuilder.deleteCharAt(0);
                            stringBuilder.deleteCharAt(stringBuilder.length() - 1);

                            movie.setGenre_id(String.valueOf(stringBuilder));
                        }
                        tvShowsList.postValue(movies);
                    }
                }catch (Exception e){
                    Log.d(MainActivity.class.getSimpleName(), e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d(MainActivity.class.getSimpleName(), t.getLocalizedMessage());
            }
        });
    }
    public LiveData<ArrayList<Movie>> getMovies(){
        return moviesList;
    }

    public LiveData<ArrayList<Movie>> getTvShows(){
        return tvShowsList;
    }

}
