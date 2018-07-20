package com.popularmovies.popularmovies.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

/**
 * Created by Safa Amin on 7/20/2018.
 */

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    private LiveData<List<MovieEntity>> movies;

    public MainViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the movie from the DataBase");
        movies = database.movieDao().loadAllFavoriteMovies();
    }

    public LiveData<List<MovieEntity>> getFavoriteMovies() {

        return movies;
    }
}
