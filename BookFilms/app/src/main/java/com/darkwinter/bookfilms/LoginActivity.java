package com.darkwinter.bookfilms;

import model.*;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText txtEmail;
    private EditText txtPassword;
    private TextView txtRegister;
    public ConnectDB connectDB = new ConnectDB();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        connectDB.setActivity(this);
        addControls();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String pass = txtPassword.getText().toString();
                connectDB.signinUser(email, pass);
                callStartScreen();
//
            }
        });
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRegisterScreen();

            }
        });
    }
    private void addControls() {
        btnLogin = findViewById(R.id.btnLogIn);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtRegister = findViewById(R.id.txtRegister);


    }
    private void callStartScreen(){
        Intent startIntent = new Intent(LoginActivity.this, StartActivity.class);
        startActivity(startIntent);
    }

    private void callRegisterScreen(){
        Intent regIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(regIntent);
        finish();
    }
}
