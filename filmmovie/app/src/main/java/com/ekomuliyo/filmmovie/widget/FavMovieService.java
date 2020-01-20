package com.ekomuliyo.filmmovie.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class FavMovieService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FavMovieWidgetRemoteViewsFactory(this.getApplicationContext());
    }
}
