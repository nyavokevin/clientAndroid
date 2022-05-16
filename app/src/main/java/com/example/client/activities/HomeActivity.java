package com.example.client.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.client.MainActivity;
import com.example.client.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }


    /**
     * fonction redirection à la page login
     */
    public void redirectToLogin(View v){
        try {
            Intent loginPage = new Intent(this, LoginActivity.class);
            startActivity(loginPage);
        }catch (Exception e){
            Log.e("Activity exception",e.getMessage());
        }
    }

    public void redirectToRegister(View v){
        try{
            Intent redirectpage = new Intent(this, RegisterActivity.class);
            startActivity(redirectpage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}