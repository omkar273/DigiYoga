package desno.hackathon.omkar.digiyoga;

import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;
import static desno.hackathon.omkar.digiyoga.Constants.Constants.HOMEPAGE_YOGA_QUOTE;
import static desno.hackathon.omkar.digiyoga.Constants.Constants.USERS_DETAILS_KEY;
import static desno.hackathon.omkar.digiyoga.Constants.Constants.YOGA_WORKOUT_SECTION;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import desno.hackathon.omkar.digiyoga.ModalClass.UserProfile;


public class HomePageActivity extends AppCompatActivity {

    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    GoogleSignInAccount signInAccount;
    TextView welcome_text, email_text;

    FirebaseAuth mAuth;
    FirebaseUser user;
    Button logout;

    //bottom navigation bar
    BottomNavigationView bottomNavigationView;

    Fragment selectorFragment;
    //user details
    UserProfile userProfile1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        initializeGoogleSignIn();

        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);

        userProfile1 = new UserProfile("uid", "displayname", "phone", "email", "dob", "password", "url");

        getUserProfile();


        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment(userProfile1)).commitNow();

        signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
//            Toast.makeText(this, "google name : " + signInAccount.getDisplayName(), Toast.LENGTH_SHORT).show();
//            welcome_text.setText("welcome  " + signInAccount.getDisplayName());
//            email_text.setText("email :" + signInAccount.getEmail());
        }


        bottomNavigationView.setSelectedItemId(R.id.Home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.Feed:
                        selectorFragment = new FeedFragment();
                        break;

                    case R.id.progress:
                        selectorFragment = new ProgressFragment();
                        break;

                    case R.id.Home:
                        selectorFragment = new HomeFragment(userProfile1);
                        break;

                    case R.id.Plan:
                        selectorFragment = new PlanFragment();
                        break;

                    case R.id.profile:
                        selectorFragment = new ProfileFragment(userProfile1);
                        break;

                    default:
                        onBackPressed();
                }

                if (selectorFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, selectorFragment).commitNow();
                }
                return true;
            }
        });
    }

    private void setData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(YOGA_WORKOUT_SECTION);

        HashMap<String, String> yogaPlan = new HashMap<>();

//        yogaPlan.put(YOGA_PLAN_NAME, "demo plan name2");
//        yogaPlan.put(YOGA_PLAN_DESCRIPTION, "demo description2");
//        yogaPlan.put(YOGA_PLAN_ESTIMATED_DAYS, "demo duration2");
//        yogaPlan.put(YOGA_PLAN_IMAGE_URL, "null2");
        yogaPlan.put("quote", "hell quote");
        databaseReference = FirebaseDatabase.getInstance().getReference().child(HOMEPAGE_YOGA_QUOTE);
//        String id = databaseReference.push().getKey();
        databaseReference.setValue("this is demo quote");
    }


    private void initializeGoogleSignIn() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, this.gso);

    }

    public void signOut() {


        Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(FLAG_ACTIVITY_NO_HISTORY);


        new AlertDialog.Builder(HomePageActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Logout").setMessage("Are you sure you want to Sign out").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    FirebaseAuth.getInstance().signOut();
                }

                if (signInAccount != null) {
                    gsc.signOut();
                }

                startActivity(intent);
                finish();
            }
        }).setNegativeButton("No", null).show();
    }

    public void onclick(View view) {
        TextView text = (TextView) view;
        text.setTextColor(getResources().getColor(R.color.white));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                text.setTextColor(getResources().getColor(R.color.colorPrimaryVariant));
            }
        }, 100);

        switch (view.getId()) {

            case R.id.edit_profile:
                startActivity(new Intent(HomePageActivity.this, UpdateProfileActivity.class));

                break;

            case R.id.settings:
                break;

            case R.id.feedback:
                break;

            case R.id.logout:
                signOut();
                break;

            case R.id.rate_us:
                break;

            default:
                onBackPressed();
        }

    }

    public void getUserProfile() {

        final UserProfile[] userProfile = {new UserProfile()};
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(USERS_DETAILS_KEY);

        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        DataSnapshot snapshot = task.getResult();

                        userProfile[0] = snapshot.getValue(UserProfile.class);
                        userProfile1 = userProfile[0];
                        Log.d("firebase", "this.userprofile showData: username  " + userProfile1.getUSER_Display_Name());
                        Log.d("firebase", "onComplete: returning");

                        return;
                    }
                } else {
                    Toast.makeText(HomePageActivity.this, "Some Error occured please restart the application", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }


}