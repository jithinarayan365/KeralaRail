package com.sololaunches.www.keralarailandmetro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MainChoiceScreen extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_choice_screen);
        getSupportActionBar().hide(); //<< this

        ImageButton metro =  (ImageButton)findViewById(R.id.metro_option);
        ImageButton rail =  (ImageButton)findViewById(R.id.rail_option);
        ImageButton helpline =  (ImageButton)findViewById(R.id.helpline_option);
        ImageButton share_button =  (ImageButton)findViewById(R.id.share_button);

        share_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String sub = "Kerala Rail and Metro V 3.0 ";
                String body = " get all Railway timetables/Train schedules & Metro timetables offline with the App.Search for 'Kerala Rail and Metro' on Playstore or Click on link : " + "http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName();
                intent.putExtra(Intent.EXTRA_SUBJECT,sub);
                intent.putExtra(Intent.EXTRA_TEXT,body);
                startActivity(Intent.createChooser(intent," Send the APP's link to your friends "));
            }
        });



        metro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), MetroMain.class);
                startActivity(intent1);            }
        });

        rail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), OpeningScreenTabs.class);
                startActivity(intent1);              }
        });

        helpline.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), HelplineMain.class);
                startActivity(intent1);                 }
        });
    }
}
