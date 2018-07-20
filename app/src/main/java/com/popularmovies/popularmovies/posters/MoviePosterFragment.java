package com.popularmovies.popularmovies.posters;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.popularmovies.popularmovies.MainActivity;
import com.popularmovies.popularmovies.R;
import com.popularmovies.popularmovies.details.MovieDetailsFragment;
import com.popularmovies.popularmovies.models.MovieDetails;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * This fragment responsible for display an grid arrangement of movie posters.
 * also, allow user to change sort order via a setting.
 * and allow the user to tap on a movie poster and transition to a details screen.
 * <p>
 * Created by Safa Amin on 7/8/2018.
 */

public class MoviePosterFragment extends Fragment {

    private static String MOVIE_POSTER_PARAM = "MOVIE_POSTER";
    private static String MOVIE_POSTER_CURRENT_STATE_PARAM = "MOVIE_POSTER_CURRENT_STATE";
    @BindView(R.id.rv_movies_posters)
    RecyclerView rvMoviesPosters;
    @BindString(R.string.app_name)
    String popMovieScreenTitle;
    @BindString(R.string.movie_details_screen_title)
    String movieDetailsTitle;

    private Unbinder unbinder;
    private List<MovieDetails> movieDetailsList;

    public static MoviePosterFragment getInstance(List<MovieDetails> moviePosterList) {
        MoviePosterFragment fragment = new MoviePosterFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(MOVIE_POSTER_PARAM, (ArrayList<? extends Parcelable>) moviePosterList);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies_posters, parent, false);
        unbinder = ButterKnife.bind(this, view);
        if (savedInstanceState != null) {
            movieDetailsList = savedInstanceState.getParcelableArrayList(MOVIE_POSTER_CURRENT_STATE_PARAM);
        }
        Bundle args = getArguments();
        if (args != null) {
            movieDetailsList = args.getParcelableArrayList(MOVIE_POSTER_PARAM);
        }
        if (getActivity() != null) {
            ((MainActivity) getActivity()).setScreenTitle(popMovieScreenTitle);
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        int mNoOfColumns = calculateNoOfColumns(getContext());
        RecyclerView.LayoutManager trailersLayoutManager = new GridLayoutManager(getContext(), mNoOfColumns);
        rvMoviesPosters.setLayoutManager(trailersLayoutManager);
        rvMoviesPosters.setNestedScrollingEnabled(false);
        rvMoviesPosters.setAdapter(new MoviePosterAdapter(movieDetailsList,
                new MoviePosterAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(MovieDetails item) {
                        if (getActivity() != null) {
                            ((MainActivity) getActivity()).invalidateView(movieDetailsTitle, MovieDetailsFragment.
                                    getInstance(item));
                        }
                    }
                }));
    }

    private int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 180);
    }

    /**
     * Save the current state of this fragment
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle currentState) {
        super.onSaveInstanceState(currentState);

        currentState.putParcelableArrayList(MOVIE_POSTER_CURRENT_STATE_PARAM,
                (ArrayList<? extends Parcelable>) movieDetailsList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }
}
