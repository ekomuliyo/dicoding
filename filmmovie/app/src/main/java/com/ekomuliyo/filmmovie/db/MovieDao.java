package com.ekomuliyo.filmmovie.db;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ekomuliyo.filmmovie.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie... movies);

    @Query("SELECT * FROM movie")
    Cursor selectAll();

    @Query("SELECT * FROM movie WHERE id = :id")
    Cursor selectById(long id);

    @Query("SELECT * FROM movie")
    List<Movie> getAllFavMovies();

    @Query("DELETE FROM movie WHERE id = :id")
    void deleteById(long id);

    @Query("SELECT COUNT(id) FROM movie WHERE title = :title")
    int getMovieByTitle(String title);

    @Query("SELECT * FROM movie WHERE movieType = :movieType")
    List<Movie> getMovieByMovieType(String movieType);

}
