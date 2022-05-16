package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.client.activities.HomeActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    public static final String userPreferenceLogged = "UserLogged" ;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //delete Top bar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        // Get SHAredPreference

        sharedpreferences = getSharedPreferences(userPreferenceLogged, Context.MODE_PRIVATE);

        /**
         * Timer pour faire un splashscreen, et lancer le Home Ou le Login activity
         */
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
                Intent loginPage = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(loginPage);
            }
        }, 5000);

    }


}