package com.example.elewa.movie.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.elewa.movie.DataInformation.MovieInfo;
import com.example.elewa.movie.Fragment.Detail_ActivityFragment;
import com.example.elewa.movie.Fragment.MainActivityFragment;
import com.example.elewa.movie.R;
import com.example.elewa.movie.Fragment.replace;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.Communication {


    private replace fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment = (replace) getSupportFragmentManager().findFragmentById(R.id.replace);

    }

    //-----------pass data depend on onePane or twoPane----------
    @Override
    public void setValue(MovieInfo movieInfo) {


        if (fragment != null && fragment.isVisible()) {
            Detail_ActivityFragment Detail = new Detail_ActivityFragment();
            Bundle b = new Bundle();
            b.putSerializable("value", movieInfo);
            Detail.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.replace, Detail, "add").commit();

        } else {
            Intent intent = new Intent(this, Detail_Activity.class);
            intent.putExtra("value",movieInfo);
            startActivity(intent);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_favourite) {
            Intent i = new Intent(this, FavoriteActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }


}
