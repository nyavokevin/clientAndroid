package com.example.client.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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
import com.example.client.models.CustomAdapter;
import com.example.client.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 *
 * create an instance of this fragment.
 */
public class CoursDetailFragment extends Fragment {
    View view;
    VolleySingleton volleySingleton;
    String baseURL = GlobalProperties.getInstance().getBASE_URL();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cours_detail, container, false);
        getDetailsCours();
        Log.d("DETAILS FRAGMENT", "Tafiditra");
        return view;
    }

    public void getDetailsCours(){
        volleySingleton = VolleySingleton.getInstance(this.getActivity());
        try{
            String nom = getArguments().getString("nom");
            System.out.print("string:" +nom);
            Log.d("LMAO", "TEST");
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,  baseURL+ "/cours/details/"+nom,null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try{
                                JSONArray ja = response.getJSONArray("transaction");
                                List<String> list = new ArrayList<String>();
                                for(int i = 0; i < ja.length();i++){
                                    JSONObject object = ja.getJSONObject(i);
                                    list.add(object.getString("details"));
                                }
                                TextView textview = view.findViewById(R.id.detailsCours);
                                System.out.print(textview);
                                //CustomAdapter listAdapter = new CustomAdapter(list, getActivity());
                                //textview.setAdapter(listAdapter);
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
                            System.out.print("ERROR");
                            String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                            System.out.print(res);
                            JSONObject object = new JSONObject(res);
                        }catch (UnsupportedEncodingException e1){
                            e1.printStackTrace();
                        }catch (JSONException e2){
                            e2.printStackTrace();
                        }
                    }
                }
            });
            request.setRetryPolicy(new DefaultRetryPolicy(500000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            volleySingleton.addToRequestQueue(request);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}