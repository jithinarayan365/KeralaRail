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
 * Created by hp on 10/12/2017.
 */

public class CustomListAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private int id;
    private List<String> items;
    private String colour;
    boolean alter;

    public CustomListAdapter(Context context, int textViewResourceId, List<String> list, String color) {
        super(context, textViewResourceId, list);
        mContext = context;
        id = textViewResourceId;
        items = list;
        colour = color;
        alter = true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);



        TextView tv = (TextView) view.findViewById(android.R.id.text1);
        if ("BLUE".equals(colour)) {
            tv.setTextColor(Color.rgb(0, 0, 139));
        } else if ("RED".equals(colour)) {
            tv.setTextColor(Color.RED);
        } else if ("GREEN".equals(colour)) {
            tv.setTextColor(Color.GREEN);

        } else if ("WHITE".equals(colour)) {

            if (position == 0) {

                tv.setTextColor(Color.BLACK);
            //    tv.setTypeface(null, Typeface.BOLD);
                tv.setTextSize(18);

            } else if (position % 2 != 1) {

                if (alter) {
                    tv.setTextColor(Color.YELLOW);
                    tv.setTypeface(null, Typeface.NORMAL);
                    tv.setTextSize(13);
                    //alter = false;
                } else {
                    tv.setTextColor(Color.YELLOW);
                    tv.setTypeface(null, Typeface.NORMAL);
                    tv.setTextSize(13);
                    // alter = true;
                }

            } else if (position % 2 == 1) {
                if (alter) {
                    tv.setTextColor(Color.YELLOW);
                    tv.setTypeface(null, Typeface.BOLD);
                    tv.setTextSize(17);
                    alter = false;
                } else {
                    tv.setTextColor(Color.YELLOW);
                    tv.setTypeface(null, Typeface.BOLD);
                    tv.setTextSize(17);
                    alter = true;
                }
            }


            return view;

        } else if ("YELLOW".equals(colour)) {


            tv.setTextColor(Color.YELLOW);
            tv.setTypeface(null, Typeface.BOLD);
            //tv.requestFocus()

        } else {
            tv.setTextColor(Color.YELLOW);
            tv.setTypeface(null, Typeface.BOLD);
            String chk = tv.getText().toString();
            if (chk.contains(colour)) {
                tv.setTextColor(Color.RED);
                tv.setFocusable(true);
                tv.setFocusableInTouchMode(true);
                tv.requestFocus();
            }


        }


        return view;
    }

}