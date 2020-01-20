package com.ekomuliyo.filmmovie.fragment;


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

import com.ekomuliyo.filmmovie.MainViewModel;
import com.ekomuliyo.filmmovie.R;
import com.ekomuliyo.filmmovie.adapter.MovieFavAdapter;
import com.ekomuliyo.filmmovie.db.MovieDao;
import com.ekomuliyo.filmmovie.db.MovieDatabase;
import com.ekomuliyo.filmmovie.model.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavFragment extends Fragment {

    @BindView(R.id.rv_movies_fav)
    RecyclerView rvMovies;
    private MovieFavAdapter adapter;


    public MovieFavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_fav, container, false);
        ButterKnife.bind(this, view);
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MovieFavAdapter(getActivity());
        rvMovies.setAdapter(adapter);

        ArrayList<Movie> data = (ArrayList<Movie>) loadFavMovies();

        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.setFavMovie(data);
        mainViewModel.getMovies().observe(this, getMovies);
        return view;
    }

    private List<Movie> loadFavMovies(){
        MovieDatabase database = Room.databaseBuilder(getActivity(), MovieDatabase.class, "db_movie")
                .allowMainThreadQueries()
                .build();
        MovieDao movieDao = database.getMovieDao();
        return movieDao.getMovieByMovieType("movie");
    }

    private Observer<ArrayList<Movie>> getMovies = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null){
                adapter.setMovies(movies);
            }
        }
    };

}
