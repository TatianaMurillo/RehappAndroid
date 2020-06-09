package com.rehapp.rehappmovil.rehapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.rehapp.rehappmovil.rehapp.Models.ExerciseRoutinesViewModel;


import java.util.List;

public class TherapyRoutinesListAdapter extends RecyclerView.Adapter<TherapyRoutinesListAdapter.TherapyRoutineListItemHolder> {

    List<ExerciseRoutinesViewModel> exercises;
    Activity activity;
    FragmentManager fragment;
    LayoutInflater inflater;
    OnVideoClickListener onVideoClickListener;

    public TherapyRoutinesListAdapter() { }

    public TherapyRoutinesListAdapter(Activity activity, List<ExerciseRoutinesViewModel> exercises, FragmentManager fragment, OnVideoClickListener onVideoClickListener) {
        this.exercises = exercises;
        this.activity = activity;
        this.exercises = exercises;
        inflater = activity.getLayoutInflater();
        this.fragment=fragment;
        this.onVideoClickListener=onVideoClickListener;
    }


    @NonNull
    @Override
    public TherapyRoutinesListAdapter.TherapyRoutineListItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_view_item,viewGroup,false);

        return  new TherapyRoutinesListAdapter.TherapyRoutineListItemHolder(view,onVideoClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TherapyRoutinesListAdapter.TherapyRoutineListItemHolder therapyRoutineListItem, int i) {
        therapyRoutineListItem.tvExerciseName.setText(exercises.get(i).getExercise_routine_name());
        if(exercises.get(i).isSelected())
        {
            therapyRoutineListItem.ivCheckbox.setBackgroundResource(R.drawable.checked);
        }else
        {
            therapyRoutineListItem.ivCheckbox.setBackgroundResource(R.drawable.unchecked);
        }
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }


    public class TherapyRoutineListItemHolder extends RecyclerView.ViewHolder{

        TextView tvExerciseName;
        ImageButton ivCheckbox;
        ImageButton tvRoutineDetail;
        ImageButton ibtnDelete;
        OnVideoClickListener onVideoClickListener;

        public TherapyRoutineListItemHolder(View itemView,final OnVideoClickListener onVideoClickListener){
            super(itemView);



           tvExerciseName=itemView.findViewById(R.id.tvExerciseName);
           ivCheckbox= itemView.findViewById(R.id.ivCheckbox);
           tvRoutineDetail=itemView.findViewById(R.id.tvExerciseUrl);
           ibtnDelete=itemView.findViewById(R.id.ibtnDelete);



            this.onVideoClickListener=onVideoClickListener;

            ivCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    onVideoClickListener.onVideoClick(position,1);
                }
            });

            tvRoutineDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position=getAdapterPosition();
                    onVideoClickListener.onVideoClick(position,2);
                }
            });

            ibtnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    onVideoClickListener.onVideoClick(position,3);
                }
            });

        }
    }

    public void updateRecords(List<ExerciseRoutinesViewModel> exercises)
    {
        this.exercises=exercises;
        notifyDataSetChanged();
    }


    public interface  OnVideoClickListener{
        void onVideoClick(int position, int option);
    }
}
