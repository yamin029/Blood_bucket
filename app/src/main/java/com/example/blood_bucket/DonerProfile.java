package com.example.blood_bucket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class DonerProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doner_profile);

        User user = getIntent().getParcelableExtra("user");

        Toast.makeText(this, user.getUserName(), Toast.LENGTH_SHORT).show();
    }
}