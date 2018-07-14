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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.startapp.android.publish.adsCommon.StartAppSDK;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RailwayFareActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ProgressBar progressBar;
    AutoCompleteTextView srcFare, destFare;
    ArrayList<String> listRail;
    EditText train_number;
    boolean hit;
    boolean srcFlag;
    boolean desFlag;
    boolean numFlag;
    String destination;
    String source;
    String trainNo;
    String dest;
    String src;


    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), OpeningScreenTabs.class);
        startActivity(i);

    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartAppSDK.init(this, "203651630", true);

        setContentView(R.layout.activity_railway_fare);




        srcFare = (AutoCompleteTextView) findViewById(R.id.source_fare);
        destFare = (AutoCompleteTextView) findViewById(R.id.destination_fare);
        train_number = (EditText) findViewById(R.id.train_number_fare);
        progressBar = (ProgressBar) findViewById(R.id.currentStatusProgress);

        //AdSettings.addTestDevice("36f984da-8af6-4d84-b8e8-ec99bfd0f10c");
        RelativeLayout adContainer = (RelativeLayout) findViewById(R.id.adViewContainer);


        SharedPreferences pref = getApplicationContext().getSharedPreferences("RETURN_MODE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        // SharedPreferences.Editor editor = getSharedPreferences("RETURN_MODE", MODE_PRIVATE).edit();
        editor.putInt("id", 1);
        editor.apply();


        srcFare.setDropDownBackgroundResource(R.color.autocompletet_background_color);
        destFare.setDropDownBackgroundResource(R.color.autocompletet_background_color);
        String[] stationArray = ArrayHolder.getAllIndiaStations();
        srcFare.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, stationArray));
        destFare.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, stationArray));
        train_number = (EditText) findViewById(R.id.train_number_fare);
        progressBar.setVisibility(View.GONE);


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

        srcFare.requestFocus();
        srcFare.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //AutoCompleteTextView stationText = (AutoCompleteTextView) getView().findViewById(R.id.station);

                source = srcFare.getText().toString();
                srcFlag = true;

                srcFare.clearFocus();
                if (!desFlag) {
                    destFare.requestFocus();
                } else {
                    train_number.requestFocus();
                }
                startHittingUrl();

            }
        });


        destFare.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //AutoCompleteTextView stationText = (AutoCompleteTextView) getView().findViewById(R.id.station);

                destination = destFare.getText().toString();
                destFare.clearFocus();
                if (!srcFlag) {
                    srcFare.requestFocus();
                } else {
                    train_number.requestFocus();
                }
                desFlag = true;
                startHittingUrl();

            }
        });


        train_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                trainNo = train_number.getText().toString().trim();

                if (trainNo.length() >= 5) {
                    try {
                        numFlag = true;
                        if (!srcFlag) {
                            srcFare.requestFocus();
                        } else if (!desFlag) {
                            destFare.requestFocus();
                        }
                        startHittingUrl();
                    } catch (Exception e) {
                        Log.d("Exception", "getJSONObjectFromURL: " + e);
                    }
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


        Button btn = (Button) findViewById(R.id.getCurrentStats);
        btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo == null) {
                    Log.d("activeNetworkInfo", "inside: " + activeNetworkInfo);
                    Toast toast = Toast.makeText(getApplicationContext(), "you are not connected to the internet", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }


                if (!activeNetworkInfo.isConnected()) {
                    Log.d("activeNetworkInfo", "inside: " + activeNetworkInfo);
                    Toast toast = Toast.makeText(getApplicationContext(), "you are not connected to the internet", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }


                hit = true;

                if (listRail != null) {
                    Log.d("inside ONCLICK", "onClick: " + listRail);
                    Intent i = new Intent(getApplicationContext(), FareDisplay.class);
                    i.putExtra("listRail", listRail);
                    startActivity(i);
                } else {


                    destination = destFare.getText().toString().trim();
                    source = srcFare.getText().toString().trim();
                    trainNo = train_number.getText().toString().trim();

                    if ("".equals(destination) || "".equals(source)) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Please Enter all Details!!", Toast.LENGTH_LONG);
                        toast.show();
                        return;
                    }

                    if (destination.length() <= 4) {
                        dest = destination.trim().toUpperCase();
                    } else {
                        String[] arr = destination.split(" ");
                        dest = arr[arr.length - 1];
                    }


                    if (source.length() <= 4) {
                        src = source.trim().toUpperCase();
                    } else {
                        String[] srcArr = source.split(" ");
                        src = srcArr[srcArr.length - 1];
                    }

                    progressBar.setVisibility(View.VISIBLE);

                    try {

                        String url = "http://indianrailapi.com/api/v2/trainfare/apikey/b5a330e9bfe74550f56a299164a95b7b/trainno/" + trainNo + "/source/" + src + "/destination/" + dest;
                        new RailwayFareActivity.RetrieveJsonObject().execute(url);

                    } catch (Exception e) {

                        Log.d("Exception", "getJSONObjectFromURL: " + e);
                    }


                }
            }
        });


    }


    private void getValues() {
        if (destination.length() <= 4) {
            dest = destination.trim().toUpperCase();
        } else {
            String[] arr = destination.split(" ");
            dest = arr[arr.length - 1];
        }


        if (source.length() <= 4) {
            src = source.trim().toUpperCase();
        } else {
            String[] srcArr = source.split(" ");
            src = srcArr[srcArr.length - 1];
        }


        trainNo = train_number.getText().toString().trim();

    }


    private void startHittingUrl() {


        if (srcFlag && desFlag && numFlag) {
            getValues();
            try {
                String url = "http://indianrailapi.com/api/v2/trainfare/apikey/b5a330e9bfe74550f56a299164a95b7b/trainno/" + trainNo + "/source/" + src + "/destination/" + dest;
                new RailwayFareActivity.RetrieveJsonObject().execute(url);

            } catch (Exception e) {
                Log.d("Exception", "getJSONObjectFromURL: " + e);
            }
        }

    }


    public class RetrieveJsonObject extends AsyncTask<String, String, String> {


        @Override
        protected synchronized String doInBackground(String... params) {

            HttpURLConnection urlConnection = null;

            try {


                URL url = new URL(params[0]);
                Log.d("URL", "doInBackground: " + url);
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

                }
                return buffer.toString();

            } catch (Exception e) {
                Log.d("Exception", "getJSONObjectFromURL: " + e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

        }


        @Override
        protected synchronized void onProgressUpdate(String... values) {

            progressBar.setProgress(Integer.parseInt(values[0]));

        }

        @Override
        protected synchronized void onPostExecute(String s) {
            super.onPostExecute(s);

            try {


                JSONObject obj = new JSONObject(s);
                String response_code = obj.getString("ResponseCode");
                String trainName = obj.getString("TrainName");
                String from = obj.getString("From");
                String to = obj.getString("To");
                listRail = new ArrayList<String>();
                listRail.add(trainName);
                listRail.add(from + " to " + to);


                if ("100".equals(response_code)) {


                    JSONArray jsonArray = obj.getJSONArray("Fares");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject objJson = jsonArray.getJSONObject(i);
                        String name = objJson.getString("Name");
                        String fare = objJson.getString("Fare");
                        listRail.add("Class " + name + "   :      INR : " + fare);
                    }
                    listRail.add("");
                    listRail.add("Tatkal Charges are as Below ");
                    listRail.add("Second Sitting (10%)  : INR 10  to 15");
                    listRail.add("Sleeper Class  (30%)  : INR 100 to 200");
                    listRail.add("AC Chair Car   (30%)  : INR 125 to 225");
                    listRail.add("AC 3 Tier         (30%)  : INR 300 to 400");
                    listRail.add("AC 2 Tier         (30%)  : INR 400 to 500");
                    listRail.add("Executive         (30%)  : INR 400 to 500");


                    if (hit) {
                        Intent i = new Intent(getApplicationContext(), FareDisplay.class);
                        i.putExtra("listRail", listRail);
                        startActivity(i);
                    }


                } else {
                    if (hit) {
                        Intent i = new Intent(getApplicationContext(), ErrorActivity.class);
                        startActivity(i);
                    }
                }


            } catch (Exception e) {
                Log.e("Error on JSON Parsing", "onPostExecute: " + e);
            }


        }

    }


}
