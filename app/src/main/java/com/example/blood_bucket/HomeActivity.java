package com.example.blood_bucket;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.googlecode.genericdao.search.Search;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeActivity extends AppCompatActivity {
    RecyclerView hRecyclerView;
    MyAdapter myAdapter;
    DrawerLayout drawerLayout;
    CircleImageView hUserProfile;
    TextView hUserName;
    EditText hEtSearch;

    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        try {
            mAuth = FirebaseAuth.getInstance();
            //System.out.println(mAuth.getCurrentUser().getEmail());
            hRecyclerView = findViewById(R.id.recyclerView);
            hRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            drawerLayout = findViewById(R.id.drawer_layout);
            hUserProfile = findViewById(R.id.dmProfilePhoto);
            hUserName = findViewById(R.id.dmUserName);

            FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                    .setQuery(FirebaseFirestore.getInstance().collection("users"), User.class)
                    .build();
            myAdapter = new MyAdapter(options);
            hRecyclerView.setAdapter(myAdapter);
            myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                    User user = documentSnapshot.toObject(User.class);
                    //Toast.makeText(HomeActivity.this, "Item Clicked"+ user.getUserName(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(HomeActivity.this, DonorProfile.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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
    public void clickMenu(View view){
        openDrawer(drawerLayout);
    }
    private static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public void clickHome(View view){
        //recreate();
        drawerLayout.close();
    }
    public void clickSearch(View view){
        //redirectActivity(this, SearchUser.class);
        startActivity(new Intent(HomeActivity.this, SearchUser.class));

    }
    public void clickProfile(View view){

        redirectActivity(this,UserProfile.class);
    }
    public void clickEmergency(View view){
        try {
            String s= "tel:"+ "911";
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse(s));
            startActivity(intent);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public static void redirectActivity(Activity activity,Class aClass){
        Intent intent = new Intent(activity,aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
    public void clickLogOut(View view){
        final Activity activity= this;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to Logout");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
//                activity.finishAffinity();
//                System.exit(0);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}