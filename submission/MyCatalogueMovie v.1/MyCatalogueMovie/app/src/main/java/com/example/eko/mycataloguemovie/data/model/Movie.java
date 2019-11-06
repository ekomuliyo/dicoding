package com.example.eko.mycataloguemovie.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable{
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("popularity")
    private float popularity;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String realeaseDate;
    @SerializedName("vote_count")
    private int voteCount;
    @SerializedName("vote_average")
    private Float voteAverage;

    public Movie(){ }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRealeaseDate() {
        return realeaseDate;
    }

    public void setRealeaseDate(String realeaseDate) {
        this.realeaseDate = realeaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.title);
        parcel.writeFloat(this.popularity);
        parcel.writeString(this.posterPath);
        parcel.writeString(this.backdropPath);
        parcel.writeString(this.overview);
        parcel.writeString(this.realeaseDate);
        parcel.writeInt(this.voteCount);
        parcel.writeFloat(this.voteAverage);
    }

    protected Movie(Parcel in){
        this.id = in.readInt();
        this.title = in.readString();
        this.popularity = in.readFloat();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
        this.overview = in.readString();
        this.realeaseDate = in.readString();
        this.voteCount = in.readInt();
        this.voteAverage = in.readFloat();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[0];
        }
    };

}
