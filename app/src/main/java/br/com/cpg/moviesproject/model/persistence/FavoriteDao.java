package br.com.cpg.moviesproject.model.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import br.com.cpg.moviesproject.model.bean.MovieBean;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM movie")
    LiveData<List<MovieBean>> loadAllFavorites();

    @Insert
    void insertFavorite(MovieBean movie);

    @Delete
    void deleteFavorite(MovieBean movie);

    @Query("SELECT * FROM movie WHERE id = :id")
    LiveData<MovieBean> loadFavoriteById(int id);
}
