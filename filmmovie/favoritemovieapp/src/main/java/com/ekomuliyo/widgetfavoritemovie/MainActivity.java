package com.ekomuliyo.widgetfavoritemovie;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    private MovieFavoriteAdapter adapter;
    private static final int CODE_MOVIE = 1;
    @BindView(R.id.rv_favorite_movie)
    RecyclerView rvFavoriteMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        rvFavoriteMovie.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new MovieFavoriteAdapter(getApplicationContext());
        rvFavoriteMovie.setAdapter(adapter);

        getSupportLoaderManager().initLoader(CODE_MOVIE, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        switch (id){
            case CODE_MOVIE:
                return new CursorLoader(getApplicationContext(), Utils.CONTENT_URI, null, null, null, null);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == CODE_MOVIE){
            try {
                adapter.setData(data);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if (loader.getId() == CODE_MOVIE){
            adapter.setData(null);
        }
    }
}
