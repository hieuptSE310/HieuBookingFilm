package com.darkwinter.bookfilms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import model.connectProfileUser;
public class ProfileActivity extends AppCompatActivity {
    public TextView txtEmail;
    public TextView txtName;
    public TextView txtDOB;
    public TextView txtZipCode;
    public TextView txtPhone;
    private Button btnUpdate;
    public connectProfileUser connectProfileUser = new connectProfileUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        addControls();
        connectProfileUser.loadCurrentUser(new connectProfileUser.OnLoadedUser() {
            @Override
            public void onLoadedUser(User user) {
                Log.d("+++++++++", user.getEmail());
                Log.d("abccccccc", user.toString());

                txtName.setText(user.getName());
                txtEmail.setText(user.getEmail());
                txtDOB.setText(user.getDOB());
                txtPhone.setText(user.getPhone());
                txtZipCode.setText(user.getZipcode());

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateIntent = new Intent(ProfileActivity.this , UpdateProfileActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("name", txtName.getText().toString());
                        bundle.putString("email", txtEmail.getText().toString());
                        bundle.putString("DOB", txtDOB.getText().toString());
                        bundle.putString("phone", txtPhone.getText().toString());
                        bundle.putString("zipcode", txtZipCode.getText().toString());
                        updateIntent.putExtra("Bundle", bundle);
                        startActivity(updateIntent);
                        finish();
                    }
                });
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
