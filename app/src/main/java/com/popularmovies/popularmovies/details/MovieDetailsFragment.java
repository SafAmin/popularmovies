package com.popularmovies.popularmovies.details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.popularmovies.popularmovies.MainActivity;
import com.popularmovies.popularmovies.R;
import com.popularmovies.popularmovies.models.MovieDetails;
import com.squareup.picasso.Picasso;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * This fragment responsible for display movie details like
 * original title, movie poster, release date, rating and overview
 * <p>
 * Created by Safa Amin on 7/8/2018.
 */

public class MovieDetailsFragment extends Fragment {

    private static String MOVIE_DETAILS_PARAM = "MOVIE_DETAILS";
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
    @BindString(R.string.movie_details_screen_title)
    String movieDetailsTitle;
    @BindString(R.string.movie_details_rate)
    String rateOutOf;

    private Unbinder unbinder;
    private MovieDetails movieDetails;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, parent, false);

        unbinder = ButterKnife.bind(this, view);

        Bundle args = getArguments();
        movieDetails = args.getParcelable(MOVIE_DETAILS_PARAM);

        ((MainActivity) getActivity()).setScreenTitle(movieDetailsTitle);
        ((MainActivity) getActivity()).addToolbarNavigationListener();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        invalidateMovieDetailsView();
    }

    private void invalidateMovieDetailsView() {
        Picasso.get().load("http://image.tmdb.org/t/p/w185//" +
                movieDetails.getMoviePoster()).into(ivMoviePoster);
        tvMovieName.setText(movieDetails.getMovieName());
        tvMovieReleaseDate.setText(movieDetails.getMovieReleaseDate());
        tvMovieRate.setText(movieDetails.getMovieRating() + rateOutOf);
        tvMovieOverview.setText(movieDetails.getMovieOverview());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
