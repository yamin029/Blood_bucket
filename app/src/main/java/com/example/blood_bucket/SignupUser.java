package com.example.blood_bucket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SignupUser extends AppCompatActivity {

    EditText sUserEmail;
    EditText sUserPassword;
    Button sButtonSignUP;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_user);

        try{
        sUserEmail = findViewById(R.id.etUserEmail);
        sUserPassword = findViewById(R.id.etUserPassword);
        sButtonSignUP = findViewById(R.id.btnSignUp);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        sButtonSignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userEmail = sUserEmail.getText().toString().trim();
                final String userPassword = sUserPassword.getText().toString().trim();

                if (userEmail.isEmpty() || userPassword.isEmpty()) {
                    Toast.makeText(SignupUser.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(SignupUser.this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(SignupUser.this, "SignUp Unsuccessful" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        startActivity(new Intent(SignupUser.this, MainActivity.class));
                                    }

                                }
                            });
                }
            }
        });
        }catch (Exception e){
            System.out.println();
        }
    }
}