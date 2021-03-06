package com.example.blood_bucket;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyAdapter extends FirestoreRecyclerAdapter<User,MyAdapter.MyViewHolder> {

    private List<User> exampleListFull;

    private OnItemClickListener listener;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public MyAdapter(FirestoreRecyclerOptions<User> options) {

        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull User model) {

        holder.maTvUserName.setText(model.getUserName());
        holder.maTvUserBloodGroup.setText(model.getUserBloodGroup());
        holder.maTvUserContactNumber.setText(model.getUserContactNumber());
        Glide.with(holder.maImageView.getContext()).load(model.getUserImageUri()).into(holder.maImageView);
        holder.maTvUserCity.setText(model.getUserCity());

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        CircleImageView maImageView;
        TextView maTvUserName,maTvUserBloodGroup,maTvUserContactNumber,maTvUserCity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            maImageView = itemView.findViewById(R.id.ivUserImage);
            maTvUserName = itemView.findViewById(R.id.tvUserName);
            maTvUserBloodGroup = itemView.findViewById(R.id.tvUserBloodGroup);
            maTvUserContactNumber = itemView.findViewById(R.id.tvUserContactNumber);
            maTvUserCity = itemView.findViewById(R.id.tvUserCity);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //startActivity(new Intent(HomeActivity.this, DonerProfile.class));
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }

                }
            });
        }
    }


    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
