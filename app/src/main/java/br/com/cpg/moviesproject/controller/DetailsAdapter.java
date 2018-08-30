package br.com.cpg.moviesproject.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.cpg.moviesproject.R;
import br.com.cpg.moviesproject.model.bean.DetailsInterface;
import br.com.cpg.moviesproject.model.bean.MovieBean;
import br.com.cpg.moviesproject.model.bean.TrailerBean;
import br.com.cpg.moviesproject.view.viewholder.DetailViewHolder;
import br.com.cpg.moviesproject.view.viewholder.DetailsBaseViewHolder;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsBaseViewHolder> {
    private enum DetailMovieType {
        DETAIL, TRAILER
    }

    private final List<DetailsInterface> mDetailsMovieData;

    public DetailsAdapter() {
        mDetailsMovieData = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        DetailMovieType type;
        DetailsInterface item = mDetailsMovieData.get(position);
        if (item instanceof MovieBean) {
            type = DetailMovieType.DETAIL;
        } else if (item instanceof TrailerBean) {
            type = DetailMovieType.TRAILER;
        } else {
            throw new UnsupportedOperationException("Invalid type at position " + position);
        }

        return type.ordinal();
    }

    @Override
    public DetailsBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        DetailsBaseViewHolder viewHolder = null;
        DetailMovieType type = DetailMovieType.values()[viewType];
        switch (type) {
            case DETAIL:
                View detailView = inflater.inflate(R.layout.detail_item, parent, false);
                viewHolder = new DetailViewHolder(detailView);
                break;
            case TRAILER:
                View trailerView = inflater.inflate(R.layout.detail_item, parent, false);
                viewHolder = new DetailViewHolder(trailerView);
                break;
            default:
                throw new UnsupportedOperationException("Invalid type " + viewType);

        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DetailsBaseViewHolder holder, int position) {
        DetailsInterface detailsMovieBean = mDetailsMovieData.get(position);
        holder.bind(detailsMovieBean);
    }

    @Override
    public int getItemCount() {
        return mDetailsMovieData.size();
    }

    public List<DetailsInterface> getItems() {
        return Collections.unmodifiableList(mDetailsMovieData);
    }

    public void setDetailsData(List<DetailsInterface> detailsMovieData) {
        mDetailsMovieData.clear();
        mDetailsMovieData.addAll(detailsMovieData);
        notifyDataSetChanged();
    }
}
