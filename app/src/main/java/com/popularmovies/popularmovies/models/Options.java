package com.popularmovies.popularmovies.models;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Safa Amin on 7/20/2018.
 */

@IntDef({
        Options.MOST_POPULAR,
        Options.HIGHEST_RATED,
        Options.FAVORITE
})
@Retention(RetentionPolicy.RUNTIME)
public @interface Options {

    int MOST_POPULAR = 0;
    int HIGHEST_RATED = 1;
    int FAVORITE = 2;
}

