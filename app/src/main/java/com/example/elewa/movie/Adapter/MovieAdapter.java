package com.example.elewa.movie.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.elewa.movie.DataInformation.MovieInfo;
import com.example.elewa.movie.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by elewa on 4/28/16.
 */
public class MovieAdapter extends BaseAdapter {

    private ArrayList<MovieInfo> arrayList = new ArrayList<>();
    Context context;

    public MovieAdapter(ArrayList<MovieInfo> arrayList, Context context) {

        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder viewHolder = null;


        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.single_row_view, parent, false);
            viewHolder = new ViewHolder(view);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        MovieInfo movieInfo = arrayList.get(position);
        Picasso.with(context).load("https://image.tmdb.org/t/p/w396/" + movieInfo.getPoster_path()).into(viewHolder.imageView);

        return view;
    }


    public class ViewHolder {
        private ImageView imageView;
        public ViewHolder(View v) {

            imageView = (ImageView) v.findViewById(R.id.image);
        }
    }
}
