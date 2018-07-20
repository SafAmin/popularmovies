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
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.popularmovies.popularmovies.details.MovieDetailsFragment;
import com.popularmovies.popularmovies.models.MovieDetails;
import com.popularmovies.popularmovies.models.PopularMoviesResponse;
import com.popularmovies.popularmovies.models.ResultsItem;
import com.popularmovies.popularmovies.network.PopularMoviesAPIs;
import com.popularmovies.popularmovies.network.PopularMoviesClient;
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
    @BindView(R.id.layout_placeholder)
    FrameLayout layoutPlaceholder;
    @BindView(R.id.tv_no_favorites_message)
    TextView tvNoFavorites;
    @BindString(R.string.app_name)
    String popMovieScreenTitle;
    @BindString(R.string.movie_details_loading)
    String loading;
    @BindString(R.string.movie_details_error)
    String error;

    private ProgressDialog progressDialog;
    private PopularMoviesAPIs service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        service = PopularMoviesClient.getRetrofitInstance().create(PopularMoviesAPIs.class);
        progressDialog = new ProgressDialog(MainActivity.this);

        if (savedInstanceState == null) {
            progressDialog.setMessage(loading);
            progressDialog.setCancelable(false);
            progressDialog.show();
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

    public void getFavoriteMovies() {

    }

    /**
     * This method responsible for map API response to view model of type {@link MovieDetails} which used to
     * invalidate views with data in {@link MovieDetailsFragment}
     *
     * @param popularMovies API response
     */
    private void generatePopularMoviesDataList(PopularMoviesResponse popularMovies) {
        List<MovieDetails> movieDetailsList = new ArrayList<>();
        ResultsItem resultsItem;

        for (int i = 0; i < popularMovies.getResults().size(); i++) {
            resultsItem = popularMovies.getResults().get(i);
            boolean isFavorite = false;
            movieDetailsList.add(i, new MovieDetails(resultsItem.getId(), resultsItem.getPosterPath(),
                    resultsItem.getOriginalTitle(), resultsItem.getReleaseDate(),
                    resultsItem.getVoteAverage(), resultsItem.getOverview(), isFavorite));
        }
        invalidateView(popMovieScreenTitle, MoviePosterFragment.getInstance(movieDetailsList));
    }

    public void invalidateView(String title, Fragment fragment) {
        togglePostersView(View.VISIBLE, View.GONE);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_placeholder, fragment);
        if (!(title.equals(popMovieScreenTitle)))
            fragmentTransaction.addToBackStack(title);
        fragmentTransaction.commit();
    }

    private void togglePostersView(int postersVisibility, int noFavoritesVisibility) {
        layoutPlaceholder.setVisibility(postersVisibility);
        tvNoFavorites.setVisibility(noFavoritesVisibility);
    }

    public void setScreenTitle(String title) {
        toolbar.setTitle(title);
    }

    public PopularMoviesAPIs getPopularMoviesAPI() {
        return service;
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
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
            case R.id.menu_favorite:
                getFavoriteMovies();
            default:
                return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }
}
