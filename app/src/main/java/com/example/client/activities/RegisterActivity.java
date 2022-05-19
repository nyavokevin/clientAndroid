package com.example.client.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.client.GlobalProperties;
import com.example.client.R;
import com.example.client.models.User;
import com.example.client.repository.UserRepository;
import com.example.client.utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    public static String baseUrl = GlobalProperties.getInstance().getBASE_URL();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // ajouter des erreur de validation des inputText
        EditText firstNameEditText = (EditText)  findViewById(R.id.firstnameEditTextRegister);
        EditText lastNameEditText = (EditText) findViewById(R.id.lastnameEditRegister);
        EditText emailEditText = (EditText) findViewById(R.id.emailEditTextRegister);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditTextRegister);

        addValidatorEditText(firstNameEditText, "text");
        addValidatorEditText(lastNameEditText, "text");
        addValidatorEditText(emailEditText, "email");
        addValidatorEditText(passwordEditText, "text");

        Spinner spinnerAge=findViewById(R.id.spinner3);
        Spinner spinnerSexe = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.age, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerAge.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapterSexe = ArrayAdapter.createFromResource(this, R.array.sexe, R.layout.support_simple_spinner_dropdown_item);
        adapterSexe.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerSexe.setAdapter(adapter);
    }


    public void addValidatorEditText(EditText editText, String type){
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        if(type == "text"){
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(!b){
                        String passwordTemp =  String.valueOf(editText.getText());
                        if(passwordTemp.isEmpty()){
                            editText.setError("Valeur invalide");
                        }
                    }
                }
            });
        }
        if(type == "email"){
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(!b){
                        String emailTemp =  String.valueOf(editText.getText());
                        Matcher mat = pattern.matcher(emailTemp);
                        if(emailTemp.isEmpty() || !mat.matches()){
                            editText.setError("email pas correcte");
                        }
                    }
                }
            });
        }
    }

    public boolean validate(EditText test[]){
        boolean val = true;
        for(int i = 0; i < test.length ; i++){
            if(test[i].getError() != null || test[i].getText().length() == 0){
                Toast.makeText(getBaseContext(),"Veuillez remplir tous les formulaires", Toast.LENGTH_LONG).show();
                val = false;
                break;
            }
        }
        return val;
    }

    public void doRegister(View v){
        EditText firstNameEditText = (EditText)  findViewById(R.id.firstnameEditTextRegister);
        EditText lastNameEditText = (EditText) findViewById(R.id.lastnameEditRegister);
        EditText emailEditText = (EditText) findViewById(R.id.emailEditTextRegister);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditTextRegister);
        EditText arrayEditText[] = new EditText[] {firstNameEditText,lastNameEditText,emailEditText,passwordEditText};
        Spinner spinnerAge= (Spinner) findViewById(R.id.spinner3);
        Spinner spinnerSexe = (Spinner)  findViewById(R.id.spinner);
        try{
            VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
            if(validate(arrayEditText)) {
                try{
                    StringRequest request = new StringRequest(Request.Method.POST, baseUrl + "/register",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        Log.e("response", jsonObject.getString("user"));
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
                                Toast.makeText(getApplicationContext(), "Eror on register", Toast.LENGTH_SHORT).show();
                            } catch (UnsupportedEncodingException | JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }){
                        @Override
                        public byte[] getBody(){
                            JSONObject obj = new JSONObject();
                            try {
                                obj.put("firstname",String.valueOf(firstNameEditText.getText()));
                                obj.put("lastname",String.valueOf(lastNameEditText.getText()));
                                obj.put("email",String.valueOf(emailEditText.getText()));
                                obj.put("password",String.valueOf(passwordEditText.getText()));
                                obj.put("age",spinnerAge.getSelectedItem().toString());
                                obj.put("sexe",spinnerSexe.getSelectedItem().toString());
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
                    volleySingleton.addToRequestQueue(request);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}