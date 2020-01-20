package com.ekomuliyo.cataloguemovies.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ekomuliyo.cataloguemovies.model.Movie;

@Dao
public interface MovieDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie... movies);

    @Query("DELETE FROM movies WHERE id = :id")
    void deletedById(Long id);

    @Query("SELECT COUNT(*) FROM movies WHERE id = :id")
    int findMovieById(Long id);
}
