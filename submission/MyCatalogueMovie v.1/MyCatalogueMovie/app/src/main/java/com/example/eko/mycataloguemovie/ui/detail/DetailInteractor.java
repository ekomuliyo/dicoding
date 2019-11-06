package com.example.eko.mycataloguemovie.ui.detail;

import com.example.eko.mycataloguemovie.base.MvpInteractor;
import com.example.eko.mycataloguemovie.data.model.Trailer;
import com.example.eko.mycataloguemovie.data.network.MovieService;

import java.util.List;

import io.reactivex.Observable;

public interface DetailInteractor extends MvpInteractor<MovieService>{
    Observable<List<Trailer>> getMovieTrailers(String id);
}
