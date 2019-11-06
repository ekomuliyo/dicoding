package com.example.eko.mycataloguemovie.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerResponse {
    @SerializedName("results")
    private List<Trailer> results;

    public List<Trailer> getResults(){
        return results;
    }

    public void setResults(List<Trailer> results){
        this.results = results;
    }
}
