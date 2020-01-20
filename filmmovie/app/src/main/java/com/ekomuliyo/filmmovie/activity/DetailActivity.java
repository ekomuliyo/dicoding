package com.ekomuliyo.filmmovie.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ekomuliyo.filmmovie.BuildConfig;
import com.ekomuliyo.filmmovie.R;
import com.ekomuliyo.filmmovie.db.MovieDao;
import com.ekomuliyo.filmmovie.db.MovieDatabase;
import com.ekomuliyo.filmmovie.model.Movie;

import java.sql.SQLClientInfoException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;
    @BindView(R.id.tv_vote_count)
    TextView tvVoteCount;
    @BindView(R.id.tv_vote_average)
    TextView tvVoteAverage;
    @BindView(R.id.tv_language)
    TextView tvLanguage;
    @BindView(R.id.tv_overview)
    TextView tvOverview;
    @BindView(R.id.iv_backrop)
    ImageView ivBackdrop;
    @BindView(R.id.iv_poster)
    ImageView ivPoster;

    private Movie movie;
    private MovieDao movieDao;

    private boolean isBtnFav = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        initToolbar();
        Intent intent = getIntent();
        movie = intent.getParcelableExtra("movie");
        showMovie(movie);

        movieDao = Room.databaseBuilder(this, MovieDatabase.class, "db_movie")
                .allowMainThreadQueries()
                .build()
                .getMovieDao(); // memanggil database

    }

    private void showMovie(Movie movie) {
        tvTitle.setText(movie.getTitle());
        tvReleaseDate.setText(movie.getReleaseDate());
        tvVoteCount.setText(movie.getVoteCount());
        tvVoteAverage.setText(movie.getVoteAverage());
        tvLanguage.setText(movie.getOriginalLanguage());
        tvOverview.setText(movie.getOverview());

        Glide.with(this)
                .load(BuildConfig.IMAGE_URL+ movie.getBackdrop())
                .into(ivBackdrop);
        Glide.with(this)
                .load(BuildConfig.IMAGE_URL+ movie.getPoster())
                .into(ivPoster);
    }

    private void initToolbar(){
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(R.string.details);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite_menu, menu);
        if (movieDao.getMovieByTitle(movie.getTitle()) > 0){
            setFavoriteSelected(menu.getItem(0));
            isBtnFav = true;
        }
        return true;
    }

    private void setFavoriteSelected(MenuItem item) {
        item.setIcon(R.drawable.ic_favorite_white_24dp);
        isBtnFav = true;

        // memasukan data ke database
        movieDao.insert(movie);
        setResult(RESULT_OK);

        // mengupdate data widget
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        sendBroadcast(intent);
    }

    private void setFavoriteUnSelected(MenuItem item){
//        item.setIcon(R.drawable.ic_favorite_border_white_24dp);
        isBtnFav = false;

        // menghapus data dari database berdasarkan id
        movieDao.deleteById(movie.getId());
        setResult(RESULT_OK);

        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        sendBroadcast(intent);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
            case R.id.fav_btn:
                try {
                    if (isBtnFav){
                        setFavoriteUnSelected(item);
                        Toast.makeText(this, R.string.delete_fav, Toast.LENGTH_SHORT).show();
                    }else {
                        setFavoriteSelected(item);
                        Toast.makeText(this, R.string.succes_insert_fav, Toast.LENGTH_SHORT).show();
                    }
                }catch (SQLiteConstraintException e){
                    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }
}
