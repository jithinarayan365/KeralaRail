package com.sololaunches.www.keralarailandmetro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AutoCompleteTextView;


public class RailwayMenu extends AppCompatActivity {

    RailwayDBAdapter railwayDBAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_railway_menu);

        railwayDBAdapter = new RailwayDBAdapter(this, null, null, 1);

        String[] stationArray = ArrayHolder.getsuggestStation();


        //destinationText.setAdapter(new ArrayAdapter<String>(this, R.layout.list2_detail, stationArray));
        // sourceText.setAdapter(new ArrayAdapter<String>(this, R.layout.list2_detail, stationArray));
    }


    public String getDestinationValue() {
        AutoCompleteTextView destinationText = (AutoCompleteTextView) findViewById(R.id.auto_destination);
        String dest = destinationText.getText().toString();
        return dest;
    }

    public String getSourceValue() {
        AutoCompleteTextView destinationText = (AutoCompleteTextView) findViewById(R.id.source);
        String source = destinationText.getText().toString();
        return source;
    }

    public static int getIndex(String source) {
        int num = KeralaRailConvenience.getIndexofColumn(source);
        return num;
    }
}
