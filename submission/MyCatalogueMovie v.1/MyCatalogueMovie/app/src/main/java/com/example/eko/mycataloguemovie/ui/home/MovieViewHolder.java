package com.example.eko.mycataloguemovie.ui.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.eko.mycataloguemovie.BuildConfig;
import com.example.eko.mycataloguemovie.R;
import com.example.eko.mycataloguemovie.data.model.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_movie_poster)
    ImageView moviePoster;
    @BindView(R.id.tv_movie_title)
    TextView movieTitle;
    @BindView(R.id.tv_movie_overview)
    TextView movieOverview;
    @BindView(R.id.tv_movie_realease_date)
    TextView movieRealeaseDate;

    public MovieViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Movie movie, Context context){
        Glide.with(context)
                .load(BuildConfig.API_POSTER_PATH+ movie.getPosterPath())
                .apply(new RequestOptions()
                .placeholder(R.drawable.ic_placeholder))
                .into(moviePoster);
        movieTitle.setText(movie.getTitle());
        movieOverview.setText(movie.getOverview());
        movieRealeaseDate.setText(movie.getRealeaseDate());
    }

}
