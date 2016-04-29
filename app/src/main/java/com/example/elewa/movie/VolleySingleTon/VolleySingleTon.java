package com.example.elewa.movie.VolleySingleTon;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by elewa on 3/22/16.
 */
public class VolleySingleTon {

    private static VolleySingleTon instance = null;

    private RequestQueue mRequest;

    private VolleySingleTon(){
        mRequest = Volley.newRequestQueue(MyApplication.getContext());
    }


    public static VolleySingleTon getInstace(){
        if(instance == null){
            instance = new VolleySingleTon();
        }
        return instance;
    }

    public RequestQueue request(){
        return mRequest;
    }

}