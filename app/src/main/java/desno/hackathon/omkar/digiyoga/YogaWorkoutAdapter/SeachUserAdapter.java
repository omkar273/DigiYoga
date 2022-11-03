package desno.hackathon.omkar.digiyoga.YogaWorkoutAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;
import desno.hackathon.omkar.digiyoga.ModalClass.UserProfile;
import desno.hackathon.omkar.digiyoga.OtherUserProfileActivity;
import desno.hackathon.omkar.digiyoga.R;

public class SeachUserAdapter extends FirebaseRecyclerAdapter<UserProfile, SeachUserAdapter.myViewHolder> {

    static boolean isUserOwn = false;

    public SeachUserAdapter(@NonNull FirebaseRecyclerOptions<UserProfile> options) {
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
                holder.follow_icon.setImageResource(R.drawable.saved_user_icon);
                gotoUserProfile(model, holder.user_display_name.getContext());
            }
        });


        holder.profile_image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                holder.follow_icon.setImageResource(R.drawable.saved_user_icon);
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

}
