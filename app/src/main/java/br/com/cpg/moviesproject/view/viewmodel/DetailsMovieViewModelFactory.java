package br.com.cpg.moviesproject.view.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import br.com.cpg.moviesproject.model.bean.MovieBean;

public class DetailsMovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private MovieBean mMovieBean;

    public DetailsMovieViewModelFactory(MovieBean movieBean) {
        mMovieBean = movieBean;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new DetailsMovieViewModel(mMovieBean);
    }
}
