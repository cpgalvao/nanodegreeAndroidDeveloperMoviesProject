package br.com.cpg.moviesproject.model.business;

import android.net.Uri;

public class YoutubeBO {
    private static final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch";

    private static final String VIDEO_KEY_PARAM = "v";

    public Uri getTrailerUrl(String videoKey) {
        Uri.Builder uriBuilder = Uri.parse(YOUTUBE_BASE_URL).buildUpon()
                .appendQueryParameter(VIDEO_KEY_PARAM, videoKey);

        return uriBuilder.build();
    }
}
