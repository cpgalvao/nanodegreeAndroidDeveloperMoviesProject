package br.com.cpg.moviesproject.view.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import br.com.cpg.moviesproject.controller.LoadTrailersTask;
import br.com.cpg.moviesproject.controller.TaskCompleteListener;
import br.com.cpg.moviesproject.model.bean.DetailsInterface;
import br.com.cpg.moviesproject.model.bean.MovieBean;
import br.com.cpg.moviesproject.model.bean.TrailersBean;

public class DetailsMovieViewModel extends ViewModel {
    private MovieBean mDetailBean;
    private MutableLiveData<TrailersBean> mTrailers;

    public DetailsMovieViewModel(MovieBean movieBean) {
        mTrailers = new MutableLiveData<>();
        mDetailBean = movieBean;
        LoadTrailersTask loadTrailersTask = new LoadTrailersTask(new TaskCompleteListener<TrailersBean>() {
            @Override
            public void onTaskComplete(TrailersBean result) {
                mTrailers.setValue(result);
            }
        });
        loadTrailersTask.execute(mDetailBean.getId());
    }

    public LiveData<TrailersBean> getTrailers() {
        return mTrailers;
    }

    public List<DetailsInterface> getDetailsData() {
        List<DetailsInterface> data = new ArrayList<>();
        data.add(mDetailBean);
        if (mTrailers.getValue() != null && !mTrailers.getValue().getTrailerList().isEmpty()) {
            data.addAll(mTrailers.getValue().getTrailerList());
        }
        return data;
    }
}
