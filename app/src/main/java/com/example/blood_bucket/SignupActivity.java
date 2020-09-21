package com.example.blood_bucket;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.auth.User;

public class SignupActivity extends AppCompatActivity {

    ImageView sImageView;
    EditText sUserName;
    EditText sUserEmail;
    EditText sUserPassword;
    EditText sUserAddress;
    EditText sUserContactNumber;
    Spinner sUserBloodGroupSpinner;
    Spinner sUserCitySpinner;
    Button btnRegister;


    private static final int PICK_IMAGE_REQUEST = 100;
    private Uri imageUri;
    private Bitmap imageTOStore;

    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        try {
            sImageView = findViewById(R.id.ivImage);
            sUserName = findViewById(R.id.etUsername);
            sUserEmail = findViewById(R.id.etUserEmail);
            sUserPassword = findViewById(R.id.etUserPassword);
            sUserAddress = findViewById(R.id.etUserAddress);
            sUserContactNumber = findViewById(R.id.etUserContactNumber);
            sUserBloodGroupSpinner = findViewById(R.id.bloodGroupSpinner);
            sUserCitySpinner = findViewById(R.id.citySpinner);
            btnRegister = findViewById(R.id.btnRegister);

            mAuth = FirebaseAuth.getInstance();


            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String userName = sUserName.getText().toString().trim();
                    final String userEmail = sUserEmail.getText().toString().trim();
                    final String userPassword = sUserPassword.getText().toString().trim();
                    final String userAddress = sUserAddress.getText().toString().trim();
                    final String userContactNumber = sUserContactNumber.getText().toString().trim();
                    final String userBloodGroup = sUserBloodGroupSpinner.getSelectedItem().toString();
                    final String userCity = sUserCitySpinner.getSelectedItem().toString();

//                  System.out.println(userName+userEmail+userPassword+userAddress+userContactNumber+userBloodGroup+userCity);


                    if (userName.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty() || userAddress.isEmpty() || userContactNumber.isEmpty() || userBloodGroup.isEmpty() || userCity.isEmpty()) {
                        Toast.makeText(SignupActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                    } else {
                        mAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(SignupActivity.this,
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(SignupActivity.this, "SignUp Unsuccessful" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            uploadProfileImage(userName,userEmail,userAddress,userContactNumber,userBloodGroup,userCity,imageUri,mAuth.getCurrentUser());

                                        } else {
                                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
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

    private void uploadProfileImage(String userName, String userEmail, String userAddress, String userContactNumber, String userBloodGroup, String userCity, Uri imageUri, FirebaseUser currentUser) {

    }

    public void chooseImage(View view) {
        try {
            Intent objectIntent = new Intent();
            objectIntent.setType("image/*");

            objectIntent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(objectIntent, "Select Image"),PICK_IMAGE_REQUEST);

        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {

            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){

                imageUri = data.getData();

                //imageTOStore = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);

                sImageView.setImageURI(imageUri);

            }
            else {
            }
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
}