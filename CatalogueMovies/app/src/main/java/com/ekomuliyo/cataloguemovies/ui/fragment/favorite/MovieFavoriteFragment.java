package com.ekomuliyo.cataloguemovies.ui.fragment.favorite;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekomuliyo.cataloguemovies.R;
import com.ekomuliyo.cataloguemovies.adapter.MovieTVFavoriteAdapter;
import com.ekomuliyo.cataloguemovies.db.DbMovie;
import com.ekomuliyo.cataloguemovies.db.MovieDAO;
import com.ekomuliyo.cataloguemovies.model.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavoriteFragment extends Fragment {

    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;
    private MovieTVFavoriteAdapter adapter;

    public MovieFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_movie_favorite, container, false);
        ButterKnife.bind(this, view);
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MovieTVFavoriteAdapter(getActivity());
        rvMovies.setAdapter(adapter);

        ArrayList<Movie> movies = (ArrayList<Movie>) loadFavMoviesFromDB();

        FavoriteViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FavoriteViewModel.class);
        viewModel.setFavorite(movies);
        viewModel.getFavorite().observe(this, getMovieFavorite);
        return view;

    }

    private List<Movie> loadFavMoviesFromDB() {
        DbMovie dbMovie = Room.databaseBuilder(getActivity(), DbMovie.class, "db_movie")
                .allowMainThreadQueries()
                .build();
        MovieDAO movieDAO = dbMovie.getMovieDAO();
        return movieDAO.getMovieByType("movie");
    }

    private Observer<ArrayList<Movie>> getMovieFavorite = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null){
                adapter.setMovies(movies);
            }
        }
    };
}
