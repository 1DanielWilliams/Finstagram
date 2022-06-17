package com.example.finstagram.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finstagram.Models.User;
import com.example.finstagram.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    private Button btnSignUpSubmit;
    private EditText etUsernameSignUp;
    private EditText etPasswordSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        btnSignUpSubmit = findViewById(R.id.btnSignUpSubmit);
        etUsernameSignUp = findViewById(R.id.etUsernameSignUp);
        etPasswordSignUp = findViewById(R.id.etPasswordSignUp);

        btnSignUpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsernameSignUp.getText().toString();
                String password = etPasswordSignUp.getText().toString();
                ParseUser user = new ParseUser();
                user.setUsername(username);
                user.setPassword(username);
                if (username == "" || password == "") {
                    Toast.makeText(SignUpActivity.this, "Cannot have empty fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                //add to the data base
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            Toast.makeText(SignUpActivity.this, "Error Signing up", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        loginUser(username, password);
                    }
                });
            }
        });

    }

    private void loginUser(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                // Checks if an error was returned from function
                if (e != null) {
                    Log.e("SignUpActivity", "done: ", e);
                    Toast.makeText(SignUpActivity.this, "Invalid Username/password", Toast.LENGTH_SHORT).show();
                    return;
                }

                goMainActivity();
                Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        // Makes it so that a user cannot simply press the back button after logging in
        finish();
    }
}