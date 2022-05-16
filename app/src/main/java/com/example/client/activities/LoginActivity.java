package com.example.client.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.client.R;
import com.example.client.models.User;
import com.example.client.repository.UserRepository;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private static Boolean canLog = false;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button logButton = (Button) findViewById(R.id.buttonLogin);
        TextView errorText = (TextView) findViewById(R.id.errorText);
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
                UserRepository test = new UserRepository();
                EditText emailTextInput = (EditText)  findViewById(R.id.emailInput);
                EditText passwordTextInput = (EditText) findViewById(R.id.passwordInput);
                User userLog = new User( String.valueOf(emailTextInput.getText()), String.valueOf(passwordTextInput.getText()));
                User val = test.login(this, userLog);
                if(val != null){
                    Intent homePage = new Intent(this, HomeActivity.class);
                    startActivity(homePage);
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