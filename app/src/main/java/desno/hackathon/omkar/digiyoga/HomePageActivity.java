package desno.hackathon.omkar.digiyoga;

import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        initializeGoogleSignIn();

//        welcome_text = findViewById(R.id.welcome_text);
//        email_text = findViewById(R.id.email_text);
//        logout = findViewById(R.id.logOut_button);
        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);

        signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
//            Toast.makeText(this, "google name : " + signInAccount.getDisplayName(), Toast.LENGTH_SHORT).show();
//            welcome_text.setText("welcome  " + signInAccount.getDisplayName());
//            email_text.setText("email :" + signInAccount.getEmail());
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commitNow();
        bottomNavigationView.setSelectedItemId(R.id.Home);
//
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signOut();
//            }
//        });


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
                        selectorFragment = new HomeFragment();
                        break;

                    case R.id.Plan:
                        selectorFragment = new PlanFragment();
                        break;

                    case R.id.profile:
                        selectorFragment = new ProfileFragment();
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
                }
        ).setNegativeButton("No", null).show();
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
}