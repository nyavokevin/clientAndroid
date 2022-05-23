package com.example.client.fragments;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
import com.example.client.models.CustomAdapter;
import com.example.client.utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    View view;
    String baseURL = GlobalProperties.getInstance().getBASE_URL();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        //les donnees recues
        String[] items = {"Coucou***", "texte kely", "texte hafa ara ity"};


//        ListView listView = (ListView)view.findViewById(R.id.listeCours);
//
//        //adapter pour listeView
//        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
//                getActivity(),
//                android.R.layout.simple_list_item_1,
//                items
//        );
//
//        listView.setAdapter(listViewAdapter);

        Button buttonCours = (Button) view.findViewById(R.id.coursButton);
        buttonCours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CoursListFragment fragment2 = new CoursListFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment2);
                fragmentTransaction.commit();
            }
        });

        Button buttonsearch = (Button) view.findViewById(R.id.buttonSearch);
        EditText searchEditText = (EditText) view.findViewById(R.id.editTextSearch);
//        searchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                searchEditText.err
//            }
//        });
        buttonsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        return  view;
    }

    public void search(){
        EditText searchEditText = (EditText) view.findViewById(R.id.editTextSearch);
        String searchText =  String.valueOf(searchEditText.getText());
        if(searchText.isEmpty()){
            searchEditText.setError("Ce formulaire ne doit pas etre vide");
        }else{
            VolleySingleton volleySingleton = VolleySingleton.getInstance(this.getActivity());
            try{
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,  baseURL+ "/search/cours",null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try{
                                    JSONArray ja = response.getJSONArray("transaction");
                                    List<String> list = new ArrayList<String>();
                                    for(int i = 0; i < ja.length();i++){
                                        JSONObject object = ja.getJSONObject(i);
                                        list.add(object.getString("nom"));
                                    }
                                    String responseString = ja.toString();
                                    if(ja.length() > 0) {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("key",responseString);
                                        SearchResultFragment fragment2 = new SearchResultFragment();
                                        fragment2.setArguments(bundle);

                                        getFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.frameLayout, fragment2)
                                                .commit();
                                    }
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
                    @Override
                    public byte[] getBody(){
                        JSONObject obj = new JSONObject();
                        try {
                            obj.put("reg",String.valueOf(searchEditText.getText()));
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
                volleySingleton.addToRequestQueue(request);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}