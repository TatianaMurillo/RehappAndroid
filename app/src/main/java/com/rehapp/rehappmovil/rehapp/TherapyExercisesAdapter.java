package com.rehapp.rehappmovil.rehapp;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapyExercise;

import java.util.List;

public class TherapyExercisesAdapter  extends BaseAdapter{

    Activity activity;
    List<TherapyExercise> exercises;
    LayoutInflater inflater;


    public TherapyExercisesAdapter(Activity activity) {
        this.activity = activity;
    }

    public TherapyExercisesAdapter(Activity activity, List<TherapyExercise> exercises) {
        this.activity = activity;
        this.exercises = exercises;
        inflater = activity.getLayoutInflater();
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
            TherapyExercise model = exercises.get(position);

            holder.tvExerciseName.setText(model.getExerciseName());
            holder.tvVideoUrl.setText(PreferencesData.therapyDetailWatchVideo);

            holder.tvVideoUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, TherapyExerciseDetail.class);
                    activity.startActivity(intent);
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

        public void updateRecords(List<TherapyExercise> exercises)
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
}
