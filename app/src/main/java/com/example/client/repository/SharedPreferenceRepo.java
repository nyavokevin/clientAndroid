package com.example.client.repository;

import android.content.SharedPreferences;

import com.example.client.models.User;

public class SharedPreferenceRepo {

    private static  SharedPreferenceRepo sharedPreferenceRepo = null;

    /**
     * creation singleTon instance pour SharedPreferenceRepo
     * @return SharedPreferenceRepo
     */
    public static synchronized SharedPreferenceRepo getInstance(){
        if(null == sharedPreferenceRepo){
            sharedPreferenceRepo = new SharedPreferenceRepo();
        }
        return sharedPreferenceRepo;
    }


}
