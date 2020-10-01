package com.example.blood_bucket;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class UserUpdate extends AppCompatActivity {
    ImageView sImageView;
    EditText sUserName;
    EditText sUserEmail;
    EditText sUserPassword;
    EditText sUserAddress;
    EditText sUserContactNumber;
    Spinner sUserBloodGroupSpinner;
    Spinner sUserCitySpinner;
    Button btnRegister;
    User user;
    User userDatabase;
    User user2;
    String documentID;


    private static final int PICK_IMAGE_REQUEST = 100;
    private String imageUri;
    private Bitmap imageTOStore;


    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
        setContentView(R.layout.activity_user_update);
        try {

            user = getIntent().getParcelableExtra("user");
            documentID = getIntent().getParcelableExtra("DID");
            System.out.println("line 79"+documentID);

            sImageView = findViewById(R.id.ivImage);
            sUserName = findViewById(R.id.etUsername);
            sUserEmail = findViewById(R.id.etUserEmail);
            sUserAddress = findViewById(R.id.etUserAddress);
            sUserContactNumber = findViewById(R.id.etUserContactNumber);
            sUserBloodGroupSpinner = findViewById(R.id.bloodGroupSpinner);
            sUserCitySpinner = findViewById(R.id.citySpinner);
            btnRegister = findViewById(R.id.btnRegister);

            Glide.with(getApplicationContext())
                    .load(user.getUserImageUri())
                    .into(sImageView);
            sUserName.setText(user.getUserName());
            sUserEmail.setText(user.getUserEmail());
            sUserAddress.setText(user.getUserAddress());
            sUserContactNumber.setText(user.getUserContactNumber());


            mDatabase = FirebaseDatabase.getInstance().getReference();
            mAuth = FirebaseAuth.getInstance();
            mStorageRef = FirebaseStorage.getInstance().getReference();

            ///fething

            db.collection("users")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    userDatabase = document.toObject(User.class);

                                    if(mAuth.getCurrentUser().getUid().equals(userDatabase.getUserID())){
                                        user2 = document.toObject(User.class);
                                        imageUri = user2.getUserImageUri();
                                        System.out.println("User Found");
                                        documentID = document.getId();
                                        System.out.println("User Found"+documentID);
                                    }
                                    //Log.d(TAG, document.getId() + " => " + document.getData());
                                }
                            } else {
                                //Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });

            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    final String userName = sUserName.getText().toString().trim();
                    final String userEmail = sUserEmail.getText().toString().trim();
                    final String userAddress = sUserAddress.getText().toString().trim();
                    final String userContactNumber = sUserContactNumber.getText().toString().trim();
                    final String userBloodGroup = sUserBloodGroupSpinner.getSelectedItem().toString();
                    final String userCity = sUserCitySpinner.getSelectedItem().toString();

//                  System.out.println(userName+userEmail+userPassword+userAddress+userContactNumber+userBloodGroup+userCity);


                    uploadProfileImage(userName,userEmail,userAddress,userContactNumber,userBloodGroup,userCity,imageUri,mAuth.getCurrentUser());
                    startActivity(new Intent(UserUpdate.this, MainActivity.class));


//                    if (userName.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty() || userAddress.isEmpty() || userContactNumber.isEmpty() || userBloodGroup.isEmpty() || userCity.isEmpty()) {
//                        Toast.makeText(UserUpdate.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
//                    } else {
//                        mAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(UserUpdate.this,
//                                new OnCompleteListener<AuthResult>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<AuthResult> task) {
//                                        if (!task.isSuccessful()) {
//                                            Toast.makeText(UserUpdate.this, "SignUp Unsuccessful" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                        } else {
//                                            uploadProfileImage(userName,userEmail,userAddress,userContactNumber,userBloodGroup,userCity,imageUri,mAuth.getCurrentUser());
//                                            startActivity(new Intent(UserUpdate.this, MainActivity.class));
//                                        }
//
//                                    }
//                                });
//                    }
                }
            });

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadProfileImage(final String userName, final String userEmail, final String userAddress, final String userContactNumber, final String userBloodGroup, final String userCity, String imageUri, final FirebaseUser currentUser) {

        //Uri file = Uri.fromFile(new File("/images/"+currentUser.getUid()));
//        final StorageReference riversRef = mStorageRef.child("images/"+currentUser.getUid());
//
//        riversRef.putFile(imageUri)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        // Get a URL to the uploaded content
//                        //Uri downloadUrl = taskSnapshot.getd
//                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                System.out.println(uri);
//                                //sImageView.setImageURI(uri);
//                                Glide.with(getApplicationContext())
//                                        .load(uri)
//                                        .into(sImageView);
//                                Map<String, Object> user = new HashMap<>();
//                                user.put("name",userName.toString());
//                                user.put("email",userEmail.toString());
//                                user.put("address",userAddress.toString());
//                                user.put("userID",currentUser.getUid().toString());
//                                user.put("image",uri);
//                                user.put("number",userContactNumber);
//                                user.put("bloodGroup",userBloodGroup);
//                                user.put("city",userCity);
//
//                                User user = new User(userName,userEmail,userContactNumber,userBloodGroup,userCity,userAddress,uri.toString(),currentUser.getUid().toString());
//                                System.out.println(user);

        DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(documentID);
        Map<String,Object> map = new HashMap<>();
        map.put("userAddress",userAddress);
        map.put("userName",userName);
        map.put("userBloodGroup",userBloodGroup);
        map.put("userContactNumber",userContactNumber);
        map.put("userEmail",userEmail);
        map.put("userID",mAuth.getCurrentUser().getUid());
        map.put("userImageUri",imageUri);

        docRef.update(map)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(UserUpdate.this, "Successful", Toast.LENGTH_SHORT).show();
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UserUpdate.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


        //mDatabase.child("users").child(user.getUserID()).setValue(user);


        // Add a new document with a generated ID
//                                db.collection("users")
//                                        .add(user)
//                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                            @Override
//                                            public void onSuccess(DocumentReference documentReference) {
//                                                Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
//                                            }
//                                        })
//                                        .addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                Log.w("TAG", "Error adding document", e);
//                                            }
//                                        });



    }
    //                      });
    //                   }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        // Handle unsuccessful uploads
//                        // ...
//                    }
//                });

    //}

    private void updateUser(Map<String, Object> user) {
        Toast.makeText(this, "updateusercalled", Toast.LENGTH_SHORT).show();
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
    }

//    public void chooseImage(View view) {
//        try {
//            Intent objectIntent = new Intent();
//            objectIntent.setType("image/*");
//
//            objectIntent.setAction(Intent.ACTION_GET_CONTENT);
//            startActivityForResult(Intent.createChooser(objectIntent, "Select Image"),PICK_IMAGE_REQUEST);
//
//        }
//        catch (Exception e){
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        try {
//
//            super.onActivityResult(requestCode, resultCode, data);
//            if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
//
//                imageUri = data.getData();
//
//                //imageTOStore = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
//
//                sImageView.setImageURI(imageUri);
//
//            }
//            else {
//            }
//        }
//        catch (Exception e){
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//
//
//    }
}