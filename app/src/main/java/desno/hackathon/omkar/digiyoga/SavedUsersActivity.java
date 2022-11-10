package desno.hackathon.omkar.digiyoga;

import static desno.hackathon.omkar.digiyoga.Constants.Constants.SAVED_USERS_SECTION_KEY;
import static desno.hackathon.omkar.digiyoga.Constants.Constants.USERS_DETAILS_KEY;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import desno.hackathon.omkar.digiyoga.ModalClass.SavedUserProfile;
import desno.hackathon.omkar.digiyoga.ModalClass.UserProfile;
import desno.hackathon.omkar.digiyoga.YogaWorkoutAdapter.SavedUsersAdapter1;

public class SavedUsersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SavedUsersAdapter1 adapter;
    ArrayList<UserProfile> users = new ArrayList<>();

    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_users);

        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        user = firebaseAuth.getCurrentUser();

        recyclerView = findViewById(R.id.saved_user_recycler_view);
        FirebaseRecyclerOptions<SavedUserProfile> options = new FirebaseRecyclerOptions.Builder<SavedUserProfile>()
                .setQuery(reference.child(USERS_DETAILS_KEY).child(user.getUid()).child(SAVED_USERS_SECTION_KEY), SavedUserProfile.class)
                .build();


        adapter = new SavedUsersAdapter1(options);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
        adapter.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        adapter.notifyDataSetChanged();
        adapter.stopListening();
    }
}