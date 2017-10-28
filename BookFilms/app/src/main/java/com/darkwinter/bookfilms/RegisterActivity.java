package com.darkwinter.bookfilms;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText txtEmail;
    private EditText txtName;
    private EditText txtDOB;
    private EditText txtPassword;
    private EditText txtZipCode;
    private EditText txtPhone;
    private Button btnRegister;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        addControls();
        //btnLogIn = findViewById(R.id.btnLogIn);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String name  = txtName.getText().toString();
                String pass = txtPassword.getText().toString();
                String DOB = txtDOB.getText().toString();
                String phone = txtPhone.getText().toString();
                String Zipcode = txtZipCode.getText().toString();
                
                registerUser(email, name, pass, DOB, phone, Zipcode);
            }
        });
        
    }

    private void registerUser(final String email, final String name, final String pass, final String dob, final String phone, final String zipcode) {
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();
                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("email", email);
                    userMap.put("name", name);
                    userMap.put("CitizenID", zipcode );
                    userMap.put("DOB", dob);
                    userMap.put("Password", pass);
                    userMap.put("Phone number", phone);
                    databaseReference.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class );
                                startActivity(loginIntent);
                                finish();
                            }
                        }
                    });
                }
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
