package com.example.eko.mycataloguemovie.ui.home;

import com.example.eko.mycataloguemovie.base.BaseInteractor;
import com.example.eko.mycataloguemovie.data.model.Movie;
import com.example.eko.mycataloguemovie.data.network.MovieService;

import java.util.List;

import io.reactivex.Observable;

public class MainInteractorImpl extends BaseInteractor<MovieService> implements MainInteractor {

    public MainInteractorImpl(MovieService service) {
        super(service);
    }

    @Override
    public Observable<List<Movie>> fetchSearchMovies(String movieName) {
        return getService().searchMovies(movieName).flatMap(moviesResponse -> Observable.just(moviesResponse.results));
    }
}
