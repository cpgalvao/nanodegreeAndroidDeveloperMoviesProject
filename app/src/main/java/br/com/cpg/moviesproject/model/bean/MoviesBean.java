package br.com.cpg.moviesproject.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesBean {
    @SerializedName("results")
    private List<MovieBean> moviesList;

    public List<MovieBean> getMoviesList() {
        return moviesList;
    }

    @SuppressWarnings("unused")
    public void setMoviesList(List<MovieBean> moviesList) {
        this.moviesList = moviesList;
    }
}
