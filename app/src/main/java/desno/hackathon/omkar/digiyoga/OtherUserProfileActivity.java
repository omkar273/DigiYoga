package desno.hackathon.omkar.digiyoga;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class OtherUserProfileActivity extends AppCompatActivity {

    TextView book_appoinment_icon, email_icon, user_email_id, user_display_name;
    CircleImageView profile_image;


    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);

        book_appoinment_icon = findViewById(R.id.book_appoinment_icon);
        email_icon = findViewById(R.id.email_icon);
        user_email_id = findViewById(R.id.user_email_id);
        profile_image = findViewById(R.id.profile_image);
        user_display_name = findViewById(R.id.user_display_name);


        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        user = firebaseAuth.getCurrentUser();

        Bundle userData = getIntent().getExtras();
        if (userData != null) {
            user_display_name.setText(userData.getString("name"));
            user_email_id.setText(" " + userData.getString("email"));
            if (!userData.getString("profileUrl").equals("null")) {
                Glide.with(this).load(userData.getString("profileUrl")).error(R.drawable.circle_shape).into(profile_image);
            }
        }

        email_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail(userData.getString("email"));
            }
        });

    }

    protected void sendEmail(String emailId) {
        Log.i("Send email", "");
        String[] TO = {emailId};
        String[] CC = {"Sir my name is " + user.getDisplayName() + "\n I would to receive a yoga training from you . MY email id is as follows " + user.getEmail()};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("", "Finished sending email...");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(OtherUserProfileActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}