package com.ekomuliyo.cataloguemovies.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ekomuliyo.cataloguemovies.R;
import com.ekomuliyo.cataloguemovies.adapter.MoviesTvShowAdapter;
import com.ekomuliyo.cataloguemovies.model.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    public static final String EXTRA_QUERY = "extra.query";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;

    private MoviesTvShowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();

        Intent intent = getIntent();
        String query = intent.getStringExtra(EXTRA_QUERY);

        rvMovies.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new MoviesTvShowAdapter(this);
        rvMovies.setAdapter(adapter);


        SearchActivityViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SearchActivityViewModel.class);
        viewModel.setMovieOrTvList(query);
        final Observer<ArrayList<Movie>> getMovieOrTv = new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
                if (movies != null){
                    adapter.setMovieArrayList(movies);
                }
            }
        };
        viewModel.getMovieOrTv().observe(this, getMovieOrTv);
    }

    private void init() {

        ButterKnife.bind(this);

        if (toolbar != null){
            toolbar.setTitle(getResources().getString(R.string.title_search_activity));
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_left_white_18dp);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
