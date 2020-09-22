package com.example.blood_bucket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    ImageView hIvProfile;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        hIvProfile = findViewById(R.id.ivImage);
        mAuth = FirebaseAuth.getInstance();
        System.out.println(mAuth.getCurrentUser().getEmail());
        Toast.makeText(this, mAuth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();

        final List<User> userList=new ArrayList<>();

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("TAG", document.getId() + " => " + document.getData());
                                //System.out.println(document.getData());

                                userList.add(document.toObject(User.class));


                            }
                            //System.out.println(userList.get(1).getUserImageUri());
                            //System.out.println((mAuth.getCurrentUser().getUid()));
                            for(User ckuser:userList){

                                System.out.println((ckuser.getUserID()));
                                if(ckuser.getUserID().equals(mAuth.getCurrentUser().getUid())){
                                    System.out.println("matches");
                                    Glide.with(getApplicationContext())
                                            .load(Uri.parse(ckuser.getUserImageUri()))
                                            .into(hIvProfile);
                                   // hIvProfile.setImageURI(Uri.parse(ckuser.getUserImageUri()));
                                }
                                else {
                                    System.out.println("doesn't match");
                                }
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });


    }
}