package br.com.cpg.moviesproject.view;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import br.com.cpg.moviesproject.R;
import br.com.cpg.moviesproject.controller.MoviesAdapter;
import br.com.cpg.moviesproject.model.bean.MovieBean;
import br.com.cpg.moviesproject.model.bean.MoviesBean;
import br.com.cpg.moviesproject.model.business.TheMovieDBBO;

/**
 * Request/Parser - OK
 * Layout home - OK
 * Layout item - OK
 * Adapter - OK
 * Loading/Error message - OK
 * Menu - sort order - OK
 * Click - OK
 * Layout detail
 * Rotation
 * Remove api key
 * Lint
 */
public class MainActivity extends AppCompatActivity implements MoviesAdapter.OnMovieClickHandler {
    private static final String STATE_MOVIES_LIST = "STATE_MOVIES_LIST";
    private RecyclerView mMoviesGrid;
    private TextView mErrorMessage;
    private ProgressBar mLoading;

    private MoviesAdapter mAdapter;
    private LoadMoviesTask mLoadMoviesTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();

        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_MOVIES_LIST)) {
            List<MovieBean> moviesList = savedInstanceState.getParcelableArrayList(STATE_MOVIES_LIST);
            mAdapter.setMoviesData(moviesList);
        } else {
            loadMoviesData(TheMovieDBBO.MoviesSortOrder.POPULAR);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int clickedItem = item.getItemId();
        switch (clickedItem) {
            case R.id.action_popular_sort:
                loadMoviesData(TheMovieDBBO.MoviesSortOrder.POPULAR);
                return true;
            case R.id.action_top_rated_sort:
                loadMoviesData(TheMovieDBBO.MoviesSortOrder.TOP_RATED);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_MOVIES_LIST, new ArrayList<Parcelable>(mAdapter.getItems()));
    }

    @Override
    public void onClick(MovieBean movieBean) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MOVIE_DATA, movieBean);
        startActivity(intent);
    }

    private void loadMoviesData(TheMovieDBBO.MoviesSortOrder sortOrder) {
        if (mLoadMoviesTask != null) {
            mLoadMoviesTask.cancel(true);
            mLoadMoviesTask = null;
        }

        mLoadMoviesTask = new LoadMoviesTask(this);
        mLoadMoviesTask.execute(sortOrder);
    }

    private void setupUI() {
        mMoviesGrid = findViewById(R.id.rv_movies_grid);
        mErrorMessage = findViewById(R.id.tv_movies_error_message);
        mLoading = findViewById(R.id.pb_movies_loading);

        int spanCount = 2;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 3;
        }

        int orientation = LinearLayoutManager.VERTICAL;

        GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount, orientation, false);
        mMoviesGrid.setLayoutManager(layoutManager);

        mMoviesGrid.setHasFixedSize(true);

        mAdapter = new MoviesAdapter(this);
        mMoviesGrid.setAdapter(mAdapter);
    }

    private void toLoadingState() {
        mErrorMessage.setVisibility(View.INVISIBLE);
        mMoviesGrid.setVisibility(View.INVISIBLE);

        mLoading.setVisibility(View.VISIBLE);
    }

    private void toErrorState() {
        mMoviesGrid.setVisibility(View.INVISIBLE);
        mLoading.setVisibility(View.INVISIBLE);

        mErrorMessage.setVisibility(View.VISIBLE);
    }

    private void toSuccessState() {
        mLoading.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.INVISIBLE);

        mMoviesGrid.setVisibility(View.VISIBLE);
    }

    private static class LoadMoviesTask extends AsyncTask<Object, Void, MoviesBean> {
        private final WeakReference<MainActivity> mActivity;

        private LoadMoviesTask(MainActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mActivity.get().toLoadingState();
        }

        @Override
        protected MoviesBean doInBackground(Object... params) {
            if (params.length == 0) {
                return null;
            }

            MoviesBean moviesBean;

            TheMovieDBBO.MoviesSortOrder sortOrder = (TheMovieDBBO.MoviesSortOrder) params[0];

            TheMovieDBBO bo = new TheMovieDBBO();

            String apiKey = mActivity.get().getString(R.string.the_movie_db_api_key);

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
            if (moviesBean != null && moviesBean.getMoviesList() != null && !moviesBean.getMoviesList().isEmpty()) {
                mActivity.get().toSuccessState();
                mActivity.get().mAdapter.setMoviesData(moviesBean.getMoviesList());
            } else {
                mActivity.get().toErrorState();
            }
        }
    }
}
