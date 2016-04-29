package com.example.elewa.movie.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.elewa.movie.Adapter.MovieAdapter;
import com.example.elewa.movie.DataInformation.MovieInfo;
import com.example.elewa.movie.FavoriteDataBase;
import com.example.elewa.movie.R;

import java.util.ArrayList;


public class FavorityActivityFragment extends Fragment implements AdapterView.OnItemClickListener {

    private GridView gridView;
    FavoriteDataBase fav;
    ArrayList<MovieInfo> arrayList;
    communicationFav communicationfav;
    TextView textView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favority_activity, container, false);
        communicationfav = (communicationFav) getActivity();
        textView = (TextView) v.findViewById(R.id.favText);
        fav = new FavoriteDataBase(getActivity());
        arrayList = fav.readMovies();
        if (arrayList.size() == 0) {
            textView.setVisibility(View.VISIBLE);
            textView.setText("No favorite selected");
        } else {
            gridView = (GridView) v.findViewById(R.id.gridView);
            gridView.setAdapter(new MovieAdapter(arrayList, getActivity()));
            gridView.deferNotifyDataSetChanged();
            gridView.setOnItemClickListener(this);
        }

        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        communicationfav.setDAtA(arrayList.get(position));
    }

    public interface communicationFav {
        public void setDAtA(MovieInfo movieInfo);
    }

}
