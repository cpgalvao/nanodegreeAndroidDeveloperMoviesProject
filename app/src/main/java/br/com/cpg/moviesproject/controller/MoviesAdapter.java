package br.com.cpg.moviesproject.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.cpg.moviesproject.R;
import br.com.cpg.moviesproject.model.bean.MovieBean;
import br.com.cpg.moviesproject.view.MovieViewHolder;

public class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder> implements MovieViewHolder.OnItemClickListener {

    private final List<MovieBean> mMoviesData;
    private final OnMovieClickHandler mClickHandler;

    public MoviesAdapter(OnMovieClickHandler clickHandler) {
        mMoviesData = new ArrayList<>();
        mClickHandler = clickHandler;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        int layoutId = R.layout.movie_grid_item;
        View view = inflater.inflate(layoutId, parent, false);

        return new MovieViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        MovieBean movieBean = mMoviesData.get(position);
        holder.bind(movieBean);
    }

    @Override
    public int getItemCount() {
        return mMoviesData.size();
    }

    public List<MovieBean> getItems() {
        return Collections.unmodifiableList(mMoviesData);
    }

    public void setMoviesData(List<MovieBean> moviesData) {
        mMoviesData.clear();
        mMoviesData.addAll(moviesData);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(int position, ImageView sharedImageView) {
        mClickHandler.onClick(mMoviesData.get(position), sharedImageView);
    }

    public interface OnMovieClickHandler {
        void onClick(MovieBean movieBean, ImageView sharedImageView);
    }
}
