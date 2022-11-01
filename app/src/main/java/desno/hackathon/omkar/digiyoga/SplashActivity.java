package desno.hackathon.omkar.digiyoga;

import static desno.hackathon.omkar.digiyoga.Constants.Constants.GOOGLE_SIGN_IN_REQUEST_CODE;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;


public class SplashActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    ImageView logo, google_authentification, twitter_authentification;
    TextView app_name, quote;
    ConstraintLayout splash_layout, after_splash_layout;

    Button login_button, register_button;
    Intent next_activity;
    FirebaseUser user;

    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    GoogleSignInAccount signInAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        logo = findViewById(R.id.splash_screen_logo);
        app_name = findViewById(R.id.app_name);
        quote = findViewById(R.id.splash_screen_quote);
        splash_layout = findViewById(R.id.splash_constrain);
        after_splash_layout = findViewById(R.id.after_splash_constrain);
        login_button = findViewById(R.id.login_button);
        register_button = findViewById(R.id.register_button);
        google_authentification = findViewById(R.id.google_logo);
        twitter_authentification = findViewById(R.id.twitter_logo);

        Animation zoomOut = AnimationUtils.loadAnimation(this, R.anim.splash_logo_animation);


        //zoom animation for
        logo.startAnimation(zoomOut);
        app_name.startAnimation(zoomOut);
        quote.startAnimation(zoomOut);

        //facebook callback manager
        callbackManager = CallbackManager.Factory.create();

        Intent intent = new Intent(SplashActivity.this, HomePageActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                user = FirebaseAuth.getInstance().getCurrentUser();
                initializeGoogleSignIn();
                signInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

                if (user != null) {
                    startActivity(intent);
                    finish();
                } else {
                    splash_layout.setVisibility(View.GONE);
                    after_splash_layout.setVisibility(View.VISIBLE);
                    login_button.startAnimation(zoomOut);
                    register_button.startAnimation(zoomOut);
                    google_authentification.startAnimation(zoomOut);
                    twitter_authentification.startAnimation(zoomOut);
                }
            }
        }, 3000);


        //on click listeners
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next_activity = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(next_activity);
            }
        });


        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next_activity = new Intent(SplashActivity.this, RegisterActivity.class);
                startActivity(next_activity);
            }
        });

        google_authentification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserByGoogle();
            }
        });

        twitter_authentification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(SplashActivity.this, Arrays.asList("public_profile"));
            }
        });


    }

    private void initializeGoogleSignIn() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, this.gso);
    }

    private void loginUserByGoogle() {

        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE);

        signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            Toast.makeText(this, "google name : " + signInAccount.getDisplayName(), Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                Toast.makeText(this, "logged in sucessfully", Toast.LENGTH_SHORT).show();

                navigateToNextActivity();
            } catch (ApiException e) {
                e.printStackTrace();
                Toast.makeText(this, "Some error occured", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void navigateToNextActivity() {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
        finish();
    }

    public void initializeFacebook() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }
}