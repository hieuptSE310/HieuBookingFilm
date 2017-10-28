package com.darkwinter.bookfilms;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button btnLogin;
    private EditText txtEmail;
    private EditText txtPassword;
    private TextView txtRegister;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        addControls();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String pass = txtPassword.getText().toString();
                signinUser(email, pass);
            }
        });
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRegisterScreen();

            }
        });

    }



    private void signinUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(" ++++ ", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            callStartScreen();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("+++++++", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
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

//    public void onClick(View view) {
//        callRegisterScreen();
//    }
}
