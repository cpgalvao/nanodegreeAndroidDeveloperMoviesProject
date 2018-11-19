package br.com.cpg.moviesproject.view.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import br.com.cpg.moviesproject.model.bean.MovieBean;
import br.com.cpg.moviesproject.model.persistence.AppDatabase;

public class FavoritesViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private AppDatabase mDatabase;

    public FavoritesViewModelFactory(AppDatabase database) {
        mDatabase = database;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new FavoritesViewModel(mDatabase);
    }
}
