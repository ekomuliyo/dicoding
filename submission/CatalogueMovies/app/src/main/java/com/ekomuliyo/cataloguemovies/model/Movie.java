package com.ekomuliyo.cataloguemovies.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "movies", indices = @Index(value = {"id"}, unique = true))
public class Movie implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private Long id;
    @SerializedName(value = "title", alternate = {"name"})
    private String title;
    @SerializedName(value = "first_air_date", alternate = {"release_date"})
    private String releaseDate;
    @SerializedName("overview")
    private String overview;
    @SerializedName("vote_count")
    private String voteCount;
    @SerializedName("vote_average")
    private String voteAverage;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("media_type")
    private String type;
    @Ignore
    @SerializedName("genre_ids")
    private List<String> genre_ids;
    @SerializedName("genre_id")
    private String genre_id;

    public Movie(){}

    protected Movie(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        title = in.readString();
        releaseDate = in.readString();
        overview = in.readString();
        posterPath = in.readString();
        backdropPath = in.readString();
        type = in.readString();
        genre_id = in.readString();
        voteCount = in.readString();
        voteAverage = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getGenre_ids() {
        return genre_ids;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(String genre_id) {
        this.genre_id = genre_id;
    }

    public void setGenre_ids(List<String> genre_ids) {
        this.genre_ids = genre_ids;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(title);
        dest.writeString(releaseDate);
        dest.writeString(overview);
        dest.writeString(posterPath);
        dest.writeString(backdropPath);
        dest.writeString(type);
        dest.writeString(genre_id);
        dest.writeString(voteCount);
        dest.writeString(voteAverage);
    }
}
