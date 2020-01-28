package com.ekomuliyo.cataloguemovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ekomuliyo.cataloguemovies.BuildConfig;
import com.ekomuliyo.cataloguemovies.R;
import com.ekomuliyo.cataloguemovies.model.Movie;
import com.ekomuliyo.cataloguemovies.ui.activity.DetailActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesTvShowAdapter extends RecyclerView.Adapter<MoviesTvShowAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Movie> movieArrayList = new ArrayList<>();

    public MoviesTvShowAdapter(Context context) {
        this.context = context;
    }

    public void setMovieArrayList(ArrayList<Movie> movieArrayList) {
        this.movieArrayList = movieArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies_and_tv_shows, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String releaseDate = movieArrayList.get(position).getReleaseDate();

        SimpleDateFormat month_date = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
        try {
            if (releaseDate != null){
                Date date = format.parse(releaseDate);
                releaseDate = month_date.format(date);
            }else {
                releaseDate = "";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvTitle.setText(movieArrayList.get(position).getTitle());
        holder.tvReleaseDate.setText(releaseDate);

        if (movieArrayList.get(position).getPosterPath() != null){
            Glide.with(context).load(BuildConfig.IMAGE_URL + movieArrayList.get(position).getPosterPath())
                    .apply(new RequestOptions())
                    .into(holder.imageView);
        }else {
            Glide.with(context).load(R.drawable.ic_local_movies_black_24dp)
                    .apply(new RequestOptions())
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_movies)
        ImageView imageView;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_release_date)
        TextView tvReleaseDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailIntent = new Intent(context, DetailActivity.class);
                    detailIntent.putExtra(DetailActivity.EXTRAS_MOVIES, movieArrayList.get(getAdapterPosition()));
                    context.startActivity(detailIntent);
                }
            });
        }
    }
}
