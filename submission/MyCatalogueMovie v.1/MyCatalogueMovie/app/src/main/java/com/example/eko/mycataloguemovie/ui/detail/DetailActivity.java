package com.example.eko.mycataloguemovie.ui.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.eko.mycataloguemovie.BuildConfig;
import com.example.eko.mycataloguemovie.R;
import com.example.eko.mycataloguemovie.base.BaseActivity;
import com.example.eko.mycataloguemovie.data.model.Movie;
import com.example.eko.mycataloguemovie.data.model.Trailer;
import com.example.eko.mycataloguemovie.data.network.MovieService;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class DetailActivity extends BaseActivity implements DetailView, View.OnClickListener{
    public static final String MOVIE_INTENT = "movie.intent";

    CollapsingToolbarLayout collapsingToolbar;
    Toolbar toolbar;
    ImageView movieBackdrop, moviePoster;
    TextView movieTitle, movieRating, movieVoteCount, movieOverview, trailersLabel, movieReleaseDate, movieSummary;
    View movieBackground;
    LinearLayout trailersLayout;
    HorizontalScrollView trailersContainer;
    AppBarLayout appbar;
    NestedScrollView scrollingContainer;
    ProgressBar loadingProgress;

    DetailInteractor detailInteractor;
    DetailPresenter detailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        toolbar = findViewById(R.id.toolbar);
        movieBackdrop = findViewById(R.id.movie_backdrop);
        moviePoster = findViewById(R.id.movie_poster);
        movieTitle = findViewById(R.id.movie_title);
        movieRating = findViewById(R.id.movie_rating);
        movieVoteCount = findViewById(R.id.movie_vote_count);
        movieOverview = findViewById(R.id.movie_overview);
        trailersLabel = findViewById(R.id.trailers_label);
        movieReleaseDate = findViewById(R.id.movie_release_date);
        movieSummary = findViewById(R.id.movie_summary);
        movieBackground = findViewById(R.id.movie_background);
        trailersLayout = findViewById(R.id.trailers);
        trailersContainer = findViewById(R.id.trailers_container);
        appbar = findViewById(R.id.appbar);
        scrollingContainer = findViewById(R.id.scrolling_container);
        loadingProgress = findViewById(R.id.loading_progress);

        setUnbinder(ButterKnife.bind(this));
        setUp();

        if (savedInstanceState == null){
            Movie movie = getIntent().getParcelableExtra(MOVIE_INTENT);
            if (movie != null){
                displayDetails(movie);
                detailPresenter.onAttach(this);
                detailPresenter.fetchTrailers(String.valueOf(movie.getId()));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detailPresenter.onDetach();
    }

    @Override
    protected void setUp() {
        detailInteractor = new DetailInteractorImpl(MovieService.ServiceGenerator.instance());
        detailPresenter = new DetailPresenter(detailInteractor, new CompositeDisposable());

        collapsingToolbar.setContentScrimColor(ContextCompat.getColor(this, R.color.colorPrimary));
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedToolbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedToolbar);
        collapsingToolbar.setTitleEnabled(true);

        if (toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void displayDetails(Movie movie){
        if (movie.getOverview().isEmpty()){
            movieOverview.setVisibility(View.GONE);
            movieSummary.setVisibility(View.GONE);
        }else {
            movieOverview.setVisibility(View.VISIBLE);
            movieSummary.setVisibility(View.VISIBLE);

            Glide.with(this)
                    .load(BuildConfig.API_BACKDROP_PATH + movie.getBackdropPath())
                    .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_placeholder)
                        .centerCrop())
                    .into(movieBackdrop);
            movieOverview.setText(movie.getOverview());
            movieRating.setText(String.format(getString(R.string.rating), String.valueOf(movie.getVoteAverage())));
            movieTitle.setText(movie.getTitle());
            movieVoteCount.setText(String.format(getString(R.string.vote_count), String.valueOf(movie.getVoteCount())));
            movieReleaseDate.setText(String.format(getString(R.string.release_date), String.valueOf(movie.getRealeaseDate())));
            collapsingToolbar.setTitle(movie.getTitle());

            RequestOptions requestOptions = new RequestOptions();
            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .asBitmap()
                    .apply(new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .placeholder(R.drawable.ic_placeholder)
                    .centerCrop())
                    .load(BuildConfig.API_POSTER_PATH + movie.getPosterPath())
                    .into(new BitmapImageViewTarget(moviePoster){
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                            super.onResourceReady(resource, transition);

                            Palette.from(resource).generate(palette -> {
                                movieBackground.setBackgroundColor(palette
                                    .getVibrantColor(getResources().getColor(R.color.black_translucent_60)));
                            });
                        }
                    });
            }

        }


    @Override
    public void displayTrailers(List<Trailer> trailers) {
        if (trailers.isEmpty()){
            trailersLabel.setVisibility(View.GONE);
            trailersContainer.setVisibility(View.GONE);
            trailersLayout.setVisibility(View.GONE);
        }else {
            trailersLabel.setVisibility(View.VISIBLE);
            trailersContainer.setVisibility(View.VISIBLE);
            trailersLayout.setVisibility(View.VISIBLE);

            trailersLayout.removeAllViews();

            for (Trailer trailer : trailers){
                View thumbContainer = LayoutInflater.from(this).inflate(R.layout.thumbail, trailersLayout, false);
                ImageView thumbView = ButterKnife.findById(thumbContainer, R.id.video_thum);
                thumbView.setTag(trailer.getKey());
                thumbView.requestLayout();
                thumbView.setOnClickListener(this);
                Picasso.with(this)
                        .load(trailer.getImageVideoUrl())
                        .centerCrop()
                        .placeholder(R.drawable.ic_placeholder)
                        .resizeDimen(R.dimen.video_width, R.dimen.video_height)
                        .into(thumbView);
                trailersLayout.addView(thumbContainer);
            }
        }
    }

    @Override
    public void onClick(View view) {
        String key = view.getTag().toString();
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + key)));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
