package com.example.client.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoursDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoursDetailFragment extends Fragment {
    View view;
    VolleySingleton volleySingleton;
    String baseURL = GlobalProperties.getInstance().getBASE_URL();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CoursDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment CoursDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CoursDetailFragment newInstance(String param1) {
        CoursDetailFragment fragment = new CoursDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cours_detail, container, false);
    }

    // il faut encore passer le 'idCours' dans l'url
    public void getDetailsCours(){
        volleySingleton = VolleySingleton.getInstance(this.getActivity());
        try{
            String idCours = "id d'une cours";
            Log.d("LMAO", "TEST");
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,  baseURL+ "/lessons/cours/"+ idCours,null,
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