package com.sololaunches.www.keralarailandmetro;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RetrieverLiveTrainStatus extends AppCompatActivity {

    KeralaRailConvenience convenience = new KeralaRailConvenience();
    String trainNumber;
    EditText train_number;
    ProgressBar progressBar;
    String stationNow = null;
    boolean clicked;
    DatePicker datePicker;
    boolean flag;
    ArrayList<String> collectedLines;
    String trainNumberHit;
    TrainStatusBean firstBean;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        collectedLines = null;
        Intent intent = new Intent(this, OpeningScreenTabs.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retriver_live_train_status);

///
       // AdSettings.addTestDevice("338986843276632_339029909938992");


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

        View titleView = getWindow().findViewById(android.R.id.title);
        if (titleView != null) {
            ViewParent parent = titleView.getParent();
            if (parent != null && (parent instanceof View)) {
                View parentView = (View) parent;
                parentView.setBackgroundColor(Color.RED);
            }
        }

        firstBean = new TrainStatusBean();
        progressBar = (ProgressBar) findViewById(R.id.currentStatusProgress);
        progressBar.setVisibility(View.GONE);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        flag = false;
        datePicker = (DatePicker) findViewById(R.id.datePicker2);
        train_number = (EditText) findViewById(R.id.train_number_fare);
        trainNumber = (String) getIntent().getSerializableExtra("trainNumber");

        if (trainNumber != null) {
            train_number.setText(trainNumber);

            clicked = true;
            String year = String.valueOf(datePicker.getYear());
            String format_month = String.format("%2s", String.valueOf(datePicker.getMonth() + 1)).replace(' ', '0');
            String format_day = String.format("%2s", String.valueOf(datePicker.getDayOfMonth())).replace(' ', '0');
            String date_required = year + format_month + format_day;

            try {

                String url = "http://indianrailapi.com/api/v1/livetrainstatus/apikey/b5a330e9bfe74550f56a299164a95b7b/trainno/" + trainNumber.trim() + "/dateofjourny/" + date_required + "/";
                new RetrieverLiveTrainStatus.RetrieveJsonObject().execute(url);

            } catch (Exception e) {
                Log.d("Exception", "getJSONObjectFromURL: " + e);
            }


        }


        train_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String year = String.valueOf(datePicker.getYear());

                String format_month = String.format("%2s", String.valueOf(datePicker.getMonth() + 1)).replace(' ', '0');
                String format_day = String.format("%2s", String.valueOf(datePicker.getDayOfMonth())).replace(' ', '0');
                String date_required = year + format_month + format_day;
                trainNumber = train_number.getText().toString();

                if (trainNumber.length() >= 5) {
                    try {

                        collectedLines = null;
                        String url = "http://indianrailapi.com/api/v1/livetrainstatus/apikey/b5a330e9bfe74550f56a299164a95b7b/trainno/" + trainNumber.trim() + "/dateofjourny/" + date_required + "/";
                        new RetrieverLiveTrainStatus.RetrieveJsonObject().execute(url);

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



                clicked = true;
                DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker2);
                String year = String.valueOf(datePicker.getYear());
                String format_month = String.format("%2s", String.valueOf(datePicker.getMonth() + 1)).replace(' ', '0');
                String format_day = String.format("%2s", String.valueOf(datePicker.getDayOfMonth())).replace(' ', '0');
                String date_required = year + format_month + format_day;
                train_number = (EditText) findViewById(R.id.train_number_fare);
                trainNumber = train_number.getText().toString();

                if (trainNumber.length() < 5) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please Enter a Valid Train Number!!", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                try {
                    if(!trainNumber.equals(trainNumberHit)){

                        String url = "http://indianrailapi.com/api/v1/livetrainstatus/apikey/b5a330e9bfe74550f56a299164a95b7b/trainno/" + trainNumber.trim() + "/dateofjourny/" + date_required + "/";
                        new RetrieverLiveTrainStatus.RetrieveJsonObject().execute(url);

                    } else if (flag && collectedLines != null) {

                        Intent intent = new Intent(getApplicationContext(), current_train_status.class);
                        intent.putExtra("collectedBeans", collectedLines);
                        intent.putExtra("line1", firstBean.getStatusDesc());
                        intent.putExtra("stationNow", stationNow);
                        flag = false;
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    Log.d("Exception", "getJSONObjectFromURL: " + e);
                }
            }
        });
    }


    public class RetrieveJsonObject extends AsyncTask<String, String, String> {


        @Override
        protected synchronized String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;

            try {
                URL url = new URL(params[0]);
                Log.d("url", "doInBackground: " + url);
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
                collectedLines = new ArrayList<String>();
                JSONObject obj = new JSONObject(s);
                String response_code = obj.getString("ResponseCode");

                if ("200".equals(response_code)) {

                    firstBean.setResponse_code(response_code);

                    trainNumberHit = obj.getString("TrainNumber");
                    firstBean.setStatusDesc(convenience.checkNULL(obj.getString("CurrentPosition")));
                    JSONObject currentStationObj = new JSONObject(obj.getString("CurrentStation"));
                    firstBean.setCurrent_station(currentStationObj.getString("StationName"));
                    firstBean.setCurrentNumber(currentStationObj.getString("SerialNo"));
                    JSONArray jsonArray = obj.getJSONArray("TrainRoute");
                    StringBuffer stbf = new StringBuffer();
                    boolean flag2 = true;
                    String IsArrived = null;
                    stationNow = null;

                    for (int i = 0; i < jsonArray.length(); i++) {

                        TrainStatusBean collectionBean = new TrainStatusBean();
                        JSONObject objJson = jsonArray.getJSONObject(i);
                        collectionBean.setNumber(objJson.getString("SerialNo"));
                        collectionBean.setStation_context(objJson.getString("StationName"));
                        collectionBean.setStation_context_act_arrival(objJson.getString("ActualArrival"));
                        collectionBean.setStation_context_sch_arrival(objJson.getString("ScheduleArrival"));
                        IsArrived = objJson.getString("IsArrived");
                        if ("false".equals(IsArrived) && flag2) {
                            flag2 = false;
                            stationNow = objJson.getString("StationName");
                        }
                        String part1 = String.format("%-18s", collectionBean.getStation_context());
                        String part2 = String.format("%-18s", collectionBean.getStation_context_act_arrival());
                        String part3 = String.format("%-14s", collectionBean.getStation_context_sch_arrival());
                        String lines = part3 + part2 + part1;
                        collectedLines.add(lines);
                    }

                    if (clicked) {
                        Intent intent = new Intent(getApplicationContext(), current_train_status.class);
                        intent.putExtra("collectedBeans", collectedLines);
                        intent.putExtra("line1", firstBean.getStatusDesc());
                        intent.putExtra("stationNow", stationNow);
                        clicked = false;
                        startActivity(intent);
                    }
                    flag = true;
                } else {
                    if (clicked) {
                        Intent intent = new Intent(getApplicationContext(), ErrorActivity.class);
                        startActivity(intent);
                    }
                }

            } catch (Exception e) {
                Log.e("Error on JSON Parsing", "onPostExecute: " + e);
            }


        }

    }

}




