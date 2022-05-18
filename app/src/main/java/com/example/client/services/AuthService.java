package com.example.client.services;

import android.content.Context;
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

    public void loadRestaurantTest(Context context){
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseUrl+"/restaurant",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response",response.substring(0, 500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error Tag",error.getMessage());
            }
        });
        queue.add(stringRequest);
    }

    /**
     * Fonction login pour les utilisateurs
     * @param  context ( pour savoir l'activity ), instance d'un class User
     * @return String (Message)
     */
    public User functionLogin(Context context, User user){
        try{
            final User[] userConnected = {new User()};
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, baseUrl + "/login",null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try{
                                JSONObject ja = response.getJSONObject("user");
                                userConnected[0].setFirstname(ja.getString("fullname"));
                                userConnected[0].setLastname(ja.getString("fullname"));
                                userConnected[0].setEmail(ja.getString("email"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    userConnected[0] = null;
                }
            }){
                @Override
                public byte[] getBody(){
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("login",user.getEmail());
                        obj.put("password",user.getPassword());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return obj.toString().getBytes();
                }
                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(500000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            return userConnected[0];
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }

    public User callBackLogin(User user){
        return user;
    }

    /**
     * fonction inscription
     * @param context
     * @param user
     */
    public void functionRegister(Context context,User user){
        try{
            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest request = new StringRequest(Request.Method.POST, baseUrl + "/register",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                System.out.println(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        String responseBody = new String(error.networkResponse.data, "utf-8");
                        JSONObject jsonObject = new JSONObject(responseBody);
                        System.out.println(jsonObject);
                    } catch (UnsupportedEncodingException | JSONException e) {
                        e.printStackTrace();
                    }

                }
            }){
                @Override
                public byte[] getBody(){
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("firstname",user.getFirstname());
                        obj.put("lastname",user.getLastname());
                        obj.put("email",user.getEmail());
                        obj.put("role","624f57ffefa3956f3481e631");
                        obj.put("password",user.getPassword());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.println(obj);
                    return obj.toString().getBytes();
                }
                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };
            queue.add(request);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
