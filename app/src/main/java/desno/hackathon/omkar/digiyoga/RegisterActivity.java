package desno.hackathon.omkar.digiyoga;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText input_full_name, input_phone_no, input_password, input_confirm_password, input_email;
    TextView already_have_account;
    Button register;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference mRootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        HashMap<String, String> map = new HashMap<>();
        map.put("key", "i am value");
//        FirebaseDatabase.getInstance().getReference().child("omkar").child("omkar child").setValue(map);


        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        input_full_name = findViewById(R.id.tie_register_full_name);
        input_phone_no = findViewById(R.id.tie_register_mobile);
        input_email = findViewById(R.id.tie_register_email);
        input_password = findViewById(R.id.tie_register_password);
        input_confirm_password = findViewById(R.id.tie_register_confirm_password);


        register = findViewById(R.id.register_button);
        already_have_account = findViewById(R.id.already_have_an_account);


        /*
         *TODO : create a register function combining both of these function
         * Onclick listners =>
         */

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAuthentication();
            }
        });

        already_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);

            }
        });

    }


    /*
     *
     * Different functions
     *
     */

    private void performAuthentication() {
        String full_name, phone_no, password, confirm_password, email;

        full_name = input_full_name.getText().toString();
        phone_no = input_phone_no.getText().toString();
        password = input_password.getText().toString();
        confirm_password = input_confirm_password.getText().toString();
        email = input_email.getText().toString();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Enter valid Email address !", Toast.LENGTH_SHORT).show();

            input_email.setError("Enter valid Email address !");
            input_email.requestFocus();
        } else if (password.length() < 6 || password.isEmpty()) {
            input_password.setError("Enter valid Password!");
            input_password.requestFocus();
            input_email.setError(null);

        } else if (!password.equals(confirm_password)) {
            input_confirm_password.setError("Both password do not matches");
            input_confirm_password.requestFocus();
            input_password.setError(null);

        } else {

            input_email.setError(null);
            input_password.setError(null);
            input_confirm_password.setError(null);

            progressDialog.setMessage("Please wait while registartion...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        // Sign in success, update UI with the signed-in user's information
                        Log.d("firebase", "createUserWithEmail:success");
                        user = firebaseAuth.getCurrentUser();
                        registerUser(full_name, phone_no, email, password);

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        progressDialog.dismiss();

                        Toast.makeText(RegisterActivity.this, "Registration failed.  " + task.getException(), Toast.LENGTH_SHORT).show();
//                                updateUI(null);
                    }
                }
            });


        }
    }

    public void registerUser(String full_name, String phone_no, String email, String password) {

        mRootRef = FirebaseDatabase.getInstance().getReference();
        HashMap<String, String> userProfile = new HashMap<>();
        userProfile.put("name", full_name);
        userProfile.put("phone_no", phone_no);
        userProfile.put("email", email);
        userProfile.put("password", password);
        userProfile.put("uId", user.getUid());


        mRootRef.child("UserDetails").child(firebaseAuth.getCurrentUser().getUid()).setValue(userProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@androidx.annotation.NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Registration completed", Toast.LENGTH_SHORT).show();
                } else if (task.isCanceled()) {
                    Toast.makeText(RegisterActivity.this, "Registration could not be completed due to some errors", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Some error occured please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sendVerificationEmail();

    }

    public void sendVerificationEmail() {
        progressDialog.dismiss();
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("firebase", "Email sent.");
                    Toast.makeText(RegisterActivity.this, "verification email sent on " + user.getEmail(), Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();

                }
            }
        }).addOnFailureListener(RegisterActivity.this, new OnFailureListener() {
            @Override
            public void onFailure(@androidx.annotation.NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "could not send email", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
