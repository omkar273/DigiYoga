package desno.hackathon.omkar.digiyoga;

import static desno.hackathon.omkar.digiyoga.Constants.Constants.PROFILE_IMAGE_UPDATE_REQUEST_CODE;
import static desno.hackathon.omkar.digiyoga.Constants.Constants.USERS_DETAILS_KEY;
import static desno.hackathon.omkar.digiyoga.Constants.Constants.USERS_DP_STORAGE_SECTION_KEY;
import static desno.hackathon.omkar.digiyoga.Constants.Constants.USER_PROFILE_DISPLAY_NAME_KEY;
import static desno.hackathon.omkar.digiyoga.Constants.Constants.USER_PROFILE_DOB_KEY;
import static desno.hackathon.omkar.digiyoga.Constants.Constants.USER_PROFILE_IMAGE_URL_KEY;
import static desno.hackathon.omkar.digiyoga.Constants.Constants.USER_PROFILE_PHONE_KEY;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import desno.hackathon.omkar.digiyoga.ModalClass.UserProfile;

public class UpdateProfileActivity extends AppCompatActivity {


    ImageView profileImage;
    EditText userName, dob, mobile;

    DatabaseReference databaseReference;
    StorageReference storageReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    Uri filepath;
    String Uid;
    Bitmap bitmap;

    Button Update_button;
    TextView edit_profile_pic;
    ProgressDialog progressDialog;

    String profilePicUrl;
    UserProfile userProfile1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        mobile = findViewById(R.id.tie_mobile);
        userName = findViewById(R.id.tie_username);
        dob = findViewById(R.id.tie_dob);
        profileImage = findViewById(R.id.profile_image);
        Update_button = findViewById(R.id.Update_button);
        edit_profile_pic = findViewById(R.id.edit_profile_pic);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference(USERS_DETAILS_KEY);
        storageReference = FirebaseStorage.getInstance().getReference(USERS_DP_STORAGE_SECTION_KEY);
        Uid = user.getUid();

        userProfile1 = getUserProfile();


        progressDialog = new ProgressDialog(this);
        edit_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, PROFILE_IMAGE_UPDATE_REQUEST_CODE);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
            }
        });
        Map<String, Object> updateData = new HashMap<>();

        Update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filepath != null) {

                    StorageReference uploader = storageReference.child(user.getUid() + ".png");
                    uploader.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(UpdateProfileActivity.this, uri.toString(), Toast.LENGTH_SHORT).show();
                                    updateData.put(USER_PROFILE_IMAGE_URL_KEY, uri.toString());

                                }
                            });
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            float percentage = (snapshot.getBytesTransferred() * 100) / snapshot.getTotalByteCount();
                            progressDialog.setMessage("uploading " + percentage + " %");
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(UpdateProfileActivity.this, "profile pic update failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                updateData.put(USER_PROFILE_DISPLAY_NAME_KEY, userName.getText().toString());
                updateData.put(USER_PROFILE_PHONE_KEY, mobile.getText().toString());
                updateData.put(USER_PROFILE_DOB_KEY, dob.getText().toString());

                UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(userName.getText().toString()).build();
                firebaseAuth.getCurrentUser().updateProfile(userProfileChangeRequest);


                databaseReference.child(user.getUid()).updateChildren(updateData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(UpdateProfileActivity.this, "Profile updated successfully \nPlease restart the appplication for changes", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateProfileActivity.this, "Profile could not be updated", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PROFILE_IMAGE_UPDATE_REQUEST_CODE) {
            filepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                profileImage.setImageBitmap(bitmap);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public UserProfile getUserProfile() {
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
                        mobile.setText(userProfile1.getUSER_Phone());
                        userName.setText(userProfile1.getUSER_Display_Name());

                        if (userProfile1.getUSER_Dob().equals("Not updated")) {
                            dob.setHint(userProfile1.getUSER_Dob());

                        } else {
                            dob.setText(userProfile1.getUSER_Dob());
                        }
                        if (!userProfile1.getUSER_Profile_Image_URl().equals("null")) {
                            Glide.with(UpdateProfileActivity.this).load(userProfile1.getUSER_Profile_Image_URl()).into(profileImage);
                        }
                    }
                } else {
                    Toast.makeText(UpdateProfileActivity.this, "Some Error occured please restart the application", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        return userProfile[0];
    }

}