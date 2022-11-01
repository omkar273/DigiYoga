package desno.hackathon.omkar.digiyoga;

import static desno.hackathon.omkar.digiyoga.Constants.Constants.GOOGLE_SIGN_IN_REQUEST_CODE;
import static desno.hackathon.omkar.digiyoga.Constants.Constants.USERS_DETAILS_KEY;
import static desno.hackathon.omkar.digiyoga.Constants.Constants.USER_PASSWORD_KEY;
import static desno.hackathon.omkar.digiyoga.Constants.Constants.USER_PROFILE_DISPLAY_NAME_KEY;
import static desno.hackathon.omkar.digiyoga.Constants.Constants.USER_PROFILE_DOB_KEY;
import static desno.hackathon.omkar.digiyoga.Constants.Constants.USER_PROFILE_EMAIL_KEY;
import static desno.hackathon.omkar.digiyoga.Constants.Constants.USER_PROFILE_IMAGE_URL_KEY;
import static desno.hackathon.omkar.digiyoga.Constants.Constants.USER_PROFILE_PHONE_KEY;
import static desno.hackathon.omkar.digiyoga.Constants.Constants.USER_UID_KEY;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    String user_password, user_email;
    EditText editText_email, editText_password;
    Button login_button;
    ProgressDialog progressDialog;
    TextView forgot_password;
    ImageView google_authentification, twitter_authentification;
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    GoogleSignInAccount signInAccount;
    FirebaseAuth firebaseAuth;


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
        firebaseAuth = FirebaseAuth.getInstance();
        initializeGoogleSignIn();

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_email = editText_email.getText().toString().trim();
                user_password = editText_password.getText().toString().trim();
                loginUserByEmailAndPassword(user_email, user_password);
            }
        });

        google_authentification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserByGoogle();
            }
        });

    }


    private void loginUserByEmailAndPassword(String user_email, String user_password) {
        progressDialog.setMessage("Please wait ...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


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
            firebaseAuth.signInWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {

                            Toast.makeText(LoginActivity.this, "Logged In successfully", Toast.LENGTH_SHORT).show();
                            navigateToNextActivity();
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

    }


    private void initializeGoogleSignIn() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, this.gso);

    }

    private void loginUserByGoogle() {

        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE);
        Log.d("google1", "loginUserByGoogle: inside method");

        signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null) {
            Toast.makeText(this, "google name : " + signInAccount.getDisplayName(), Toast.LENGTH_SHORT).show();
            Log.d("google1", "loginUserByGoogle: google account non null");
        } else {
            Log.d("google1", "loginUserByGoogle: google account null");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Log.d("google1", "onActivityResult: got in");
            try {
                task.getResult(ApiException.class);
                signInAccount = task.getResult();
                Log.d("google1", "onActivityResult: task completed sucessfully" + signInAccount.getDisplayName());

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(signInAccount.getEmail(), signInAccount.getId()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            progressDialog.setMessage("Please wait...");
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.show();


                            Log.d("google1", "onComplete: Task Sucessfull");
                            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(signInAccount.getDisplayName()).build();
                            FirebaseAuth.getInstance().getCurrentUser().updateProfile(userProfileChangeRequest);


                            HashMap<String, String> userProfile = new HashMap<>();
                            userProfile.put(USER_UID_KEY, FirebaseAuth.getInstance().getCurrentUser().getUid());
                            userProfile.put(USER_PROFILE_DISPLAY_NAME_KEY, signInAccount.getDisplayName());
                            userProfile.put(USER_PROFILE_PHONE_KEY, "Not Updated");
                            userProfile.put(USER_PROFILE_EMAIL_KEY, signInAccount.getEmail());
                            userProfile.put(USER_PROFILE_DOB_KEY, "Not updated");
                            userProfile.put(USER_PASSWORD_KEY, signInAccount.getId());
                            userProfile.put(USER_PROFILE_IMAGE_URL_KEY, "null");

                            FirebaseDatabase.getInstance().getReference().child(USERS_DETAILS_KEY).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Log.d("google1", "Data entered");

                                        gsc.signOut();
                                        Toast.makeText(LoginActivity.this, "logged in sucessfully ", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                        navigateToNextActivity();
                                    } else {
                                        Log.d("google1", "onComplete: Couldnt enter data in database refernce");
                                    }

                                }
                            });


                        } else {
                            Log.d("google1", "onComplete: Couldnt create user in authentification");
                            FirebaseAuth.getInstance().signInWithEmailAndPassword(signInAccount.getEmail(), signInAccount.getId()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Log.d("google1", "onSuccess: sign in sucess");
                                    gsc.signOut();
                                    Toast.makeText(LoginActivity.this, "logged in sucessfully ", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    navigateToNextActivity();
                                }
                            });
                        }
                    }
                });
//                navigateToNextActivity();
            } catch (ApiException e) {
                e.printStackTrace();
                Toast.makeText(this, "Some error occured", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void navigateToNextActivity() {
        Intent intent = new Intent(LoginActivity.this, HomePageActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}

