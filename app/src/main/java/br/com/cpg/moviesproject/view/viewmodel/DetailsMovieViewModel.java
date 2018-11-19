package br.com.cpg.moviesproject.view.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.com.cpg.moviesproject.R;
import br.com.cpg.moviesproject.controller.LoadReviewsTask;
import br.com.cpg.moviesproject.controller.LoadTrailersTask;
import br.com.cpg.moviesproject.controller.TaskCompleteListener;
import br.com.cpg.moviesproject.model.bean.DetailsInterface;
import br.com.cpg.moviesproject.model.bean.HeaderBean;
import br.com.cpg.moviesproject.model.bean.MovieBean;
import br.com.cpg.moviesproject.model.bean.ReviewsBean;
import br.com.cpg.moviesproject.model.bean.TrailersBean;
import br.com.cpg.moviesproject.model.persistence.AppDatabase;

public class DetailsMovieViewModel extends ViewModel {
    private MutableLiveData<TrailersBean> mTrailers;
    private MutableLiveData<ReviewsBean> mReviews;
    private LiveData<MovieBean> mFavoriteBean;
    private MovieBean mMovieBean;

    public DetailsMovieViewModel(AppDatabase database, MovieBean movieBean) {
        mMovieBean = movieBean;
        mTrailers = new MutableLiveData<>();
        mReviews = new MutableLiveData<>();

        mFavoriteBean = database.favoriteDao().loadFavoriteById(movieBean.getId());

        LoadTrailersTask loadTrailersTask = new LoadTrailersTask(new TaskCompleteListener<TrailersBean>() {
            @Override
            public void onTaskComplete(TrailersBean result) {
                mTrailers.setValue(result);
            }
        });
        loadTrailersTask.execute(movieBean.getId());

        LoadReviewsTask loadReviewsTask = new LoadReviewsTask(new TaskCompleteListener<ReviewsBean>() {
            @Override
            public void onTaskComplete(ReviewsBean result) {
                mReviews.setValue(result);
            }
        });
        loadReviewsTask.execute(movieBean.getId());
    }

    public LiveData<MovieBean> getMovie() {
        return mFavoriteBean;
    }

    public LiveData<TrailersBean> getTrailers() {
        return mTrailers;
    }

    public LiveData<ReviewsBean> getReviews() {
        return mReviews;
    }

    public List<DetailsInterface> getDetailsData(Context ctx) {
        List<DetailsInterface> data = new ArrayList<>();
        mMovieBean.setFavorite(mFavoriteBean.getValue() != null);
        data.add(mMovieBean);

        if (mTrailers.getValue() != null && !mTrailers.getValue().getTrailerList().isEmpty()) {
            data.add(new HeaderBean(ctx.getString(R.string.trailer_header)));
            data.addAll(mTrailers.getValue().getTrailerList());
        }
        if (mReviews.getValue() != null && !mReviews.getValue().getReviewsList().isEmpty()) {
            data.add(new HeaderBean(ctx.getString(R.string.reviews_header)));
            data.addAll(mReviews.getValue().getReviewsList());
        }
        return data;
    }
}
