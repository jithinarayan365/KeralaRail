package com.sololaunches.www.keralarailandmetro;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;


import com.startapp.android.publish.adsCommon.StartAppSDK;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class HelplineMain extends AppCompatActivity {


    private static final String[] districts = {"Select District", "Trichur", "Cannore", "Kozhikode", "Malapurram", "Kasargod", "Wayanad", "Ernakulam", "Palakkad", "Thiruvananthapuram", "Kollam", "Alappuzha", "Pathanamthita", "Kottayam", "Idukki", ""};
    private Spinner spinner = null;

    LinearLayout newLayout;
    LinearLayout newLayout2;
    LinearLayout newLayout3;
    LinearLayout newLayout4;
    LinearLayout newLayout5;
    LinearLayout newLayout6;
    LinearLayout newLayout7;
    boolean flag1;
    boolean unset;
    HashMap<String, String[]> phoneNumber;
    RelativeLayout adContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartAppSDK.init(this, "203651630", false);

        setContentView(R.layout.activity_helpline_main);

        //     AdSettings.addTestDevice("36f984da-8af6-4d84-b8e8-ec99bfd0f10c");
        adContainer = (RelativeLayout) findViewById(R.id.adViewContainer);


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            new HelplineMain.DisplayAdContacts().execute();
        }

        spinner = (Spinner) findViewById(R.id.district);

        AtomicReference<ArrayAdapter<String>> adapter = new AtomicReference<ArrayAdapter<String>>();
        adapter.set(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, districts));
        adapter.get().setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter.get());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

                PhoneNumberGetter phoneNumberGetter = new PhoneNumberGetter();
                String district = null;
                phoneNumber = null;
                String dis = (String) spinner.getSelectedItem();

                phoneNumber = phoneNumberGetter.getPhoneNumber(dis);
                setNewLayoutMechanism(phoneNumber, v);

                if (newLayout != null) {
                    newLayout.removeAllViews();
                    newLayout.removeAllViews();
                    newLayout2.removeAllViews();
                    newLayout3.removeAllViews();
                    newLayout4.removeAllViews();
                    newLayout5.removeAllViews();
                    newLayout6.removeAllViews();
                    newLayout7.removeAllViews();

                    newLayout = (LinearLayout) findViewById(R.id.place_holder_buttons);
                    newLayout2 = (LinearLayout) findViewById(R.id.rail_police_list);
                    newLayout3 = (LinearLayout) findViewById(R.id.blood_list);
                    newLayout4 = (LinearLayout) findViewById(R.id.fire_list);
                    newLayout5 = (LinearLayout) findViewById(R.id.hospital_list);
                    newLayout6 = (LinearLayout) findViewById(R.id.disaster_list);
                    newLayout7 = (LinearLayout) findViewById(R.id.women_list);
                    setNewLayoutMechanism(phoneNumber, v);
                } else {
                    flag1 = true;
                    district = (String) spinner.getSelectedItem().toString();
                    phoneNumber = phoneNumberGetter.getPhoneNumber(district);
                    setNewLayoutMechanism(phoneNumber, v);

                    newLayout = (LinearLayout) findViewById(R.id.place_holder_buttons);
                    newLayout2 = (LinearLayout) findViewById(R.id.rail_police_list);
                    newLayout3 = (LinearLayout) findViewById(R.id.blood_list);
                    newLayout4 = (LinearLayout) findViewById(R.id.fire_list);
                    newLayout5 = (LinearLayout) findViewById(R.id.hospital_list);
                    newLayout6 = (LinearLayout) findViewById(R.id.disaster_list);
                    newLayout7 = (LinearLayout) findViewById(R.id.women_list);

                    setNewLayoutMechanism(phoneNumber, v);


                }

            }

        });
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        PhoneNumberGetter phoneNumberGetter = new PhoneNumberGetter();
        String district = null;
        phoneNumber = null;
        String dis = (String) spinner.getSelectedItem();

        phoneNumber = phoneNumberGetter.getPhoneNumber(dis);
        setNewLayoutMechanism(phoneNumber, v);

        if (newLayout != null) {
            newLayout.removeAllViews();
            newLayout.removeAllViews();
            newLayout2.removeAllViews();
            newLayout3.removeAllViews();
            newLayout4.removeAllViews();
            newLayout5.removeAllViews();
            newLayout6.removeAllViews();
            newLayout7.removeAllViews();

            newLayout = (LinearLayout) findViewById(R.id.place_holder_buttons);
            newLayout2 = (LinearLayout) findViewById(R.id.rail_police_list);
            newLayout3 = (LinearLayout) findViewById(R.id.blood_list);
            newLayout4 = (LinearLayout) findViewById(R.id.fire_list);
            newLayout5 = (LinearLayout) findViewById(R.id.hospital_list);
            newLayout6 = (LinearLayout) findViewById(R.id.disaster_list);
            newLayout7 = (LinearLayout) findViewById(R.id.women_list);
            setNewLayoutMechanism(phoneNumber, v);
        } else {
            flag1 = true;
            district = (String) spinner.getSelectedItem().toString();
            phoneNumber = phoneNumberGetter.getPhoneNumber(district);
            setNewLayoutMechanism(phoneNumber, v);

            newLayout = (LinearLayout) findViewById(R.id.place_holder_buttons);
            newLayout2 = (LinearLayout) findViewById(R.id.rail_police_list);
            newLayout3 = (LinearLayout) findViewById(R.id.blood_list);
            newLayout4 = (LinearLayout) findViewById(R.id.fire_list);
            newLayout5 = (LinearLayout) findViewById(R.id.hospital_list);
            newLayout6 = (LinearLayout) findViewById(R.id.disaster_list);
            newLayout7 = (LinearLayout) findViewById(R.id.women_list);

            setNewLayoutMechanism(phoneNumber, v);


        }

/*

        if (parent.getId() == R.id.police) {
            if (newLayout != null) {
                Log.d("police unset", "onItemSelected: " + R.id.police);

                flag1 = true;
                newLayout.removeAllViews();
                district = (String) spinner.getSelectedItem().toString();
                newLayout = (LinearLayout) getActivity().findViewById(R.id.place_holder_buttons);
                setNewLayoutMechanism(phoneNumber, v);


            } else {
                Log.d("police unset", "onItemSelected: " + R.id.police);

                flag1 = true;
                district = (String) spinner.getSelectedItem().toString();
                phoneNumber = phoneNumberGetter.getPhoneNumber(district, "POLICE");
            }


        } else if (parent.getId() == R.id.hospital) {
            if (newLayout != null) {
                flag7 = true;
                newLayout.removeAllViews();
                district = (String) spinner7.getSelectedItem().toString();
                phoneNumber = phoneNumberGetter.getPhoneNumber(district, "HOSPITAL");
                setNewLayoutMechanism(phoneNumber, v);

            } else {
                // Unset();
                flag7 = true;
                district = (String) spinner7.getSelectedItem().toString();
                phoneNumber = phoneNumberGetter.getPhoneNumber(district, "HOSPITAL");
                newLayout = (LinearLayout) getActivity().findViewById(R.id.hospital_list);
            }
        } else if (parent.getId() == R.id.rail_police_spinner) {

            if (newLayout != null) {
                Log.d("police unset", "onItemSelected: " + R.id.rail_police_spinner);

                flag2 = true;
                newLayout.removeAllViews();
                district = spinner2.getSelectedItem().toString();
                phoneNumber = phoneNumberGetter.getPhoneNumber(district, "RAIL_POLICE");
                newLayout = (LinearLayout) getActivity().findViewById(R.id.rail_police_list);
                setNewLayoutMechanism(phoneNumber, v);

            } else {
                Log.d("police unset", "onItemSelected: " + R.id.rail_police_spinner);

                flag2 = true;
                district = spinner2.getSelectedItem().toString();
                phoneNumber = phoneNumberGetter.getPhoneNumber(district, "RAIL_POLICE");
                newLayout = (LinearLayout) getActivity().findViewById(R.id.rail_police_list);
                setNewLayoutMechanism(phoneNumber, v);

            }

        } else if (parent.getId() == R.id.spinner_blood_bank) {

            if (newLayout != null) {
                flag3 = true;
                newLayout.removeAllViews();
                district = (String) spinner3.getSelectedItem().toString();
                phoneNumber = phoneNumberGetter.getPhoneNumber(district, "BLOOD_BANK");
                newLayout = (LinearLayout) getActivity().findViewById(R.id.blood_list);
                setNewLayoutMechanism(phoneNumber, v);

            } else {
                //  Unset();
                flag3 = true;
                district = (String) spinner3.getSelectedItem().toString();
                phoneNumber = phoneNumberGetter.getPhoneNumber(district, "BLOOD_BANK");
                newLayout = (LinearLayout) getActivity().findViewById(R.id.blood_list);
                setNewLayoutMechanism(phoneNumber, v);
            }
        } else if (parent.getId() == R.id.fire_spinner) {

            if (newLayout != null) {
                flag4 = true;
                newLayout.removeAllViews();
                district = (String) spinner4.getSelectedItem().toString();
                phoneNumber = phoneNumberGetter.getPhoneNumber(district, "FIRE");
                newLayout = (LinearLayout) getActivity().findViewById(R.id.fire_list);
                setNewLayoutMechanism(phoneNumber, v);
            } else {
                flag4 = true;
                district = (String) spinner4.getSelectedItem().toString();
                phoneNumber = phoneNumberGetter.getPhoneNumber(district, "FIRE");
                newLayout = (LinearLayout) getActivity().findViewById(R.id.fire_list);
                setNewLayoutMechanism(phoneNumber, v);

            }
        } else if (parent.getId() == R.id.disaster_management_spinner) {

            if (newLayout != null) {
                flag5 = true;
                newLayout.removeAllViews();
                district = (String) spinner5.getSelectedItem().toString();
                phoneNumber = phoneNumberGetter.getPhoneNumber(district, "DISASTER");
                newLayout = (LinearLayout) getActivity().findViewById(R.id.disaster_list);
                setNewLayoutMechanism(phoneNumber, v);

            } else {
                flag5 = true;
                district = (String) spinner5.getSelectedItem().toString();
                phoneNumber = phoneNumberGetter.getPhoneNumber(district, "DISASTER");
                newLayout = (LinearLayout) getActivity().findViewById(R.id.disaster_list);
                setNewLayoutMechanism(phoneNumber, v);


            }

        } else if (parent.getId() == R.id.womens_cell_spinner) {

            if (newLayout != null) {
                flag6 = true;
                newLayout.removeAllViews();
                district = spinner6.getSelectedItem().toString();
                phoneNumber = phoneNumberGetter.getPhoneNumber(district, "WOMEN");
                newLayout = (LinearLayout) getActivity().findViewById(R.id.women_list);
                setNewLayoutMechanism(phoneNumber, v);
            } else {
                flag6 = true;
                district = spinner6.getSelectedItem().toString();
                phoneNumber = phoneNumberGetter.getPhoneNumber(district, "WOMEN");
                newLayout = (LinearLayout) getActivity().findViewById(R.id.women_list);
                setNewLayoutMechanism(phoneNumber, v);
            }


        }


/*
        switch (parent.getId()) {
            case R.id.police:
                if (newLayout != null) {
                    Log.d("police unset", "onItemSelected: ");

                    Unset();
                    flag1 = true;
                    newLayout.removeAllViews();
                    district = (String) spinner.getSelectedItem().toString();
                    phoneNumber = phoneNumberGetter.getPhoneNumber(district, "POLICE");
                    newLayout = (LinearLayout) getActivity().findViewById(R.id.place_holder_buttons);
                    setNewLayoutMechanism(phoneNumber, v);
                    break;

                } else {
                    Log.d("police unset", "onItemSelected: ");
                    Unset();
                    flag1 = true;
                    district = (String) spinner.getSelectedItem().toString();
                    phoneNumber = phoneNumberGetter.getPhoneNumber(district, "POLICE");
                    setNewLayoutMechanism(phoneNumber, v);
                    break;
                }


            case R.id.hospital:
                if (newLayout != null) {
                   // Unset();
                    flag7 = true;
                    newLayout.removeAllViews();
                    district = (String) spinner7.getSelectedItem().toString();
                    phoneNumber = phoneNumberGetter.getPhoneNumber(district, "HOSPITAL");
                    newLayout = (LinearLayout) getActivity().findViewById(R.id.hospital_list);
                    setNewLayoutMechanism(phoneNumber, v);
                    break;

                } else {
                   // Unset();
                    flag7 = true;
                    district = (String) spinner7.getSelectedItem().toString();
                    phoneNumber = phoneNumberGetter.getPhoneNumber(district, "HOSPITAL");
                    newLayout = (LinearLayout) getActivity().findViewById(R.id.hospital_list);
                    setNewLayoutMechanism(phoneNumber, v);
                    break;
                }

            case R.id.rail_police_spinner:

                if (newLayout != null) {
                    Log.d("rail unset", "onItemSelected: ");
                    Unset();
                    flag2 = true;
                    newLayout.removeAllViews();
                    district = spinner2.getSelectedItem().toString();
                    phoneNumber = phoneNumberGetter.getPhoneNumber(district, "RAIL_POLICE");
                    newLayout = (LinearLayout) getActivity().findViewById(R.id.rail_police_list);
                    setNewLayoutMechanism(phoneNumber, v);
                    break;
                } else {
                    Log.d("rail unset", "onItemSelected: ");

                    Unset();
                    flag2 = true;
                    district = spinner2.getSelectedItem().toString();
                    phoneNumber = phoneNumberGetter.getPhoneNumber(district, "RAIL_POLICE");
                    newLayout = (LinearLayout) getActivity().findViewById(R.id.rail_police_list);
                    setNewLayoutMechanism(phoneNumber, v);
                    break;
                }

            case R.id.spinner_blood_bank:

                if (newLayout != null) {
                  //  Unset();
                    flag3 = true;
                    newLayout.removeAllViews();
                    district = (String) spinner3.getSelectedItem().toString();
                    phoneNumber = phoneNumberGetter.getPhoneNumber(district, "BLOOD_BANK");
                    newLayout = (LinearLayout) getActivity().findViewById(R.id.blood_list);
                    setNewLayoutMechanism(phoneNumber, v);
                    break;
                } else {
                  //  Unset();
                    flag3 = true;
                    district = (String) spinner3.getSelectedItem().toString();
                    phoneNumber = phoneNumberGetter.getPhoneNumber(district, "BLOOD_BANK");
                    newLayout = (LinearLayout) getActivity().findViewById(R.id.blood_list);
                    setNewLayoutMechanism(phoneNumber, v);
                    break;
                }

            case R.id.fire_spinner:


                if (newLayout != null) {
                    flag4 = true;
                    newLayout.removeAllViews();
                    district = (String) spinner4.getSelectedItem().toString();
                    phoneNumber = phoneNumberGetter.getPhoneNumber(district, "FIRE");
                    newLayout = (LinearLayout) getActivity().findViewById(R.id.fire_list);
                    setNewLayoutMechanism(phoneNumber, v);
                    break;
                } else {
                    flag4 = true;
                    district = (String) spinner4.getSelectedItem().toString();
                    phoneNumber = phoneNumberGetter.getPhoneNumber(district, "FIRE");
                    newLayout = (LinearLayout) getActivity().findViewById(R.id.fire_list);
                    setNewLayoutMechanism(phoneNumber, v);
                    break;
                }

            case R.id.disaster_management_spinner:
                if (newLayout != null) {
                    flag5 = true;
                    newLayout.removeAllViews();
                    district = (String) spinner5.getSelectedItem().toString();
                    phoneNumber = phoneNumberGetter.getPhoneNumber(district, "DISASTER");
                    newLayout = (LinearLayout) getActivity().findViewById(R.id.disaster_list);
                    setNewLayoutMechanism(phoneNumber, v);
                    break;
                } else {
                    flag5 = true;
                    district = (String) spinner5.getSelectedItem().toString();
                    phoneNumber = phoneNumberGetter.getPhoneNumber(district, "DISASTER");
                    newLayout = (LinearLayout) getActivity().findViewById(R.id.disaster_list);
                    setNewLayoutMechanism(phoneNumber, v);
                    break;

                }


            case R.id.womens_cell_spinner:

                if (newLayout != null) {
                    flag6 = true;
                    newLayout.removeAllViews();
                    district = spinner6.getSelectedItem().toString();
                    phoneNumber = phoneNumberGetter.getPhoneNumber(district, "WOMEN");
                    newLayout = (LinearLayout) getActivity().findViewById(R.id.women_list);
                    setNewLayoutMechanism(phoneNumber, v);
                    break;
                } else {
                    flag6 = true;
                    district = spinner6.getSelectedItem().toString();
                    phoneNumber = phoneNumberGetter.getPhoneNumber(district, "WOMEN");
                    newLayout = (LinearLayout) getActivity().findViewById(R.id.women_list);
                    setNewLayoutMechanism(phoneNumber, v);
                    break;
                }

        }
        */

    }


    private void dialContactPhone(String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }


    private void setNewLayoutMechanism(HashMap<String, String[]> phoneNumbers, View v) {
        Button btn1 = new Button(getApplicationContext());
        Button btn2 = new Button(getApplicationContext());
        Button btn3 = new Button(getApplicationContext());
        Button[] btn = new Button[]{btn1, btn2, btn3};

        int i = 0;
        if (phoneNumbers != null) {
            for (String key : phoneNumbers.keySet()) {

                if ("POLICE".equals(key)) {
                    LinearLayout newLayout = (LinearLayout) findViewById(R.id.place_holder_buttons);
                }


                String[] arr = phoneNumbers.get(key);
                for (String str : arr) {

                    LinearLayout ll = new LinearLayout(getApplicationContext());
                    ll.setOrientation(LinearLayout.HORIZONTAL);
                    ll.setPadding(100, 0, 0, 0);

                    Button button = new Button(getApplicationContext());
                    button.setId(i + 1);
                    button.setText(str);
                    button.setTextSize(12);
                    button.setGravity(Gravity.CENTER);
                    button.setLayoutParams(new ViewGroup.LayoutParams(450, 125));
                    button.setOnClickListener(btnCLick);
                    ll.addView(button);

                    ImageButton imgBtn = new ImageButton(getApplicationContext());


                    imgBtn.setImageResource(R.drawable.call);

                    imgBtn.setLayoutParams(new ViewGroup.LayoutParams(100, 125));
                    imgBtn.setId(i + 1);
                    imgBtn.setTag(str);
                    imgBtn.setOnClickListener(imgBtnCLick);
                    ll.addView(imgBtn);


                    if ("POLICE".equals(key)) {
                        newLayout.addView(ll, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    } else if ("RAIL_POLICE".equals(key)) {
                        newLayout2.addView(ll, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    } else if ("BLOOD_BANK".equals(key)) {
                        newLayout3.addView(ll, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    } else if ("FIRE".equals(key)) {
                        newLayout4.addView(ll, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    } else if ("HOSPITAL".equals(key)) {
                        newLayout5.addView(ll, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    } else if ("DISASTER".equals(key)) {
                        newLayout6.addView(ll, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    } else if ("WOMEN".equals(key)) {
                        newLayout7.addView(ll, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    }

                    i++;

                }
            }

        }


    }

    View.OnClickListener btnCLick = (new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Button btn = (Button) v;
            String number = (String) btn.getText();
            String formatd = PhoneNumberUtils.formatNumber(number);
            dialContactPhone(formatd);

        }
    });


    View.OnClickListener imgBtnCLick = (new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            ImageButton btn = (ImageButton) v;
            String number = (String) btn.getTag();
            String formatd = PhoneNumberUtils.formatNumber(number);
            dialContactPhone(formatd);
        }
    });


    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    public class DisplayAdContacts extends AsyncTask<String, String, String> {


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
                adContainer = (RelativeLayout) findViewById(R.id.adViewContainer);


            } catch (Exception e) {

                Log.e("Error on JSON Parsing", "onPostExecute: " + e);
            }


        }

    }

}
