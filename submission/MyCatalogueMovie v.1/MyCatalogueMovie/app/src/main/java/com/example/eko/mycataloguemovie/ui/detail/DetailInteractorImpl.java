package com.example.eko.mycataloguemovie.ui.detail;

import com.example.eko.mycataloguemovie.base.BaseInteractor;
import com.example.eko.mycataloguemovie.data.model.Trailer;
import com.example.eko.mycataloguemovie.data.network.MovieService;

import java.util.List;

import io.reactivex.Observable;

public class DetailInteractorImpl extends BaseInteractor<MovieService> implements DetailInteractor {

    public DetailInteractorImpl(MovieService service) {
        super(service);
    }

    @Override
    public Observable<List<Trailer>> getMovieTrailers(String id) {
        return getService().getMovieTrailers(id).flatMap(trailerResponse -> Observable.just(trailerResponse.getResults()));
    }
}
