package com.ekomuliyo.widgetfavoritemovie;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieFavoriteAdapter extends RecyclerView.Adapter<MovieFavoriteAdapter.ViewHolder> {

    private Cursor cursor;
    private Context context;

    public MovieFavoriteAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_fav_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(cursor.moveToPosition(position));
    }

    @Override
    public int getItemCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    public void setData(Cursor data) {
        cursor = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_overview)
        TextView tvOverview;
        @BindView(R.id.iv_movie_fav)
        ImageView ivMovieFav;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(boolean movePosition){
            if (movePosition){
                tvTitle.setText(cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_TITLE)));
                tvOverview.setText(cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_DESCRIPTION)));
                Glide.with(context).load(Utils.POSTER_BASE_URL
                        + cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_POSTER))).into(ivMovieFav);
            }
        }
    }
}
