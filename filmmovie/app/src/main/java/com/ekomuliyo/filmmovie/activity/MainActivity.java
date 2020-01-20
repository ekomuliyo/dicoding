package com.ekomuliyo.filmmovie.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.ekomuliyo.filmmovie.R;
import com.ekomuliyo.filmmovie.fragment.FavoriteFragment;
import com.ekomuliyo.filmmovie.fragment.MovieFragment;
import com.ekomuliyo.filmmovie.fragment.SearchMovieFragment;
import com.ekomuliyo.filmmovie.fragment.SearchTVFragment;
import com.ekomuliyo.filmmovie.fragment.TVFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    private SearchView mSearchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (getSupportActionBar() != null){
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setTitle(R.string.movies);
        }
        if (savedInstanceState == null){
            loadFragment(new MovieFragment());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null){
            mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
            mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            mSearchView.setQueryHint(getResources().getString(R.string.search_hint));
            setupSearchView();
        }
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            toolbar.collapseActionView();
            int mMenuId = menuItem.getItemId();
            switch (mMenuId){
                case R.id.nav_bottom_movies:
                    mSearchView.setIconified(true); // close searchview
                    mSearchView.clearFocus(); // close keyboard searchview

                    if (getSupportActionBar() != null){
                        getSupportActionBar().setTitle(R.string.movies);
                        getSupportActionBar().show();
                    }
                    fragment = new MovieFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_bottom_tv:
                    mSearchView.setIconified(true); // close searchview
                    mSearchView.clearFocus(); // close keyboard searchview
                    if (getSupportActionBar() != null){
                        getSupportActionBar().setTitle(R.string.tv_shows);
                        getSupportActionBar().show();
                    }
                    fragment = new TVFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_bottom_favorite:
                    getSupportActionBar().hide();
                    mSearchView.setIconified(true); // close searchview
                    mSearchView.clearFocus(); // close keboard searchview
                    fragment = new FavoriteFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void setupSearchView() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Bundle bundle = new Bundle();
                bundle.putString("query", newText);

                if (newText.length() > 0){
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    int selectedItemBottom = navigation.getSelectedItemId();
                    if (selectedItemBottom == R.id.nav_bottom_movies){
                        SearchMovieFragment fragment = new SearchMovieFragment();
                        fragment.setArguments(bundle);
                        transaction.replace(R.id.frame_container, fragment);
                        transaction.commit();
                    }else if (selectedItemBottom == R.id.nav_bottom_tv){
                        SearchTVFragment fragment = new SearchTVFragment();
                        fragment.setArguments(bundle);
                        transaction.replace(R.id.frame_container, fragment);
                        transaction.commit();
                    }
                }
                else {
                    navigation.setSelectedItemId(navigation.getSelectedItemId());
                }
                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int items = item.getItemId();
        switch (items){
            case R.id.btn_localization:
                Intent intentSetting = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intentSetting);
                break;
            case R.id.btn_notification:
                Intent intentNotification = new Intent(this, NotificationSettingActivity.class);
                startActivity(intentNotification);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
