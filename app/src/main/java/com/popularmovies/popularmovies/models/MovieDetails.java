package com.popularmovies.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Safa Amin on 7/8/2018.
 */

public class MovieDetails implements Parcelable {

    private int movieId;
    private String moviePoster;
    private String movieName;
    private String movieReleaseDate;
    private String movieRating;
    private String movieOverview;
    private boolean isFavorite;

    public MovieDetails() {

    }

    public MovieDetails(int movieId, String moviePoster, String movieName, String movieReleaseDate,
                        double movieRating, String movieOverview, boolean isFavorite) {
        setMovieId(movieId);
        setMoviePoster(moviePoster);
        setMovieName(movieName);
        setMovieReleaseDate(movieReleaseDate);
        setMovieRating(movieRating);
        setMovieOverview(movieOverview);
        setFavorite(isFavorite);
    }

    public void setMovieId(int id) {
        this.movieId = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieReleaseDate(String releaseDate) {
        this.movieReleaseDate = releaseDate;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieRating(double rating) {
        this.movieRating = String.valueOf(rating);
    }

    public String getMovieRating() {
        return movieRating;
    }

    public void setMovieOverview(String overview) {
        this.movieOverview = overview;
    }

    public String getMovieOverview() {
        return movieOverview;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.movieId);
        dest.writeString(this.moviePoster);
        dest.writeString(this.movieName);
        dest.writeString(this.movieReleaseDate);
        dest.writeString(this.movieRating);
        dest.writeString(this.movieOverview);
    }

    protected MovieDetails(Parcel in) {
        this.movieId = in.readInt();
        this.moviePoster = in.readString();
        this.movieName = in.readString();
        this.movieReleaseDate = in.readString();
        this.movieRating = in.readString();
        this.movieOverview = in.readString();
    }

    public static final Creator<MovieDetails> CREATOR = new Creator<MovieDetails>() {
        @Override
        public MovieDetails createFromParcel(Parcel source) {
            return new MovieDetails(source);
        }

        @Override
        public MovieDetails[] newArray(int size) {
            return new MovieDetails[size];
        }
    };
}
