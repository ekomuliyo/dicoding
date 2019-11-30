package com.ekomuliyo.filmmovie.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekomuliyo.filmmovie.MainViewModel;
import com.ekomuliyo.filmmovie.R;
import com.ekomuliyo.filmmovie.adapter.MovieAdapter;
import com.ekomuliyo.filmmovie.model.Movie;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVFragment extends Fragment {

    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;
    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout mShimmerViewContainer;
    private MovieAdapter adapter;


    public TVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_tv, container, false);
        ButterKnife.bind(this, view);
        rvMovies.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        adapter = new MovieAdapter(getActivity());
        rvMovies.setAdapter(adapter);
        mShimmerViewContainer.startShimmer();

        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.setMovies("tv");
        mainViewModel.getMovies().observe(this, getMovies);
        return view;
    }

    private Observer<ArrayList<Movie>> getMovies = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            mShimmerViewContainer.stopShimmer();
            mShimmerViewContainer.setVisibility(View.GONE);
            adapter.setMovieArrayList(movies);
        }
    };

}
