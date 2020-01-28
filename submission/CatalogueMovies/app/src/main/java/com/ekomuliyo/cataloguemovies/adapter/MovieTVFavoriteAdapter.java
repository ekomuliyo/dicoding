package com.ekomuliyo.cataloguemovies.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ekomuliyo.cataloguemovies.BuildConfig;
import com.ekomuliyo.cataloguemovies.R;
import com.ekomuliyo.cataloguemovies.db.DbMovie;
import com.ekomuliyo.cataloguemovies.db.MovieDAO;
import com.ekomuliyo.cataloguemovies.model.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieTVFavoriteAdapter extends RecyclerView.Adapter<MovieTVFavoriteAdapter.ViewHolder>{

    private Context context;
    private List<Movie> movies = new ArrayList<>();

    public MovieTVFavoriteAdapter(Context context) {
        this.context = context;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(movies.get(position).getTitle());
        holder.tvOverview.setText(movies.get(position).getOverview());
        Glide.with(context)
                .load(BuildConfig.IMAGE_URL + movies.get(position).getPosterPath())
                .apply(new RequestOptions())
                .into(holder.ivMovie);
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle(R.string.confirm_to_delete);
                builder.setMessage(R.string.are_you_sure_want_to_delete_this_item);
                builder.setCancelable(false);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DbMovie dbMovie = Room.databaseBuilder(holder.itemView.getContext(), DbMovie.class, "db_movie")
                                .allowMainThreadQueries()
                                .build();
                        MovieDAO movieDAO = dbMovie.getMovieDAO();
                        movieDAO.deletedById(movies.get(position).getId());
                        movies.remove(movies.get(position));
                        notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        builder.setCancelable(true);
                    }
                });
                builder.show();
            }
        });
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
        @BindView(R.id.iv_movie)
        ImageView ivMovie;
        @BindView(R.id.btn_remove)
        ImageButton btnRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
