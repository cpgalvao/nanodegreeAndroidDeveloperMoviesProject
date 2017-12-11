package br.com.cpg.moviesproject.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static String executeGetRequest(URL url) throws IOException {
        String responseContent;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        ResponseBody body = response.body();
        if (body != null) {
            responseContent = body.string();
        } else {
            throw new IOException("Error: response body null");
        }

        return responseContent;
    }

    public static URL getURL(Uri uri) {
        URL url = null;
        try {
            url = new URL(uri.toString());
            Log.v(TAG, "Generated URL: " + url.toString());
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error generating URL", e);
        }
        return url;
    }
}
