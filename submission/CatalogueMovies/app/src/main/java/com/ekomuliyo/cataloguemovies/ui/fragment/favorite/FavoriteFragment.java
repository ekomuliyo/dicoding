package com.ekomuliyo.cataloguemovies.ui.fragment.favorite;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekomuliyo.cataloguemovies.R;
import com.ekomuliyo.cataloguemovies.adapter.TabLayoutAdapter;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;


    public FavoriteFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_favorite, container, false);

        ButterKnife.bind(this, view);
        TabLayoutAdapter tabLayoutAdapter = new TabLayoutAdapter(getChildFragmentManager());
        tabLayoutAdapter.addFragment(new MovieFavoriteFragment(), getResources().getString(R.string.movies));
        tabLayoutAdapter.addFragment(new TVFavoriteFragment(), getResources().getString(R.string.tvshows));
        viewPager.setAdapter(tabLayoutAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

}
