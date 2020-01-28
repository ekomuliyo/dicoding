package com.ekomuliyo.cataloguemovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieResponse {

    @SerializedName("results")
    private ArrayList<Movie> result;

    public ArrayList<Movie> getResult() {
        return result;
    }

    public void setResult(ArrayList<Movie> result) {
        this.result = result;
    }
}
