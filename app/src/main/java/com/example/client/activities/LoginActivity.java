package com.example.client.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.example.client.models.User;
import com.example.client.repository.UserRepository;
import com.example.client.services.AuthService;
import com.example.client.utils.VolleySingleton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    public static final String KEY_CONNECTIONS = "KEY_CONNECTIONS";
    private static Boolean canLog = false;
    private String email;
    private String password;
    private static String baseURL = GlobalProperties.getInstance().getBASE_URL();
    public static Context context = null;
    AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        Button logButton = (Button) findViewById(R.id.buttonLogin);
        TextView errorText = (TextView) findViewById(R.id.errorText);
        authService = AuthService.getInstance(this);
        /**
         * Validator email
         */
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");

        EditText emailTextInput = (EditText)  findViewById(R.id.emailInput);
        emailTextInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    String emailTemp =  String.valueOf(emailTextInput.getText());
                    Matcher mat = pattern.matcher(emailTemp);
                    if(emailTemp.isEmpty() || !mat.matches()){
                        emailTextInput.setError("email pas correcte");
                    }else{
                        email = emailTemp;
                    }
                }else{
                    errorText.setVisibility(View.INVISIBLE);
                }
            }
        });

        /**
         * Validator password
         */

        EditText passwordTextInput = (EditText) findViewById(R.id.passwordInput);
        passwordTextInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    String passwordTemp =  String.valueOf(passwordTextInput.getText());
                    if(passwordTemp.isEmpty()){
                        passwordTextInput.setError("mot de passe pas correcte");
                    }else{
                        password = passwordTemp;
                    }
                }else{
                    errorText.setVisibility(View.INVISIBLE);
                }
            }
        });

        context = getApplicationContext();
    }

    private boolean validate(){
        EditText emailTextInput = (EditText)  findViewById(R.id.emailInput);
        EditText passwordTextInput = (EditText) findViewById(R.id.passwordInput);
        if(emailTextInput.getError()== null && passwordTextInput.getError()==null){
            if(emailTextInput.getText().length() != 0 && passwordTextInput.getText().length() != 0){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public void login(View v){
        try {
            if(validate()){
                EditText emailTextInput = (EditText)  findViewById(R.id.emailInput);
                EditText passwordTextInput = (EditText) findViewById(R.id.passwordInput);
                TextView errorText = (TextView) findViewById(R.id.errorText);
                User userLog = new User( String.valueOf(emailTextInput.getText()), String.valueOf(passwordTextInput.getText()));
                // Utilisation de volley request
                VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
                try{
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,  baseURL+ "/login",null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try{
                                        JSONObject ja = response.getJSONObject("user");
                                        Log.e("response", ja.getString("firstname"));

                                        SharedPreferences sh = getSharedPreferences("auth",MODE_PRIVATE);
                                        SharedPreferences.Editor myEdit = sh.edit();
                                        System.out.println("JSON STRING");
                                        System.out.println(ja.toString());
                                        myEdit.putString("jsondata", ja.toString());
                                        myEdit.commit();
                                        Intent homePage = new Intent(getApplicationContext(), HomeActivity.class);
                                        startActivity(homePage);
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
                                    errorText.setText(object.getString("message"));
                                    errorText.setVisibility(View.VISIBLE);
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
                                obj.put("email",String.valueOf(emailTextInput.getText()));
                                obj.put("password",String.valueOf(passwordTextInput.getText()));
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
                    Toast.makeText(this, "Exception server", Toast.LENGTH_SHORT).show();
                }

            }else{
                TextView errorText = (TextView) findViewById(R.id.errorText);
                errorText.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){
            Log.e("Login exception", e.getMessage());
        }
    }

}