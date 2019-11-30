package com.ekomuliyo.filmmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ekomuliyo.filmmovie.BuildConfig;
import com.ekomuliyo.filmmovie.R;
import com.ekomuliyo.filmmovie.activity.DetailActivity;
import com.ekomuliyo.filmmovie.model.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Movie> movieArrayList = new ArrayList<>();

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setMovieArrayList(ArrayList<Movie> movies){
        this.movieArrayList = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(movieArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView movieTitle;
        @BindView(R.id.poster)
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent details = new Intent(context, DetailActivity.class);
                    details.putExtra("movie", movieArrayList.get(getAdapterPosition()));
                    context.startActivity(details);
                }
            });

        }

        void bind(Movie movie){
            Glide.with(context)
                .load(BuildConfig.IMAGE_URL + movie.getPoster())
                .apply(new RequestOptions())
                .into(ivPoster);
            movieTitle.setText(movie.getTitle());
        }
    }
}
