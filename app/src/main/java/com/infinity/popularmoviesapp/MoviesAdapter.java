package com.infinity.popularmoviesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.popularmoviesapp.model.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {



    private ArrayList<Movie> mMovieArrayList;
    private  OnMovieClickListener onMovieClickListener;
    public  MoviesAdapter(ArrayList<Movie> movieArrayList )
    {
        mMovieArrayList=movieArrayList;
    }

    public interface  OnMovieClickListener
    {
        void onItemClick(int position);

    }
    public void  setOnMovieClickListener (OnMovieClickListener listener)
    {
        onMovieClickListener=listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from (parent.getContext ());
        View view= inflater.inflate (R.layout.movie_cards,parent,false);
        return new ViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        Movie movie= mMovieArrayList.get (position);
        final String imgUrl =movie.getPosterUrl ();
        ////////////////////////////////////////
        Picasso.get ().load(imgUrl).fetch (new Callback () {
                                               @Override
                                               public void onSuccess() {
                                                   holder.mMovieposter.setAlpha (0f);
                                                   Picasso.get ().load(imgUrl).into(holder.mMovieposter);
                                                   holder.mMovieposter.animate().setDuration(1000).rotationX (360).alpha (1).start ();

                                               }

                                               @Override
                                               public void onError(Exception e) {
                                                    e.printStackTrace ();
                                               }
                                           });

    }

    @Override
    public int getItemCount() {
        if(mMovieArrayList==null) return 0;
        return  mMovieArrayList.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView mMovieposter;

        public ViewHolder(@NonNull View itemView) {
            super (itemView);
        mMovieposter= itemView.findViewById(R.id.MoviePoster);
        itemView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(onMovieClickListener!=null)
                {
                 int position = getAdapterPosition ();
                 if(position != RecyclerView.NO_POSITION)
                 {
                     onMovieClickListener.onItemClick (position);
                 }
                }
            }
        });
        }
    }
}
