package com.ekomuliyo.cataloguemovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ekomuliyo.cataloguemovie.R;
import com.ekomuliyo.cataloguemovie.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    ArrayList<Movie> movies;

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.tvMovieTitle.setText(movie.getTitle());
        holder.tvMovieOverview.setText(movie.getOverview());
        holder.tvMovieRealease.setText(movie.getOverview());

        Glide.with(context)
                .load(movie.getPosterPath())
                .into(holder.ivMovie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView ivMovie;
        private final TextView tvMovieTitle, tvMovieOverview, tvMovieRealease;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivMovie = itemView.findViewById(R.id.iv_movie_poster);
            tvMovieTitle = itemView.findViewById(R.id.tv_movie_title);
            tvMovieOverview = itemView.findViewById(R.id.tv_movie_overview);
            tvMovieRealease = itemView.findViewById(R.id.tv_movie_realease_date);

        }
    }
}
