package com.popularmovies.popularmovies.posters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.popularmovies.popularmovies.R;
import com.popularmovies.popularmovies.models.MovieDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This Adapter responsible for making a View for each item in the movie poster RecyclerView
 * within {@link MoviePosterFragment}
 * <p>
 * Created by Safa Amin on 7/8/2018.
 */

public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.ViewHolder> {

    private List<MovieDetails> movieDetailsList;
    private final OnItemClickListener listener;

    MoviePosterAdapter(List<MovieDetails> movieDetailsList, OnItemClickListener listener) {
        this.movieDetailsList = movieDetailsList;
        this.listener = listener;
    }

    @Override
    @NonNull
    public MoviePosterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(R.layout.movie_poster_item_view, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieDetails model = movieDetailsList.get(position);
        holder.bindData(model, listener);
    }

    public void add(int position, MovieDetails item) {
        movieDetailsList.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        movieDetailsList.remove(position);
        notifyItemRemoved(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_movie_poster)
        ImageView ivMoviePoster;
        @BindView(R.id.tv_movie_name)
        TextView tvMovieName;
        @BindString(R.string.movie_poster_base_url)
        String moviePosterBaseURL;

        ViewHolder(final View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void bindData(final MovieDetails model, final OnItemClickListener listener) {
            Picasso.get().load(moviePosterBaseURL +
                    model.getMoviePoster()).into(ivMoviePoster);
            tvMovieName.setText(model.getMovieName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(model);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(MovieDetails item);
    }

    @Override
    public int getItemCount() {
        return movieDetailsList.size();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
