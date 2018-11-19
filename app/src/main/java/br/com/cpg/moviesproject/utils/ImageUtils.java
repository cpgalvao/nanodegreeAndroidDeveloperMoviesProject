package br.com.cpg.moviesproject.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import br.com.cpg.moviesproject.R;
import br.com.cpg.moviesproject.model.business.TheMovieDBBO;

public class ImageUtils {
    public static void loadMoviePosterThumb(Context context, String posterPath, ImageView imageView) {
        int imageWidth = imageView.getResources().getDisplayMetrics().widthPixels / 2;

        loadMoviePoster(context, posterPath, imageView, imageWidth, 0);
    }

    public static void loadMoviePosterDetail(Context context, String posterPath, ImageView imageView) {
        int imageHeight = context.getResources().getDimensionPixelSize(R.dimen.movie_detail_poster_height);

        loadMoviePoster(context, posterPath, imageView, 0, imageHeight);
    }

    private static void loadMoviePoster(Context context, String posterPath, ImageView imageView, int imageWidth, int imageHeight) {
        TheMovieDBBO bo = new TheMovieDBBO();
        String posterUrl = bo.mountPosterUrl(posterPath);

        Picasso.with(context)
                .load(posterUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .resize(imageWidth, imageHeight)
                .into(imageView);
    }
}
