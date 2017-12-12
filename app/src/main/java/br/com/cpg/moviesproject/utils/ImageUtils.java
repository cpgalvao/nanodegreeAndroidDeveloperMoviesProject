package br.com.cpg.moviesproject.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import br.com.cpg.moviesproject.R;
import br.com.cpg.moviesproject.model.business.TheMovieDBBO;


public class ImageUtils {
    public static void loadMoviePoster(Context context, String posterPath, ImageView imageView) {
        TheMovieDBBO bo = new TheMovieDBBO();
        String posterUrl = bo.mountPosterUrl(posterPath);
        int imageWidth = imageView.getResources().getDisplayMetrics().widthPixels / 2;

        Picasso.with(context)
                .load(posterUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .resize(imageWidth, 0)
                .into(imageView);
    }

    public static void loadMoviePoster2(Context context, String posterPath, ImageView imageView) {
        TheMovieDBBO bo = new TheMovieDBBO();
        String posterUrl = bo.mountPosterUrl(posterPath);
        int imageHeight = context.getResources().getDimensionPixelSize(R.dimen.movie_detail_poster_height);

        Picasso.with(context)
                .load(posterUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .resize(0, imageHeight)
                .into(imageView);
    }
}
