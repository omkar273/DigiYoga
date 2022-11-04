package desno.hackathon.omkar.digiyoga.YogaWorkoutAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import desno.hackathon.omkar.digiyoga.ModalClass.UserProfile;
import desno.hackathon.omkar.digiyoga.R;

public class SavedUsersAdapter extends RecyclerView.Adapter<SavedUsersAdapter.myViewHolder> {

    ArrayList<UserProfile> users = new ArrayList<>();

    public SavedUsersAdapter(ArrayList<UserProfile> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_users_profile_grid_layout, parent, false);
        return new SavedUsersAdapter.myViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.user_display_name.setText(users.get(position).getUSER_Display_Name());
        if (!users.get(position).getUSER_Profile_Image_URl().equals("null")) {
            Glide.with(holder.profile_image.getContext()).load(users.get(position).getUSER_Profile_Image_URl()).error(R.drawable.search_profile_icon).into(holder.profile_image);

        }
    }

    @Override
    public int getItemCount() {
        return users.size();
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
