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
import br.com.cpg.moviesproject.model.bean.HeaderBean;
import br.com.cpg.moviesproject.model.bean.MovieBean;
import br.com.cpg.moviesproject.model.bean.ReviewBean;
import br.com.cpg.moviesproject.model.bean.TrailerBean;
import br.com.cpg.moviesproject.view.viewholder.DetailViewHolder;
import br.com.cpg.moviesproject.view.viewholder.DetailsBaseViewHolder;
import br.com.cpg.moviesproject.view.viewholder.HeaderViewHolder;
import br.com.cpg.moviesproject.view.viewholder.ReviewViewHolder;
import br.com.cpg.moviesproject.view.viewholder.TrailerViewHolder;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsBaseViewHolder> {
    private final List<DetailsInterface> mDetailsMovieData;
    private final TrailerViewHolder.TrailerClickHandler mTrailerClickHandler;

    public DetailsAdapter(TrailerViewHolder.TrailerClickHandler trailerClickHandler) {
        mDetailsMovieData = new ArrayList<>();
        mTrailerClickHandler = trailerClickHandler;
    }

    @Override
    public int getItemViewType(int position) {
        DetailMovieType type;
        DetailsInterface item = mDetailsMovieData.get(position);
        if (item instanceof MovieBean) {
            type = DetailMovieType.DETAIL;
        } else if (item instanceof TrailerBean) {
            type = DetailMovieType.TRAILER;
        } else if (item instanceof ReviewBean) {
            type = DetailMovieType.REVIEW;
        } else if (item instanceof HeaderBean) {
            type = DetailMovieType.HEADER;
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
                View trailerView = inflater.inflate(R.layout.trailer_item, parent, false);
                viewHolder = new TrailerViewHolder(trailerView, mTrailerClickHandler);
                break;
            case REVIEW:
                View reviewView = inflater.inflate(R.layout.review_item, parent, false);
                viewHolder = new ReviewViewHolder(reviewView);
                break;
            case HEADER:
                View headerView = inflater.inflate(R.layout.header_item, parent, false);
                viewHolder = new HeaderViewHolder(headerView);
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

    private enum DetailMovieType {
        DETAIL, TRAILER, HEADER, REVIEW
    }

    public interface DetailsClickListener {
        void onItemClick(DetailsInterface item);
    }
}
