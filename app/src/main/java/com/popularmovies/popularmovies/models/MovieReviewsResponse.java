package com.popularmovies.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieReviewsResponse implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("page")
    private int page;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    private List<MovieReviewsResultsItem> results;

    @SerializedName("total_results")
    private int totalResults;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

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

    public void setResults(List<MovieReviewsResultsItem> results) {
        this.results = results;
    }

    public List<MovieReviewsResultsItem> getResults() {
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
        dest.writeInt(this.id);
        dest.writeInt(this.page);
        dest.writeInt(this.totalPages);
        dest.writeList(this.results);
        dest.writeInt(this.totalResults);
    }

    public MovieReviewsResponse() {
    }

    protected MovieReviewsResponse(Parcel in) {
        this.id = in.readInt();
        this.page = in.readInt();
        this.totalPages = in.readInt();
        this.results = new ArrayList<MovieReviewsResultsItem>();
        in.readList(this.results, MovieReviewsResultsItem.class.getClassLoader());
        this.totalResults = in.readInt();
    }

    public static final Creator<MovieReviewsResponse> CREATOR = new Creator<MovieReviewsResponse>() {
        @Override
        public MovieReviewsResponse createFromParcel(Parcel source) {
            return new MovieReviewsResponse(source);
        }

        @Override
        public MovieReviewsResponse[] newArray(int size) {
            return new MovieReviewsResponse[size];
        }
    };
}