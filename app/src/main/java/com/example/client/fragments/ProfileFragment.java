package com.example.client.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.client.R;
import com.example.client.models.User;
import com.example.client.services.AuthService;

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    View view;
    SharedPreferences preferences ;
    User userConnected;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        preferences = this.getActivity().getSharedPreferences("auth", Context.MODE_PRIVATE);
        setUserInformation();

        //ADD listener LOUGOUT
        Button button = (Button) view.findViewById(R.id.todoB);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences.edit().clear().commit();
                HomeFragment fragment2 = new HomeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment2);
                fragmentTransaction.commit();
            }
        });
        return  view;
    }

    /**
     * Fonction pour avoir les informations de l'utilsateur connecter
     */
    private void setUserInformation(){
        String strJSON = preferences.getString("jsondata","");
        try{
            JSONObject tempRes = new JSONObject(strJSON);
            TextView name = (TextView) view.findViewById(R.id.textView2);
            name.setText(tempRes.get("firstname") + " " + tempRes.get("lastname"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }




}