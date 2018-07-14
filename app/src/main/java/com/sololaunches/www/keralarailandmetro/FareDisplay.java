package com.sololaunches.www.keralarailandmetro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FareDisplay extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fare_display);

        //AdSettings.addTestDevice("36f984da-8af6-4d84-b8e8-ec99bfd0f10c");



        ArrayList listRail = (ArrayList) getIntent().getSerializableExtra("listRail");

        ListAdapter trainListAdapter = new CustomListAdapter(this, android.R.layout.simple_list_item_1, listRail, "YELLOW");
        ListView trains = (ListView) findViewById(R.id.train_journey);
        trains.setAdapter(trainListAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


            Intent intent = new Intent(this, RailwayFareActivity.class);
            startActivity(intent);

    }


}
