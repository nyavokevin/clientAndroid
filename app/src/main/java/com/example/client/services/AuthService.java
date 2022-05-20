package com.example.client.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.client.GlobalProperties;
import com.example.client.models.User;
import com.example.client.utils.ServerCallback;
import com.example.client.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class AuthService {
    String baseUrl = GlobalProperties.getInstance().getBASE_URL();

    Context context;
    private static AuthService authService = null;

    public AuthService(Context context) {
        this.context = context;
    }

    /**
     * creation singleTon instance pour AuthService
     * @return
     */
    public static synchronized AuthService getInstance(Context context){
        if(null == authService){
            authService = new AuthService(context);
        }
        return authService;
    }


    public void saveUser(String firstname, String lastname, String email, String age, String sexe){
        try{

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
