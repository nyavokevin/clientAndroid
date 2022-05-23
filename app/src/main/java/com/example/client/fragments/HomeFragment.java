package com.example.client.fragments;

import android.app.ActionBar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.client.GlobalProperties;
import com.example.client.R;
import com.example.client.models.Cours;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    View view;
    ArrayList<Cours> coursArrayList;
    GlobalProperties properties;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        //les donnees recues
        String[] items = {"Coucou***", "texte kely", "texte hafa ara ity"};


        //ListView listView = (ListView)view.findViewById(R.id.listeCours);

        //adapter pour listeView
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                items
        );

        //listView.setAdapter(listViewAdapter);

        //return  view;
   // }

  //  public void getCours() {
  //  coursArrayList = new ArrayList<>();
 //   String url = properties.getBASE_URL() + "/cours";
  //  }

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
    return view;
    }
}

