package com.darkwinter.bookfilms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import model.ConnectDB;

public class UpdateProfileActivity extends AppCompatActivity {
    private EditText txtEmail;
    private EditText txtName;
    private EditText txtDOB;
    private EditText txtPassword;
    private EditText txtZipCode;
    private EditText txtPhone;
    private Button btnRegister;
    private String name;
    private String email;
    private String DOB;
    private String phone;
    private String zipcode;
    private ConnectDB connectDB = new ConnectDB();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        addControls();
        Intent receiveIntent = getIntent();
        Bundle bundle = receiveIntent.getBundleExtra("Bundle");
        txtName.setText(bundle.getString("name"));
        txtEmail.setText(bundle.getString("email"));
        txtDOB.setText(bundle.getString("DOB"));
        txtPhone.setText(bundle.getString("phone"));
        txtZipCode.setText(bundle.getString("zipcode"));
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = txtName.getText().toString();
                email = txtEmail.getText().toString();
                DOB = txtDOB.getText().toString();
                phone = txtPhone.getText().toString();
                zipcode = txtZipCode.getText().toString();
                connectDB.checkUID();
                connectDB.saveData(email, name, DOB, phone, zipcode, connectDB.curuid);
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
