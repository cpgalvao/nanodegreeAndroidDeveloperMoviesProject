package br.com.cpg.moviesproject.view.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import br.com.cpg.moviesproject.R;
import br.com.cpg.moviesproject.model.bean.MovieBean;
import br.com.cpg.moviesproject.utils.ImageUtils;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final ImageView mMoviePoster;
    private final OnItemClickListener mClickListener;

    public MovieViewHolder(View itemView, OnItemClickListener clickListener) {
        super(itemView);

        mClickListener = clickListener;

        mMoviePoster = itemView.findViewById(R.id.iv_movie_poster);
        itemView.setOnClickListener(this);
    }

    public void bind(MovieBean movieBean) {
        Context context = mMoviePoster.getContext();

        Picasso.with(context).cancelRequest(mMoviePoster);
        String posterPath = movieBean.getPosterPath();
        ImageUtils.loadMoviePosterThumb(context, posterPath, mMoviePoster);
    }

    @Override
    public void onClick(View view) {
        mClickListener.onClick(getAdapterPosition(), mMoviePoster);
    }

    public interface OnItemClickListener {
        void onClick(int position, ImageView sharedImageView);
    }
}
