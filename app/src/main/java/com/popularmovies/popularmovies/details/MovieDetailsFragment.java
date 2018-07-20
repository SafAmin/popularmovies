package com.popularmovies.popularmovies.details;

import android.os.Bundle;
import android.os.Parcelable;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.popularmovies.popularmovies.BuildConfig;
import com.popularmovies.popularmovies.MainActivity;
import com.popularmovies.popularmovies.R;
import com.popularmovies.popularmovies.database.AppExecutors;
import com.popularmovies.popularmovies.database.MovieEntity;
import com.popularmovies.popularmovies.models.MovieDetails;
import com.popularmovies.popularmovies.models.MovieReviewsResponse;
import com.popularmovies.popularmovies.models.MovieReviewsResultsItem;
import com.popularmovies.popularmovies.models.MovieTrailersResponse;
import com.popularmovies.popularmovies.models.MovieTrailersResultsItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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
    private static String MOVIE_DETAILS_TRAILERS_STATE_PARAM = "MOVIE_DETAILS_TRAILERS_STATE";
    private static String MOVIE_DETAILS_REVIEWS_STATE_PARAM = "MOVIE_DETAILS_REVIEWS_STATE";
    @BindView(R.id.scroll_movie_details)
    ScrollView scrollMovieDetails;
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
    private List<MovieTrailersResultsItem> trailers;
    private List<MovieReviewsResultsItem> reviews;
    private MainActivity mainActivity;

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
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, parent, false);

        unbinder = ButterKnife.bind(this, view);

        Bundle args = getArguments();
        if (args != null) {
            movieDetails = args.getParcelable(MOVIE_DETAILS_PARAM);
        }

        if (savedInstanceState != null) {
            movieDetails = savedInstanceState.getParcelable(MOVIE_DETAILS_CURRENT_STATE_PARAM);
            trailers = savedInstanceState.getParcelableArrayList(MOVIE_DETAILS_TRAILERS_STATE_PARAM);
            reviews = savedInstanceState.getParcelableArrayList(MOVIE_DETAILS_REVIEWS_STATE_PARAM);
            invalidateTrailersView(trailers);
            invalidateReviewsView(reviews);
        } else {
            mainActivity.showProgressDialog();
            getMovieTrailers(movieDetails.getMovieId());
        }

        addFavoriteIconListener();
        setFavoriteIcon();

        mainActivity.setScreenTitle(movieDetailsTitle);
        mainActivity.addToolbarNavigationListener();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
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
            ivMovieFavorite.setImageResource(R.drawable.ic_baseline_star_border);
            removeMovieToFavorite(mapToMovieEntity(movieDetails));
            Toast.makeText(mainActivity, removeFromFavorite, Toast.LENGTH_SHORT).show();
        } else {
            movieDetails.setFavorite(true);
            ivMovieFavorite.setImageResource(R.drawable.ic_baseline_star);
            addMovieToFavorite(mapToMovieEntity(movieDetails));
            Toast.makeText(mainActivity, addToFavorite, Toast.LENGTH_SHORT).show();
        }
    }

    private MovieEntity mapToMovieEntity(MovieDetails movieDetails) {
        MovieEntity movieEntity = new MovieEntity(movieDetails.getMovieId(), movieDetails.getMoviePoster(),
                movieDetails.getMovieName(), movieDetails.getMovieReleaseDate(),
                movieDetails.getMovieRating(), movieDetails.getMovieOverview(),
                movieDetails.isFavorite());

        return movieEntity;
    }

    private void addMovieToFavorite(final MovieEntity movieEntity) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mainActivity.getDatabase().movieDao().insertFavoriteMovie(movieEntity);
            }
        });
    }

    private void removeMovieToFavorite(final MovieEntity movieEntity) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mainActivity.getDatabase().movieDao().deleteFavorite(movieEntity);
            }
        });
    }

    public void getMovieTrailers(int movieId) {
        Call<MovieTrailersResponse> call = mainActivity.getPopularMoviesAPI().getMovieTrailers(movieId, BuildConfig.ApiKey);
        call.enqueue(new Callback<MovieTrailersResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieTrailersResponse> call,
                                   @NonNull Response<MovieTrailersResponse> response) {
                trailers = response.body().getResults();
                invalidateTrailersView(trailers);
            }

            @Override
            public void onFailure(@NonNull Call<MovieTrailersResponse> call, @NonNull Throwable t) {
                getMovieReviews(movieDetails.getMovieId());
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
                reviews = response.body().getResults();
                invalidateReviewsView(reviews);
            }

            @Override
            public void onFailure(@NonNull Call<MovieReviewsResponse> call, @NonNull Throwable t) {
                mainActivity.dismissProgressDialog();
                layoutMovieReviewsContainer.setVisibility(View.GONE);
            }
        });
    }

    private void invalidateTrailersView(List<MovieTrailersResultsItem> trailers) {
        if (trailers != null && trailers.size() > 0) {
            layoutMovieTrailersContainer.setVisibility(View.VISIBLE);
            rvMovieTrailers.setAdapter(new MovieTrailersAdapter(mainActivity, trailers));
        } else {
            layoutMovieTrailersContainer.setVisibility(View.GONE);
        }
        getMovieReviews(movieDetails.getMovieId());
    }

    private void invalidateReviewsView(List<MovieReviewsResultsItem> reviews) {
        if (reviews != null && reviews.size() > 0) {
            layoutMovieReviewsContainer.setVisibility(View.VISIBLE);
            rvMovieReviews.setAdapter(new MovieReviewsAdapter(reviews));
            mainActivity.dismissProgressDialog();
        } else {
            layoutMovieReviewsContainer.setVisibility(View.GONE);
            mainActivity.dismissProgressDialog();
        }
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
    public void onSaveInstanceState(@NonNull Bundle currentState) {
        super.onSaveInstanceState(currentState);

        currentState.putParcelable(MOVIE_DETAILS_CURRENT_STATE_PARAM, movieDetails);
        currentState.putParcelableArrayList(MOVIE_DETAILS_TRAILERS_STATE_PARAM,
                (ArrayList<? extends Parcelable>) trailers);
        currentState.putParcelableArrayList(MOVIE_DETAILS_REVIEWS_STATE_PARAM,
                (ArrayList<? extends Parcelable>) reviews);

     /*   currentState.putIntArray("ARTICLE_SCROLL_POSITION",
                new int[]{scrollMovieDetails.getScrollX(), scrollMovieDetails.getScrollY()});*/
    }

/*    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            final int[] position = savedInstanceState.getIntArray("ARTICLE_SCROLL_POSITION");
            if (position != null)
                scrollMovieDetails.post(new Runnable() {
                    public void run() {
                        scrollMovieDetails.scrollTo(position[0], position[1]);
                    }
                });
        }
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
