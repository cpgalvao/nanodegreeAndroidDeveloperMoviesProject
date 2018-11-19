package br.com.cpg.moviesproject.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import br.com.cpg.moviesproject.R;
import br.com.cpg.moviesproject.controller.DetailsAdapter;
import br.com.cpg.moviesproject.model.bean.DetailsInterface;
import br.com.cpg.moviesproject.model.bean.MovieBean;
import br.com.cpg.moviesproject.model.bean.ReviewsBean;
import br.com.cpg.moviesproject.model.bean.TrailerBean;
import br.com.cpg.moviesproject.model.bean.TrailersBean;
import br.com.cpg.moviesproject.model.business.YoutubeBO;
import br.com.cpg.moviesproject.model.persistence.AppDatabase;
import br.com.cpg.moviesproject.utils.NetworkUtils;
import br.com.cpg.moviesproject.view.viewmodel.DetailsMovieViewModel;
import br.com.cpg.moviesproject.view.viewmodel.DetailsMovieViewModelFactory;

/**
 * Load trailers - OK
 * Change Details UI - OK
 * Adapter - OK
 * Trailers UI - OK
 * Play trailer - OK
 * Load comments - OK
 * Comments UI - OK
 * Favorite - OK
 * Create database/room - OK
 * Save favorite - OK
 * Animation
 * Rotation - OK
 * Change share - first trailer - OK
 * Lint - OK
 * Remove api key
 */
public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE_DATA = "br.com.cpg.moviesproject.view.extra.MOVIE_DATA";
    public static final String TRANSITION_NAME = "POSTER_TRANSITION";
    private static final String TAG = DetailActivity.class.getName();
    private MovieBean mMovieData;
    private TrailerBean mFirstTrailer;
    private RecyclerView mDetailsList;
    private TextView mErrorMessage;
    private ProgressBar mLoading;

    private DetailsAdapter mAdapter;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDb = AppDatabase.getInstance(getApplicationContext());

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_MOVIE_DATA)) {
            mMovieData = intent.getParcelableExtra(EXTRA_MOVIE_DATA);
        }

        setupUI();

        toLoadingState();

        if (mMovieData != null) {
            List<DetailsInterface> data = new ArrayList<>();
            data.add(mMovieData);
            mAdapter.setDetailsData(data);

            toSuccessState();

            if (NetworkUtils.verifyNetworkConnection(this)) {
                DetailsMovieViewModelFactory factory = new DetailsMovieViewModelFactory(mDb, mMovieData);
                final DetailsMovieViewModel viewModel = ViewModelProviders.of(this, factory).get(DetailsMovieViewModel.class);

                viewModel.getMovie().observe(this, new Observer<MovieBean>() {
                    @Override
                    public void onChanged(@Nullable MovieBean movieBean) {
                        if (movieBean != null) {
                            mMovieData = movieBean;
                            mAdapter.setDetailsData(viewModel.getDetailsData(DetailActivity.this));
                        }
                    }
                });

                viewModel.getTrailers().observe(this, new Observer<TrailersBean>() {
                    @Override
                    public void onChanged(@Nullable TrailersBean trailersBean) {
                        viewModel.getTrailers().removeObserver(this);
                        if (trailersBean != null && trailersBean.getTrailerList() != null && !trailersBean.getTrailerList().isEmpty()) {
                            mFirstTrailer = trailersBean.getTrailerList().get(0);
                            mAdapter.setDetailsData(viewModel.getDetailsData(DetailActivity.this));
                        }
                    }
                });

                viewModel.getReviews().observe(this, new Observer<ReviewsBean>() {
                    @Override
                    public void onChanged(@Nullable ReviewsBean reviewsBean) {
                        viewModel.getReviews().removeObserver(this);
                        if (reviewsBean != null && reviewsBean.getReviewsList() != null && !reviewsBean.getReviewsList().isEmpty()) {
                            mAdapter.setDetailsData(viewModel.getDetailsData(DetailActivity.this));
                        }
                    }
                });
            }
        } else {
            toErrorState();
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
                YoutubeBO youtubeBO = new YoutubeBO();
                String trailerUrl = (mFirstTrailer != null) ? youtubeBO.getTrailerUrl(mFirstTrailer.getKey()).toString() : "";
                ShareCompat.IntentBuilder.from(this).setType(mimeType)
                        .setChooserTitle(title).setText(mMovieData.getTitle() + "\n" + mMovieData.getOverview() + "\n" + trailerUrl).startChooser();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupUI() {
        mDetailsList = findViewById(R.id.rv_details_list);
        mErrorMessage = findViewById(R.id.tv_details_error_message);
        mLoading = findViewById(R.id.pb_details_loading);

        mAdapter = new DetailsAdapter(new DetailsAdapter.OnTrailerClickHandler() {
            @Override
            public void onTrailerClick(DetailsInterface item) {
                if (item != null) {
                    TrailerBean trailer = (TrailerBean) item;
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    YoutubeBO bo = new YoutubeBO();
                    intent.setData(bo.getTrailerUrl(trailer.getKey()));

                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        Log.d(TAG, "Couldn't call " + trailer.getKey() + ", no receiving apps installed!");
                    }
                }
            }
        }, new DetailsAdapter.OnFavoriteClickListener() {
            @Override
            public void onFavoriteClick(int position, DetailsInterface item) {
                Log.d(TAG, "save");

                if (item != null) {
                    final MovieBean movie = (MovieBean) item;
                    final boolean favorite = movie.isFavorite();
                    Log.d(TAG, "save" + !movie.isFavorite());
                    movie.setFavorite(!movie.isFavorite());
                    mMovieData.setFavorite(!mMovieData.isFavorite());
                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (favorite) {
                                mDb.favoriteDao().deleteFavorite(movie);
                            } else {
                                mDb.favoriteDao().insertFavorite(movie);
                            }
                        }
                    });

                    mAdapter.notifyItemChanged(position);
                }
            }
        });

        mDetailsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mDetailsList.setAdapter(mAdapter);
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecor.setDrawable(getResources().getDrawable(R.drawable.divider));
        mDetailsList.addItemDecoration(itemDecor);

        toSuccessState();
    }

    private void toLoadingState() {
        mErrorMessage.setVisibility(View.INVISIBLE);
        mDetailsList.setVisibility(View.INVISIBLE);

        mLoading.setVisibility(View.VISIBLE);
    }

    private void toErrorState() {
        mDetailsList.setVisibility(View.INVISIBLE);
        mLoading.setVisibility(View.INVISIBLE);

        mErrorMessage.setVisibility(View.VISIBLE);
    }

    private void toSuccessState() {
        mLoading.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.INVISIBLE);

        mDetailsList.setVisibility(View.VISIBLE);
    }
}
