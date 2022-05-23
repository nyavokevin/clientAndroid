package com.example.client.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
 * Use the {@link CoursListFragment} factory method to
 * create an instance of this fragment.
 */
public class CoursListFragment extends Fragment {
    View view;
    VolleySingleton volleySingleton;
    String baseURL = GlobalProperties.getInstance().getBASE_URL();
    SharedPreferences preferences ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cours_list, container, false);
        preferences = this.getActivity().getSharedPreferences("auth", Context.MODE_PRIVATE);
        addAllCourse();
        ListView listView = view.findViewById(R.id.listViewCours);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CoursListFragment fragment2 = new CoursListFragment();
                FragmentManager fragmentManager = getFragmentManager();
                Bundle bundle = new Bundle();

                String selected = ge;
                bundle.putString("param1", a);
                fragment2.setArgument(bundle);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment2);
                fragmentTransaction.commit();
            }
        });
        return view;
    }
    public void addAllCourse(){
        volleySingleton = VolleySingleton.getInstance(this.getActivity());
        try{
            Log.d("LMAO", "TEST");
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,  baseURL+ "/cours",null,
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
                                ListView listView = view.findViewById(R.id.listViewCours);
                                CustomAdapter listAdapter = new CustomAdapter(list, getActivity());
                                listView.setAdapter(listAdapter);
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