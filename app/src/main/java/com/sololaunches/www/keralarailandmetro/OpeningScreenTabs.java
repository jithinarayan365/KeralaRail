package com.sololaunches.www.keralarailandmetro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.startapp.android.publish.adsCommon.StartAppSDK;

public class OpeningScreenTabs extends AppCompatActivity {


    RailwayDBAdapter railwayDBAdapter;


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainChoiceScreen.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  StartAppSDK.init(this, "203651630", false);

        setContentView(R.layout.activity_kerala_rail_metro_main_screen);

        /*
        SharedPreferences prefs = getSharedPreferences("RETURN_MODE", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("id");
        editor.clear();
        editor.apply();
        */

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.diesel_yellow);
        tabLayout.getTabAt(1).setIcon(R.drawable.diesel_green);
        // tabLayout.getTabAt(2).setIcon(R.drawable.kochi_metro);
        //  tabLayout.getTabAt(3).setIcon(R.drawable.call_logo);

        railwayDBAdapter = new RailwayDBAdapter(this, null, null, 1);

    }


    @Override
    protected void onResume() {
        super.onResume();

        /*
        editor.putInt("id", 2);
        editor.apply();
        */
        Log.d("CHECK Shared", "onResume: ");

        SharedPreferences prefs = getSharedPreferences("RETURN_MODE", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        int idName = prefs.getInt("id", 0); //0 is the default value.
        editor.remove("id");
        editor.clear();
        editor.apply();
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        TabLayout.Tab tab = tabLayout.getTabAt(idName);
        tab.select();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_opening_screen_tabs, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        if (id == R.id.action_settings2) {


            Intent intent = new Intent(this, DeveloperInfoActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_settings3) {


            Intent intent = new Intent(this, UpdateScreen.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.rate_action) {


            Intent intent = new Intent(this, RatingActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    Tab1Railways tab1 = new Tab1Railways();
                    return tab1;
                case 1:
                    Tab4RailwayOffline tab2 = new Tab4RailwayOffline();
                    return tab2;


                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "OFFLINE Schedule";
                case 1:
                    return "LIVE STATS, PNR, FARE";
                /*
                case 2:
                    return "Metro";
                case 3:
                    return "Helpline";
                    */
            }
            return null;
        }
    }
}
