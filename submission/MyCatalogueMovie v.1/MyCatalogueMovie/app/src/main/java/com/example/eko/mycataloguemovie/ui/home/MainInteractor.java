package com.example.eko.mycataloguemovie.ui.home;


import com.example.eko.mycataloguemovie.data.model.Movie;
import com.example.eko.mycataloguemovie.data.network.MovieService;
import com.example.eko.mycataloguemovie.base.MvpInteractor;

import java.util.List;

import io.reactivex.Observable;


public interface MainInteractor extends MvpInteractor<MovieService>{
    Observable<List<Movie>> fetchSearchMovies(String movieName);
}
