package br.com.cpg.moviesproject.model.business;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;

import br.com.cpg.moviesproject.model.bean.MoviesBean;
import br.com.cpg.moviesproject.model.bean.ReviewsBean;
import br.com.cpg.moviesproject.model.bean.TrailersBean;
import br.com.cpg.moviesproject.utils.NetworkUtils;

public class TheMovieDBBO {
    private static final String TAG = TheMovieDBBO.class.getSimpleName();

    private static final String THE_MOVIE_DB_BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String THE_MOVIE_DB_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";

    private static final String POPULAR_SORT_PATH = "popular";
    private static final String TOP_RATED_SORT_PATH = "top_rated";
    private static final String IMAGE_SIZE_PATH = "w185";

    private static final String VIDEOS_PATH = "videos";
    private static final String REVIEWS_PATH = "reviews";

    private static final String API_KEY_PARAM = "api_key";

    public String mountPosterUrl(String posterPath) {
        // "w92", "w154", "w185", "w342", "w500", "w780", or "original". For most phones we recommend using “w185”.
        return Uri.parse(THE_MOVIE_DB_IMAGE_BASE_URL).buildUpon()
                .appendPath(IMAGE_SIZE_PATH)
                .appendEncodedPath(posterPath)
                .build().toString();
    }

    public TrailersBean getTrailersList(String apiKey, int movieId) {
        TrailersBean trailersBean;

        Uri.Builder uriBuilder = Uri.parse(THE_MOVIE_DB_BASE_URL).buildUpon()
                .appendPath(String.valueOf(movieId))
                .appendPath(VIDEOS_PATH)
                .appendQueryParameter(API_KEY_PARAM, apiKey);

        Uri uri = uriBuilder.build();
        URL url = NetworkUtils.getURL(uri);

        try {
            String jsonString = NetworkUtils.executeGetRequest(url);

            Gson gson = new Gson();
            trailersBean = gson.fromJson(jsonString, TrailersBean.class);
            Log.d(TAG, "Request: " + url + "\nResponse" + jsonString);
        } catch (IOException e) {
            Log.e(TAG, "Error on request " + url, e);
            trailersBean = null;
        }

        return trailersBean;
    }

    public ReviewsBean getReviewsList(String apiKey, int movieId) {
        ReviewsBean reviewsBean;

        Uri.Builder uriBuilder = Uri.parse(THE_MOVIE_DB_BASE_URL).buildUpon()
                .appendPath(String.valueOf(movieId))
                .appendPath(REVIEWS_PATH)
                .appendQueryParameter(API_KEY_PARAM, apiKey);

        Uri uri = uriBuilder.build();
        URL url = NetworkUtils.getURL(uri);

        try {
            String jsonString = NetworkUtils.executeGetRequest(url);

            Gson gson = new Gson();
            reviewsBean = gson.fromJson(jsonString, ReviewsBean.class);
            Log.d(TAG, "Request: " + url + "\nResponse" + jsonString);
        } catch (IOException e) {
            Log.e(TAG, "Error on request " + url, e);
            reviewsBean = null;
        }

        return reviewsBean;
    }

    public MoviesBean getPopularMoviesList(String apiKey) {
        return getMoviesList(MoviesSortOrder.POPULAR, apiKey);
    }

    public MoviesBean getTopRatedMoviesList(String apiKey) {
        return getMoviesList(MoviesSortOrder.TOP_RATED, apiKey);
    }

    private MoviesBean getMoviesList(MoviesSortOrder sortOrder, String apiKey) {
        MoviesBean moviesBean;

        Uri.Builder uriBuilder = Uri.parse(THE_MOVIE_DB_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, apiKey);

        switch (sortOrder) {
            case TOP_RATED:
                uriBuilder.appendPath(TOP_RATED_SORT_PATH);
                break;
            case POPULAR:
            default:
                uriBuilder.appendPath(POPULAR_SORT_PATH);
                break;
        }

        Uri uri = uriBuilder.build();

        URL url = NetworkUtils.getURL(uri);

        try {
            String jsonString = NetworkUtils.executeGetRequest(url);

            Gson gson = new Gson();
            moviesBean = gson.fromJson(jsonString, MoviesBean.class);

            Log.d(TAG, "Request: " + url + "\nResponse" + jsonString);
        } catch (IOException e) {
            Log.e(TAG, "Error on request " + url, e);
            moviesBean = null;
        }

        return moviesBean;
    }

    public enum MoviesSortOrder {
        POPULAR, TOP_RATED
    }
}
