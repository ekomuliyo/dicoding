package com.ekomuliyo.cataloguemovies.ui.fragment.favorite;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekomuliyo.cataloguemovies.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavoriteFragment extends Fragment {


    public MovieFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_favorite, container, false);
    }

}
