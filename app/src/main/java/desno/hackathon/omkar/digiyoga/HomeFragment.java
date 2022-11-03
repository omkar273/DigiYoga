package desno.hackathon.omkar.digiyoga;

import static desno.hackathon.omkar.digiyoga.Constants.Constants.YOGA_WORKOUT_SECTION;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import desno.hackathon.omkar.digiyoga.ModalClass.YogaWorkoutPlans;
import desno.hackathon.omkar.digiyoga.YogaWorkoutAdapter.YogaWorkoutAdapter;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    YogaWorkoutAdapter adapter;

    TextView greeting_text, yoga_quote;
    String userName, Quote;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseUser user;

    public HomeFragment() {
        // Required empty public constructor

    }

    public HomeFragment(String userName, String quote) {
        this.userName = userName;
        Quote = quote;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        user = firebaseAuth.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.yoga_workout_recyclerview);
        greeting_text = view.findViewById(R.id.greeting_text);
        yoga_quote = view.findViewById(R.id.yoga_quote);

        greeting_text.setText("Welcome " + user.getDisplayName());
//        yoga_quote.setText();

        FirebaseRecyclerOptions<YogaWorkoutPlans> options = new FirebaseRecyclerOptions.Builder<YogaWorkoutPlans>().setQuery(databaseReference.child(YOGA_WORKOUT_SECTION), YogaWorkoutPlans.class).build();


        adapter = new YogaWorkoutAdapter(options);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}