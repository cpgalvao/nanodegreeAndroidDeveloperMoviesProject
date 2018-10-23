package br.com.cpg.moviesproject.controller;

import android.os.AsyncTask;

import br.com.cpg.moviesproject.BuildConfig;
import br.com.cpg.moviesproject.model.bean.ReviewsBean;
import br.com.cpg.moviesproject.model.business.TheMovieDBBO;

public class LoadReviewsTask extends AsyncTask<Object, Void, ReviewsBean> {
    private final TaskCompleteListener<ReviewsBean> mTaskCompleteListener;

    public LoadReviewsTask(TaskCompleteListener<ReviewsBean> taskCompleteListener) {
        mTaskCompleteListener = taskCompleteListener;
    }

    @Override
    protected ReviewsBean doInBackground(Object... params) {
        if (params.length == 0) {
            return null;
        }

        ReviewsBean reviewsBean;

        int movieId = (int) params[0];

        TheMovieDBBO bo = new TheMovieDBBO();

        String apiKey = BuildConfig.THE_MOVIE_DB_API_KEY;

        reviewsBean = bo.getReviewsList(apiKey, movieId);

        return reviewsBean;
    }

    @Override
    protected void onPostExecute(ReviewsBean reviewsBean) {
        super.onPostExecute(reviewsBean);
        if (mTaskCompleteListener != null) {
            mTaskCompleteListener.onTaskComplete(reviewsBean);
        }
    }
}
