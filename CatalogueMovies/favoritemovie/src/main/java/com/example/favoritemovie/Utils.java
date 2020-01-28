package com.example.favoritemovie;

import android.net.Uri;

public class Utils {

    private static final String TABLE_NAME = "movies";
    private static final String AUTHORITY = "com.ekomuliyo.cataloguemovies.provider";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_OVERVIEW = "overview";
    public static final String COLUMN_POSTER = "posterPath";
    public static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185";

}
