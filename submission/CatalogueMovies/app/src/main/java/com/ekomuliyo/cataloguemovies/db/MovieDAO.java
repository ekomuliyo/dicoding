package com.ekomuliyo.cataloguemovies.db;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ekomuliyo.cataloguemovies.model.Movie;

import java.util.Collection;
import java.util.List;

@Dao
public interface MovieDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie... movies);

    @Query("DELETE FROM movies WHERE id = :id")
    void deletedById(Long id);

    @Query("SELECT COUNT(*) FROM movies WHERE id = :id")
    int findMovieById(Long id);

    @Query("SELECT * FROM movies WHERE type = :type")
    List<Movie> getMovieByType(String type);

    @Query("SELECT * FROM movies")
    List<Movie> getAllFavoriteMovie();

    @Query("SELECT * FROM movies")
    Cursor getAll();

    @Query("SELECT * FROM movies WHERE id = :id")
    Cursor getById(Long id);
}
