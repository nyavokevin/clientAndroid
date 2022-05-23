package com.example.client.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.client.R;
import com.example.client.fragments.HomeFragment;
import com.example.client.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;

public class HomeActivity extends AppCompatActivity {

    SharedPreferences settings;

    BottomNavigationItemView bottomNavigationItemView;

    boolean checkUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        Button homeFrag = (Button) findViewById(R.id.homeButton);
        Button profileFrag = (Button) findViewById(R.id.profileButton);

        settings = getSharedPreferences("auth",MODE_PRIVATE);

        checkUser = checkUserConnected(settings);

        homeFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new HomeFragment());
            }
        });

        profileFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkUser) {
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

    /**
     * fonction pour verifier si un user est deja dans le cache du telephone
     * @param sh
     * @return
     */
    public Boolean checkUserConnected(SharedPreferences sh){
        if(sh.contains("jsondata")){
            return  true;
        }else{
            return false;
        }
    }

}