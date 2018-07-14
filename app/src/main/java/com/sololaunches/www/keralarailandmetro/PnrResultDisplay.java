package com.sololaunches.www.keralarailandmetro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;


import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;

import java.util.ArrayList;

public class PnrResultDisplay extends AppCompatActivity {

    private StartAppAd startAppAdInterstitial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartAppSDK.init(this, "203651630", false);
        startAppAdInterstitial = new StartAppAd(this);
        setContentView(R.layout.activity_pnr_result_display);


        final ArrayList list = (ArrayList) getIntent().getSerializableExtra("collectedBeans");
        ListAdapter trainListAdapter = new CustomListAdapter(this, android.R.layout.simple_list_item_1, list, "YELLOW");
        ListView trains = (ListView) findViewById(R.id.pnr_result_details);
        trains.setAdapter(trainListAdapter);

    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
            startAppAdInterstitial.showAd();
            Intent intent = new Intent(this, OpeningScreenTabs.class);
            startActivity(intent);
    }


}
