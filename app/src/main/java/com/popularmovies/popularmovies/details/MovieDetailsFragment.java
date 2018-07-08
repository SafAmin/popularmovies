package com.popularmovies.popularmovies.details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.popularmovies.popularmovies.R;

/**
 * Created by Safa Amin on 7/8/2018.
 */

public class MovieDetailsFragment extends Fragment {

    public static MovieDetailsFragment getInstance() {
        return new MovieDetailsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_details, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }
}
