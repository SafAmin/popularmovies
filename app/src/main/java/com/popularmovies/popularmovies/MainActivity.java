package com.popularmovies.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.popularmovies.popularmovies.models.MoviePoster;
import com.popularmovies.popularmovies.posters.MoviePosterFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindString(R.string.app_name)
    String popMovieTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        List<MoviePoster> moviePosterList = new ArrayList<>();
        moviePosterList.add(0, new MoviePoster("/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "KMovie"));
        moviePosterList.add(1, new MoviePoster("/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "KMovie"));
        moviePosterList.add(2, new MoviePoster("/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "KMovie"));
        moviePosterList.add(3, new MoviePoster("/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "KMovie"));
        moviePosterList.add(4, new MoviePoster("/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "KMovie"));
        moviePosterList.add(5, new MoviePoster("/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "KMovie"));
        moviePosterList.add(6, new MoviePoster("/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "KMovie"));
        moviePosterList.add(7, new MoviePoster("/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "KMovie"));


        invalidateView(popMovieTitle, MoviePosterFragment.getInstance(moviePosterList));
    }

    public void invalidateView(String title, Fragment fragment) {
        setTitle(title);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_placeholder, fragment);
        if (!(title.equals(popMovieTitle)))
            fragmentTransaction.addToBackStack(title);
        fragmentTransaction.commit();
    }

    /**
     * Inflate the menu; this adds items to the action bar if it is present.
     *
     * @param menu The menu to inflate into
     * @return boolean   When items inflated, return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Handle action bar item clicks. The action bar will
     * automatically handle clicks on the Home/Up button, so long
     * as you specify a parent activity in AndroidManifest.xml.
     *
     * @param item The clicked item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_most_popular:
                Toast.makeText(this, "Most Popular", Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_highest_rated:
                Toast.makeText(this, "Highest Rated", Toast.LENGTH_LONG).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }
}
