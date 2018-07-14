package com.sololaunches.www.keralarailandmetro;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;


import java.util.ArrayList;

public class SeatAvailabilityDisplay extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_availability_display);

        new SeatAvailabilityDisplay.DisplayAd().execute();
        ArrayList listRail = (ArrayList) getIntent().getSerializableExtra("lines");




        ListAdapter trainListAdapter = new CustomListAdapter(this, android.R.layout.simple_list_item_1, listRail, "YELLOW");
        ListView trains = (ListView) findViewById(R.id.train_journey);

        trains.setAdapter(trainListAdapter);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

            Intent intent = new Intent(this, RailwaySeatAvailabilityActivity.class);
            startActivity(intent);


    }


    public class DisplayAd extends AsyncTask<String, String, String> {


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

            } catch (Exception e) {

                Log.e("Error on JSON Parsing", "onPostExecute: " + e);
            }


        }

    }
}
