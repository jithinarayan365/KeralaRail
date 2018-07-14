package com.sololaunches.www.keralarailandmetro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;



import java.util.ArrayList;

public class current_train_status extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_train_status);



        final ArrayList<String> list = (ArrayList<String>) getIntent().getSerializableExtra("collectedBeans");
        String stationNow = (String) getIntent().getSerializableExtra("stationNow");
        String line = (String) getIntent().getSerializableExtra("line1");
        int val = 0;

        for (String s : list) {

            if (s.contains(stationNow)) {
                val = val - 2;
                break;
            }
            val++;

        }

        ListAdapter trainListAdapter = new CustomListAdapter(this, android.R.layout.simple_list_item_1, list, stationNow);

        String[] strings = line.split("and");
        TextView line1 = (TextView) findViewById(R.id.line1);
        TextView line2 = (TextView) findViewById(R.id.line2);

        line1.setText(" " + strings[0]);
        line2.setText(" " + strings[1]);

        ListView trains = (ListView) findViewById(R.id.status_list);

        trains.setAdapter(trainListAdapter);
        trains.setSelection(val);

        //    trains.setAdapter(trainListAdapter);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

            Intent intent = new Intent(this, RetrieverLiveTrainStatus.class);
            startActivity(intent);

    }
}
