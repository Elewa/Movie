package com.example.elewa.movie.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.elewa.movie.R;
import com.example.elewa.movie.DataInformation.VideoInfo;

import java.util.ArrayList;

/**
 * Created by elewa on 4/28/16.
 */
public class TrailerAdapter extends BaseAdapter {
    Context context;

    ArrayList<VideoInfo> arrayList = new ArrayList<>();
    public TrailerAdapter(ArrayList<VideoInfo> arrayList, Context context) {

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
            view = inflater.inflate(R.layout.single_row_taliers, parent, false);
            viewHolder = new ViewHolder(view);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        VideoInfo videoInfo = arrayList.get(position);

        viewHolder.talier.setText(videoInfo.getTalier());


        return view;
    }


    public class ViewHolder {
        private TextView talier;


        public ViewHolder(View v) {

            talier = (TextView) v.findViewById(R.id.talier);

        }
    }
}