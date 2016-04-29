package com.example.elewa.movie.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.elewa.movie.DataInformation.MovieInfo;
import com.example.elewa.movie.DataInformation.ReviewInfo;
import com.example.elewa.movie.DataInformation.VideoInfo;
import com.example.elewa.movie.DialogFragment.Fragment_Overview_Dialog;
import com.example.elewa.movie.DialogFragment.Fragment_Trailer_Dialog;
import com.example.elewa.movie.FavoriteDataBase;
import com.example.elewa.movie.R;
import com.example.elewa.movie.VolleySingleTon.VolleySingleTon;
import com.example.elewa.movie.key;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Detail_ActivityFragment extends Fragment implements View.OnClickListener {

    private TextView title, year, rating, overview;
    private ImageView poster;
    private Button Trailers, Reviews;
    private CheckBox checkBox;
    private MovieInfo movieInfo;
    private ArrayList<ReviewInfo> reviewsList;
    private ArrayList<VideoInfo> videosList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail__activity, container, false);
        title = (TextView) v.findViewById(R.id.title_text);
        poster = (ImageView) v.findViewById(R.id.movie_poster);
        year = (TextView) v.findViewById(R.id.year);
        rating = (TextView) v.findViewById(R.id.rating);
        overview = (TextView) v.findViewById(R.id.overView);
        checkBox = (CheckBox) v.findViewById(R.id.checkBox);
        Trailers = (Button) v.findViewById(R.id.Trailer);
        Reviews = (Button) v.findViewById(R.id.reviews);

        //---------------handle click ------------------
        checkBox.setOnClickListener(this);
        Trailers.setOnClickListener(this);
        Reviews.setOnClickListener(this);

        extractData();

        return v;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.Trailer) {

            Fragment_Trailer_Dialog fragmentTrailer_dialog = new Fragment_Trailer_Dialog(getActivity(), videosList);
            fragmentTrailer_dialog.show(getFragmentManager(), "trailers");

        } else if (v.getId() == R.id.reviews) {

            Fragment_Overview_Dialog fragment_overview_dialog = new Fragment_Overview_Dialog(getActivity(), reviewsList);
            fragment_overview_dialog.show(getFragmentManager(), "reviews");
        } else if (checkBox.isChecked())

        {
            FavoriteDataBase fav = new FavoriteDataBase(getActivity());
            fav.insertMovies(movieInfo);
            Toast.makeText(getActivity(), "Movie add to favorite", Toast.LENGTH_SHORT).show();
        } else

        {
            FavoriteDataBase ah = new FavoriteDataBase(getActivity());
            ah.delete(movieInfo.getOriginal_title());
        }

    }


    // ------------------- Option settings for fragment ---------------------------------------
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detail_, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if (id == R.id.share) {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.putExtra(Intent.EXTRA_TEXT, movieInfo.getOriginal_title() + " .. From Movie App ");
            share.setType("text/plain");
            startActivity(share);
        }

        return super.onOptionsItemSelected(item);

    }


    public void extractData() {


        try {

            movieInfo = (MovieInfo) getArguments().getSerializable("value");

        } catch (Exception e) {
            Intent intent = getActivity().getIntent();
            movieInfo = (MovieInfo) intent.getSerializableExtra("value");
        }


        FavoriteDataBase favoriteDataBase = new FavoriteDataBase(getActivity());
        boolean ah = favoriteDataBase.MovieExcit(movieInfo.getOriginal_title());
        if (ah) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }

        title.setText(movieInfo.getOriginal_title());
        Picasso.with(getActivity()).load("https://image.tmdb.org/t/p/w396/" + movieInfo.getPoster_path()).into(poster);

        if (movieInfo.getRelease_date() != null) {

            year.setText(movieInfo.getRelease_date());
        } else {
            year.setText("N/A");
        }
        if (movieInfo.getVote_average() != -1) {
            rating.setText(movieInfo.getVote_average() + "/10");
        } else {
            rating.setText("N/A");
        }
        if (movieInfo.getOverview() != "N/A") {
            overview.setText(movieInfo.getOverview());
        } else {
            overview.setText("N/A");
        }


        // -------------------- for fetch the reviews information ------------------------------------
        reviewsList = new ArrayList<>();
        RequestQueue TalierMRequest = VolleySingleTon.getInstace().request();
        JsonObjectRequest TalierRequest = new JsonObjectRequest(Request.Method.GET,
                "http://api.themoviedb.org/3/movie/" + movieInfo.getId() + "/reviews?api_key=bf4374991a509671072ca4f3fafd0348",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray pageArray = response.getJSONArray(key.KEY_RESULTS);
                            for (int i = 0; i < pageArray.length(); i++) {
                                String auther = "N/A";
                                String description = "N/A";
                                JSONObject currentObject = pageArray.getJSONObject(i);

                                if (currentObject.has(key.Key_Reviews_AUTHOR) && !currentObject.isNull(key.Key_Reviews_AUTHOR)) {
                                    auther = currentObject.getString(key.Key_Reviews_AUTHOR);
                                }
                                if (currentObject.has(key.Key_Reviews_description) && !currentObject.isNull(key.Key_Reviews_description)) {
                                    description = currentObject.getString(key.Key_Reviews_description);
                                }

                                reviewsList.add(new ReviewInfo(auther, description));


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        //---------------------------- for the fetch videos information ---------------------------------
        videosList = new ArrayList<>();
        RequestQueue VideoMRequest = VolleySingleTon.getInstace().request();
        JsonObjectRequest VideoRequest = new JsonObjectRequest(Request.Method.GET,
                "http://api.themoviedb.org/3/movie/" + movieInfo.getId() + "/videos?api_key=bf4374991a509671072ca4f3fafd0348",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray pageArray = response.getJSONArray(key.KEY_RESULTS);
                            for (int i = 0; i < pageArray.length(); i++) {
                                String talier = "N/A";
                                String Key = "N/A";

                                JSONObject currentObject = pageArray.getJSONObject(i);

                                if (currentObject.has(key.Key_Videos_talier) && !currentObject.isNull(key.Key_Videos_talier)) {
                                    talier = currentObject.getString(key.Key_Videos_talier);
                                }

                                if (currentObject.has(key.Key_Videos_Key) && !currentObject.isNull(key.Key_Videos_Key)) {
                                    Key = currentObject.getString(key.Key_Videos_Key);
                                }
                                videosList.add(new VideoInfo(talier, Key));


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        TalierMRequest.add(TalierRequest);
        VideoMRequest.add(VideoRequest);
    }
}
