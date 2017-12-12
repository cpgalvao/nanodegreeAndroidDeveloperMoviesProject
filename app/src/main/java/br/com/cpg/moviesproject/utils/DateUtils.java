package br.com.cpg.moviesproject.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    private static final String TAG = DateUtils.class.getSimpleName();

    public static String getYear(String releaseDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", new Locale("pt-BR"));
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", new Locale("pt-BR"));
        String year = null;
        try {
            Date date = dateFormat.parse(releaseDate);
            year = yearFormat.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing date", e);
        }
        return year;
    }
}
