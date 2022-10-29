package desno.hackathon.omkar.digiyoga;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        initializeGoogleSignIn();

        welcome_text = findViewById(R.id.welcome_text);
        email_text = findViewById(R.id.email_text);
        logout = findViewById(R.id.logOut_button);

        signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
//            Toast.makeText(this, "google name : " + signInAccount.getDisplayName(), Toast.LENGTH_SHORT).show();
            welcome_text.setText("welcome  " + signInAccount.getDisplayName());
            email_text.setText("email :" + signInAccount.getEmail());
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
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
}