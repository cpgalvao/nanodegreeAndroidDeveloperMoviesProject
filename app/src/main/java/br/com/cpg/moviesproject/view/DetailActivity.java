package br.com.cpg.moviesproject.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.cpg.moviesproject.R;
import br.com.cpg.moviesproject.controller.LoadMoviesTask;
import br.com.cpg.moviesproject.controller.LoadTrailersTask;
import br.com.cpg.moviesproject.controller.TaskCompleteListener;
import br.com.cpg.moviesproject.model.bean.MovieBean;
import br.com.cpg.moviesproject.model.bean.MoviesBean;
import br.com.cpg.moviesproject.model.bean.TrailersBean;
import br.com.cpg.moviesproject.model.business.TheMovieDBBO;
import br.com.cpg.moviesproject.utils.DateUtils;
import br.com.cpg.moviesproject.utils.ImageUtils;
import br.com.cpg.moviesproject.utils.NetworkUtils;

/**
 * Load trailers - OK
 * Change Details UI
 * Adapter
 * Trailers UI
 * Play trailer
 * Load comments
 * Comments UI
 * Favorite
 * Create database/room
 * Rotation
 * Change share - first trailer
 * Lint
 * Remove api key
 */
public class DetailActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getName();
    public static final String EXTRA_MOVIE_DATA = "br.com.cpg.moviesproject.view.extra.MOVIE_DATA";
    public static final String TRANSITION_NAME = "POSTER_TRANSITION";
    private MovieBean mMovieData;

    private TextView mTitle;
    private ImageView mPoster;
    private TextView mReleaseDate;
    private TextView mRating;
    private TextView mOverview;
    private LoadTrailersTask mLoadTrailersTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_MOVIE_DATA)) {
            mMovieData = intent.getParcelableExtra(EXTRA_MOVIE_DATA);
        }

        setupUI();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_element_transition));
            mPoster.setTransitionName(TRANSITION_NAME);
        }

        fillData();
        if (mMovieData != null) {
            loadTrailersData(mMovieData.getId());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int clickedItem = item.getItemId();
        switch (clickedItem) {
            case R.id.action_share:
                String mimeType = "text/plain";
                String title = "Pop Movies";
                ShareCompat.IntentBuilder.from(this).setType(mimeType)
                        .setChooserTitle(title).setText(mMovieData.getTitle() + "\n" + mMovieData.getOverview()).startChooser();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupUI() {
        mTitle = findViewById(R.id.tv_movie_title);
        mPoster = findViewById(R.id.iv_movie_poster);
        mReleaseDate = findViewById(R.id.tv_movie_release_date);
        mOverview = findViewById(R.id.tv_movie_overview);
        mRating = findViewById(R.id.tv_movie_rating);
    }

    private void fillData() {
        if (mMovieData != null) {
            mTitle.setText(mMovieData.getTitle());
            String releaseYear = DateUtils.getYear(mMovieData.getReleaseDate());
            mReleaseDate.setText(releaseYear);
            mOverview.setText(mMovieData.getOverview());
            String rating = String.format(getString(R.string.rating), mMovieData.getUserRating().toString());
            mRating.setText(rating);

            String posterPath = mMovieData.getPosterPath();
            ImageUtils.loadMoviePosterDetail(this, posterPath, mPoster);
        }
    }

    private void loadTrailersData(int movieId) {
        if (mLoadTrailersTask != null) {
            mLoadTrailersTask.cancel(true);
            mLoadTrailersTask = null;
        }

        if (NetworkUtils.verifyNetworkConnection(this)) {
            mLoadTrailersTask = new LoadTrailersTask(new LoadTrailersCompleteListener());
            mLoadTrailersTask.execute(movieId);
        } else {
            Log.e(TAG, "Error loading trailers");
        }
    }

    private class LoadTrailersCompleteListener implements TaskCompleteListener<TrailersBean> {
        @Override
        public void onTaskComplete(TrailersBean trailersBean) {
            if (trailersBean != null && trailersBean.getTrailerList() != null && !trailersBean.getTrailerList().isEmpty()) {
                Log.d(TAG, "Trailers loaded");
            } else {
                Log.d(TAG, "Trailers not loaded");
            }
        }
    }
}
