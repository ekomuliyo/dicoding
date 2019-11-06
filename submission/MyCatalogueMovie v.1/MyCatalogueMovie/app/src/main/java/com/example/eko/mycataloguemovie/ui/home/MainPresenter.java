package com.example.eko.mycataloguemovie.ui.home;

import com.example.eko.mycataloguemovie.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter<V extends  MainView, I extends MainInteractor> extends BasePresenter<V, I> {

    public MainPresenter(I mvpInteractor, CompositeDisposable compositeDisposable) {
        super(mvpInteractor, compositeDisposable);
    }

    public void getSearchMovies(String movieName){
        getMvpView().showLoading(true);

        getCompositeDisposable()
                .add(getInteractor().fetchSearchMovies(movieName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movies -> {
                    getMvpView().showLoading(false);

                    if (movies != null && movies.size() > 0){
                        getMvpView().displayMovies(movies);
                        getMvpView().showStatus(true);
                    }else {
                        getMvpView().showStatus(false);
                    }
                }, throwable -> {
                    getMvpView().showLoading(false);
                    getMvpView().onError(throwable.getLocalizedMessage());
                }));
    }
}
