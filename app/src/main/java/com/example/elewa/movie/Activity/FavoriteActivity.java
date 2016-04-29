package com.example.elewa.movie.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.elewa.movie.DataInformation.MovieInfo;
import com.example.elewa.movie.Fragment.Detail_ActivityFragment;
import com.example.elewa.movie.Fragment.FavorityActivityFragment;
import com.example.elewa.movie.R;
import com.example.elewa.movie.Fragment.replace;

public class FavoriteActivity extends AppCompatActivity implements FavorityActivityFragment.communicationFav {

    replace fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        fragment = (replace) getSupportFragmentManager().findFragmentById(R.id.replace);

    }


    @Override
    public void setDAtA(MovieInfo movieInfo) {


        if (fragment != null && fragment.isVisible()) {
            Detail_ActivityFragment Detail = new Detail_ActivityFragment();
            Bundle b = new Bundle();
            b.putSerializable("value", movieInfo);
            Detail.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.replace, Detail, "add").commit();

        } else {
            Intent intent = new Intent(this, Detail_Activity.class);
            intent.putExtra("value", movieInfo);
            startActivity(intent);
        }

    }
}
