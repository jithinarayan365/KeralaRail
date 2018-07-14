package com.sololaunches.www.keralarailandmetro;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;

import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;

public class MetroChoiceScreen extends AppCompatActivity {

    private StartAppAd startAppAdInterstitial = new StartAppAd(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new MetroChoiceScreen.DisplayAdMetro().execute();
        setContentView(R.layout.activity_metro_choice_screen);
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
                StartAppSDK.init(getApplicationContext(), "203651630", false);
                startAppAdInterstitial.showAd();
              //  startAppAdInterstitial.
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
