package com.example.elewa.movie.DataInformation;

import java.io.Serializable;

/**
 * Created by elewa on 4/28/16.
 */
public class VideoInfo implements Serializable {

    private String talier;
    private String Key;

    public VideoInfo (String talier, String Key) {
        this.talier = talier;
        this.Key = Key;
    }

    public String getTalier() {
        return talier;
    }

    public String getKey() {
        return Key;
    }

}
