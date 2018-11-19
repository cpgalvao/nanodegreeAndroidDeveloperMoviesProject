package br.com.cpg.moviesproject.view.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import br.com.cpg.moviesproject.model.bean.MovieBean;
import br.com.cpg.moviesproject.model.persistence.AppDatabase;

public class FavoritesViewModel extends ViewModel {
    private final LiveData<List<MovieBean>> mFavorites;

    public FavoritesViewModel(AppDatabase database) {
        mFavorites = database.favoriteDao().loadAllFavorites();
    }

    public LiveData<List<MovieBean>> getFavorites() {
        return mFavorites;
    }
}
