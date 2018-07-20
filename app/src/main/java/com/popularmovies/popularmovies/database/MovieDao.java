package com.popularmovies.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Safa Amin on 7/20/2018.
 */

@Dao
public interface MovieDao {

    @Query("SELECT * FROM favorites")
    LiveData<List<MovieEntity>> loadAllFavoriteMovies();

    @Insert
    void insertFavoriteMovie(MovieEntity movieEntity);

    @Delete
    void deleteFavorite(MovieEntity movieEntity);
}
