package com.example.blood_bucket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class DonorProfile extends AppCompatActivity {
    CircleImageView dUserProfile;
    TextView dUserName,dUserBloodGroup,dUserAddress,dUserCity,dUserEmail,dUserContactNumber;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_profile);

        dUserProfile = findViewById(R.id.userProfile);
        dUserName = findViewById(R.id.userName);
        dUserBloodGroup = findViewById(R.id.userBloodGroup);
        dUserAddress = findViewById(R.id.userAddress);
        dUserCity = findViewById(R.id.userCity);
        dUserEmail = findViewById(R.id.userEmail);
        dUserContactNumber = findViewById(R.id.userContactNumber);


        user = getIntent().getParcelableExtra("user");

        Glide.with(getApplicationContext())
                .load(user.getUserImageUri())
                .into(dUserProfile);
        dUserName.setText(user.getUserName());
        dUserBloodGroup.setText(user.getUserBloodGroup());
        dUserAddress.setText(user.getUserAddress());
        dUserCity.setText(user.getUserCity());
        dUserEmail.setText(user.getUserEmail());
        dUserContactNumber.setText(user.getUserContactNumber());
    }

    public void callUser(View view) {

        try {
            String s= "tel:"+ user.getUserContactNumber();
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse(s));
            startActivity(intent);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}