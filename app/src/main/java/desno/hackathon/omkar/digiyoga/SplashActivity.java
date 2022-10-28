package desno.hackathon.omkar.digiyoga;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {
    ImageView logo;
    TextView app_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        logo = findViewById(R.id.splash_screen_logo);
        app_name = findViewById(R.id.app_name);

        Animation zoomOut = AnimationUtils.loadAnimation(this, R.anim.splash_logo_animation);
        logo.startAnimation(zoomOut);
        app_name.startAnimation(zoomOut);

        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 4000);

    }
}