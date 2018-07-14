package com.popularmovies.popularmovies.posters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
 * This Adapter responsible for making a View for each item in the movie poster GridView
 * within {@link MoviePosterFragment}
 * <p>
 * Created by Safa Amin on 7/8/2018.
 */

public class MoviePosterAdapter extends BaseAdapter {

    private Context context;
    private List<MovieDetails> movieDetailsList;

    MoviePosterAdapter(Context context, List<MovieDetails> movieDetailsList) {
        this.context = context;
        this.movieDetailsList = movieDetailsList;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = inflater != null ? inflater.inflate(R.layout.movie_poster_item_view, viewGroup,
                    false) : null;
            holder = new ViewHolder(view);
            if (view != null) {
                view.setTag(holder);
            }
        } else {
            holder = (ViewHolder) view.getTag();
        }
        MovieDetails movieDetails = movieDetailsList.get(position);
        // Picasso.get().setLoggingEnabled(true);
        Picasso.get().load(holder.moviePosterBaseURL +
                movieDetails.getMoviePoster()).into(holder.ivMoviePoster);
        holder.tvMovieName.setText(movieDetails.getMovieName());

        return view;
    }

    static class ViewHolder {
        @BindView(R.id.iv_movie_poster)
        ImageView ivMoviePoster;
        @BindView(R.id.tv_movie_name)
        TextView tvMovieName;
        @BindString(R.string.movie_poster_base_url)
        String moviePosterBaseURL;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public int getCount() {
        if (movieDetailsList.size() > 0)
            return movieDetailsList.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
