package com.example.mygym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {
SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sp = getSharedPreferences(Constantsp.PREF,MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
             if(sp.getString(Constantsp.Remember,"").equalsIgnoreCase("")){
                 new common_behave(SplashActivity.this, MainActivity.class);

             }
                else {
                 new common_behave(SplashActivity.this, Dashboard_activity.class);

             }
            }
        },3000);

    }
}