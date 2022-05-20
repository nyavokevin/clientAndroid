package com.example.client.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.client.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);

        SharedPreferences sr = getSharedPreferences("auth", MODE_PRIVATE);
        String firstname = sr.getString("firstname","");
        System.out.println(firstname);
    }
}