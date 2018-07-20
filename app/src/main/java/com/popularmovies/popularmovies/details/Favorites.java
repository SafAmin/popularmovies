package com.popularmovies.popularmovies.details;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.popularmovies.popularmovies.models.MovieDetails;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class responsible for adding and getting items for favorites
 * <p>
 * Created by Safa Amin on 7/17/2018.
 */

public class Favorites {

    private static final String PREFS_NAME = "Favorite";
    private static final String FAVORITES_KEY = "FAVORITES";

    public Favorites() {
        super();
    }

    void addToFavorite(Context context, MovieDetails movieDetails) {
        ArrayList<MovieDetails> favorites = getFavorites(context);
        if (favorites == null) {
            favorites = new ArrayList<>();
        }
        favorites.add(movieDetails);
        saveFavorites(context, favorites);
    }

    public ArrayList<MovieDetails> getFavorites(Context context) {
        ArrayList<MovieDetails> favorites;
        SharedPreferences settings;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES_KEY)) {
            String jsonFavorites = settings.getString(FAVORITES_KEY, null);
            Gson gson = new Gson();
            MovieDetails[] favoriteItems = gson.fromJson(jsonFavorites, MovieDetails[].class);

            favorites = new ArrayList<>(Arrays.asList(favoriteItems));
            favorites = new ArrayList<>(favorites);
        } else {
            return null;
        }

        return favorites;
    }

    private void saveFavorites(Context context, ArrayList<MovieDetails> favorites) {
        SharedPreferences settings;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES_KEY, jsonFavorites);

        editor.apply();
    }

    void removeFavorites(Context context, MovieDetails movieItem) {
        ArrayList<MovieDetails> favorites = getFavorites(context);
        if (favorites != null) {
            for (int i = 0; i < favorites.size(); i++) {
                if (favorites.get(i).getMovieId() == movieItem.getMovieId()) {
                    favorites.remove(favorites.get(i));
                    break;
                }
            }
            saveFavorites(context, favorites);
        }
    }
}
