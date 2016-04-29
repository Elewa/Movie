package com.example.elewa.movie.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.elewa.movie.Fragment.Detail_ActivityFragment;
import com.example.elewa.movie.R;

public class Detail_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.detail,new Detail_ActivityFragment(),"detailFragment").commit();
        }
    }
}
