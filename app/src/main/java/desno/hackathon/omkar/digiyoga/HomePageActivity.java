package desno.hackathon.omkar.digiyoga;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseAuth.getInstance().signOut();
        } else if (signInAccount != null) {
            gsc.signOut();
        }

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void onclick(View view) {
        TextView text = (TextView) view;
        text.setTextColor(getResources().getColor(R.color.white));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                text.setTextColor(getResources().getColor(R.color.black));

            }
        }, 100);
    }
}