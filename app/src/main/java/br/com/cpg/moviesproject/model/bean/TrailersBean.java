package br.com.cpg.moviesproject.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailersBean {
    @SerializedName("results")
    private List<TrailerBean> trailerList;

    public List<TrailerBean> getTrailerList() {
        return trailerList;
    }

    @SuppressWarnings("unused")
    public void setTrailerList(List<TrailerBean> trailerList) {
        this.trailerList = trailerList;
    }
}
