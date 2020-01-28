package com.ekomuliyo.cataloguemovies.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.media.UnsupportedSchemeException;
import android.net.Uri;

import androidx.room.Room;

import com.ekomuliyo.cataloguemovies.db.DbMovie;
import com.ekomuliyo.cataloguemovies.db.MovieDAO;

public class FavoriteMovieContentProvider extends ContentProvider {

    private DbMovie dbMovie;
    private MovieDAO movieDAO;
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final String DB_NAME = "db_movie";
    private static final String DB_TABLE = "movies";
    private static final String AUTHORITY = "com.ekomuliyo.cataloguemovies.provider";

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, DB_TABLE, MOVIE);
        uriMatcher.addURI(AUTHORITY, DB_TABLE + "/#", MOVIE_ID);
    }

    public FavoriteMovieContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        dbMovie = Room.databaseBuilder(getContext(), DbMovie.class, DB_NAME).build();
        movieDAO = dbMovie.getMovieDAO();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        final int code = uriMatcher.match(uri);
        if (code == MOVIE || code == MOVIE_ID){
            final Context context = getContext();
            if (context == null){
                return null;
            }
            final Cursor cursor;
            if (code == MOVIE){
                cursor = movieDAO.getAll();
            }
            else {
                cursor = movieDAO.getById(ContentUris.parseId(uri));
            }
            return cursor;
        }
        else {
            throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
