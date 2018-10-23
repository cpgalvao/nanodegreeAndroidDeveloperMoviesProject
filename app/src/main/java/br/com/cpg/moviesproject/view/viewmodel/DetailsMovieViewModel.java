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

public class DetailsMovieViewModel extends ViewModel {
    private MovieBean mDetailBean;
    private MutableLiveData<TrailersBean> mTrailers;
    private MutableLiveData<ReviewsBean> mReviews;

    public DetailsMovieViewModel(MovieBean movieBean) {
        mTrailers = new MutableLiveData<>();
        mReviews = new MutableLiveData<>();
        mDetailBean = movieBean;

        LoadTrailersTask loadTrailersTask = new LoadTrailersTask(new TaskCompleteListener<TrailersBean>() {
            @Override
            public void onTaskComplete(TrailersBean result) {
                mTrailers.setValue(result);
            }
        });
        loadTrailersTask.execute(mDetailBean.getId());

        LoadReviewsTask loadReviewsTask = new LoadReviewsTask(new TaskCompleteListener<ReviewsBean>() {
            @Override
            public void onTaskComplete(ReviewsBean result) {
                mReviews.setValue(result);
            }
        });
        loadReviewsTask.execute(mDetailBean.getId());
    }

    public LiveData<TrailersBean> getTrailers() {
        return mTrailers;
    }

    public LiveData<ReviewsBean> getReviews() {
        return mReviews;
    }

    public List<DetailsInterface> getDetailsData(Context ctx) {
        List<DetailsInterface> data = new ArrayList<>();
        data.add(mDetailBean);
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
