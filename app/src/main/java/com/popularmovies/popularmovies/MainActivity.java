package com.popularmovies.popularmovies;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.popularmovies.popularmovies.models.MovieDetails;
import com.popularmovies.popularmovies.models.PopularMoviesResponse;
import com.popularmovies.popularmovies.models.ResultsItem;
import com.popularmovies.popularmovies.network.PopularMoviesClient;
import com.popularmovies.popularmovies.network.PopularMoviesInterface;
import com.popularmovies.popularmovies.posters.MoviePosterFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindString(R.string.app_name)
    String popMovieScreenTitle;
    @BindString(R.string.movie_details_loading)
    String loading;
    @BindString(R.string.movie_details_error)
    String error;
    private ProgressDialog progressDialog;
    private PopularMoviesInterface service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage(loading);
            progressDialog.setCancelable(false);
            progressDialog.show();

            service = PopularMoviesClient.getRetrofitInstance().create(PopularMoviesInterface.class);

            getPopularMovies();
        }
    }

    public void addToolbarNavigationListener() {
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void getPopularMovies() {
        Call<PopularMoviesResponse> call = service.getPopularMovies(BuildConfig.ApiKey);
        call.enqueue(new Callback<PopularMoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<PopularMoviesResponse> call,
                                   @NonNull Response<PopularMoviesResponse> response) {
                progressDialog.dismiss();
                generatePopularMoviesDataList(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<PopularMoviesResponse> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getTopRatedMovies() {
        Call<PopularMoviesResponse> call = service.getTopRatedMovies(BuildConfig.ApiKey);
        call.enqueue(new Callback<PopularMoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<PopularMoviesResponse> call,
                                   @NonNull Response<PopularMoviesResponse> response) {
                progressDialog.dismiss();
                generatePopularMoviesDataList(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<PopularMoviesResponse> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generatePopularMoviesDataList(PopularMoviesResponse popularMovies) {
        List<MovieDetails> movieDetailsList = new ArrayList<>();
        ResultsItem resultsItem;
        for (int i = 0; i < popularMovies.getResults().size(); i++) {
            resultsItem = popularMovies.getResults().get(i);
            movieDetailsList.add(i, new MovieDetails(resultsItem.getPosterPath(),
                    resultsItem.getOriginalTitle(), resultsItem.getReleaseDate(),
                    resultsItem.getVoteAverage(), resultsItem.getOverview()));
        }
        invalidateView(popMovieScreenTitle, MoviePosterFragment.getInstance(movieDetailsList));
    }

    public void invalidateView(String title, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_placeholder, fragment);
        if (!(title.equals(popMovieScreenTitle)))
            fragmentTransaction.addToBackStack(title);
        fragmentTransaction.commit();
    }

    public void setScreenTitle(String title) {
        toolbar.setTitle(title);
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
                getPopularMovies();
                break;
            case R.id.menu_highest_rated:
                getTopRatedMovies();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }
}
