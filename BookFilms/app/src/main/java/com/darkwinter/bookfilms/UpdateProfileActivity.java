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

public class UpdateProfileActivity extends AppCompatActivity {
    private EditText txtEmail;
    private EditText txtName;
    private EditText txtDOB;
    private EditText txtPassword;
    private EditText txtZipCode;
    private EditText txtPhone;
    private Button btnRegister;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private String name;
    private String email;
    private String DOB;
    private String phone;
    private String zipcode;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        mAuth = FirebaseAuth.getInstance();
        addControls();
        Intent receiveIntent = getIntent();
        Bundle bundle = receiveIntent.getBundleExtra("Bundle");
        txtName.setText(bundle.getString("name"));
        txtEmail.setText(bundle.getString("email"));
        txtDOB.setText(bundle.getString("DOB"));
        txtPhone.setText(bundle.getString("phone"));
        txtZipCode.setText(bundle.getString("zipcode"));
        password = bundle.getString("password");
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = txtName.getText().toString();
                email = txtEmail.getText().toString();
                DOB = txtDOB.getText().toString();
                phone = txtPhone.getText().toString();
                zipcode = txtZipCode.getText().toString();
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String uid = currentUser.getUid();
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                HashMap<String, Object> userMap = new HashMap<>();
                userMap.put("email", email);
                userMap.put("name", name);
                userMap.put("CitizenID", zipcode );
                userMap.put("DOB", DOB);
                userMap.put("Phone number", phone);
                userMap.put("Password", password);
                databaseReference.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent loginIntent = new Intent(UpdateProfileActivity.this, ProfileActivity.class );
                            startActivity(loginIntent);
                            finish();
                        }
                    }
                });
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
