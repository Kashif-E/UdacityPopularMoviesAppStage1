package com.infinity.popularmoviesapp.model;

public class Movie {

    private String posterUrl;
    private String title;
    private String releaseDate;
    private String backDropUrl;
    private String voteAverage;
    private String plotSynopsis;

    public Movie(String posterUrl, String title, String releaseDate, String backDropUrl, String voteAverage , String plotSynopsis) {
        this.posterUrl = posterUrl;
        this.title = title;
        this.releaseDate = releaseDate;
        this.backDropUrl = backDropUrl;
        this.voteAverage = voteAverage ;
        this.plotSynopsis = plotSynopsis;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getBackDropUrl() {
        return backDropUrl;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }
}
