package com.sololaunches.www.keralarailandmetro;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class train_list extends AppCompatActivity {

    RailwayDBAdapter railwayDBAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_list);
        railwayDBAdapter = new RailwayDBAdapter(this, null, null, 1);

        final ArrayList list = (ArrayList) getIntent().getSerializableExtra("dest");
        ListAdapter trainListAdapter = new CustomListAdapter(this, android.R.layout.simple_list_item_1, list, "WHITE");
        ListView trains = (ListView) findViewById(R.id.train_status_list);
        trains.setCacheColorHint(Color.TRANSPARENT);
        trains.setAdapter(trainListAdapter);


        trains.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if(position%2==0){
                    position =  position-1;
                }


                String number = (String) list.get(position);
                String trainNumber = number.substring(9, 14);
                ArrayList<String> listRail = new ArrayList<String>();
                Cursor cur = railwayDBAdapter.getTrainJourney(trainNumber);

                if (cur.getCount() == 0) {
                    return;
                }


                while (cur.moveToNext()) {

                    for (int n = 4; n <= 100; n++) {
                        String addition = cur.getString(n);
                        if (addition != null) {
                            String[] value = addition.split("-");
                            String timing = value[1];
                            String dayWeek = value[2];
                            String formatdTiming = timing.substring(0, 5);
                            String stn = value[0];

                            String getCompleteStn = ArrayHolder.getCompleteStation(stn);

                            if (addition == null) {

                                continue;
                            } else if (addition != null) {


                                String trainStn = String.format("%-30s", getCompleteStn);
                                listRail.add(formatdTiming + "  " + trainStn);
                                continue;
                            }
                        } else if (addition == null) {

                            break;

                        }
                    }
                }
                Intent i = new Intent(getApplicationContext(), TrainJourney.class);
                ArrayList<String> listFormatted = KeralaRailConvenience.setTrainOrderForTrain(listRail);
                i.putExtra("listRail", listFormatted);
                startActivity(i);

            }
        });


    }
}
