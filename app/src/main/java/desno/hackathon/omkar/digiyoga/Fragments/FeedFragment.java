package desno.hackathon.omkar.digiyoga.Fragments;

import static desno.hackathon.omkar.digiyoga.Constants.Constants.USERS_DETAILS_KEY;
import static desno.hackathon.omkar.digiyoga.Constants.Constants.USER_PROFILE_DISPLAY_NAME_KEY;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import desno.hackathon.omkar.digiyoga.ModalClass.UserProfile;
import desno.hackathon.omkar.digiyoga.R;
import desno.hackathon.omkar.digiyoga.YogaWorkoutAdapter.SearchUserAdapter;

public class FeedFragment extends Fragment {

    SearchView searchView;
    RecyclerView recyclerView;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseUser user;

    SearchUserAdapter adapter;

    public FeedFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        searchView = view.findViewById(R.id.search_view);
        recyclerView = view.findViewById(R.id.search_recycler_view);


        FirebaseRecyclerOptions<UserProfile> options = new FirebaseRecyclerOptions.Builder<UserProfile>().setQuery(databaseReference.child(USERS_DETAILS_KEY).orderByChild(USER_PROFILE_DISPLAY_NAME_KEY), UserProfile.class).build();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SearchUserAdapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.GONE);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recyclerView.setVisibility(View.VISIBLE);
                processSearch(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerView.setVisibility(View.VISIBLE);
                processSearch(newText);
                return true;
            }
        });

        return view;
    }

    private void processSearch(String query) {
        query = query.toUpperCase();
        FirebaseRecyclerOptions<UserProfile> options = new FirebaseRecyclerOptions.Builder<UserProfile>().setQuery(databaseReference.child(USERS_DETAILS_KEY).orderByChild(USER_PROFILE_DISPLAY_NAME_KEY).startAt(query).endAt(query + "\uf8ff"), UserProfile.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SearchUserAdapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
        adapter.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        adapter.notifyDataSetChanged();
        adapter.stopListening();
    }


}