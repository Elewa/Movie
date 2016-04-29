package com.example.elewa.movie.DataInformation;

import java.io.Serializable;


public class MovieInfo implements Serializable {


    private int id;
    private String poster_path;
    private String overview;
    private String original_title;
    private String release_date;
    private double vote_average;

    public MovieInfo() {
    }

    public MovieInfo(int id, String poster_path, String overview, String original_title, String release_date, double vote_average) {

        this.id = id;
        this.poster_path = poster_path;
        this.overview = overview;
        this.original_title = original_title;
        this.release_date = release_date;
        this.vote_average = vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public double getVote_average() {
        return vote_average;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }
}
