package com.popularmovies.popularmovies.details;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.popularmovies.popularmovies.R;
import com.popularmovies.popularmovies.models.MovieReviewsResultsItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This Adapter responsible for making a View for each item in the movie reviews RecyclerView
 * within {@link MovieDetailsFragment}
 * <p>
 * <p>
 * Created by Safa Amin on 7/14/2018.
 */

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.ViewHolder> {

    private List<MovieReviewsResultsItem> movieReviews;

    public MovieReviewsAdapter(List<MovieReviewsResultsItem> movieReviews) {
        this.movieReviews = movieReviews;
    }

    @Override
    public MovieReviewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.movie_review_item_view, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieReviewsResultsItem model = movieReviews.get(position);
        holder.bindData(model, position);
    }

    @Override
    public int getItemCount() {
        return movieReviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_movie_review)
        TextView tvMovieReview;
        @BindView(R.id.view_movie_reviews_separator)
        View movieReviewsSeparator;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void bindData(MovieReviewsResultsItem model, int position) {
            tvMovieReview.setText(model.getContent());
            if (position == movieReviews.size() - 1) {
                movieReviewsSeparator.setVisibility(View.GONE);
            }
        }
    }
}
