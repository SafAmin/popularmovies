package com.popularmovies.popularmovies.details;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.popularmovies.popularmovies.MainActivity;
import com.popularmovies.popularmovies.R;
import com.popularmovies.popularmovies.models.MovieTrailersResultsItem;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This Adapter responsible for making a View for each item in the movie trailers RecyclerView
 * within {@link MovieDetailsFragment}
 * <p>
 * <p>
 * Created by Safa Amin on 7/14/2018.
 */

public class MovieTrailersAdapter extends RecyclerView.Adapter<MovieTrailersAdapter.ViewHolder> {

    private Context context;
    private List<MovieTrailersResultsItem> movieTrailers;

    public MovieTrailersAdapter(Context context, List<MovieTrailersResultsItem> movieTrailers) {
        this.context = context;
        this.movieTrailers = movieTrailers;
    }

    @Override
    public MovieTrailersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.movie_trailer_item_view, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieTrailersResultsItem model = movieTrailers.get(position);
        holder.bindData(model, position);
    }

    @Override
    public int getItemCount() {
        return movieTrailers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_movie_trailer_number)
        TextView tvMovieTrailerNumber;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    watchTrailerInYoutube(movieTrailers.get(getAdapterPosition()).getKey());
                }
            });
        }

        public void watchTrailerInYoutube(String id){
            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            try {
                context.startActivity(appIntent);
            } catch (ActivityNotFoundException ex) {
                context.startActivity(webIntent);
            }
        }

        public void bindData(MovieTrailersResultsItem model, int position) {
            tvMovieTrailerNumber.setText(model.getName());
        }
    }
}
