package com.example.client.fragments;

import android.app.ActionBar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.client.R;

public class HomeFragment extends Fragment {

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        //les donnees recues
        String[] items = {"Coucou***", "texte kely", "texte hafa ara ity"};


        ListView listView = (ListView)view.findViewById(R.id.listeCours);

        //adapter pour listeView
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                items
        );

        listView.setAdapter(listViewAdapter);

        return  view;
    }
}