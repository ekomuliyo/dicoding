package com.ekomuliyo.filmmovie.adapter;

import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ekomuliyo.filmmovie.BuildConfig;
import com.ekomuliyo.filmmovie.R;
import com.ekomuliyo.filmmovie.db.MovieDao;
import com.ekomuliyo.filmmovie.db.MovieDatabase;
import com.ekomuliyo.filmmovie.model.Movie;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieFavAdapter extends RecyclerView.Adapter<MovieFavAdapter.ViewHolder> {

    private Context context;
    private List<Movie> movies = new ArrayList<>();


    public MovieFavAdapter(Context context) {
        this.context = context;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_fav, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        if (movies == null){
            return 0;
        }
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_overview)
        TextView tvOverview;
        @BindView(R.id.iv_movie_fav)
        ImageView poster;
        @BindView(R.id.btn_delete)
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bind(final Movie movie){
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            Glide.with(context)
                    .load(BuildConfig.IMAGE_URL + movie.getPoster())
                    .apply(new RequestOptions())
                    .into(poster);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    builder.setTitle(R.string.confirm_title);
                    builder.setMessage(R.string.confirm_message);
                    builder.setCancelable(false);
                    builder.setPositiveButton(R.string.string_yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MovieDao movieDao = Room.databaseBuilder(itemView.getContext(), MovieDatabase.class, "db_movie")
                                    .allowMainThreadQueries()
                                    .build()
                                    .getMovieDao();
                            movieDao.deleteById(movies.get(getAdapterPosition()).getId());
                            movies.remove(movie);
                            notifyDataSetChanged();
                            // melakukan update widget melalui intent broadcast
//                            Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//                            context.sendBroadcast(intent);
                            Snackbar.make(v, R.string.succes_info, Snackbar.LENGTH_LONG).show();
                        }
                    });

                    builder.setNegativeButton(R.string.string_no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            builder.setCancelable(true);
                        }
                    });
                    builder.show();
                }
            });
        }
    }



}
