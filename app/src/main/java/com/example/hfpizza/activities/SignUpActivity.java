package com.example.hfpizza.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userEmailET, userPasswordET;
    private Button registerButton;
    private TextView signInButton;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fieldsInitialization();

        registerButton.setOnClickListener(this);
        signInButton.setOnClickListener(this);
    }

    private void fieldsInitialization() {
        userEmailET = findViewById(R.id.signUpActivityEnterEmailEditTextId);
        userPasswordET = findViewById(R.id.signUpActivityEnterPasswordEditTextId);
        registerButton = findViewById(R.id.signUpActivityRegisterButtonId);
        signInButton = findViewById(R.id.signUpActivitySignInTextViewId);
        databaseHandler = new DatabaseHandler(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpActivityRegisterButtonId:
                registerUser();
                break;
            case R.id.signUpActivitySignInTextViewId:
                Intent signUpIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                signUpIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(signUpIntent);
                finish();
                break;
        }
    }

    private void registerUser() {
        String userEmailString = userEmailET.getText().toString().trim();
        String userPasswordString = userPasswordET.getText().toString().trim();
        if (!TextUtils.isEmpty(userEmailString) && !TextUtils.isEmpty(userPasswordString)) {
            User user = new User();
            user.setUserEmail(userEmailString);
            user.setUserPassword(userPasswordString);
            user.setUserType("M");
            databaseHandler.addUser(user);
            Intent signUpIntent = new Intent(SignUpActivity.this, MainActivity.class);
            signUpIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            signUpIntent.putExtra(Constants.USER_TYPE_KEY, "M");
            startActivity(signUpIntent);
            finish();
        } else {
            Toast.makeText(SignUpActivity.this, "Please fill all fields", Toast.LENGTH_LONG).show();
        }
    }

}