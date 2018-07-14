package com.sololaunches.www.keralarailandmetro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TrainJourney extends AppCompatActivity {


    String trainNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_journey);


        ArrayList listRail = (ArrayList) getIntent().getSerializableExtra("listRail");
        trainNumber = (String) getIntent().getSerializableExtra("trainNumber");

        Button btn = (Button) findViewById(R.id.getCurrentStats);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), RetrieverLiveTrainStatus.class);
                i.putExtra("trainNumber", trainNumber);
                startActivity(i);


            }
        });


        ListAdapter trainListAdapter = new CustomListAdapter(this, android.R.layout.simple_list_item_1, listRail, "YELLOW");
        ListView trains = (ListView) findViewById(R.id.train_journey);

        trains.setAdapter(trainListAdapter);

    }

}
