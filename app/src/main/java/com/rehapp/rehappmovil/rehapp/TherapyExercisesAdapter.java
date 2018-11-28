package com.rehapp.rehappmovil.rehapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView ==null)
        {
            convertView = inflater.inflate(R.layout.list_view_item, parent,false);
            holder = new ViewHolder();
            holder.tvExerciseName=(TextView)convertView.findViewById(R.id.tvExerciseName);
            holder.ivCheckbox=(ImageView) convertView.findViewById(R.id.ivCheckbox);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
            TherapyExercise model = exercises.get(position);

            holder.tvExerciseName.setText(model.getExerciseName());

            if(model.isSelected())
            {
                holder.ivCheckbox.setBackgroundResource(R.drawable.checked);
            }else
            {
                holder.ivCheckbox.setBackgroundResource(R.drawable.unchecked);
            }
            return convertView;
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
    }
}
