package desno.hackathon.omkar.digiyoga.YogaWorkoutAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;
import desno.hackathon.omkar.digiyoga.ModalClass.SavedUserProfile;
import desno.hackathon.omkar.digiyoga.R;

public class SavedUsersAdapter1 extends FirebaseRecyclerAdapter<SavedUserProfile, SavedUsersAdapter1.myViewHolder> {

    public SavedUsersAdapter1(@NonNull FirebaseRecyclerOptions<SavedUserProfile> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull SavedUserProfile model) {
        holder.user_display_name.setText(model.getUSER_Display_Name());

        if (!model.getUSER_Profile_Image_URl().equals("null")) {
            Glide.with(holder.profile_image.getContext()).load(model.getUSER_Profile_Image_URl()).error(R.drawable.search_profile_icon).into(holder.profile_image);

        }
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_users_profile_grid_layout, parent, false);
        return new SavedUsersAdapter1.myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        CircleImageView profile_image;
        TextView user_display_name;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            profile_image = itemView.findViewById(R.id.profile_image);
            user_display_name = itemView.findViewById(R.id.user_display_name);

        }
    }
}
