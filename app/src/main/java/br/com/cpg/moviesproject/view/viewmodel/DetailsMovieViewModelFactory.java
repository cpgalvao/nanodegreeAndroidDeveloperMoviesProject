package br.com.cpg.moviesproject.view.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import br.com.cpg.moviesproject.model.bean.MovieBean;
import br.com.cpg.moviesproject.model.persistence.AppDatabase;

public class DetailsMovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final AppDatabase mDatabase;
    private final MovieBean mMovieBean;

    public DetailsMovieViewModelFactory(AppDatabase database, MovieBean movieBean) {
        mDatabase = database;
        mMovieBean = movieBean;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new DetailsMovieViewModel(mDatabase, mMovieBean);
    }
}
