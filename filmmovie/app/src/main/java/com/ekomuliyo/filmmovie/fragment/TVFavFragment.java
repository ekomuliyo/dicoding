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
public class TVFavFragment extends Fragment {

    @BindView(R.id.rv_tv_fav)
    RecyclerView rvTv;
    private MovieFavAdapter adapter;

    public TVFavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_tv_fav, container, false);
        ButterKnife.bind(this, view);
        rvTv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MovieFavAdapter(getActivity());
        rvTv.setAdapter(adapter);

        ArrayList<Movie> data = (ArrayList<Movie>) listMovie();

        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.setFavMovie(data);
        mainViewModel.getMovies().observe(this, getMovies);
        return view;
    }

    private List<Movie> listMovie() {
        MovieDatabase database = Room.databaseBuilder(getActivity(), MovieDatabase.class, "db_movie")
                .allowMainThreadQueries()
                .build();
        MovieDao movieDao = database.getMovieDao();
        return movieDao.getMovieByMovieType("tv");
    }

    public Observer<ArrayList<Movie>> getMovies = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null){
                adapter.setMovies(movies);
            }
        }
    };

}
