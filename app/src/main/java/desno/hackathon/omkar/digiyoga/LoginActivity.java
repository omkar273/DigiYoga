package desno.hackathon.omkar.digiyoga;

import static desno.hackathon.omkar.digiyoga.Constants.Constants.GOOGLE_SIGN_IN_REQUEST_CODE;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    String user_password, user_email;
    EditText editText_email, editText_password;
    Button login_button;
    ProgressDialog progressDialog;
    TextView forgot_password;
    ImageView google_authentification, twitter_authentification;
    FirebaseAuth mAuth;
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    GoogleSignInAccount signInAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText_email = findViewById(R.id.tie_login_email);
        editText_password = findViewById(R.id.tie_login_password);
        login_button = findViewById(R.id.login_button);
        forgot_password = findViewById(R.id.forget_password);
        google_authentification = findViewById(R.id.google_logo);
        twitter_authentification = findViewById(R.id.twitter_logo);
        progressDialog = new ProgressDialog(this);


        //firebase initializing
        mAuth = FirebaseAuth.getInstance();
        initializeGoogleSignIn();

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserByEmailAndPassword();
            }
        });

        google_authentification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserByGoogle();
            }
        });

    }


    private void loginUserByEmailAndPassword() {
        progressDialog.setMessage("Please wait ...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        user_email = editText_email.getText().toString().trim();
        user_password = editText_password.getText().toString().trim();


        if (TextUtils.isEmpty(user_email) && TextUtils.isEmpty(user_password)) {

            progressDialog.dismiss();
            editText_email.setError("Please enter a valid email");
//            editText_password.setError("Please enter password");
            Toast.makeText(this, "Empty credentials", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(user_email)) {

            editText_password.setError(null);
            progressDialog.dismiss();
            editText_email.setError("Please enter a valid email");

        } else if (TextUtils.isEmpty(user_password)) {
            editText_email.setError(null);
            progressDialog.dismiss();
            editText_password.setError("Please enter password");
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {

                            Toast.makeText(LoginActivity.this, "Logged In successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, HomePageActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Email not verified", Toast.LENGTH_SHORT).show();
                        }

                    } else if (task.isCanceled()) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            });
        }

        loginUserByGoogle();
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


}