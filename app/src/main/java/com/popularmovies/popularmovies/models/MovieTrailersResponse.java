package com.popularmovies.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieTrailersResponse implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("results")
    private List<MovieTrailersResultsItem> results;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setResults(List<MovieTrailersResultsItem> results) {
        this.results = results;
    }

    public List<MovieTrailersResultsItem> getResults() {
        return results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeTypedList(this.results);
    }

    public MovieTrailersResponse() {
    }

    protected MovieTrailersResponse(Parcel in) {
        this.id = in.readInt();
        this.results = in.createTypedArrayList(MovieTrailersResultsItem.CREATOR);
    }

    public static final Creator<MovieTrailersResponse> CREATOR = new Creator<MovieTrailersResponse>() {
        @Override
        public MovieTrailersResponse createFromParcel(Parcel source) {
            return new MovieTrailersResponse(source);
        }

        @Override
        public MovieTrailersResponse[] newArray(int size) {
            return new MovieTrailersResponse[size];
        }
    };
}