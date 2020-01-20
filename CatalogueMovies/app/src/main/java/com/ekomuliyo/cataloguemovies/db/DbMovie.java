package com.ekomuliyo.cataloguemovies.db;

import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.RoomDatabase;

import com.ekomuliyo.cataloguemovies.model.Movie;

@Database(entities = {Movie.class}, version = 1)
public abstract class DbMovie extends RoomDatabase {
    public abstract MovieDAO getMovieDAO();
}
