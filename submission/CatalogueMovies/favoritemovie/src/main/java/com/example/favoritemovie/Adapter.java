package com.example.favoritemovie;

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

import butterknife.BindView;
import butterknife.ButterKnife;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context context;
    private Cursor cursor;

    public Adapter(Context context) {
        this.context = context;
    }

    public void setData(Cursor data){
        cursor = data;
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
        holder.bind(cursor.moveToPosition(position));
    }

    @Override
    public int getItemCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_overview)
        TextView tvOverview;
        @BindView(R.id.iv_movie)
        ImageView ivMovie;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(boolean moveToPosition) {
            if (moveToPosition){
                tvTitle.setText(cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_TITLE)));
                tvOverview.setText(cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_OVERVIEW)));
                Glide.with(context)
                        .load(Utils.POSTER_BASE_URL
                        + cursor.getString(cursor.getColumnIndexOrThrow(Utils.COLUMN_POSTER)))
                        .into(ivMovie);
            }
        }
    }
}
