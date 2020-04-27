package com.infinity.popularmoviesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.infinity.popularmoviesapp.JSONutil.*;
import com.infinity.popularmoviesapp.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.OnMovieClickListener {

    private RecyclerView mMovieList;
    private MoviesAdapter mMoviesAdapter;
    private ArrayList<Movie> mMovieArrayList;
    private JSONParser jsonParser;
    private String moviesURL ="https://api.themoviedb.org/3/movie/";
    private String apiKey="api_key=9da3e04402ffad9e5a100c5569dc26b1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        mMovieList= findViewById (R.id.MovieList);
        mMovieList.setHasFixedSize (true);
        GridLayoutManager gridLayoutManager= new GridLayoutManager (this,2);
        mMovieList.setLayoutManager (gridLayoutManager);
        
        
        mMovieArrayList= new ArrayList<> ();
        
        loadMovieData(moviesURL+"popular?"+apiKey);



    }

    public  void  loadMovieData(String listurl)
    {
        GetMoviesList task= new GetMoviesList ();
        try {
            URL url = new URL (listurl);
            String result1 = task.execute(url).get();

        } catch (MalformedURLException e) {
            e.printStackTrace ();
        } catch (InterruptedException e) {
            e.printStackTrace ();
        } catch (ExecutionException e) {
            e.printStackTrace ();
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent (this,DetailActivity.class);
        Movie clickedMovie = mMovieArrayList.get (position);

        detailIntent.putExtra ("EXTRA_PLOT",clickedMovie.getPlotSynopsis ());
        detailIntent.putExtra ("EXTRA_VOTE",clickedMovie.getVoteAverage ());
        detailIntent.putExtra ("EXTRA_TITLE",clickedMovie.getTitle ());
        detailIntent.putExtra ("EXTRA_RELEASE_DATE",clickedMovie.getReleaseDate ());
        detailIntent.putExtra ("EXTRA_BACK_DROP",clickedMovie.getBackDropUrl ());
        detailIntent.putExtra ("EXTRA_POSTER",clickedMovie.getPosterUrl ());
        startActivity (detailIntent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate (R.menu.movies_sort_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        mMovieArrayList.clear ();
        int id =item.getItemId ();
        if(id == R.id.Popular)
        { loadMovieData(moviesURL+"popular?"+apiKey);
        }
        else
        {
            loadMovieData(moviesURL+"top_rated?"+apiKey);
        }
        return true;
    }

    public  class GetMoviesList extends AsyncTask<URL,Void ,String>
    {

        @Override
        protected String doInBackground(URL... urls) {
            String result="";
            URL url = urls[0];
            try {

                HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection ();
                InputStream inputStreamr=  httpURLConnection.getInputStream ();
                InputStreamReader reader= new InputStreamReader (inputStreamr);
                int data = reader.read ();
                while (data!= -1)
                {
                    result += (char) data;
                    data=reader.read ();
                }
            } catch (IOException e) {
                e.printStackTrace ();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String moviesData) {
            if (moviesData== null) showErrorMessage();
            else
            {
                try {
                    JSONObject jsonObject = new JSONObject (moviesData);
                    JSONArray jsonArray = jsonObject.getJSONArray ("results");
                    for (int i=0 ; i<jsonArray.length ();i++)
                    {
                        JSONObject Movie = jsonArray.getJSONObject (i);
                        Movie movie= JSONParser.parseJSON(Movie);
                        mMovieArrayList.add (movie);
                    }
                    mMoviesAdapter= new MoviesAdapter (mMovieArrayList);
                    mMoviesAdapter.setOnMovieClickListener (MainActivity.this);

                    mMovieList.setAdapter (mMoviesAdapter);
                } catch (JSONException e) {
                    e.printStackTrace ();
                }
            }
        }
    }



    private void showErrorMessage() {

        Toast.makeText (this, "An Error Occured While Fetching Data", Toast.LENGTH_SHORT).show ();
    }
}
