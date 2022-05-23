package com.example.client.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.client.R;
import com.example.client.models.CustomAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchResultFragment} factory method to
 * create an instance of this fragment.
 */
public class SearchResultFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String searchResult = getArguments().getString("key");
        view = inflater.inflate(R.layout.fragment_search_result, container, false);
        try {
            JSONObject jsonObject = new JSONObject(searchResult);
            JSONArray ja = jsonObject.getJSONArray("transaction");
            List<String> list = new ArrayList<String>();
            for(int i = 0; i < ja.length();i++){
                JSONObject object = ja.getJSONObject(i);
                list.add(object.getString("nom"));
            }
            ListView listView = view.findViewById(R.id.resultListView);
            CustomAdapter listAdapter = new CustomAdapter(list, getActivity());
            listView.setAdapter(listAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }
}