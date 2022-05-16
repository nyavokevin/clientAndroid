package com.example.client.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.client.R;
import com.example.client.models.User;
import com.example.client.repository.UserRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

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
        try{
            if(validate(arrayEditText)) {
                UserRepository repo = UserRepository.getInstance(this);
                User userTemp = new User(
                        String.valueOf(emailEditText.getText()),
                        String.valueOf(passwordEditText.getText()),
                        String.valueOf(firstNameEditText.getText()),
                        String.valueOf(lastNameEditText.getText())
                );
                repo.register(this, userTemp);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}