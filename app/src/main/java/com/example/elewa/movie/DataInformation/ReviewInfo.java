package com.example.elewa.movie.DataInformation;

import java.io.Serializable;

public class ReviewInfo implements Serializable {

    private String auther;
    private String description;

    public ReviewInfo(String auther, String description) {
        this.auther = auther;
        this.description = description;
    }

    public String getAuther() {
        return auther;
    }

    public String getDescription() {
        return description;
    }

}
