package com.ekomuliyo.filmmovie.api;

import com.ekomuliyo.filmmovie.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndponts {
    @GET("discover/{type}")
    Call<MovieResponse> getMoviesOrTV(@Path("type") String movieType);

    @GET("search/movie")
    Call<MovieResponse> searchMovies(@Query("query") String query);

    @GET("search/tv")
    Call<MovieResponse> searchTVShow(@Query("query") String query);

}
