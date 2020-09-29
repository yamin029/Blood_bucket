package com.example.blood_bucket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SearchUser extends AppCompatActivity {
    RecyclerView hRecyclerView;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);
 //       try {
            hRecyclerView = findViewById(R.id.recyclerViewSearch);
            hRecyclerView.setLayoutManager(new LinearLayoutManager(this));


            FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                    .setQuery(FirebaseFirestore.getInstance().collection("users"), User.class)
                    .build();
            System.out.println(options);
            myAdapter = new MyAdapter(options);
            hRecyclerView.setAdapter(myAdapter);
//            myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
//                    User user = documentSnapshot.toObject(User.class);
//                    //Toast.makeText(HomeActivity.this, "Item Clicked"+ user.getUserName(), Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(SearchUser.this, DonorProfile.class);
//                    intent.putExtra("user", user);
//                    startActivity(intent);
//                }
//            });
//        }catch (Exception e){
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
    }
}