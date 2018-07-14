package com.sololaunches.www.keralarailandmetro;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FinalUpdateStage extends AppCompatActivity {

    RailwayDBAdapter railwayDBAdapter;
    Button btn;
    String currTrnNumber = null;
    String currDate;
    KeralaRailConvenience keralaRailConvenience;
    ProgressBar progressBar;
    TextView mssg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_update_stage);


        mssg = (TextView) findViewById(R.id.update_message);
        mssg.setVisibility(View.GONE);
        progressBar = (ProgressBar) findViewById(R.id.update_progress);
        progressBar.setVisibility(View.GONE);

        railwayDBAdapter = new RailwayDBAdapter(getApplicationContext(), null, null, 1);
        keralaRailConvenience = new KeralaRailConvenience();
        btn = (Button) findViewById(R.id.start_update);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                mssg.setVisibility(View.VISIBLE);
                mssg.setText("It will take some time to update");
                currDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


                Thread t = new Thread() {
                    public void run() {

                        try {
                            Cursor cur = railwayDBAdapter.getAllTrainListForUpdate();
                            if (cur.getCount() == 0) {
                                return;
                            }


                            ArrayList<String> list = new ArrayList<String>();
                            while (cur.moveToNext()) {


                                String trainNo = cur.getString(1);
                                String updateDate = cur.getString(3);

                                if (currDate.equals(updateDate)) {
                                    continue;
                                } else {
                                    list.add(trainNo);
                                }


                            }
                            int val = list.size();

                            int n = 0;
                            for (String s : list) {
                                currTrnNumber = s;
                                String urlNEw = "http://indianrailapi.com/api/v1/trainroute/apikey/b5a330e9bfe74550f56a299164a95b7b/trainno/" + s + "/";
                                new FinalUpdateStage.RetrieveJsonObjectUpdate().execute(urlNEw);
                                sleep(2000);
                                n++;
                                float proportion = ((float) n) / ((float) val);
                                float progress = proportion * 100;
                                int finalCal = (int) progress;
                                progressBar.setProgress(finalCal);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                };
                t.start();
            }


        });

    }

    public class RetrieveJsonObjectUpdate extends AsyncTask<String, String, String> {

        RailwayDBAdapter railwayDBAdapter;

        @Override
        protected synchronized String doInBackground(String... params) {

            HttpURLConnection urlConnection = null;

            try {
                String generatedQuery;

                URL url = new URL(params[0]);

                railwayDBAdapter = new RailwayDBAdapter(getApplicationContext(), null, null, 1);
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

                String sample = buffer.toString();

                if (sample.length() > 180) {

                    JSONObject obj = new JSONObject(sample);
                    JSONObject objTrn = obj.getJSONObject("Train");
                    String trnNumb = objTrn.getString("TrainNo");
                    String trname = objTrn.getString("Name");

                    ArrayList<String> collectedLines = new ArrayList<String>();
                    String firstLine = " Scheduled  Actual-Arrivabl    Station           ";
                    collectedLines.add(firstLine);

                    JSONArray jsonArrayDays = objTrn.getJSONArray("Days");
                    StringBuffer stbDays = new StringBuffer();

                    for (int i = 0; i < jsonArrayDays.length(); i++) {

                        JSONObject objJson = jsonArrayDays.getJSONObject(i);
                        String day = objJson.getString("DayCode");
                        String value = objJson.getString("RunsOn");
                        if (day.equals("MON") && value.equals("Y")) {
                            stbDays.append("M");
                        } else if (day.equals("TUE") && value.equals("Y")) {
                            stbDays.append("T");
                        } else if (day.equals("WED") && value.equals("Y")) {
                            stbDays.append("W");
                        } else if (day.equals("THU") && value.equals("Y")) {
                            stbDays.append("H");
                        } else if (day.equals("FRI") && value.equals("Y")) {
                            stbDays.append("F");
                        } else if (day.equals("SAT") && value.equals("Y")) {
                            stbDays.append("A");
                        } else if (day.equals("SUN") && value.equals("Y")) {
                            stbDays.append("S");
                        }
                    }

                    String dayFormed = stbDays.toString();

                    if (dayFormed.equals("MTWHFAS")) {
                        dayFormed = "D";
                    }
                    JSONArray jsonArray = obj.getJSONArray("TrainRoute");
                    StringBuffer stbf = new StringBuffer();
                    boolean srcFlag = false;
                    int n = 0;
                    for (int i = 0; i < jsonArray.length(); i++) {

                        TrainStatusBean collectionBean = new TrainStatusBean();
                        JSONObject objJson = jsonArray.getJSONObject(i);
                        String station = objJson.getString("Code");
                        String day = objJson.getString("Day");
                        String finalDay = KeralaRailConvenience.getAccurateDay(day, dayFormed);
                        String timing = objJson.getString("ScheduleArrival");
                        String time = "";
                        if ("SOURCE".equals(timing)) {
                            if (srcFlag) {
                                break;
                            }
                            srcFlag = true;
                            String temp = objJson.getString("ScheduleDeparture");
                            String[] str = temp.split(":");
                            time = day + str[0] + str[1];
                        } else {
                            String[] str = timing.split(":");
                            time = day + str[0] + str[1];
                        }
                        collectionBean.setStation_context_sch_arrival(time);
                        stbf.append(",STN" + (++n) + "='" + station + "-" + collectionBean.getStation_context_sch_arrival() + "-" + finalDay + "'");
                    }

                    generatedQuery = "UPDATE RL_STATIONS_TRAIN_MAIN SET UPDATED_ON ='" + currDate + "',TRAIN_NAME='" + trname + "'" + stbf + " WHERE TRAIN_NUMBER='" + trnNumb + "';";
                    railwayDBAdapter.updateScheduleTrain(generatedQuery);


                } else {
                    generatedQuery = "UPDATE RL_STATIONS_TRAIN_MAIN SET UPDATED_ON='" + currDate + "' WHERE TRAIN_NUMBER='" + currTrnNumber + "';";
                    railwayDBAdapter.updateScheduleTrain(generatedQuery);
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

        }

        @Override
        protected synchronized void onPostExecute(String s) {
            super.onPostExecute(s);

        }

    }
}






