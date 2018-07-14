package com.sololaunches.www.keralarailandmetro;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

public class MetroMain extends AppCompatActivity {

    Spinner from_metro = null;
    RailwayDBAdapter railwayDBAdapter;
    InputMethodManager imm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metro_main);


      //  AdSettings.addTestDevice("36f984da-8af6-4d84-b8e8-ec99bfd0f10c");


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            new MetroMain.DisplayAdMetro().execute();
        }


        ImageButton fare = (ImageButton) findViewById(R.id.metro_fare);
        from_metro = (Spinner) findViewById(R.id.from_metro);


        String[] stationArray = ArrayHolder.getsuggestMetroStationForTime();


        AtomicReference<ArrayAdapter<String>> adapter = new AtomicReference<ArrayAdapter<String>>();
        adapter.set(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, stationArray));
        adapter.get().setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        from_metro.setAdapter(adapter.get());

        railwayDBAdapter = new RailwayDBAdapter(this, null, null, 1);

        from_metro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {

                    ArrayList<Integer> upList = new ArrayList<Integer>();
                    ArrayList<Integer> dwList = new ArrayList<Integer>();
                    ArrayList<String> upListStr = new ArrayList<String>();
                    ArrayList<String> dwListStr = new ArrayList<String>();

                    Cursor cur = railwayDBAdapter.getMetroTimeTables();
                    if (cur.getCount() == 0) {
                        Toast toast = Toast.makeText(getApplicationContext(), "No Records Found", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    while (cur.moveToNext()) {

                        String start = cur.getString(4);
                        Log.d("checker", "onItemSelected: " + (position + 3));
                        String timingSource = cur.getString(position + 3);
                        String end = cur.getString(19);
                        boolean endShifted = false;
                        boolean strtShifted = false;


                        if ("END".equals(end.trim())) {
                            end = cur.getString(18);
                            endShifted = true;
                        }

                        if ("END".equals(start.trim())) {
                            start = cur.getString(5);
                            strtShifted = true;
                        }


                        if ("END".equals(timingSource.trim())) {
                            continue;
                        }


                        if ((Integer.parseInt(start)) > (Integer.parseInt(timingSource))) {

                            upList.add(Integer.parseInt(timingSource));

                        } else if ((Integer.parseInt(end)) > (Integer.parseInt(timingSource))) {

                            dwList.add(Integer.parseInt(timingSource));
                        } else if ((Integer.parseInt(end)) == (Integer.parseInt(timingSource)) && endShifted) {
                            dwList.add(Integer.parseInt(timingSource));

                        } else if ((Integer.parseInt(start)) == (Integer.parseInt(timingSource)) && strtShifted) {
                            upList.add(Integer.parseInt(timingSource));

                        }

                        Collections.sort(dwList);
                        Collections.sort(upList);
                        upListStr = KeralaRailConvenience.getSortedMetroTimeTableUp(upList);
                        dwListStr = KeralaRailConvenience.getSortedMetroTimeTableDw(dwList);

                    }


                    Intent intent1 = new Intent(getApplicationContext(), MetroToAndFromActivity.class);
                    intent1.putExtra("upList", upListStr);
                    intent1.putExtra("dwList", dwListStr);

                    startActivity(intent1);
                }
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), MetroFareActivity.class);
                startActivity(intent1);

            }
        });


        ImageButton mapMetro = (ImageButton) findViewById(R.id.metro_map);
        mapMetro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), metro_map.class);
                startActivity(intent);
            }
        });


    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    public class DisplayAdMetro extends AsyncTask<String, String, String> {


        @Override
        protected synchronized String doInBackground(String... params) {
            return null;
        }


        @Override
        protected synchronized void onProgressUpdate(String... values) {

        }

        @Override
        protected synchronized void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                RelativeLayout adContainer = (RelativeLayout) findViewById(R.id.adViewContainer);

            } catch (Exception e) {

                Log.e("Error on JSON Parsing", "onPostExecute: " + e);
            }


        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainChoiceScreen.class);
        startActivity(intent);
    }



}
