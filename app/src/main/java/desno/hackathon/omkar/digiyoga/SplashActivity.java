package desno.hackathon.omkar.digiyoga;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


public class SplashActivity extends AppCompatActivity {
    ImageView logo, google_authentification, twitter_authentification;
    TextView app_name, quote;
    ConstraintLayout splash_layout, after_splash_layout;

    Button login_button, register_button;
    Intent next_activity;

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


        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                splash_layout.setVisibility(View.GONE);
                after_splash_layout.setVisibility(View.VISIBLE);
//                startActivity(intent);
//                finish();
                login_button.startAnimation(zoomOut);
                register_button.startAnimation(zoomOut);
                google_authentification.startAnimation(zoomOut);
                twitter_authentification.startAnimation(zoomOut);
            }
        }, 4000);


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

    }
}