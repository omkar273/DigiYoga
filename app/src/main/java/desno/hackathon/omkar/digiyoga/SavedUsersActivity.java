package desno.hackathon.omkar.digiyoga;

import static desno.hackathon.omkar.digiyoga.Constants.Constants.SAVED_USERS_SECTION_KEY;
import static desno.hackathon.omkar.digiyoga.Constants.Constants.USERS_DETAILS_KEY;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import desno.hackathon.omkar.digiyoga.ModalClass.UserProfile;
import desno.hackathon.omkar.digiyoga.YogaWorkoutAdapter.SavedUsersAdapter;

public class SavedUsersActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    SavedUsersAdapter adapter;
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

        FirebaseDatabase.getInstance().getReference(USERS_DETAILS_KEY).child(user.getUid()).child(SAVED_USERS_SECTION_KEY).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Toast.makeText(SavedUsersActivity.this, snapshot1.getValue(String.class), Toast.LENGTH_SHORT).show();
                    Log.w("firebase", "onDataChange: " + snapshot1.getKey());


                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(USERS_DETAILS_KEY);
                    databaseReference.child(snapshot1.getKey()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult().exists()) {
                                    DataSnapshot snapshot = task.getResult();

                                    users.add(snapshot.getValue(UserProfile.class));
                                }
                            } else {
                                Toast.makeText(SavedUsersActivity.this, "Some Error occured please restart the application", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        users.add(new UserProfile("demo uid", "om", "phone", "email", "dob", "pass", "url"));
        users.add(new UserProfile("demo uid", "om", "phone", "email", "dob", "pass", "url"));
        users.add(new UserProfile("demo uid", "om", "phone", "email", "dob", "pass", "url"));
        users.add(new UserProfile("demo uid", "om", "phone", "email", "dob", "pass", "url"));
        recyclerView = findViewById(R.id.saved_user_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        Log.d("firebase", "onCreate    ok: ");
        adapter = new SavedUsersAdapter(users);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public ArrayList<UserProfile> getData() {
        Log.d("firebase ", "getData: entered in getdata");
        ArrayList<UserProfile> userList = new ArrayList<>();


        return userList;
    }


    public UserProfile getUserProfile(String uId) {

        Log.d("firebase", "getUserProfile: entered in get user profile" + uId);
        final UserProfile[] userProfile = {new UserProfile()};

        return userProfile[0];
    }

}