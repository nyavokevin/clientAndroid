package com.example.client;

public class GlobalProperties {
    private static GlobalProperties mGlobalProperty = null;

    //ADD here all Global variable
    //String BASE_URL = "http://192.168.16.102:5000/api";
    String BASE_URL = "http://192.168.1.144:5000/api";


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
