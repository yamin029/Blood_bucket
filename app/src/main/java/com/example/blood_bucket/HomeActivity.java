package com.example.blood_bucket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {
    //ImageView hIvProfile;
    RecyclerView hrecyclerView;
    MyAdapter myAdapter;

    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        try {
            mAuth = FirebaseAuth.getInstance();
            System.out.println(mAuth.getCurrentUser().getEmail());

            hrecyclerView = findViewById(R.id.recyclerView);
            hrecyclerView.setLayoutManager(new LinearLayoutManager(this));




            FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                    .setQuery(FirebaseFirestore.getInstance().collection("users"), User.class)
                    .build();

            myAdapter = new MyAdapter(options);
            hrecyclerView.setAdapter(myAdapter);


        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        //hIvProfile = findViewById(R.id.ivImage);

//        final List<User> userList=new ArrayList<>();
//        db.collection("users")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                //Log.d("TAG", document.getId() + " => " + document.getData());
//                                //System.out.println(document.getData());
//
//                                userList.add(document.toObject(User.class));
//                            }
//                            for(User ckuser:userList){
//                                System.out.println((ckuser.getUserID()));
//                                if(ckuser.getUserID().equals(mAuth.getCurrentUser().getUid())){
//                                    System.out.println("matches");
//                                    Glide.with(getApplicationContext())
//                                            .load(Uri.parse(ckuser.getUserImageUri()))
//                                            .into(hIvProfile);
//                                }
//                                else {
//                                    System.out.println("doesn't match");
//                                }
//                            }
//                        } else {
//                            Log.w("TAG", "Error getting documents.", task.getException());
//                        }
//                    }
//                });


    }

    @Override
    protected void onStart() {
        super.onStart();
        myAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myAdapter.stopListening();
    }
}