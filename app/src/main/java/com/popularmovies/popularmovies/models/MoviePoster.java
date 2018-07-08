package com.popularmovies.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Safa Amin on 7/8/2018.
 */

public class MoviePoster implements Parcelable {

    private String moviePoster;
    private String movieName;

    public MoviePoster(String moviePoster, String movieName) {
        setMoviePoster(moviePoster);
        setMovieName(movieName);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.moviePoster);
        dest.writeString(this.movieName);
    }

    protected MoviePoster(Parcel in) {
        this.moviePoster = in.readString();
        this.movieName = in.readString();
    }

    public static final Creator<MoviePoster> CREATOR = new Creator<MoviePoster>() {
        @Override
        public MoviePoster createFromParcel(Parcel source) {
            return new MoviePoster(source);
        }

        @Override
        public MoviePoster[] newArray(int size) {
            return new MoviePoster[size];
        }
    };
}
