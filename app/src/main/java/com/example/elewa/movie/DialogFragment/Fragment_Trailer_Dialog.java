package com.example.elewa.movie.DialogFragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.elewa.movie.Adapter.TrailerAdapter;
import com.example.elewa.movie.DataInformation.VideoInfo;
import com.example.elewa.movie.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Trailer_Dialog extends DialogFragment implements AdapterView.OnItemClickListener {

    Context context;
    ArrayList<VideoInfo> arrayList;
    private LayoutInflater inflater;
    private View v;
    private ListView listView;
    TextView textView;

    public Fragment_Trailer_Dialog(Context context, ArrayList<VideoInfo> arrayList) {
        // Required empty public constructor
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //------------------------- inflate List view ---------------------
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.fragment__dialog, null);
        listView = (ListView) v.findViewById(R.id.trailers);
        listView.setOnItemClickListener(this);
        textView = (TextView) v.findViewById(R.id.text);
        if (arrayList.size() == 0) {
            textView.setVisibility(View.VISIBLE);
            textView.setText("NO Reviews");
        } else {
            listView.setAdapter(new TrailerAdapter(arrayList, context));
        }

        //-----------build Dialog ------------------
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(v);
        builder.setCancelable(true);
        builder.setNegativeButton(R.string.oK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        builder.setTitle("Chose trailers");
        Dialog dialog = builder.create();
        return dialog;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //--------------------Explicit intent for Youtube reference -----------------
        VideoInfo vidInfo = arrayList.get(position);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://www.youtube.com/watch?v=" + vidInfo.getKey()));
        startActivity(i);

    }
}
