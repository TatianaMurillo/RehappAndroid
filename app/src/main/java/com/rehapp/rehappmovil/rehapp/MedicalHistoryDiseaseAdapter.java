package com.rehapp.rehappmovil.rehapp;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rehapp.rehappmovil.rehapp.Models.DiseaseViewModel;
import com.rehapp.rehappmovil.rehapp.Models.ExerciseRoutinesViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;

import java.util.List;

public class MedicalHistoryDiseaseAdapter extends BaseAdapter{

    Activity activity;
    List<DiseaseViewModel> diseases;
    LayoutInflater inflater;


    public MedicalHistoryDiseaseAdapter(Activity activity) {
        this.activity = activity;
    }

    public MedicalHistoryDiseaseAdapter(Activity activity, List<DiseaseViewModel> diseases) {
        this.activity = activity;
        this.diseases = diseases;
        inflater = activity.getLayoutInflater();
    }


    @Override
    public int getCount() {
        return diseases.size();
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
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if(view ==null)
        {
            view = inflater.inflate(R.layout.list_view_item_diseases, parent,false);
            holder = new ViewHolder();
            holder.tvDiseaseName=(TextView)view.findViewById(R.id.tvDiseaseName);
            holder.ivCheckbox=(ImageView) view.findViewById(R.id.ivCheckbox);
            holder.ivIsBase=(ImageView) view.findViewById(R.id.ivIsBase);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        DiseaseViewModel model = diseases.get(position);

            holder.tvDiseaseName.setText(model.getDisease_name());

            if(model.isSelected())
            {
                holder.ivCheckbox.setBackgroundResource(R.drawable.checked);
            }else
            {
                holder.ivCheckbox.setBackgroundResource(R.drawable.unchecked);
            }

            if ("0".equals(model.getPatient_disease_is_base())) {
                holder.ivIsBase.setBackgroundResource(R.drawable.checked);
            } else {
                holder.ivIsBase.setBackgroundResource(R.drawable.unchecked);
            }

            holder.ivIsBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiseaseViewModel model = diseases.get(position);
                if ("0".equals(model.getPatient_disease_is_base())) {
                    model.setPatient_disease_is_base("1");
                } else {
                    model.setPatient_disease_is_base("0");
                }
                diseases.set(position, model);
                updateRecords(diseases);
            }
        });

            return view;
        }

        public void updateRecords(List<DiseaseViewModel> diseases)
        {
            this.diseases=diseases;
            notifyDataSetChanged();
        }

    class ViewHolder
    {
        TextView tvDiseaseName;
        ImageView ivCheckbox;
        ImageView ivIsBase;
    }
}
