package desno.hackathon.omkar.digiyoga;

import static desno.hackathon.omkar.digiyoga.Constants.Constants.MEETING_ID_TO_JOIN;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class JoinMeetingActivity extends AppCompatActivity {
    EditText meetingId;
    Button joinMeet, createMeet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_meeting);

        meetingId = findViewById(R.id.meetingId);
        joinMeet = findViewById(R.id.joinMeetingBtn);
        createMeet = findViewById(R.id.createMeetingBtn);

        joinMeet.setOnClickListener(view -> {
            startMeet();
        });


        createMeet.setOnClickListener(view -> {
            startMeet();
        });

    }

    private void startMeet() {
        String meetingIDString = "123456";
        Bundle meetID = new Bundle();
        if (!meetingId.getText().toString().isEmpty()) {
            meetingIDString = meetingId.getText().toString();
        }

        meetID.putString(MEETING_ID_TO_JOIN, meetingIDString);
        Intent intent = new Intent(JoinMeetingActivity.this, ConferenceActivity.class);
        intent.putExtras(meetID);
        Toast.makeText(this, "Meeting ID :" + meetingIDString, Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }
}