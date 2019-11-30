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
import android.widget.Toast;

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
public class SearchMovieFragment extends Fragment {

    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout mShimmerViewContainer;
    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;
    private MovieAdapter adapter;

    public SearchMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_movie, container, false);
        ButterKnife.bind(this, view);
        rvMovies.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        adapter = new MovieAdapter(getActivity());
        rvMovies.setAdapter(adapter);
        mShimmerViewContainer.startShimmer();

        String query = getArguments().getString("query");

        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.searchMovie(query); // mengarahkan api endpoint ke searchMovie
        mainViewModel.getMovies().observe(this, getMovies);
        return view;
    }

    Observer<ArrayList<Movie>> getMovies = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            mShimmerViewContainer.startShimmer();
            mShimmerViewContainer.setVisibility(View.GONE);
            if (movies != null && movies.size() > 0){
                adapter.setMovieArrayList(movies);
            }else {
                Toast.makeText(getContext(), R.string.not_found, Toast.LENGTH_SHORT).show();
            }
        }
    };



}
