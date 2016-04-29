package com.example.elewa.movie.DialogFragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.elewa.movie.Adapter.ReviewAdapter;
import com.example.elewa.movie.DataInformation.ReviewInfo;
import com.example.elewa.movie.R;

import java.util.ArrayList;


public class Fragment_Overview_Dialog extends DialogFragment {

    private Context context;
    private ArrayList<ReviewInfo> arrayList;
    private LayoutInflater inflater;
    private View v;
    private ListView listView;
    private TextView textView;

    public Fragment_Overview_Dialog (Context context, ArrayList<ReviewInfo> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.fragment__overview__dialog, null);
        listView = (ListView) v.findViewById(R.id.review);
        textView = (TextView) v.findViewById(R.id.textno);
        if(arrayList.size() == 0){
            textView.setVisibility(View.VISIBLE);
            textView.setText("NO Reviews");
        }else {
            listView.setAdapter(new ReviewAdapter(arrayList, context));
        }


        // --------------------- build dialog -----------------
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(v);
        builder.setCancelable(true);
        builder.setNegativeButton(R.string.oK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        builder.setTitle("List Of Reviews");
        Dialog dialog = builder.create();
        return dialog;
    }
}
