package com.sololaunches.www.keralarailandmetro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    TextView tv,tv2,tv3;

    Handler handler;
    boolean rail;
    boolean metro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        tv = (TextView)findViewById(R.id.startText);
        tv2 = (TextView)findViewById(R.id.startText2);
        tv3 = (TextView)findViewById(R.id.startText3);


        handler = new Handler();
        Runnable runnable = new Runnable() {
            private long startTime = System.currentTimeMillis();
            public void run() {
                for(int i=0;i<3;i++) {
                    try {
                        Thread.sleep(500);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable(){
                        public void run() {

                            if(metro){



                                tv3.setText("EMERGENCY CONTACTS");
                                /*
                                RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
                                        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                                params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
                                tv.setLayoutParams(params1);
*/
                                Intent intent = new Intent(getApplicationContext(), MainChoiceScreen.class);
                                startActivity(intent);


                            }
                            else if(rail){

                                /*
                                RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
                                        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                                params1.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                                tv.setLayoutParams(params1);
                                */

                                tv2.setText("KOCHI METRO");
                                metro = true;
                            }else{
                                tv.setText("RAILWAYS");
                                rail= true;
                            }



                        }
                    });
                }
            }
        };
        new Thread(runnable).start();


    }

}
