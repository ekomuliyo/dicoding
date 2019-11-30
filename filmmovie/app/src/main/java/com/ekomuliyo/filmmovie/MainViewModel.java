package com.ekomuliyo.filmmovie;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ekomuliyo.filmmovie.activity.MainActivity;
import com.ekomuliyo.filmmovie.api.ApiClient;
import com.ekomuliyo.filmmovie.api.ApiEndponts;
import com.ekomuliyo.filmmovie.model.Movie;
import com.ekomuliyo.filmmovie.model.MovieResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Movie>> movieList = new MutableLiveData<>();
    private ApiEndponts apiEndponts = ApiClient.getClient().create(ApiEndponts.class);

    public void setMovies(final String movieType){
        Call<MovieResponse> call = apiEndponts.getMoviesOrTV(movieType);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                try {
                    ArrayList<Movie> movies = response.body().getResults();
                    for (Movie data : movies){
                        data.setMovieType(movieType);
                    }
                    movieList.postValue(movies);
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

    public void setFavMovie(ArrayList<Movie> movies){
        movieList.postValue(movies);
    }

    public void searchMovie(String query){
        Call<MovieResponse> call = apiEndponts.searchMovies(query);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                try {
                    ArrayList<Movie> movies = response.body().getResults();
                    movieList.postValue(movies);
                    Log.d(MainActivity.class.getSimpleName(), movies.toString());
                }catch (Exception e){
                    Log.d(MainActivity.class.getSimpleName(), e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d(MainActivity.class.getSimpleName(), "Throwable"+ t.getLocalizedMessage());
            }
        });
    }

    public void searchTV(String query){
        Call<MovieResponse> call = apiEndponts.searchTVShow(query);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                try {
                    ArrayList<Movie> movies = response.body().getResults();
                    movieList.postValue(movies);
                    Log.d(MainActivity.class.getSimpleName(), movies.toString());
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

    public LiveData<ArrayList<Movie>> getMovies(){return movieList;}
}
