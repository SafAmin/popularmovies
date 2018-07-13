package com.popularmovies.popularmovies.network;


import com.popularmovies.popularmovies.models.PopularMoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Safa Amin on 7/12/2018.
 */

public interface PopularMoviesInterface {

    @GET("movie/popular")
    Call<PopularMoviesResponse> getPopularMovies(@Query("api_key") String key);

    @GET("movie/top_rated")
    Call<PopularMoviesResponse> getTopRatedMovies(@Query("api_key") String key);
}
