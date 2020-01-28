package com.ekomuliyo.cataloguemovies.ui.fragment.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ekomuliyo.cataloguemovies.R;
import com.ekomuliyo.cataloguemovies.adapter.MoviesTvShowAdapter;
import com.ekomuliyo.cataloguemovies.model.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;
    @BindView(R.id.rv_tv_shows)
    RecyclerView rvTvShows;
    private MoviesTvShowAdapter adapterMovies;
    private MoviesTvShowAdapter adapterTvShows;

    public HomeFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        rvMovies.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapterMovies = new MoviesTvShowAdapter(getActivity());
        rvMovies.setAdapter(adapterMovies);

        rvTvShows.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        adapterTvShows = new MoviesTvShowAdapter(getActivity());
        rvTvShows.setAdapter(adapterTvShows);

        HomeViewModel homeViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);

        homeViewModel.setMoviesOrTvShows("movie");
        homeViewModel.getMovies().observe(this, getMovies);

        homeViewModel.setMoviesOrTvShows("tv");
        homeViewModel.getTvShows().observe(this, getTvShows);
        return view;
    }

    private Observer<ArrayList<Movie>> getMovies = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null){
                adapterMovies.setMovieArrayList(movies);
            }
        }
    };

    private Observer<ArrayList<Movie>> getTvShows = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null){
                adapterTvShows.setMovieArrayList(movies);
            }
        }
    };
}