package com.ekomuliyo.cataloguemovies.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ekomuliyo.cataloguemovies.BuildConfig;
import com.ekomuliyo.cataloguemovies.R;
import com.ekomuliyo.cataloguemovies.db.DbMovie;
import com.ekomuliyo.cataloguemovies.db.MovieDAO;
import com.ekomuliyo.cataloguemovies.model.Movie;

import java.util.ArrayList;

public class FavoriteRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private final ArrayList<Movie> movieArrayList = new ArrayList<>();
    private DbMovie dbMovie;
    private final Context context;

    public FavoriteRemoteViewFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        final long token = Binder.clearCallingIdentity();
        dbMovie = Room.databaseBuilder(context.getApplicationContext(), DbMovie.class, "db_movie")
                .allowMainThreadQueries()
                .build();
        Binder.restoreCallingIdentity(token);

        Log.d("onCreate()", movieArrayList.toString());
    }

    @Override
    public void onDataSetChanged() {
        try {
            MovieDAO movieDAO = dbMovie.getMovieDAO();
            movieArrayList.clear();
            movieArrayList.addAll(movieDAO.getAllFavoriteMovie());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        dbMovie.close();
    }

    @Override
    public int getCount() {
        return movieArrayList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        try {
            Bitmap bitmap = Glide.with(context)
                    .asBitmap()
                    .load(BuildConfig.IMAGE_URL + movieArrayList.get(position).getPosterPath())
                    .apply(new RequestOptions())
                    .submit(800, 550)
                    .get();
            remoteViews.setImageViewBitmap(R.id.iv_widget_movie, bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();
        bundle.putInt(FavoriteWidget.EXTRA_ITEM, position);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        remoteViews.setOnClickFillInIntent(R.id.iv_widget_movie, intent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
