package com.example.blood_bucket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {
    ImageView userProfileCover;
    CircleImageView userProfile;
    TextView userName,userBloodGroup,userCity,userAddress,userEmail,userContactNumber;
    ProgressDialog progressDialog;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        try {
            userProfile = findViewById(R.id.ivUserProfile);
            userProfileCover = findViewById(R.id.ivUserCover);
            userName = findViewById(R.id.tvUserName);
            userBloodGroup = findViewById(R.id.tvUserBloodGroup);
            userCity = findViewById(R.id.tvUserCity);
            userAddress = findViewById(R.id.tvUserEmail);
            userEmail = findViewById(R.id.tvUserEmail);
            userContactNumber = findViewById(R.id.tvUserContactNumber);

            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("login");
            progressDialog.setMessage("Processing....");
            progressDialog.show();

            //System.out.println(mAuth.getCurrentUser().getUid());


            db.collection("users")
                    .whereEqualTo("userID", mAuth.getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("TAG", document.getId() + " => " + document.getData());
                                    User user = document.toObject(User.class);
                                    //System.out.println(user.getUserName());
                                    Glide.with(getApplicationContext())
                                            .load(user.getUserImageUri())

                                            .into(userProfile);
                                    Glide.with(getApplicationContext())
                                            .load(user.getUserImageUri())
                                            .into(userProfileCover);
                                    userName.setText(user.getUserName());
                                    userBloodGroup.setText("blood group "+ user.getUserBloodGroup());
                                    userCity.setText(user.getUserCity());
                                    userAddress.setText(user.getUserAddress());
                                    userEmail.setText(user.getUserEmail());
                                    userContactNumber.setText(user.getUserContactNumber());

                                }
                            } else {
                                Log.d("TAG", "Error getting documents: ", task.getException());
                            }
                        }
                    });
            progressDialog.dismiss();

;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}