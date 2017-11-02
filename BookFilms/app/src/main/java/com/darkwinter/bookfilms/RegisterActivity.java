package com.darkwinter.bookfilms;

import model.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class RegisterActivity extends AppCompatActivity {
    private EditText txtEmail;
    private EditText txtName;
    private EditText txtDOB;
    private EditText txtPassword;
    private EditText txtZipCode;
    private EditText txtPhone;
    private Button btnRegister;
    private ConnectDB connectDB = new ConnectDB();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addControls();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String name  = txtName.getText().toString();
                String pass = txtPassword.getText().toString();
                String DOB = txtDOB.getText().toString();
                String phone = txtPhone.getText().toString();
                String Zipcode = txtZipCode.getText().toString();
                connectDB.saveDataUser(email, name, pass, DOB, phone, Zipcode);
            }
        });

    }
    private void addControls() {
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtName = findViewById(R.id.txtName);
        txtDOB = findViewById(R.id.txtBirth);
        txtPhone = findViewById(R.id.txtPhone);
        txtZipCode = findViewById(R.id.txtZipcode);
        btnRegister = findViewById(R.id.btnRegister);
        
    }
}
