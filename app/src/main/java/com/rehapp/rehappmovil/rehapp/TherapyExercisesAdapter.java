package com.rehapp.rehappmovil.rehapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.ExerciseRoutinesViewModel;
import com.rehapp.rehappmovil.rehapp.fragments.ExampleFragment;
import com.rehapp.rehappmovil.rehapp.fragments.TherapyExcerciseRoutineFragment;

import java.util.List;

public class TherapyExercisesAdapter  extends BaseAdapter{

    Activity activity;
    TherapyExercisesDialog dialog;
    List<ExerciseRoutinesViewModel> exercises;
    FragmentManager fragment;
    LayoutInflater inflater;


    public TherapyExercisesAdapter(Activity activity) {
        this.activity = activity;
    }

    public TherapyExercisesAdapter(Activity activity, List<ExerciseRoutinesViewModel> exercises, FragmentManager fragment,TherapyExercisesDialog dialog) {
        this.activity = activity;
        this.exercises = exercises;
        inflater = activity.getLayoutInflater();
        this.fragment=fragment;
        this.dialog=dialog;
    }


    @Override
    public int getCount() {
        return exercises.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if(view ==null)
        {
            view = inflater.inflate(R.layout.list_view_item, parent,false);
            holder = new ViewHolder();
            holder.tvExerciseName=(TextView)view.findViewById(R.id.tvExerciseName);
            holder.ivCheckbox=(ImageView) view.findViewById(R.id.ivCheckbox);
            holder.tvVideoUrl=(TextView)view.findViewById(R.id.tvExerciseUrl);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
            final ExerciseRoutinesViewModel model = exercises.get(position);

            holder.tvExerciseName.setText(model.getExerciseName());
            holder.tvVideoUrl.setText(PreferencesData.therapyDetailWatchVideo);

            holder.tvVideoUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    TherapyExcerciseRoutineFragment fragment=new TherapyExcerciseRoutineFragment();
                    Bundle extras = new Bundle();
                    extras.putString(PreferencesData.ExerciseRoutineUrl, model.getExercise_routine_url());
                    extras.putInt(PreferencesData.ExerciseRoutineId, model.getExercise_routine_id());
                    fragment.setArguments(extras);
                    loadFragment(new TherapyExcerciseRoutineFragment());

                }
            });

            if(model.isSelected())
            {
                holder.ivCheckbox.setBackgroundResource(R.drawable.checked);
            }else
            {
                holder.ivCheckbox.setBackgroundResource(R.drawable.unchecked);
            }
            return view;
        }

        public void updateRecords(List<ExerciseRoutinesViewModel> exercises)
        {
            this.exercises=exercises;
            notifyDataSetChanged();
        }

    class ViewHolder
    {
        TextView tvExerciseName;
        ImageView ivCheckbox;
        TextView tvVideoUrl;
    }


    public void loadFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=this.fragment.beginTransaction();
        fragmentTransaction.replace(R.id.content,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
