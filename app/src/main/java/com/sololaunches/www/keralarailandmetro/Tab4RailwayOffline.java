package com.sololaunches.www.keralarailandmetro;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.RelativeLayout;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class Tab4RailwayOffline extends Fragment {


    private static View rootView;
    RelativeLayout adContainer;

    public Tab4RailwayOffline() {

    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //  MobileAds.initialize(this.getContext(), "ca-app-pub-4406711663764042~3253627221");


        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);

        }
        try {

            rootView = inflater.inflate(R.layout.tab4_railway_online, container, false);


            ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                //we are connected to a network
                new Tab4RailwayOffline.DisplayAdMetro().execute();
            }


            ImageButton btn = (ImageButton) rootView.findViewById(R.id.pnr_status_button);
            btn.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), PnrStatus.class);
                    startActivity(intent);
                }
            });


            ImageButton live_train_stats = (ImageButton) rootView.findViewById(R.id.train_live_button);
            live_train_stats.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), RetrieverLiveTrainStatus.class);
                    startActivity(intent);
                }


            });


            ImageButton fare_report = (ImageButton) rootView.findViewById(R.id.fare_button);
            fare_report.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), RailwayFareActivity.class);
                    startActivity(intent);

                }
            });


            ImageButton seat_button = (ImageButton) rootView.findViewById(R.id.seat_button);
            seat_button.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), RailwaySeatAvailabilityActivity.class);
                    startActivity(intent);

                }
            });


            return rootView;


        } catch (
                InflateException e)

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

                //AdSettings.addTestDevice("0cfd0fa2-9810-4e17-a8ee-49893a9c90bb");
                adContainer = (RelativeLayout) rootView.findViewById(R.id.adViewContainer);

            } catch (Exception e) {

                Log.e("Error on JSON Parsing", "onPostExecute: " + e);
            }

        }

    }
}
