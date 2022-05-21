package com.example.client.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.client.MainActivity;
import com.example.client.R;
import com.example.client.fragments.HomeFragment;
import com.example.client.fragments.ProfileFragment;
import com.example.client.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class HomeActivity extends AppCompatActivity {

    SharedPreferences settings;

    BottomNavigationItemView bottomNavigationItemView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        Button homeFrag = (Button) findViewById(R.id.homeButton);
        Button profileFrag = (Button) findViewById(R.id.profileButton);

        SharedPreferences sh = getSharedPreferences("auth",MODE_PRIVATE);
        System.out.println("SHARED");
        System.out.println(sh.getString("firstname",""));

        homeFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new HomeFragment());
            }
        });

        profileFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sh.contains("jsondata")) {
                    replaceFragment(new ProfileFragment());
                }else{
                    Intent loginPage = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(loginPage);
                }
            }
        });

    }

    public void replaceFragment(Fragment frag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, frag);
        fragmentTransaction.commit();
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

    public void redirectProfile(View v){
        try{
            Intent redirectProfile = new Intent(this, ProfileActivity.class);
            startActivity(redirectProfile);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}