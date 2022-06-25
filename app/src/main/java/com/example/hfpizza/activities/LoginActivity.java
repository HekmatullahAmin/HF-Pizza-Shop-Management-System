package com.example.hfpizza.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hfpizza.R;
import com.example.hfpizza.data.DatabaseHandler;
import com.example.hfpizza.model.User;
import com.example.hfpizza.utils.Constants;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userEmailET, userPasswordET;
    private Button loginButton;
    private TextView signUpButton;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fieldsInitialization();

        loginButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent goToMainActivityIntent = new Intent(LoginActivity.this, MainActivity.class);
//                goToMainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(goToMainActivityIntent);
//                finish();
//            }
//        }, 1000);
    }

    private void fieldsInitialization() {
        userEmailET = findViewById(R.id.loginActivityEnterEmailEditTextId);
        userPasswordET = findViewById(R.id.loginActivityEnterPasswordEditTextId);
        loginButton = findViewById(R.id.loginActivityLoginButtonId);
        signUpButton = findViewById(R.id.loginActivitySignUpTextViewId);
        databaseHandler = new DatabaseHandler(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginActivityLoginButtonId:
                verifyUserLogin();
                break;
            case R.id.loginActivitySignUpTextViewId:
                Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                signUpIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(signUpIntent);
                finish();
                break;
        }
    }

    private void verifyUserLogin() {
        ArrayList<User> users = databaseHandler.getAllUsers();
        String userEmailString = userEmailET.getText().toString().trim();
        String userPasswordString = userPasswordET.getText().toString().trim();

        if (!TextUtils.isEmpty(userEmailString) && !TextUtils.isEmpty(userPasswordString)) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUserEmail().equals(userEmailString) && users.get(i).getUserPassword().equals(userPasswordString)) {
                    Intent mainActivityIntent = new Intent(LoginActivity.this, MainActivity.class);
                    mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mainActivityIntent.putExtra(Constants.USER_TYPE_KEY, users.get(i).getUserType());
                    startActivity(mainActivityIntent);
                    finish();
                    break;
                }
                if (i == (users.size() - 1)) {
                    Toast.makeText(LoginActivity.this, "Email and password doesn't match", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_LONG).show();
        }
    }
}