package com.example.client.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.client.GlobalProperties;
import com.example.client.R;
import com.example.client.activities.HomeActivity;
import com.example.client.services.AuthService;
import com.example.client.utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoireFragment} factory method to
 * create an instance of this fragment.
 */
public class HistoireFragment extends Fragment {

    VolleySingleton volleySingleton;
    String baseURL = GlobalProperties.getInstance().getBASE_URL();
    SharedPreferences preferences ;
    AuthService authService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        preferences = this.getActivity().getSharedPreferences("auth", Context.MODE_PRIVATE);
        Integer user_id = preferences.getInt("id",0);
        if(user_id != 0){
            getAllHistory(user_id);
        }

        return inflater.inflate(R.layout.fragment_histoire, container, false);
    }

    public void getAllHistory(Integer user_id){
        volleySingleton = VolleySingleton.getInstance(this.getActivity());

        try{
            Integer id = user_id;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,  baseURL+ "/livres/users",null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try{
                                JSONObject ja = response.getJSONObject("user");
                                System.out.println(ja);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    NetworkResponse response = error.networkResponse;
                    if(error instanceof ServerError && response != null) {
                        try {
                            String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                            JSONObject object = new JSONObject(res);
                        }catch (UnsupportedEncodingException e1){
                            e1.printStackTrace();
                        }catch (JSONException e2){
                            e2.printStackTrace();
                        }
                    }
                }
            }){
                protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id",id.toString());
                    return params;
                };
                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(500000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            volleySingleton.addToRequestQueue(request);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}