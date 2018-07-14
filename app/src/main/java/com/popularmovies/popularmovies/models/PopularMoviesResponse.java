package com.popularmovies.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PopularMoviesResponse implements Parcelable {

    @SerializedName("page")
    private int page;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    private List<ResultsItem> results;

    @SerializedName("total_results")
    private int totalResults;

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setResults(List<ResultsItem> results) {
        this.results = results;
    }

    public List<ResultsItem> getResults() {
        return results;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalResults() {
        return totalResults;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeInt(this.totalPages);
        dest.writeTypedList(this.results);
        dest.writeInt(this.totalResults);
    }

    public PopularMoviesResponse() {
    }

    protected PopularMoviesResponse(Parcel in) {
        this.page = in.readInt();
        this.totalPages = in.readInt();
        this.results = in.createTypedArrayList(ResultsItem.CREATOR);
        this.totalResults = in.readInt();
    }

    public static final Creator<PopularMoviesResponse> CREATOR = new Creator<PopularMoviesResponse>() {
        @Override
        public PopularMoviesResponse createFromParcel(Parcel source) {
            return new PopularMoviesResponse(source);
        }

        @Override
        public PopularMoviesResponse[] newArray(int size) {
            return new PopularMoviesResponse[size];
        }
    };
}