package desno.hackathon.omkar.digiyoga;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import io.agora.agorauikit_android.AgoraConnectionData;
import io.agora.agorauikit_android.AgoraSettings;
import io.agora.agorauikit_android.AgoraVideoViewer;
import io.agora.agorauikit_android.DevicePermissionsKt;
import io.agora.rtc2.Constants;

public class LiveMeetActivity extends AppCompatActivity {

    // Fill the App ID of your project generated on Agora Console.
    private final String appId = "64505be134b548af88fc3c0b08c03731";
    // Fill the channel name.
    private final String channelName = "omkar";
    // Fill the temp token generated on Agora Console.
    private final String token = "<007eJxTYAhr271GcXdkhVZd8baDCzadmnNRRoKXpfbZ5wyZVzfqUs8rMJiZmBqYJqUaGpskmZpYJKZZWKQlGycbJBlYJBsYmxsbuu1NSm4IZGTIv5HAysgAgSA+K0N+bnZiEQMDAKMIIHE=>";
    // Object of AgoraVideoVIewer class
    private AgoraVideoViewer agView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_meet);
        initializeAndJoinChannel();
    }

    private void initializeAndJoinChannel() {
        // Create AgoraVideoViewer instance
        try {
            agView = new AgoraVideoViewer(this, new AgoraConnectionData(appId, token), AgoraVideoViewer.Style.FLOATING, new AgoraSettings(), null);
        } catch (Exception e) {
            Log.e("AgoraVideoViewer",
                    "Could not initialize AgoraVideoViewer. Check that your app Id is valid.");
            Log.e("Exception", e.toString());
            return;
        }


        // Add the AgoraVideoViewer to the Activity layout
        this.addContentView(agView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT)
        );
        // Check permission and join a channel
        if (DevicePermissionsKt.requestPermissions(AgoraVideoViewer.Companion, this)) {
            joinChannel();
        } else {
            Button joinButton = new Button(this);
            joinButton.setText("Allow camera and microphone access, then click here");
            joinButton.setOnClickListener(new View.OnClickListener() {
                // When the button is clicked, check permissions again and join channel
                @Override
                public void onClick(View view) {
                    if (DevicePermissionsKt.requestPermissions(AgoraVideoViewer.Companion, getApplicationContext())) {
                        ((ViewGroup) joinButton.getParent()).removeView(joinButton);
                        joinChannel();
                    }
                }
            });
            this.addContentView(joinButton, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 200));
        }
    }

    void joinChannel() {
        agView.join(channelName, token, Constants.CLIENT_ROLE_BROADCASTER, 0);
    }
}