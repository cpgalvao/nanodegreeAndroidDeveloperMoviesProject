package br.com.cpg.moviesproject.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.cpg.moviesproject.R;
import br.com.cpg.moviesproject.controller.LoadMoviesTask;
import br.com.cpg.moviesproject.controller.MoviesAdapter;
import br.com.cpg.moviesproject.controller.TaskCompleteListener;
import br.com.cpg.moviesproject.model.bean.MovieBean;
import br.com.cpg.moviesproject.model.bean.MoviesBean;
import br.com.cpg.moviesproject.model.business.TheMovieDBBO;
import br.com.cpg.moviesproject.utils.NetworkUtils;

/**
 * Request/Parser - OK
 * Layout home - OK
 * Layout item - OK
 * Adapter - OK
 * Loading/Error message - OK
 * Menu - sort order - OK
 * Click - OK
 * Layout detail - OK
 * Rotation - OK
 * Share - OK
 * Animation - OK
 * Lint - OK
 * Remove api key - OK
 * Menu favorites
 */
public class MainActivity extends AppCompatActivity implements MoviesAdapter.OnMovieClickHandler {
    private static final String STATE_MOVIES_LIST = "STATE_MOVIES_LIST";
    private static final String STATE_MOVIES_LIST_STATE = "STATE_MOVIES_LIST_STATE";
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

            if (savedInstanceState.containsKey(STATE_MOVIES_LIST_STATE)) {
                Parcelable state = savedInstanceState.getParcelable(STATE_MOVIES_LIST_STATE);
                mMoviesGrid.getLayoutManager().onRestoreInstanceState(state);
            }
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
        outState.putParcelable(STATE_MOVIES_LIST_STATE, mMoviesGrid.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onClick(MovieBean movieBean, ImageView sharedImageView) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MOVIE_DATA, movieBean);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedImageView.setTransitionName(DetailActivity.TRANSITION_NAME);
            intent.putExtra(DetailActivity.TRANSITION_NAME, ViewCompat.getTransitionName(sharedImageView));
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    sharedImageView,
                    ViewCompat.getTransitionName(sharedImageView));
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    private void loadMoviesData(TheMovieDBBO.MoviesSortOrder sortOrder) {
        if (mLoadMoviesTask != null) {
            mLoadMoviesTask.cancel(true);
            mLoadMoviesTask = null;
        }

        if (NetworkUtils.verifyNetworkConnection(this)) {
            toLoadingState();
            mLoadMoviesTask = new LoadMoviesTask(new LoadMoviesCompleteListener());
            mLoadMoviesTask.execute(sortOrder);
        } else {
            toErrorState();
        }
    }

    private void setupUI() {
        mMoviesGrid = findViewById(R.id.rv_movies_grid);
        mErrorMessage = findViewById(R.id.tv_movies_error_message);
        mLoading = findViewById(R.id.pb_movies_loading);

        int spanCount = 2;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
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

    private class LoadMoviesCompleteListener implements TaskCompleteListener<MoviesBean> {
        @Override
        public void onTaskComplete(MoviesBean moviesBean) {
            if (moviesBean != null && moviesBean.getMoviesList() != null && !moviesBean.getMoviesList().isEmpty()) {
                MainActivity.this.toSuccessState();
                MainActivity.this.mAdapter.setMoviesData(moviesBean.getMoviesList());
                MainActivity.this.mMoviesGrid.scrollToPosition(0);
            } else {
                MainActivity.this.toErrorState();
            }
        }
    }
}
