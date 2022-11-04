package desno.hackathon.omkar.digiyoga.YogaWorkoutAdapter;

import static desno.hackathon.omkar.digiyoga.Constants.Constants.SAVED_USERS_SECTION_KEY;
import static desno.hackathon.omkar.digiyoga.Constants.Constants.USERS_DETAILS_KEY;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import desno.hackathon.omkar.digiyoga.ModalClass.UserProfile;
import desno.hackathon.omkar.digiyoga.OtherUserProfileActivity;
import desno.hackathon.omkar.digiyoga.R;

public class SearchUserAdapter extends FirebaseRecyclerAdapter<UserProfile, SearchUserAdapter.myViewHolder> {

    static boolean isUserOwn = false;

    public SearchUserAdapter(@NonNull FirebaseRecyclerOptions<UserProfile> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull UserProfile model) {

        if (model.getUSER_Uid().equals(FirebaseAuth.getInstance().getUid())) {
            isUserOwn = true;
        }

        holder.user_display_name.setText(model.getUSER_Display_Name());
        Glide.with(holder.profile_image.getContext()).load(model.getUSER_Profile_Image_URl()).error(R.drawable.search_profile_icon).into(holder.profile_image);

        holder.user_display_name.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                gotoUserProfile(model, holder.user_display_name.getContext());
            }
        });


        holder.follow_icon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                Map<String, Object> saved = new HashMap<>();
                saved.put(model.getUSER_Uid(), model.getUSER_Display_Name());
                FirebaseDatabase.getInstance().getReference(USERS_DETAILS_KEY).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(SAVED_USERS_SECTION_KEY).updateChildren(saved).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(holder.follow_icon.getContext(), "saved", Toast.LENGTH_SHORT).show();
                        holder.follow_icon.setImageResource(R.drawable.saved_user_icon);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(holder.follow_icon.getContext(), "value not added", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        holder.profile_image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                gotoUserProfile(model, holder.profile_image.getContext());
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_single_row, parent, false);
        return new myViewHolder(view);
    }

    public void gotoUserProfile(UserProfile model, Context context) {
        Bundle userData = new Bundle();

        userData.putString("name", model.getUSER_Display_Name());
        userData.putString("email", model.getUSER_Email());
        userData.putString("email", model.getUSER_Email());
        userData.putString("profileUrl", model.getUSER_Profile_Image_URl());

        Intent intent = new Intent(context, OtherUserProfileActivity.class);
        intent.putExtras(userData);
        context.startActivity(intent);

    }

    class myViewHolder extends RecyclerView.ViewHolder {

        CircleImageView profile_image;
        TextView user_display_name;
        ImageView follow_icon;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_image = itemView.findViewById(R.id.profile_image);
            user_display_name = itemView.findViewById(R.id.user_display_name);
            follow_icon = itemView.findViewById(R.id.follow_icon);

        }
    }

}
