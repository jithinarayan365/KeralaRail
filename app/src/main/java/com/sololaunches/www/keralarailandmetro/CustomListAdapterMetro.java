package com.sololaunches.www.keralarailandmetro;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hp on 04-02-2018.
 */

public class CustomListAdapterMetro extends ArrayAdapter<String> {

    private Context mContext;
    private int id;
    private List<String> items;
    private String colour;
    private String signature;

    public CustomListAdapterMetro(Context context, int textViewResourceId, List<String> list, String sign) {
        super(context, textViewResourceId, list);
        mContext = context;
        id = textViewResourceId;
        items = list;
        signature = sign;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        TextView tv = (TextView) view.findViewById(android.R.id.text1);

        if (signature.equals("UP")) {
            tv.setTextColor(Color.BLACK);
            tv.setTypeface(null, Typeface.BOLD);

        } else {
            tv.setTextColor(Color.BLACK);
            tv.setTypeface(null, Typeface.BOLD);
        }


        return view;
    }


}
