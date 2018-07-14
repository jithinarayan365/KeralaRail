package com.sololaunches.www.keralarailandmetro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.startapp.android.publish.adsCommon.StartAppSDK;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PnrStatus extends AppCompatActivity {

    EditText pnr;
    String url;
    String pnr_numberOn;
    ArrayList<String> collectedStatus;
    boolean hit;
    ProgressBar progressBar;
    String pnrHit;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        collectedStatus = null;
        Intent intent = new Intent(this, OpeningScreenTabs.class);
        startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pnr_status);
        hit = false;

        new PnrStatus.DisplayAdMetro().execute();





        progressBar = (ProgressBar) findViewById(R.id.currentStatusProgress);
        progressBar.setVisibility(View.GONE);
        progressBar.setProgress(0);
        progressBar.setMax(100);

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            new PnrStatus.DisplayAdMetro().execute();
        }


        SharedPreferences pref = getApplicationContext().getSharedPreferences("RETURN_MODE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("id", 1);
        editor.apply();

        Button btnBack = (Button) findViewById(R.id.back);
        Button btnClose = (Button) findViewById(R.id.close);
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OpeningScreenTabs.class);
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


        // interstitialAd.loadAd(new AdRequest.Builder().addTestDevice("0BC6A1F908708204FB57FC1DDE364FAA").build());//AdRequest.DEVICE_ID_EMULATOR).build());
        pnr = (EditText) findViewById(R.id.pnr_number);

        pnr.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pnr_numberOn = pnr.getText().toString();
                if (pnr_numberOn.length() >= 10) {
                    collectedStatus = null;
                    url = "http://indianrailapi.com/api/v1/pnrstatus/apikey/b5a330e9bfe74550f56a299164a95b7b/pnr/" + pnr_numberOn + "/";
                    new PnrStatus.RetrieveJsonObject2().execute(url);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });


        Button btn = (Button) findViewById(R.id.get_status);

        btn.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {



                ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo == null) {
                    Toast toast = Toast.makeText(getApplicationContext(), "you are not connected to the internet", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                if (!activeNetworkInfo.isConnected()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "you are not connected to the internet", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                String pnr_number = pnr.getText().toString();
                try {

                    if (pnr_number.length() > 10 || pnr_number.length() < 10) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Please Valid 10 Digit Number", Toast.LENGTH_LONG);
                        toast.show();
                        return;
                    }
                    hit = true;

                    if (!pnr_number.equals(pnrHit)) {
                        collectedStatus = null;
                        url = "http://indianrailapi.com/api/v1/pnrstatus/apikey/b5a330e9bfe74550f56a299164a95b7b/pnr/" + pnr_number + "/";
                        new PnrStatus.RetrieveJsonObject2().execute(url);

                    } else if (collectedStatus.size() > 1) {

                        Intent intent = new Intent(getApplicationContext(), PnrResultDisplay.class);
                        intent.putExtra("collectedBeans", collectedStatus);
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    Log.d("Exception", "getJSONObjectFromURL: " + e);
                }
            }
        });


    }

    private void loadFbAds() {

    }


    public class RetrieveJsonObject2 extends AsyncTask<String, String, String> {

        //  ProgressDialog dialog;

        @Override
        protected synchronized String doInBackground(String... params) {

            HttpURLConnection urlConnection = null;

            try {


                URL url = new URL(params[0]);
                StringBuilder result = new StringBuilder();
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                    return buffer.toString();
                }

            } catch (Exception e) {
                Log.d("Exception", "getJSONObjectFromURL: " + e);
                return null;
            } finally {

                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }


        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected synchronized void onPostExecute(String s) {
            super.onPostExecute(s);


            try {
                JSONObject obj = new JSONObject(s);

                collectedStatus = new ArrayList<String>();
                String code = obj.getString("ResponceCode");

                if ("200".equals(code)) {
                    collectedStatus = new ArrayList<String>();
                    String trainNumber = obj.getString("TrainNo");
                    String trainName = obj.getString("TrainName");
                    pnrHit = obj.getString("PNR");
                    collectedStatus.add("Train-Number :" + trainNumber + " " + trainName);
                    String doj = obj.getString("DateOfJourny");
                    String seatClass = obj.getString("ClassType");
                    JSONObject subObj = obj.getJSONObject("BoardingStation");
                    String from = subObj.getString("Name");
                    JSONObject subObj2 = obj.getJSONObject("ReservationUpTo");
                    String to = subObj2.getString("Name");

                    collectedStatus.add(" From : " + from);
                    collectedStatus.add(" To : " + to);
                    collectedStatus.add("Date : " + doj + "   CLASS " + seatClass);
                    collectedStatus.add("Passenger   " + " Booking-Status  " + " Current-Status ");


                    JSONArray jsonArray = obj.getJSONArray("PassengersList");
                    int n = 0;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject objJson = jsonArray.getJSONObject(i);
                        String passenger = "Passenger" + (++n);
                        String bookingStatus = String.format("%-15s", (objJson.getString("BookingStatus")));
                        String currentStatus = String.format("%-15s", (objJson.getString("CurrentStatus")));
                        collectedStatus.add(passenger + "  " + bookingStatus + " " + currentStatus);
                    }


                    if (hit && collectedStatus.size() > 1) {

                        Intent intent = new Intent(getApplicationContext(), PnrResultDisplay.class);
                        intent.putExtra("collectedBeans", collectedStatus);
                        startActivity(intent);
                    }

                    if (hit && collectedStatus.size() == 0) {
                        Intent intent = new Intent(getApplicationContext(), ErrorActivity.class);
                        startActivity(intent);
                    }

                } else {

                    if (hit) {
                        Intent intent = new Intent(getApplicationContext(), ErrorActivity.class);
                        startActivity(intent);
                    }
                }


            } catch (JSONException e) {
                Log.e("Error on JSON Parsing", "onPostExecute: " + e);
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

            try {
                StartAppSDK.init(getApplicationContext(), "203651630", false);
                RelativeLayout adContainer = (RelativeLayout)findViewById(R.id.adViewContainer);


            } catch (Exception e) {

                Log.e("Error on JSON Parsing", "onPostExecute: " + e);
            }

        }

        @Override
        protected synchronized void onPostExecute(String s) {
            super.onPostExecute(s);


        }

    }

}


