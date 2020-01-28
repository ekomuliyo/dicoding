package com.ekomuliyo.cataloguemovies.ui.activity;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ekomuliyo.cataloguemovies.api.Api;
import com.ekomuliyo.cataloguemovies.api.ApiRetrofit;
import com.ekomuliyo.cataloguemovies.model.Movie;
import com.ekomuliyo.cataloguemovies.model.MovieResponse;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivityViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Movie>> movieListMovieOrTv = new MutableLiveData<>();

    private Api api = ApiRetrofit.getRetrofit().create(Api.class);

    public void setMovieOrTvList(String query){
        Call<MovieResponse> responseCall = api.getSearchMovieOrTvShow(query);
        responseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                try {
                    ArrayList<Movie> movies = response.body().getResult();
                    for (Movie movie : movies){
                        StringBuilder stringBuilder = new StringBuilder(String.valueOf(movie.getGenre_ids()));
                        stringBuilder.deleteCharAt(0);
                        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

                        movie.setGenre_id(String.valueOf(stringBuilder));
                    }
                    movieListMovieOrTv.postValue(movies);
                    Log.d(SearchActivityViewModel.class.getSimpleName(), movies.toString());
                }catch (Exception e){
                    Log.d(MainActivity.class.getSimpleName(), e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d(SearchActivityViewModel.class.getSimpleName(), t.getMessage());
            }
        });
    }

    public LiveData<ArrayList<Movie>> getMovieOrTv(){
        return movieListMovieOrTv;
    }
}
