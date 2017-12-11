package br.com.cpg.moviesproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.cpg.moviesproject.R;
import br.com.cpg.moviesproject.model.bean.MovieBean;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_DATA = "br.com.cpg.moviesproject.view.extra.MOVIE_DATA";
    private MovieBean mMovieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_MOVIE_DATA)) {
            mMovieData = intent.getParcelableExtra(EXTRA_MOVIE_DATA);
        }
    }
}
