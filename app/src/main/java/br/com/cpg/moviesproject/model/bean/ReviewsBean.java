package br.com.cpg.moviesproject.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewsBean {
    @SerializedName("results")
    private List<ReviewBean> reviewsList;

    public List<ReviewBean> getReviewsList() {
        return reviewsList;
    }

    @SuppressWarnings("unused")
    public void setReviewsList(List<ReviewBean> reviewsList) {
        this.reviewsList = reviewsList;
    }
}
