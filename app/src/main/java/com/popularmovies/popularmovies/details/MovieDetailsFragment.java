package com.popularmovies.popularmovies.details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.popularmovies.popularmovies.BuildConfig;
import com.popularmovies.popularmovies.MainActivity;
import com.popularmovies.popularmovies.R;
import com.popularmovies.popularmovies.models.MovieDetails;
import com.popularmovies.popularmovies.models.MovieReviewsResponse;
import com.popularmovies.popularmovies.models.MovieTrailersResponse;
import com.squareup.picasso.Picasso;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This fragment responsible for display movie details like
 * original title, movie poster, release date, rating and overview
 * <p>
 * Created by Safa Amin on 7/8/2018.
 */

public class MovieDetailsFragment extends Fragment {

    private static String MOVIE_DETAILS_PARAM = "MOVIE_DETAILS";
    private static String MOVIE_DETAILS_CURRENT_STATE_PARAM = "MOVIE_DETAILS_CURRENT_STATE";
    @BindView(R.id.iv_movie_details_poster)
    ImageView ivMoviePoster;
    @BindView(R.id.tv_movie_details_name)
    TextView tvMovieName;
    @BindView(R.id.tv_movie_details_release_date)
    TextView tvMovieReleaseDate;
    @BindView(R.id.tv_movie_details_rate)
    TextView tvMovieRate;
    @BindView(R.id.tv_movie_details_overview)
    TextView tvMovieOverview;
    @BindView(R.id.layout_movie_trailers)
    LinearLayout layoutMovieTrailersContainer;
    @BindView(R.id.layout_movie_reviews)
    LinearLayout layoutMovieReviewsContainer;
    @BindView(R.id.rv_movie_trailers)
    RecyclerView rvMovieTrailers;
    @BindView(R.id.rv_movie_reviews)
    RecyclerView rvMovieReviews;
    @BindView(R.id.iv_movie_favorite)
    ImageView ivMovieFavorite;
    @BindString(R.string.movie_details_screen_title)
    String movieDetailsTitle;
    @BindString(R.string.movie_poster_base_url)
    String moviePosterBaseURL;
    @BindString(R.string.movie_details_rate)
    String rateOutOf;
    @BindString(R.string.movie_details_add_favorite_message)
    String addToFavorite;
    @BindString(R.string.movie_details_remove_favorite_message)
    String removeFromFavorite;


    private Unbinder unbinder;
    private MovieDetails movieDetails;
    private MainActivity mainActivity;
    private Favorites favorites;

    public static MovieDetailsFragment getInstance(MovieDetails movieDetails) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(MOVIE_DETAILS_PARAM, movieDetails);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        mainActivity = (MainActivity) getActivity();
        favorites = new Favorites();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, parent, false);

        unbinder = ButterKnife.bind(this, view);

        if (savedInstanceState != null) {
            movieDetails = savedInstanceState.getParcelable(MOVIE_DETAILS_CURRENT_STATE_PARAM);
        }

        Bundle args = getArguments();
        movieDetails = args.getParcelable(MOVIE_DETAILS_PARAM);

        mainActivity.setScreenTitle(movieDetailsTitle);
        mainActivity.addToolbarNavigationListener();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        invalidateMovieDetailsView();
    }

    private void invalidateMovieDetailsView() {
        Picasso.get().load(moviePosterBaseURL + movieDetails.getMoviePoster()).into(ivMoviePoster);
        tvMovieName.setText(movieDetails.getMovieName());
        tvMovieReleaseDate.setText(movieDetails.getMovieReleaseDate());
        tvMovieRate.setText(movieDetails.getMovieRating() + rateOutOf);
        tvMovieOverview.setText(movieDetails.getMovieOverview());

        RecyclerView.LayoutManager trailersLayoutManager = new LinearLayoutManager(getContext());
        rvMovieTrailers.setLayoutManager(trailersLayoutManager);
        rvMovieTrailers.setNestedScrollingEnabled(false);

        RecyclerView.LayoutManager reviewsLayoutManager = new LinearLayoutManager(getContext());
        rvMovieReviews.setLayoutManager(reviewsLayoutManager);
        rvMovieReviews.setNestedScrollingEnabled(false);

        setFavoriteIcon();

        getMovieTrailers(movieDetails.getMovieId());
        getMovieReviews(movieDetails.getMovieId());

        addFavoriteIconListener();
    }

    private void setFavoriteIcon() {
        if (movieDetails.isFavorite()) {
            ivMovieFavorite.setImageResource(R.drawable.ic_baseline_star);
        } else {
            ivMovieFavorite.setImageResource(R.drawable.ic_baseline_star_border);
        }

    }

    private void addFavoriteIconListener() {
        ivMovieFavorite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleOnFavoriteClick();
            }
        });
    }

    private void handleOnFavoriteClick() {
        if (movieDetails.isFavorite()) {
            movieDetails.setFavorite(false);
            ivMovieFavorite.setImageResource(R.drawable.ic_baseline_star_border);
            favorites.removeFavorites(mainActivity, movieDetails);
            Toast.makeText(mainActivity, removeFromFavorite, Toast.LENGTH_SHORT).show();
        } else {
            movieDetails.setFavorite(true);
            ivMovieFavorite.setImageResource(R.drawable.ic_baseline_star);
            favorites.addToFavorite(mainActivity, movieDetails);
            Toast.makeText(mainActivity, addToFavorite, Toast.LENGTH_SHORT).show();
        }
    }

    public void getMovieTrailers(int movieId) {
        Call<MovieTrailersResponse> call = mainActivity.getPopularMoviesAPI().getMovieTrailers(movieId, BuildConfig.ApiKey);
        call.enqueue(new Callback<MovieTrailersResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieTrailersResponse> call,
                                   @NonNull Response<MovieTrailersResponse> response) {
                mainActivity.getProgressDialog().dismiss();
                if (response.body().getResults() != null && response.body().getResults().size() > 0) {
                    layoutMovieTrailersContainer.setVisibility(View.VISIBLE);
                    rvMovieTrailers.setAdapter(new MovieTrailersAdapter(mainActivity, response.body().getResults()));
                } else {
                    layoutMovieTrailersContainer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieTrailersResponse> call, @NonNull Throwable t) {
                mainActivity.getProgressDialog().dismiss();
                layoutMovieTrailersContainer.setVisibility(View.GONE);
            }
        });
    }

    public void getMovieReviews(int movieId) {
        Call<MovieReviewsResponse> call = mainActivity.getPopularMoviesAPI().getMovieReviews(movieId, BuildConfig.ApiKey);
        call.enqueue(new Callback<MovieReviewsResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieReviewsResponse> call,
                                   @NonNull Response<MovieReviewsResponse> response) {
                mainActivity.getProgressDialog().dismiss();
                if (response.body().getResults() != null && response.body().getResults().size() > 0) {
                    layoutMovieReviewsContainer.setVisibility(View.VISIBLE);
                    rvMovieReviews.setAdapter(new MovieReviewsAdapter(response.body().getResults()));
                } else {
                    layoutMovieReviewsContainer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieReviewsResponse> call, @NonNull Throwable t) {
                mainActivity.getProgressDialog().dismiss();
                layoutMovieReviewsContainer.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();
    }

    /**
     * Save the current state of this fragment
     */
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);

        currentState.putParcelable(MOVIE_DETAILS_CURRENT_STATE_PARAM, movieDetails);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
