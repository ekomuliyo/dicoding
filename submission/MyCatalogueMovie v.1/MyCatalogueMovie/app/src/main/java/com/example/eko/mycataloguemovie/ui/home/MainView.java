package com.example.eko.mycataloguemovie.ui.home;

import com.example.eko.mycataloguemovie.data.model.Movie;
import com.example.eko.mycataloguemovie.base.MvpView;

import java.util.List;

public interface MainView extends MvpView{
    void showLoading(boolean isShowLoading);
    void displayMovies(List<Movie> movies);
    void showStatus(boolean isDataFound);
}
