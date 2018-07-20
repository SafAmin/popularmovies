package com.popularmovies.popularmovies.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Safa Amin on 7/20/2018.
 */

@Entity(tableName = "favorites")
public class MovieEntity {

    @PrimaryKey
    private int id;
    private String poster;
    private String name;
    private String releaseDate;
    private String rating;
    private String overview;
    private boolean isFavorite;

    public MovieEntity(int id, String poster, String name, String releaseDate,
                       String rating, String overview, boolean isFavorite) {
        setId(id);
        setPoster(poster);
        setName(name);
        setReleaseDate(releaseDate);
        setRating(rating);
        setOverview(overview);
        setFavorite(isFavorite);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String movieName) {
        this.name = movieName;
    }

    public String getName() {
        return name;
    }

    public void setPoster(String moviePoster) {
        this.poster = moviePoster;
    }

    public String getPoster() {
        return poster;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOverview() {
        return overview;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isFavorite() {
        return isFavorite;
    }
}
