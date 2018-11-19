package br.com.cpg.moviesproject.view.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import br.com.cpg.moviesproject.model.persistence.AppDatabase;

public class FavoritesViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final AppDatabase mDatabase;

    public FavoritesViewModelFactory(AppDatabase database) {
        mDatabase = database;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new FavoritesViewModel(mDatabase);
    }
}
