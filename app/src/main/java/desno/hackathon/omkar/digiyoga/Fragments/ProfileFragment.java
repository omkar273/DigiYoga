package desno.hackathon.omkar.digiyoga.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import desno.hackathon.omkar.digiyoga.ModalClass.UserProfile;
import desno.hackathon.omkar.digiyoga.R;

public class ProfileFragment extends Fragment {

    CircleImageView profile_image;
    TextView user_display_name, user_mobile_number, user_email_id, user_dob;
    UserProfile userProfile;

    public ProfileFragment(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profile_image = view.findViewById(R.id.profile_image);
        user_display_name = view.findViewById(R.id.user_display_name);
        user_mobile_number = view.findViewById(R.id.user_mobile_number);
        user_email_id = view.findViewById(R.id.user_email_id);
        user_dob = view.findViewById(R.id.user_dob);


        user_display_name.setText(userProfile.getUSER_Display_Name());
        user_mobile_number.setText(" " + userProfile.getUSER_Phone());
        user_email_id.setText(" " + userProfile.getUSER_Email());
        user_dob.setText(" " + userProfile.getUSER_Dob());

        if (!userProfile.getUSER_Profile_Image_URl().equals("null")) {
            Glide.with(profile_image.getContext()).load(userProfile.getUSER_Profile_Image_URl()).into(profile_image);
        } else {
//            Toast.makeText(getContext()
//                    , "Failed to load Image", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    public void onclick(View view) {
        TextView text = (TextView) view;
        text.setTextColor(getResources().getColor(R.color.white));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                text.setTextColor(getResources().getColor(R.color.black));

            }
        }, 300);
    }


}