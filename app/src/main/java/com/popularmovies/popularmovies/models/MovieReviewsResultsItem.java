package com.popularmovies.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MovieReviewsResultsItem implements Parcelable {

    @SerializedName("author")
    private String author;

    @SerializedName("id")
    private String id;

    @SerializedName("content")
    private String content;

    @SerializedName("url")
    private String url;

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.author);
        dest.writeString(this.id);
        dest.writeString(this.content);
        dest.writeString(this.url);
    }

    public MovieReviewsResultsItem() {
    }

    protected MovieReviewsResultsItem(Parcel in) {
        this.author = in.readString();
        this.id = in.readString();
        this.content = in.readString();
        this.url = in.readString();
    }

    public static final Creator<MovieReviewsResultsItem> CREATOR = new Creator<MovieReviewsResultsItem>() {
        @Override
        public MovieReviewsResultsItem createFromParcel(Parcel source) {
            return new MovieReviewsResultsItem(source);
        }

        @Override
        public MovieReviewsResultsItem[] newArray(int size) {
            return new MovieReviewsResultsItem[size];
        }
    };
}