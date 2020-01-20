package com.ekomuliyo.filmmovie.db;



import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ekomuliyo.filmmovie.model.Movie;

@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDao getMovieDao();
}
