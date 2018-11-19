package br.com.cpg.moviesproject.controller;

import android.os.AsyncTask;

import br.com.cpg.moviesproject.BuildConfig;
import br.com.cpg.moviesproject.model.bean.TrailersBean;
import br.com.cpg.moviesproject.model.business.TheMovieDBBO;

public class LoadTrailersTask extends AsyncTask<Object, Void, TrailersBean> {
    private final TaskCompleteListener<TrailersBean> mTaskCompleteListener;

    public LoadTrailersTask(TaskCompleteListener<TrailersBean> taskCompleteListener) {
        mTaskCompleteListener = taskCompleteListener;
    }

    @Override
    protected TrailersBean doInBackground(Object... params) {
        if (params.length == 0) {
            return null;
        }

        TrailersBean trailersBean;

        int movieId = (int) params[0];

        TheMovieDBBO bo = new TheMovieDBBO();

        String apiKey = BuildConfig.THE_MOVIE_DB_API_KEY;

        trailersBean = bo.getTrailersList(apiKey, movieId);

        return trailersBean;
    }

    @Override
    protected void onPostExecute(TrailersBean trailersBean) {
        super.onPostExecute(trailersBean);
        if (mTaskCompleteListener != null) {
            mTaskCompleteListener.onTaskComplete(trailersBean);
        }
    }
}
