package com.example.client;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.client.activities.HomeActivity;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;

public class GlobalProperties extends HomeActivity {
    private static GlobalProperties mGlobalProperty = null;
    private Channel channel;
    //ADD here all Global variable
    String BASE_URL = "http://192.168.16.102:5000/api";

    //ADD User static global

    /**
     * Constructor class global
     */
    protected GlobalProperties(){}

    /**
     * Configure et creation d'un singleton pour le Class GlobalProperties
     * @return GlobalProperties instance
     */
    public static synchronized GlobalProperties getInstance(){
        if(null == mGlobalProperty){
            mGlobalProperty = new GlobalProperties();
        }
        return mGlobalProperty;
    }

    public String getBASE_URL(){
        return this.BASE_URL;
    }

}
