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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.startapp.android.publish.adsCommon.StartAppSDK;

import java.util.concurrent.atomic.AtomicReference;

public class MetroFareActivity extends AppCompatActivity {


    Spinner source;
    Spinner destination;
    TextView fareDesc;
    Boolean status = false;
    Boolean status1 = false;
    RailwayDBAdapter railwayDBAdapter;
    String srcCODE = null;
    String destCODE = null;
    AdView mAd;
    KeralaRailConvenience keralaRailConvenience;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartAppSDK.init(this, "203651630", false);

        setContentView(R.layout.activity_metro_fare);

       ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            new MetroFareActivity.DisplayAdMetro().execute();
        }

        keralaRailConvenience = new KeralaRailConvenience();
        source = (Spinner) findViewById(R.id.source_metro);
        destination = (Spinner) findViewById(R.id.destination_metro);
        fareDesc = (TextView) findViewById(R.id.metro_fare_Desc);



        Button btnBack = (Button) findViewById(R.id.back);
        Button btnClose = (Button) findViewById(R.id.close);
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MetroMain.class);
                startActivity(intent);
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        final String[] stationArray = ArrayHolder.getsuggestMetroStation();

        AtomicReference<ArrayAdapter<String>> adapter = new AtomicReference<ArrayAdapter<String>>();
        adapter.set(new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_text, stationArray));
        adapter.get().setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        source.setAdapter(adapter.get());
        destination.setAdapter(adapter.get());

        destination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    status = true;
                    destCODE = keralaRailConvenience.getMetroCode(position);
                    String temp = String.valueOf(source.getSelectedItemId());

                    if ("0".equals(temp)) {

                    } else {

                        srcCODE = keralaRailConvenience.getMetroCode(Integer.parseInt(temp));
                        getFare(status, status1);
                    }
                }
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        source.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position != 0) {

                    srcCODE = keralaRailConvenience.getMetroCode(position);
                    String temp = String.valueOf(destination.getSelectedItemId());

                    if ("0".equals(temp)) {

                    } else {
                        destCODE = keralaRailConvenience.getMetroCode(Integer.parseInt(temp));
                        getFare(status, status1);
                    }
                    status1 = true;


                    getFare(status, status1);
                }
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void getFare(Boolean b1, Boolean b2) {

        Boolean st1 = false;
        Boolean st2 = false;
        st1 = b1;
        st2 = b2;


        if (st1 && st2) {

            if (String.valueOf(srcCODE).equals(String.valueOf(destCODE))) {
                Toast toast = Toast.makeText(getApplicationContext(), "Looks like you Entered Same Station", Toast.LENGTH_LONG);
                toast.show();
            } else if (srcCODE == null && destCODE == null) {
                Toast toast = Toast.makeText(getApplicationContext(), "Enter both Source and Destination", Toast.LENGTH_LONG);
                toast.show();
            } else {

                railwayDBAdapter = new RailwayDBAdapter(getApplicationContext(), null, null, 1);
                Cursor cur = railwayDBAdapter.getMetroFares(destCODE, srcCODE);
                if (cur.getCount() == 0) {
                    return;
                }

                while (cur.moveToNext()) {
                    String fare = cur.getString(0);
                    fareDesc.setText(" INR : " + fare);
                }
            }
        }
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

}
