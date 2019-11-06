package com.example.eko.mycataloguemovie.ui.detail;

import com.example.eko.mycataloguemovie.base.MvpView;
import com.example.eko.mycataloguemovie.data.model.Trailer;

import java.util.List;

public interface DetailView extends MvpView {
    void displayTrailers(List<Trailer> trailers);
}
