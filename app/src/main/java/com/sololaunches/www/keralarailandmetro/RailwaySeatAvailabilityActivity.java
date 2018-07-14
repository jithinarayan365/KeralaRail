package com.sololaunches.www.keralarailandmetro;

import android.app.Activity;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class RailwaySeatAvailabilityActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    Spinner quota, classSeat;
    ProgressBar progressBar;
    AutoCompleteTextView srcFare, destFare;
    String selection;
    EditText train_number;
    String selectionCls;
    String destination;
    String source;
    String trainNo;
    boolean ticketClass;
    boolean des;
    boolean sorc;
    boolean trainN;
    boolean hit;
    DatePicker datePicker;
    String date_required;
    String src;
    String dest;
    InputMethodManager imm;
    ArrayList<String> lines;


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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_railway_seat_availability);




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


        imm = (InputMethodManager) getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        datePicker = (DatePicker) findViewById(R.id.datePickerSeat);
        quota = (Spinner) findViewById(R.id.spinner_Quota);
        classSeat = (Spinner) findViewById(R.id.spinnerClass);
        destFare = (AutoCompleteTextView) findViewById(R.id.destination_seat);
        srcFare = (AutoCompleteTextView) findViewById(R.id.source_seat);
        train_number = (EditText) findViewById(R.id.train_number_seat);
        srcFare.setDropDownBackgroundResource(R.color.autocompletet_background_color);
        destFare.setDropDownBackgroundResource(R.color.autocompletet_background_color);
        String[] stationArray = ArrayHolder.getAllIndiaStations();
        srcFare.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, stationArray));
        destFare.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, stationArray));
        progressBar = (ProgressBar) findViewById(R.id.currentStatusProgress);
        progressBar.setVisibility(View.GONE);
        String[] quotas = ArrayHolder.getAllRailQuota();
        AtomicReference<ArrayAdapter<String>> adapter = new AtomicReference<ArrayAdapter<String>>();
        adapter.set(new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_text, quotas));
        adapter.get().setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quota.setAdapter(adapter.get());

        String[] classes = new String[]{"select class", "1A", "2A", "3A", "FC", "SL", "CC", "2S"};
        AtomicReference<ArrayAdapter<String>> adapter1 = new AtomicReference<ArrayAdapter<String>>();
        adapter1.set(new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item_text, classes));
        adapter1.get().setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSeat.setAdapter(adapter1.get());
        classSeat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectionCls = classSeat.getSelectedItem().toString().trim();
                ticketClass = true;
                startHittingUrl();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        quota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String test[] = quota.getSelectedItem().toString().split(" ");
                selection = test[0];
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        destination = destFare.getText().toString().trim();
        source = srcFare.getText().toString().trim();
        trainNo = train_number.getText().toString().trim();

        Button btn = (Button) findViewById(R.id.get_train_status_seat);
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


                hit = true;
                if (hit && lines != null) {
                    hit = false;
                    Intent i = new Intent(getApplicationContext(), SeatAvailabilityDisplay.class);
                    i.putExtra("lines", lines);
                    startActivity(i);
                }


                if ("".equals(destination) || "".equals(source)) {
                    hit = false;
                    Toast toast = Toast.makeText(getApplicationContext(), "Please Enter all Details!!", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                if ("select class".equals(selectionCls)) {
                    hit = false;
                    Toast toast = Toast.makeText(getApplicationContext(), "Please Select a Class!!", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }


                if ("".equals(trainNo)) {
                    hit = false;
                    Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Train Number!!", Toast.LENGTH_LONG);
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

                String year = String.valueOf(datePicker.getYear());
                String format_month = String.format("%2s", String.valueOf(datePicker.getMonth() + 1)).replace(' ', '0');
                String format_day = String.format("%2s", String.valueOf(datePicker.getDayOfMonth())).replace(' ', '0');
                String date_required = year + format_month + format_day;

                try {

                    String url = "http://indianrailapi.com/api/v1/seatavailable/apikey/b5a330e9bfe74550f56a299164a95b7b/trainno/" + trainNo + "/dateofjourny/" + date_required + "/source/" + src + "/destination/" + dest + "/classcode/" + selectionCls + "/quota/" + selection + "/";
                    new RailwaySeatAvailabilityActivity.RetrieveJsonObject().execute(url);

                } catch (Exception e) {
                    Log.d("Exception", "getJSONObjectFromURL: " + e);
                }


            }
        });


        destFare.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //AutoCompleteTextView stationText = (AutoCompleteTextView) getView().findViewById(R.id.station);

                String year = String.valueOf(datePicker.getYear());
                String format_month = String.format("%2s", String.valueOf(datePicker.getMonth() + 1)).replace(' ', '0');
                String format_day = String.format("%2s", String.valueOf(datePicker.getDayOfMonth())).replace(' ', '0');
                date_required = year + format_month + format_day;

                AutoCompleteTextView destinationText = (AutoCompleteTextView) findViewById(R.id.source_seat);
                destination = destinationText.getText().toString();
                destFare.clearFocus();
                if (!sorc) {
                    srcFare.requestFocus();
                } else if (!trainN) {
                    train_number.requestFocus();
                } else {
                    classSeat.requestFocus();

                }
                des = true;
                startHittingUrl();


            }
        });

        srcFare.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //AutoCompleteTextView stationText = (AutoCompleteTextView) getView().findViewById(R.id.station);
                String year = String.valueOf(datePicker.getYear());
                String format_month = String.format("%2s", String.valueOf(datePicker.getMonth() + 1)).replace(' ', '0');
                String format_day = String.format("%2s", String.valueOf(datePicker.getDayOfMonth())).replace(' ', '0');
                date_required = year + format_month + format_day;

                AutoCompleteTextView destinationText = (AutoCompleteTextView) findViewById(R.id.source_seat);
                source = destinationText.getText().toString();
                srcFare.clearFocus();
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                if (!des) {
                    destFare.requestFocus();
                } else if (!trainN) {
                    train_number.requestFocus();
                } else {
                    classSeat.requestFocus();
                }
                sorc = true;


                startHittingUrl();

            }
        });


        train_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String year = String.valueOf(datePicker.getYear());
                String format_month = String.format("%2s", String.valueOf(datePicker.getMonth() + 1)).replace(' ', '0');
                String format_day = String.format("%2s", String.valueOf(datePicker.getDayOfMonth())).replace(' ', '0');
                date_required = year + format_month + format_day;
                destination = destFare.getText().toString().trim();
                source = srcFare.getText().toString().trim();
                trainNo = train_number.getText().toString().trim();


                if (trainNo.length() >= 5) {
                    try {
                        train_number.clearFocus();
                        //   imm.hideSoftInputFromWindow(getWindowToken(), 0);

                        trainN = true;
                        if (!sorc) {
                            srcFare.requestFocus();
                        } else if (!des) {
                            destFare.requestFocus();
                        }
                        if (sorc && des) {
                            classSeat.requestFocus();
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


        Log.d("startHittingUrl", "startHittingUrl: " + trainN + "  " + ticketClass + "  " + sorc + "  " + des);
        if (trainN && ticketClass && sorc && des) {
            try {
                getValues();
                String url = "http://indianrailapi.com/api/v1/seatavailable/apikey/b5a330e9bfe74550f56a299164a95b7b/trainno/" + trainNo + "/dateofjourny/" + date_required + "/source/" + src + "/destination/" + dest + "/classcode/" + selectionCls + "/quota/" + selection + "/";
                // hit = true;
                new RailwaySeatAvailabilityActivity.RetrieveJsonObject().execute(url);

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

                lines = new ArrayList<String>();


                if ("200".equals(response_code)) {

                    String trainName = obj.getString("TrainName");
                    String trainNumber = obj.getString("TrainNumber");
                    JSONObject objFrom = (JSONObject) obj.get("From");
                    JSONObject objClass = (JSONObject) obj.get("Class");
                    JSONObject objTo = (JSONObject) obj.get("To");

                    lines.add(" " + trainNumber + "    " + trainName);
                    lines.add(" From " + objFrom.getString("Name") + " To " + objTo.getString("Name"));
                    lines.add(" CLASS " + objClass.getString("Name") + "    ");


                    JSONArray jsonArray = obj.getJSONArray("Availabile");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject objJson = jsonArray.getJSONObject(i);
                        String date = objJson.getString("Date");
                        String status = objJson.getString("Status");
                        lines.add(" " + date + "    : " + status);

                    }

                    if (hit) {

                        if (lines.size() > 1) {
                            Intent i = new Intent(getApplicationContext(), SeatAvailabilityDisplay.class);
                            i.putExtra("lines", lines);
                            startActivity(i);
                        }
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, OpeningScreenTabs.class);
        startActivity(intent);

    }
}
