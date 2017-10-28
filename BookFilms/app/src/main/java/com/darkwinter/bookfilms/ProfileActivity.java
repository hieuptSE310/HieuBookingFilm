package com.darkwinter.bookfilms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    private EditText txtEmail;
    private EditText txtName;
    private EditText txtDOB;
    private EditText txtPassword;
    private EditText txtZipCode;
    private EditText txtPhone;
    private Button btnUpdate;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserReference;
    private FirebaseDatabase database;
    private String name;
    private String email;
    private String DOB;
    private String phone;
    private String zipcode;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        addControls();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseUser currentUSer = mAuth.getCurrentUser();
        if(currentUSer!=null){
            mUserReference = database.getReference("Users");
            //Toast.makeText(ProfileActivity.this, currentUSer.getUid(), Toast.LENGTH_LONG).show();
            final String ID = currentUSer.getUid();

            mUserReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Toast.makeText(ProfileActivity.this, dataSnapshot.toString(), Toast.LENGTH_LONG).show();
                    name = dataSnapshot.child(ID).child("name").getValue().toString();
                    email = dataSnapshot.child(ID).child("email").getValue().toString();
                    DOB = dataSnapshot.child(ID).child("DOB").getValue().toString();
                    phone = dataSnapshot.child(ID).child("Phone number").getValue().toString();
                    zipcode = dataSnapshot.child(ID).child("CitizenID").getValue().toString();
                    password = dataSnapshot.child(ID).child("Password").getValue().toString();

                    txtName.setText(name);
                    txtDOB.setText(DOB);
                    txtEmail.setText(email);
                    txtPhone.setText(phone);
                    txtZipCode.setText(zipcode);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateIntent = new Intent(ProfileActivity.this , UpdateProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("email", email);
                bundle.putString("DOB", DOB);
                bundle.putString("phone", phone);
                bundle.putString("zipcode", zipcode);
                bundle.putString("password", password);
                updateIntent.putExtra("Bundle", bundle);
                startActivity(updateIntent);

            }
        });
    }

    private void addControls() {
        txtEmail = findViewById(R.id.txtEmail);
        txtName = findViewById(R.id.txtName);
        txtDOB = findViewById(R.id.txtBirth);
        txtPhone = findViewById(R.id.txtPhone);
        txtZipCode = findViewById(R.id.txtZipcode);
        btnUpdate = findViewById(R.id.btnUpdate);

    }
}
