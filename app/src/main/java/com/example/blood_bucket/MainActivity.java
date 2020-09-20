package com.example.blood_bucket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText mEtUserName;
    EditText mEtPassword;
    Button mButtonSignIn;
    TextView mTvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            mEtUserName = findViewById(R.id.etUsername);
            mEtPassword = findViewById(R.id.etPassword);
            mButtonSignIn = findViewById(R.id.btnSignIn);
            mTvSignUp = findViewById(R.id.tvSignUp);

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

                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}