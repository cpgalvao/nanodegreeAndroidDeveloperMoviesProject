package br.com.cpg.moviesproject.view.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.cpg.moviesproject.R;
import br.com.cpg.moviesproject.model.bean.DetailsInterface;
import br.com.cpg.moviesproject.model.bean.MovieBean;
import br.com.cpg.moviesproject.utils.DateUtils;
import br.com.cpg.moviesproject.utils.ImageUtils;

public class DetailViewHolder extends DetailsBaseViewHolder {
    private TextView mTitle;
    private ImageView mPoster;
    private TextView mReleaseDate;
    private TextView mRating;
    private TextView mOverview;

    public DetailViewHolder(View itemView) {
        super(itemView);

        mTitle = itemView.findViewById(R.id.tv_movie_title);
        mPoster = itemView.findViewById(R.id.iv_movie_poster);
        mReleaseDate = itemView.findViewById(R.id.tv_movie_release_date);
        mOverview = itemView.findViewById(R.id.tv_movie_overview);
        mRating = itemView.findViewById(R.id.tv_movie_rating);
    }

    public void bind(DetailsInterface detailsBean) {
        Context context = itemView.getContext();

        MovieBean movieBean = (MovieBean) detailsBean;
        mTitle.setText(movieBean.getTitle());
        String releaseYear = DateUtils.getYear(movieBean.getReleaseDate());
        mReleaseDate.setText(releaseYear);
        mOverview.setText(movieBean.getOverview());
        String rating = String.format(context.getString(R.string.rating), movieBean.getUserRating().toString());
        mRating.setText(rating);

        String posterPath = movieBean.getPosterPath();
        ImageUtils.loadMoviePosterDetail(context, posterPath, mPoster);
    }
}
