package com.example.elewa.movie.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.elewa.movie.Adapter.MovieAdapter;
import com.example.elewa.movie.DataInformation.MovieInfo;
import com.example.elewa.movie.R;
import com.example.elewa.movie.VolleySingleTon.VolleySingleTon;
import com.example.elewa.movie.key;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment implements AdapterView.OnItemClickListener {


    private GridView gridView;
    private RequestQueue mRequest;
    private JsonObjectRequest request;
    private ArrayList<MovieInfo> arrayList ;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy");
    private Communication communication;
    private DateFormat date = new SimpleDateFormat("yyyy");
    private String topRated = "http://api.themoviedb.org/3/movie/top_rated?api_key=bf4374991a509671072ca4f3fafd0348";
    private String popular = "http://api.themoviedb.org/3/movie/popular?api_key=bf4374991a509671072ca4f3fafd0348";



    /*-------- On Creat ot set Option menu for Fragment
       ------------- setHasOptionMenu(true) ------------
     */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    // ----------------- On createView to find View inside Fragment ----------------------------------------
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.main_activity_fragment, container, false);
        communication = (Communication) getActivity();
        arrayList = new ArrayList<>();
        gridView = (GridView) view.findViewById(R.id.gridView);
        gridView.setOnItemClickListener(this);

        //--------------Volley start to fetch data -----------------------------------------

        Volley(popular);

        return view;
    }


    public void Volley(String uri){
        mRequest = VolleySingleTon.getInstace().request();
        request = new JsonObjectRequest(Request.Method.GET,
                uri,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        arrayList =  parseJson(response);
                        updateGrid();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(getActivity(),"No internet connection",Toast.LENGTH_LONG).show();

                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(getActivity(),"Failure Error",Toast.LENGTH_LONG).show();

                        } else if (error instanceof ServerError) {
                            Toast.makeText(getActivity(),"Server Error",Toast.LENGTH_LONG).show();

                        } else if (error instanceof NetworkError) {
                            Toast.makeText(getActivity(),"Network Error",Toast.LENGTH_LONG).show();

                        } else if (error instanceof ParseError) {
                            Toast.makeText(getActivity(),"Parse Error",Toast.LENGTH_LONG).show();
                        }

                    }
                });


        mRequest.add(request);
    }

    public void updateGrid(){
        gridView.setAdapter(new MovieAdapter(arrayList, getActivity()));
        gridView.deferNotifyDataSetChanged();
    }

    //------------------------------- fetch data from json Format --------------------------------
    public ArrayList<MovieInfo> parseJson(JSONObject response) {


        ArrayList<MovieInfo> arrayList   = new ArrayList<>();
        try {
            JSONArray pageArray = response.getJSONArray(key.KEY_RESULTS);

            for (int i = 0; i < pageArray.length(); i++) {

                String poster_path = "N/A";
                String overview = "N/A";
                String original_title = "N/A";
                String release_date = "N/A";
                double vote_average = -1;
                int id = -1;
                String Year = null;


                JSONObject currentObject = pageArray.getJSONObject(i);

                if (currentObject.has(key.KEY_POSTER_PATH) && !currentObject.isNull(key.KEY_POSTER_PATH)) {
                    poster_path = currentObject.getString(key.KEY_POSTER_PATH);
                }
                if (currentObject.has(key.KEY_overview) && !currentObject.isNull(key.KEY_overview)) {
                    overview = currentObject.getString(key.KEY_overview);
                }
                if (currentObject.has(key.KEY_original_title) && !currentObject.isNull(key.KEY_original_title)) {
                    original_title = currentObject.getString(key.KEY_original_title);
                }
                if (currentObject.has(key.KEY_release_date) && !currentObject.isNull(key.KEY_release_date)) {
                    release_date = currentObject.getString(key.KEY_release_date);
                }
                if (currentObject.has(key.KEY_vote_average) && !currentObject.isNull(key.KEY_vote_average)) {
                    vote_average = currentObject.getDouble(key.KEY_vote_average);
                }

                if (currentObject.has(key.KEY_id) && !currentObject.isNull(key.KEY_id)) {
                    id = currentObject.getInt(key.KEY_id);
                }

                try {
                    Year = dateFormat.format(date.parse(release_date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (id != -1 && poster_path != "N/A" && original_title != "N/A") {

                    arrayList.add(new MovieInfo(id, poster_path, overview, original_title, Year, vote_average));
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;

    }

    // --------------------------- GridView on item click listener implementation -----------------------------
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        communication.setValue(arrayList.get(position));

    }


    //-------------- interface to get data for onePane or TwoPane ---------------------
    public interface Communication {
        public void setValue(MovieInfo movieInfo);
    }



    // ------------------- Option settings for fragment ---------------------------------------

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        if(id == R.id.action_most_rated){
            Volley(topRated);

        }else if(id == R.id.action_popular){
            Volley(popular);
        }

        return super.onOptionsItemSelected(item);
    }

}
