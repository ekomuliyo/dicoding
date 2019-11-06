package com.example.eko.mycataloguemovie.ui.detail;

import com.example.eko.mycataloguemovie.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DetailPresenter extends BasePresenter<DetailView, DetailInteractor> {
    public DetailPresenter(DetailInteractor mvpInteractor, CompositeDisposable compositeDisposable) {
        super(mvpInteractor, compositeDisposable);
    }

    public void fetchTrailers(String id){
        getCompositeDisposable()
                .add(getInteractor().getMovieTrailers(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(trailers -> {
                        if (trailers != null && trailers.size() > 0){
                            getMvpView().displayTrailers(trailers);
                        }
                    }, throwable -> {
                        getMvpView().onError(throwable.getLocalizedMessage());
                    }));
    }
}
