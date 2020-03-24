package com.ekomuliyo.cataloguemovies.api;

import com.ekomuliyo.cataloguemovies.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    // get data movie
    @GET("discover/{type}")
    Call<MovieResponse> getMoviesOrTvShows(@Path("type") String type);

    // get data movie and tv
    @GET("search/multi")
    Call<MovieResponse> getSearchMovieOrTvShow(@Query("query") String query);

    // get release movie
    @GET("discover/movie")
    Call<MovieResponse> getReleaseMovie(@Query("primary_release_date.gte") String now,@Query("primary_release_date.lte") String now2);
}
