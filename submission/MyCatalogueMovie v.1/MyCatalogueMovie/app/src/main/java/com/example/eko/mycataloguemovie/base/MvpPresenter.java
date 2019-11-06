package com.example.eko.mycataloguemovie.base;

public interface MvpPresenter<V extends MvpView, I extends MvpInteractor> {
    void onAttach(V mvpView);

    void onDetach();

    V getMvpView();

    I getInteractor();
}
