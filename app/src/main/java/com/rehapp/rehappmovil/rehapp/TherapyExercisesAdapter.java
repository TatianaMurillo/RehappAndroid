package com.rehapp.rehappmovil.rehapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.ExerciseRoutinesViewModel;
import com.rehapp.rehappmovil.rehapp.fragments.TherapyExcerciseRoutineFragment;

import java.util.List;

public class TherapyExercisesAdapter  extends BaseAdapter{

    Activity activity;
    List<ExerciseRoutinesViewModel> exercises;
    FragmentManager fragment;
    LayoutInflater inflater;
    Context context;

    public TherapyExercisesAdapter(Activity activity, List<ExerciseRoutinesViewModel> exercises, FragmentManager fragment,Context context) {
        this.activity = activity;
        this.exercises = exercises;
        inflater = activity.getLayoutInflater();
        this.fragment=fragment;
        this.context=context;
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
            holder.tvExerciseName=view.findViewById(R.id.tvExerciseName);
            holder.ivCheckbox= view.findViewById(R.id.ivCheckbox);
            holder.tvVideoUrl=view.findViewById(R.id.tvExerciseUrl);
            holder.ibtnDelete=view.findViewById(R.id.ibtnDelete);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
            final ExerciseRoutinesViewModel model = exercises.get(position);

            holder.tvExerciseName.setText(model.getExerciseName());

            holder.tvVideoUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    TherapyExcerciseRoutineFragment fragment=new TherapyExcerciseRoutineFragment();
                    Bundle extras = new Bundle();
                    extras.putString(PreferencesData.ExerciseRoutineUrl, model.getExercise_routine_url());
                    extras.putInt(PreferencesData.ExerciseRoutineId, model.getExercise_routine_id());
                    fragment.setArguments(extras);
                    loadFragment(fragment);

                }
            });
            holder.ibtnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isDelete("Al borrar este elemento se borrará todo el detalle relacionado.");
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
        ImageButton ivCheckbox;
        ImageButton tvVideoUrl;
        ImageButton ibtnDelete;
    }


    public void loadFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=this.fragment.beginTransaction();
        fragmentTransaction.replace(R.id.content,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    public void isDelete(String alertmessage) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(alertmessage)
                .setPositiveButton("De acuerdo.", dialogClickListener).show();

    }
}
