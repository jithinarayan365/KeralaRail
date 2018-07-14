package com.sololaunches.www.keralarailandmetro;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.startapp.android.publish.adsCommon.StartAppSDK;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by jithinarayan365@gmail.com on 7/16/2017.
 */

public class Tab1Railways extends Fragment {

    RailwayDBAdapter railwayDBAdapter;
    private static View rootView;
    AutoCompleteTextView destinationText;
    AutoCompleteTextView sourceText;
    AutoCompleteTextView station;
    String dest;
    String source;
    boolean first;
    boolean second;
    TextView titletext;
    Button cleared;
    Button schedule;
    View viewV;
    RelativeLayout adContainer;

    public Tab1Railways() {

    }

    public void startProcessing(View View) {


        if (second && first) {
            if (dest.length() <= 0 || sourceText.length() <= 0) {
                Toast toast = Toast.makeText(getActivity(), "Looks like you forgot to Enter Station", Toast.LENGTH_LONG);
                toast.show();
                return;
            } else if (dest.length() >= 0 || sourceText.length() >= 0) {
                boolean check = false;
                boolean check2 = false;
                String[] stationArrays = ArrayHolder.getAllIndiaStations();

                for (String s : stationArrays) {

                    if (s.equals(dest)) {
                        check = true;
                    }
                    if (s.equals(source)) {
                        check2 = true;
                    } else continue;

                }

                if (!check || !check2) {
                    Toast toast = Toast.makeText(getActivity(), "Please select a Valid Station fro Suggestions", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                } else if (check && check2) {

                    String destination = dest.substring(dest.length() - 3, dest.length());
                    String sourceName = source.substring(source.length() - 3, source.length());

                    if (destination.equals(sourceName)) {
                        Toast toast = Toast.makeText(getActivity(), "Source and Destination cannot be Same", Toast.LENGTH_LONG);
                        toast.show();
                        return;
                    }

                    Cursor cur = railwayDBAdapter.getAllTrainListNew();
                    if (cur.getCount() == 0) {
                        Intent in = new Intent(getActivity(), ErrorActivity.class);
                        startActivity(in);
                    }

                    ArrayList<String> list = new ArrayList<String>();

                    while (cur.moveToNext()) {
                        String trainNo = "";
                        String big = "";
                        String trainName2 = "";
                        String dayFinal = "";
                        boolean containS = false;
                        boolean containD = false;
                        String timeS = null;
                        String timeD = null;
                        String temp;
                        String large = "0";
                        String destinationFound = null;

                        for (int x = 4; x <= 100; x++) {
                            String content = cur.getString(x);

                            if (content == null) {
                                break;
                            } else {
                                String[] strArr = content.split("-");
                                String stn = strArr[0];

                                temp = strArr[1];
                                if (Long.parseLong(temp) > Long.parseLong(large)) {
                                    large = temp;
                                    destinationFound = strArr[0];
                                } else {

                                }

                                String dayCheck = strArr[2];

                                if (stn.equalsIgnoreCase(sourceName)) {
                                    dayFinal = KeralaRailConvenience.getDays(dayCheck);
                                    timeS = strArr[1];
                                    containS = true;

                                } else if (stn.equalsIgnoreCase(destination)) {
                                    timeD = strArr[1];
                                    containD = true;
                                }
                            }
                        }

                        if (containS && containD) {

                            int sourceTime = Integer.parseInt(timeS);
                            int destTime = Integer.parseInt(timeD);

                            if (destTime - sourceTime < 1) {
                                continue;
                            } else {
                                trainNo = cur.getString(1);
                                trainName2 = cur.getString(2);
                                String formatTrainNumber = String.format("%-11s", trainNo);
                                String formatedTraintime = String.format("%-9s", timeS);
                                big += formatedTraintime;
                                big += formatTrainNumber;
                                String tem = ArrayHolder.getCompleteStation(destinationFound);
                                big += "         " + String.format("%-8s", tem);
                                big += " # " + String.format("%-12s", trainName2);
                                big += "   Days: " + dayFinal;
                                list.add(big);
                            }

                        } else {
                            continue;
                        }
                    }

                    ArrayList<String> sortedList = KeralaRailConvenience.setTrainOrder(list);
                    Intent intent = new Intent(getActivity(), train_list.class);
                    intent.putExtra("dest", sortedList);
                    source = null;
                    dest = null;
                    first = false;
                    second = false;
                    startActivity(intent);

                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        destinationText = (AutoCompleteTextView) getView().findViewById(R.id.auto_destination);
        sourceText = (AutoCompleteTextView) getView().findViewById(R.id.source);
        AutoCompleteTextView station = (AutoCompleteTextView) getView().findViewById(R.id.station);
        destinationText.setText("");
        sourceText.setText("");
        station.setText("");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StartAppSDK.init(getContext(), "203651630", false);

        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);

        }
        try {


            rootView = inflater.inflate(R.layout.tab1_railways, container, false);
            viewV = inflater.inflate(R.layout.first_fragment_railways, container, false);
/*
            AdSettings.addTestDevice("0cfd0fa2-9810-4e17-a8ee-49893a9c90bb");
            adView = new com.facebook.ads.AdView(getContext(), "151174165565411_151176025565225", AdSize.BANNER_HEIGHT_50);
            adContainer = (RelativeLayout)rootView.findViewById(R.id.adViewContainer);
            adContainer.addView(adView);
            adView.loadAd();
*/


            ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                // Log.d("DisplayAd", "DisplayAd: ");
                new Tab1Railways.DisplayAd().execute();
            }


            railwayDBAdapter = new RailwayDBAdapter(getActivity(), null, null, 1);
            AutoCompleteTextView destinationText = (AutoCompleteTextView) rootView.findViewById(R.id.auto_destination);
            AutoCompleteTextView sourceText = (AutoCompleteTextView) rootView.findViewById(R.id.source);
            destinationText.setDropDownBackgroundResource(R.color.autocompletet_background_color);
            sourceText.setDropDownBackgroundResource(R.color.autocompletet_background_color);

            String[] stationArray = ArrayHolder.getAllIndiaStations();
            destinationText.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, stationArray));
            sourceText.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, stationArray));


            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, stationArray);
            destinationText.setAdapter(adapter);
            destinationText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    AutoCompleteTextView destinationText = (AutoCompleteTextView) getView().findViewById(R.id.auto_destination);
                    dest = destinationText.getText().toString();


                    String station = adapter.getItem(position).toString();
                    first = true;
                    startProcessing(rootView);

                }
            });

            sourceText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    String station = adapter.getItem(position).toString();
                    AutoCompleteTextView sourceText = (AutoCompleteTextView) getView().findViewById(R.id.source);
                    source = sourceText.getText().toString();
                    second = true;
                    startProcessing(rootView);
                }
            });

            ////////////////////////////////////////////Phase 2 ///////////Get Time Table///////////////////////////////////////

            station = (AutoCompleteTextView) rootView.findViewById(R.id.station);
            station.setVisibility(View.GONE);
            titletext = (TextView) rootView.findViewById(R.id.title_text);
            schedule = (Button) rootView.findViewById(R.id.schedule);
            cleared = (Button) rootView.findViewById(R.id.AtoB);
            cleared.setTextColor(Color.WHITE);
            cleared.setTypeface(null, Typeface.BOLD);

            cleared.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    titletext.setText("Schedule for Trains from Station A to B");
                    AutoCompleteTextView sourceText = (AutoCompleteTextView) getView().findViewById(R.id.source);
                    AutoCompleteTextView destination = (AutoCompleteTextView) getView().findViewById(R.id.auto_destination);
                    ImageView imageA = (ImageView) getView().findViewById(R.id.image_a);
                    ImageView imageB = (ImageView) getView().findViewById(R.id.image_b);
                    sourceText.setHint("Enter Starting Station");
                    imageA.setVisibility(View.VISIBLE);
                    imageB.setVisibility(View.VISIBLE);
                    destination.setVisibility(View.VISIBLE);
                    sourceText.setVisibility(View.VISIBLE);
                    station.setVisibility(View.GONE);
                    cleared.setTextColor(Color.WHITE);
                    cleared.setTypeface(null, Typeface.BOLD);
                    schedule.setTextColor(Color.BLACK);
                    schedule.setTypeface(null, Typeface.NORMAL);


                }
            });


            schedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    titletext.setText("Train Schedule/ Station Timetable");
                    AutoCompleteTextView sourceText = (AutoCompleteTextView) getView().findViewById(R.id.source);
                    AutoCompleteTextView destination = (AutoCompleteTextView) getView().findViewById(R.id.auto_destination);
                    ImageView imageA = (ImageView) getView().findViewById(R.id.image_a);
                    ImageView imageB = (ImageView) getView().findViewById(R.id.image_b);

                    sourceText.setHint("Enter Train Number / Station Name");
                    destination.setVisibility(View.GONE);
                    imageA.setVisibility(View.GONE);
                    imageB.setVisibility(View.GONE);

                    sourceText.setVisibility(View.GONE);
                    station.setVisibility(View.VISIBLE);
                    schedule.setTextColor(Color.WHITE);
                    schedule.setTypeface(null, Typeface.BOLD);
                    cleared.setTextColor(Color.BLACK);
                    cleared.setTypeface(null, Typeface.NORMAL);


                }
            });

            String[] stationTrainArray = ArrayHolder.getAllIndiaStationsAndTrains();
            final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, stationTrainArray);
            station.setDropDownBackgroundResource(R.color.autocompletet_background_color);
            station.setAdapter(adapter2);

            station.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    String station = adapter2.getItem(position).toString();
                    String nmb = station.substring(0, 5).trim();
                    Pattern pattern = Pattern.compile(".*[^0-9].*");


                    if (!pattern.matcher(nmb).matches()) {

                        ArrayList<String> listRail = new ArrayList<String>();
                        Cursor cur = railwayDBAdapter.getTrainJourney(nmb);

                        if (cur.getCount() == 0) {
                            return;
                        }

                        while (cur.moveToNext()) {
                            for (int n = 4; n <= 83; n++) {
                                String addition = cur.getString(n);
                                if (addition != null) {
                                    String[] value = addition.split("-");
                                    String timing = value[1];
                                    String dayWeek = value[2];
                                    String stn = value[0];
                                    String getCompleteStn = ArrayHolder.getCompleteStation(stn);

                                    if (addition == null) {
                                        continue;
                                    } else if (addition != null) {
                                        String trainStn = String.format("%-30s", getCompleteStn);
                                        listRail.add(timing + "  " + trainStn);
                                        continue;
                                    }
                                } else if (addition == null) {

                                    break;

                                }
                            }
                        }

                        Intent i = new Intent(getActivity(), TrainJourney.class);
                        ArrayList<String> listFormatted = KeralaRailConvenience.setTrainOrderForTrain(listRail);
                        i.putExtra("listRail", listFormatted);
                        i.putExtra("trainNumber", nmb);
                        startActivity(i);


                    } else {


                        String exactSt[] = station.split("\\s+");

                        String exactStation = exactSt[exactSt.length - 1];
                        Cursor cur2 = railwayDBAdapter.getTimeTableForStation(exactStation);


                        if (cur2.getCount() == 0) {
                            return;
                        }

                        ArrayList<String> list2 = new ArrayList<String>();

                        while (cur2.moveToNext()) {
                            String big = "";
                            String daySour = "";
                            String time2 = "";
                            boolean valid = false;
                            StringBuilder sb = new StringBuilder();
                            String temp;
                            String large = "0";
                            String destinationFound = null;

                            for (int n = 4; n <= 100; n++) {
                                String addition = cur2.getString(n);
                                sb.append("part" + n + addition);

                                if (addition == null) {
                                    break;
                                } else {

                                    String[] strArr = addition.split("-");
                                    String stn = strArr[0];
                                    temp = strArr[1];
                                    if (Long.parseLong(temp) > Long.parseLong(large)) {
                                        large = temp;
                                        destinationFound = strArr[0];
                                    } else {

                                    }


                                    if (stn.equalsIgnoreCase(exactStation)) {


                                        time2 = strArr[1];
                                        daySour = strArr[2];
                                        valid = true;
                                    } else {

                                        continue;
                                    }
                                }
                            }


                            // String checker = KeralaRailConvenience.getDayOfWeek();

                            // if ((daySour.contains(checker)) || (daySour.contains("D"))) {

                            String dayFinal = KeralaRailConvenience.getDays(daySour);


                            if (valid) {


                                String trainNo = cur2.getString(1);
                                String trainName2 = cur2.getString(2);


                                String formatTrainNumber = String.format("%-11s", trainNo);
                                String formatedTraintime = String.format("%-9s", time2);

                                big += formatedTraintime;
                                big += formatTrainNumber;

                                String tem = ArrayHolder.getCompleteStation(destinationFound);
                                big += "         " + String.format("%-8s", tem);

                                big += " # " + String.format("%-12s", trainName2);

                                big += "   Days: " + dayFinal;
                                Log.d("added list", "onClick: " + big);
                                list2.add(big);

                            } else {

                            }

                            //     }


                        }

                        ArrayList<String> sortedList = KeralaRailConvenience.setTrainOrder(list2);
                        Intent intent = new Intent(getActivity(), train_list.class);
                        intent.putExtra("dest", sortedList);
                        startActivity(intent);

                    }


                }
            });

        } catch (InflateException e)

        {

    /* map is already there, just return view as it is */

        }


        return rootView;
    }


    public static int getIndex(String source) {
        int num = KeralaRailConvenience.getIndexofColumn(source);
        return num;
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
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

                adContainer = (RelativeLayout) rootView.findViewById(R.id.adViewContainer);


            } catch (Exception e) {

                Log.e("Error on JSON Parsing", "onPostExecute: " + e);
            }

        }

    }


}
