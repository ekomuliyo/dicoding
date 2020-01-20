package com.ekomuliyo.filmmovie.widget;

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
import com.ekomuliyo.filmmovie.BuildConfig;
import com.ekomuliyo.filmmovie.R;
import com.ekomuliyo.filmmovie.db.MovieDao;
import com.ekomuliyo.filmmovie.db.MovieDatabase;
import com.ekomuliyo.filmmovie.model.Movie;

import java.util.ArrayList;

public class FavMovieWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final ArrayList<Movie> widgetItems = new ArrayList<>();
    private MovieDatabase database;
    private final Context context;

    public FavMovieWidgetRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        final long indetityToken = Binder.clearCallingIdentity();
        database = Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class, "db_movie")
                .allowMainThreadQueries()
                .build();
        Binder.restoreCallingIdentity(indetityToken);

        Log.d("FavMovieWidgetRemoteV", widgetItems.toString());
    }

    @Override
    public void onDataSetChanged() {
        try {
            MovieDao movieDao = database.getMovieDao();
            widgetItems.clear();
            widgetItems.addAll(movieDao.getAllFavMovies());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        database.close();
    }

    @Override
    public int getCount() {
        return widgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        try {
            Bitmap bitmap = Glide.with(context)
                    .asBitmap()
                    .load(BuildConfig.IMAGE_URL + widgetItems.get(position).getPoster())
                    .apply(new RequestOptions().fitCenter())
                    .submit(800, 550)
                    .get();
            remoteViews.setImageViewBitmap(R.id.iv_widget, bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();
        bundle.putInt(FavMovieWidget.EXTRA_ITEM, position);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        remoteViews.setOnClickFillInIntent(R.id.iv_widget, intent);
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
