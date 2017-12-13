package br.com.cpg.moviesproject.controller;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import br.com.cpg.moviesproject.BuildConfig;
import br.com.cpg.moviesproject.R;
import br.com.cpg.moviesproject.model.bean.MoviesBean;
import br.com.cpg.moviesproject.model.business.TheMovieDBBO;
import br.com.cpg.moviesproject.view.MainActivity;

public class LoadMoviesTask extends AsyncTask<Object, Void, MoviesBean> {
    private final TaskCompleteListener<MoviesBean> mTaskCompleteListener;

    public LoadMoviesTask(TaskCompleteListener<MoviesBean> taskCompleteListener) {
        mTaskCompleteListener = taskCompleteListener;
    }

    @Override
    protected MoviesBean doInBackground(Object... params) {
        if (params.length == 0) {
            return null;
        }

        MoviesBean moviesBean;

        TheMovieDBBO.MoviesSortOrder sortOrder = (TheMovieDBBO.MoviesSortOrder) params[0];

        TheMovieDBBO bo = new TheMovieDBBO();

        String apiKey = BuildConfig.THE_MOVIE_DB_API_KEY;

        switch (sortOrder) {
            case TOP_RATED:
                moviesBean = bo.getTopRatedMoviesList(apiKey);
                break;
            case POPULAR:
            default:
                moviesBean = bo.getPopularMoviesList(apiKey);
                break;

        }

        return moviesBean;
    }

    @Override
    protected void onPostExecute(MoviesBean moviesBean) {
        super.onPostExecute(moviesBean);
        if (mTaskCompleteListener != null) {
            mTaskCompleteListener.onTaskComplete(moviesBean);
        }
    }
}
