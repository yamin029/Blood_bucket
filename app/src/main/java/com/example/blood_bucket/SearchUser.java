package com.example.blood_bucket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SearchUser extends AppCompatActivity {

    RecyclerView hRecyclerView;
    MyAdapter myAdapter;
    TextView hSearch;
    Button searchButton;
    Spinner hSearchItem;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        hRecyclerView = findViewById(R.id.recyclerView2);
        hRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        hSearch = findViewById(R.id.etSearch);
        searchButton = findViewById(R.id.btnSearch);
        hSearchItem = findViewById(R.id.spSearchItem);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        //.whereEqualTo("userCity", "hashara");

        FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                .setQuery(FirebaseFirestore.getInstance().collection("users"), User.class)
                .build();
        System.out.println(options.getSnapshots());
        myAdapter = new MyAdapter(options);
        hRecyclerView.setAdapter(myAdapter);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchItem = hSearch.getText().toString().trim();
                String selectedItem = hSearchItem.getSelectedItem().toString();
                //System.out.println(hSearchItem.getSelectedItem().toString());
                if(selectedItem.equals("Blood Group")){
                    final Query query = FirebaseFirestore.getInstance().collection("users").whereEqualTo("userBloodGroup",searchItem);
                    FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                            .setQuery(query, User.class)
                            .build();
                    myAdapter = new MyAdapter(options);
                    hRecyclerView.setAdapter(myAdapter);
                }
                else if(selectedItem.equals("Name")){
                    final Query query = FirebaseFirestore.getInstance().collection("users").whereEqualTo("userName",searchItem);
                    FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                            .setQuery(query, User.class)
                            .build();
                    myAdapter = new MyAdapter(options);
                    hRecyclerView.setAdapter(myAdapter);
                }
                else if(selectedItem.equals("City")){
                    final Query query = FirebaseFirestore.getInstance().collection("users").whereEqualTo("userCity",searchItem);
                    FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                            .setQuery(query, User.class)
                            .build();
                    myAdapter = new MyAdapter(options);
                    hRecyclerView.setAdapter(myAdapter);
                }


                myAdapter.startListening();
                myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                        User user = documentSnapshot.toObject(User.class);
                        //Toast.makeText(HomeActivity.this, "Item Clicked"+ user.getUserName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SearchUser.this, DonorProfile.class);
                        intent.putExtra("user", user);
                        startActivity(intent);
                    }
                });


            }
        });
        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                User user = documentSnapshot.toObject(User.class);
                //Toast.makeText(HomeActivity.this, "Item Clicked"+ user.getUserName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SearchUser.this, DonorProfile.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
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