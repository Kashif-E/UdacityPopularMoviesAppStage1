package com.infinity.popularmoviesapp.JSONutil;

import com.infinity.popularmoviesapp.model.Movie;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {

    private static final String plot = "overview";
    private static final String title ="title";
    private static final String releaseDAte ="release_date";
    private static final String votes ="vote_average";
    private static final String backDrop ="backdrop_path";
    private static final String poster ="poster_path";
    /////////////////////////////////////////////////
    private static String mplot = "";
    private static String mtitle ="";
    private static String mreleaseDAte ="";
    private static String mvotes ="";
    private static String mbackDrop ="";
    private static String mposter="";
    
    public static Movie parseJSON(JSONObject movieDetails) {
        
        try {
           mposter = "http://image.tmdb.org/t/p/w185/" + movieDetails.getString (poster);
           mplot=movieDetails.getString (plot);
           mtitle=movieDetails.getString (title);
           mreleaseDAte=movieDetails.getString (releaseDAte);
           mvotes=movieDetails.getString (votes);
           mbackDrop="http://image.tmdb.org/t/p/original/" + movieDetails.getString (backDrop);
        } catch (JSONException e) {
            e.printStackTrace ();
        }

        return new Movie (mposter,mtitle,mreleaseDAte,mbackDrop,mvotes,mplot);
    }


}
