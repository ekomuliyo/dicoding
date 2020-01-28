package com.example.favoritemovie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Adapter adapter;
    private static final int MOVIE = 1;
    @BindView(R.id.rv_favorites)
    RecyclerView rvFavorites;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        rvFavorites.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new Adapter(getApplicationContext());
        rvFavorites.setAdapter(adapter);

        LoaderManager.getInstance(this).initLoader(MOVIE, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        switch (id){
            case MOVIE:
                return new CursorLoader(getApplicationContext(), Utils.CONTENT_URI, null, null, null, null);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == MOVIE){
            try {
                adapter.setData(data);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if (loader.getId() == MOVIE){
            adapter.setData(null);
        }
    }
}
