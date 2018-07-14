package com.sololaunches.www.keralarailandmetro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MetroToAndFromActivity extends AppCompatActivity {

    private StartAppAd startAppAdInterstitial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartAppSDK.init(this, "203651630", false);
        startAppAdInterstitial = new StartAppAd(this);
        setContentView(R.layout.activity_metro_to_and_from);


        final ArrayList<String> listUp = (ArrayList) getIntent().getSerializableExtra("upList");
        final ArrayList<String> listDown = (ArrayList) getIntent().getSerializableExtra("dwList");

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm:ss");
        String datetime = dateformat.format(c.getTime());
        String current = datetime.substring(0, 2) + datetime.substring(3, 5);

        int up = 0;
        int down = 0;
        for (String s : listUp) {
            if (s.length() > 4) {
                String st = s.substring(0, 2) + s.substring(3, 6);
                if (Integer.parseInt(st.trim()) >= Integer.parseInt(current.trim())) {
                    up--;
                    up--;
                    break;
                }
                up++;
            }
        }

        for (String s : listDown) {
            if (s.length() > 4) {
                String st2 = s.substring(0, 2) + s.substring(3, 6);

                if (Integer.parseInt(st2.trim()) >= Integer.parseInt(current.trim())) {
                    down--;
                    down--;
                    break;
                }
                down++;
            }
        }

        ListAdapter trainListAdapter = new CustomListAdapterMetro(this, android.R.layout.simple_list_item_1, listUp, "UP");
        ListView metroUp = (ListView) findViewById(R.id.up_list);
        metroUp.setAdapter(trainListAdapter);
        metroUp.setSelection(up);

        ListAdapter metroListAdapter2 = new CustomListAdapterMetro(this, android.R.layout.simple_list_item_1, listDown, "DOWN");
        ListView metroDown = (ListView) findViewById(R.id.list_down);
        metroDown.setAdapter(metroListAdapter2);
        metroDown.setSelection(down);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startAppAdInterstitial.showAd();

      //  Intent intent = new Intent(this, MetroMain.class);
      //  startActivity(intent);
    }
}
