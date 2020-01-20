package com.ekomuliyo.cataloguemovies.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ekomuliyo.cataloguemovies.BuildConfig;
import com.ekomuliyo.cataloguemovies.R;
import com.ekomuliyo.cataloguemovies.adapter.GenreAdapter;
import com.ekomuliyo.cataloguemovies.db.DbMovie;
import com.ekomuliyo.cataloguemovies.db.MovieDAO;
import com.ekomuliyo.cataloguemovies.model.Movie;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRAS_MOVIES = "extra.movies";

    @BindView(R.id.fab_favorite)
    FloatingActionButton fabFavorite;
    @BindView(R.id.rb_movie)
    RatingBar rbMovie;
    @BindView(R.id.rv_genre)
    RecyclerView rvGenre;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.iv_backdrop)
    ImageView ivBackdrop;
    @BindView(R.id.iv_poster)
    ImageView ivPoster;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_vote_count)
    TextView tvVoteCount;
    @BindView(R.id.tv_vote_average)
    TextView tvVoteAverage;
    @BindView(R.id.tv_overview)
    TextView tvOverView;

    private Movie movie;
    private GenreAdapter genreAdapter;
    private boolean isBtnFav = false;
    private MovieDAO movieDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        movieDAO = Room.databaseBuilder(this, DbMovie.class, "db_movie")
                .allowMainThreadQueries()
                .build()
                .getMovieDAO();

        init();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init(){
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_left_white_18dp);
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.colorTextWhite));
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.colorTextWhite));
        collapsingToolbarLayout.setTitleEnabled(false);

        movie = getIntent().getParcelableExtra(EXTRAS_MOVIES);
        show(movie);

        fabFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBtnFav){
                    fabFavorite.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_border_white_24dp));
                    Toast.makeText(DetailActivity.this, getResources().getString(R.string.message_success_remove_favorite), Toast.LENGTH_SHORT).show();
                    movieDAO.deletedById(movie.getId());
                    isBtnFav = false;

                }else {
                    fabFavorite.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_white_24dp));
                    movieDAO.insert(movie);
                    Toast.makeText(DetailActivity.this, getResources().getString(R.string.message_success_add_favorite), Toast.LENGTH_SHORT).show();
                    isBtnFav = true;
                }
            }
        });
    }

    private void show(Movie movie) {

        if (movieDAO.findMovieById(movie.getId()) > 0){
            fabFavorite.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_star_white_24dp));
            isBtnFav = true;
        }

        // menampilkan data genre
        if (movie.getGenre_ids() != null) {

            String genreId = movie.getGenre_id();

            String[] splitGenre = genreId.split(",");
            ArrayList<String> genreName = new ArrayList<>();
            for (int i = 0; i < splitGenre.length; i++) {
                String switchStr = splitGenre[i];
                switchStr = switchStr.replace(".0", "").trim();
                int switchInt = Integer.parseInt(switchStr);

                switch (switchInt) {
                    case 10759:
                        genreName.add("Action & Adventure");
                        break;
                    case 16:
                        genreName.add("Animation");
                        break;
                    case 35:
                        genreName.add("Comedy");
                        break;
                    case 80:
                        genreName.add("Crime");
                        break;
                    case 99:
                        genreName.add("Documentary");
                        break;
                    case 18:
                        genreName.add("Drama");
                        break;
                    case 10751:
                        genreName.add("Family");
                        break;
                    case 10762:
                        genreName.add("Kids");
                        break;
                    case 9648:
                        genreName.add("Mystery");
                        break;
                    case 10763:
                        genreName.add("News");
                        break;
                    case 10764:
                        genreName.add("Reality");
                        break;
                    case 10765:
                        genreName.add("Sci-Fi & Fantasy");
                        break;
                    case 10766:
                        genreName.add("Soap");
                        break;
                    case 10767:
                        genreName.add("Talk");
                        break;
                    case 10768:
                        genreName.add("War & Politics");
                        break;
                    case 37:
                        genreName.add("Western");
                        break;
                    case 28:
                        genreName.add("Action");
                        break;
                    case 12:
                        genreName.add("Adventure");
                        break;
                    case 14:
                        genreName.add("Fantasy");
                        break;
                    case 36:
                        genreName.add("History");
                        break;
                    case 27:
                        genreName.add("Horror");
                        break;
                    case 10402:
                        genreName.add("Music");
                        break;
                    case 10749:
                        genreName.add("Romance");
                        break;
                    case 878:
                        genreName.add("Science Fiction");
                        break;
                    case 10770:
                        genreName.add("TV Movie");
                        break;
                    case 53:
                        genreName.add("Thriller");
                        break;
                    case 10752:
                        genreName.add("War");
                        break;
                    default:
                        break;
                }

            }
            rvGenre.setLayoutManager(new GridLayoutManager(this, 2));
            genreAdapter = new GenreAdapter(this, genreName);
            rvGenre.setAdapter(genreAdapter);
        }

        // menampilkan gambar backdrop dan poster
        if (movie.getPosterPath() != null){
            Glide.with(this)
                    .load(BuildConfig.IMAGE_URL + movie.getPosterPath())
                    .into(ivPoster);
        }else{
            Glide.with(this)
                    .load(R.drawable.ic_local_movies_white_24dp)
                    .apply(new RequestOptions())
                    .into(ivPoster);
        }

        if (movie.getBackdropPath() != null){
            Glide.with(this)
                    .load(BuildConfig.IMAGE_URL + movie.getBackdropPath())
                    .into(ivBackdrop);
        }else {
            Glide.with(this)
                    .load(R.drawable.ic_local_movies_white_24dp)
                    .apply(new RequestOptions())
                    .into(ivBackdrop);
        }

        tvTitle.setText(movie.getTitle());
        tvVoteCount.setText("(" + movie.getVoteCount() + ")");
        float floatAverage = Float.parseFloat(String.valueOf(movie.getVoteAverage())) / 2 ;
        tvVoteAverage.setText(String.valueOf(floatAverage));
        rbMovie.setRating(floatAverage);
        tvOverView.setText(movie.getOverview());


    }
}
