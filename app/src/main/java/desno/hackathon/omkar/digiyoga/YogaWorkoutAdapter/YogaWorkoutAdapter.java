package desno.hackathon.omkar.digiyoga.YogaWorkoutAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import desno.hackathon.omkar.digiyoga.ModalClass.YogaWorkoutPlans;
import desno.hackathon.omkar.digiyoga.R;

public class YogaWorkoutAdapter extends FirebaseRecyclerAdapter<YogaWorkoutPlans, YogaWorkoutAdapter.myViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public YogaWorkoutAdapter(@NonNull FirebaseRecyclerOptions<YogaWorkoutPlans> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull YogaWorkoutPlans model) {
        holder.yoga_plan_name.setText(model.getYoga_Plan_Name());
        holder.yoga_plan_description.setText(model.getYoga_Plan_Description());
        holder.yoga_plan_duration.setText(model.getYoga_Plan_Estimated_Days());

        Glide.with(holder.backgroundImg.getContext()).load(model.getYoga_Plan_Image_Url()).into(holder.backgroundImg);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.yoga_workout_plan_recycler_layout, parent, false);

        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        ImageView backgroundImg;
        TextView yoga_plan_name, yoga_plan_description, yoga_plan_duration;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            backgroundImg = itemView.findViewById(R.id.backgroundImg);
            yoga_plan_description = itemView.findViewById(R.id.yoga_plan_description);
            yoga_plan_duration = itemView.findViewById(R.id.yoga_plan_duration);
            yoga_plan_name = itemView.findViewById(R.id.yoga_plan_name);


        }
    }
}
