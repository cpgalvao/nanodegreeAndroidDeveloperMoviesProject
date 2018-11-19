package br.com.cpg.moviesproject.view.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import br.com.cpg.moviesproject.model.bean.MovieBean;
import br.com.cpg.moviesproject.model.persistence.AppDatabase;

public class DetailsMovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private AppDatabase mDatabase;
    private MovieBean mMovieBean;

    public DetailsMovieViewModelFactory(AppDatabase database, MovieBean movieBean) {
        mDatabase = database;
        mMovieBean = movieBean;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new DetailsMovieViewModel(mDatabase, mMovieBean);
    }
}
