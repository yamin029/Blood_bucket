package com.example.blood_bucket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {
    EditText mEtUserEmail;
    EditText mEtPassword;
    Button mButtonSignIn;
    TextView mTvSignUp;
    ProgressDialog progressDialog;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            mEtUserEmail = findViewById(R.id.etUsername);
            mEtPassword = findViewById(R.id.etPassword);
            mButtonSignIn = findViewById(R.id.btnSignIn);
            mTvSignUp = findViewById(R.id.tvSignUp);

            mAuth = FirebaseAuth.getInstance();

            progressDialog = new ProgressDialog(this);

            mTvSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent signUpIntent = new Intent(MainActivity.this, SignupActivity.class);
                    startActivity(signUpIntent);
                }
            });

            mButtonSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    progressDialog.setTitle("login");
                    progressDialog.setMessage("Processing....");
                    progressDialog.show();
                    final String userEmail = mEtUserEmail.getText().toString().trim();
                    final String userPassword = mEtPassword.getText().toString().trim();

                    if(userEmail.isEmpty() || userPassword.isEmpty()){
                        Toast.makeText(MainActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        mAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(MainActivity.this,
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()){
                                            progressDialog.dismiss();
                                            Toast.makeText(MainActivity.this, "SignUp Unsuccessful"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }else {
                                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                            progressDialog.dismiss();
                                            //Toast.makeText(MainActivity.this, mAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });


                    }

                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}