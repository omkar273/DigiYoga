package desno.hackathon.omkar.digiyoga;

import static desno.hackathon.omkar.digiyoga.Constants.Constants.MEETING_ID_TO_JOIN;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallConfig;
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallFragment;

public class ConferenceActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    long appID = 986476302;
    String appSign = "a0fea4bd783f33bd841f263c5ead84ed3a7b244eabc1441efa43072028245e94";
    String callID;
    String userID;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        callID = getIntent().getExtras().get(MEETING_ID_TO_JOIN).toString();
        userID = user.getUid();
        userName = userID + user.getDisplayName();
        addFragment();

    }

//    public void addFragment() {
//        long appID = 986476302;
//        String appSign = "a0fea4bd783f33bd841f263c5ead84ed3a7b244eabc1441efa43072028245e94";
//
//        String conferenceID = "123456";
//        String userID = Build.MANUFACTURER + "";
//        String userName = user.getDisplayName();
//
//        ZegoUi config = new ();
//        ZegoUIKitPrebuiltVideoConferenceFragment fragment = ZegoUIKitPrebuiltVideoConferenceFragment.newInstance(appID, appSign, userID, userName, conferenceID, config);
//
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commitNow();
//    }

    public void addFragment() {
        ZegoUIKitPrebuiltCallConfig config = ZegoUIKitPrebuiltCallConfig.groupVideoCall();
        ZegoUIKitPrebuiltCallFragment fragment = ZegoUIKitPrebuiltCallFragment.newInstance(appID, appSign, userID, userName, callID, config);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commitNow();
    }
}