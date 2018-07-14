package com.sololaunches.www.keralarailandmetro;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;
import com.startapp.android.publish.adsCommon.VideoListener;


public class UpdateScreen extends AppCompatActivity {

    Button btn;
    private StartAppAd startAppAd = new StartAppAd(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartAppSDK.init(this, "203651630", false);
        setContentView(R.layout.activity_update_screen);
        startAppAd = new StartAppAd(this);
        startAppAd.loadAd(StartAppAd.AdMode.REWARDED_VIDEO);
        startAppAd.setVideoListener(new VideoListener() {
            @Override
            public void onVideoCompleted() {
                Intent intent = new Intent(getApplicationContext(), FinalUpdateStage.class);
                startActivity(intent);

            }
        });

        btn = (Button) findViewById(R.id.continue_next);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAppAd.showAd();
                //Intent intent = new Intent(getApplicationContext(), FinalUpdateStage.class);
               // startActivity(intent);
            }
        });


    }


}
