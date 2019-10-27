package com.rehapp.rehappmovil.rehapp;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rehapp.rehappmovil.rehapp.Models.ExerciseRoutinesViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PatientMedicalHistoryViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;

import java.util.ArrayList;
import java.util.List;

public class MedicalHistoriesAdapter extends BaseAdapter{

    Activity activity;
    ArrayList<PatientMedicalHistoryViewModel> medicalHistories;
    LayoutInflater inflater;


    public MedicalHistoriesAdapter(Activity activity) {
        this.activity = activity;
    }

    public MedicalHistoriesAdapter(Activity activity, ArrayList<PatientMedicalHistoryViewModel> medicalHistories) {
        this.activity = activity;
        this.medicalHistories = medicalHistories;
        inflater = activity.getLayoutInflater();
    }


    @Override
    public int getCount() {
        return medicalHistories.size();
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
            view = inflater.inflate(R.layout.list_view_medical_history_item, parent,false);
            holder = new ViewHolder();

            holder.tvMedicalHistoryName=view.findViewById(R.id.tvMedicalHistoryName);
            holder.tvCreatedAt=view.findViewById(R.id.tvCreatedAt);
            holder.tvUpdatedAt=view.findViewById(R.id.tvUpdatedAt);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        PatientMedicalHistoryViewModel model = medicalHistories.get(position);
        String createdAtText="Created at: ";
        String updatedAtText="Last time updated: ";
        String noDataText="No data. ";

            holder.tvMedicalHistoryName.setText(model.getPtnt_mdcl_hstry_name());

            if(model.getCreated_at()!=null) {
                holder.tvCreatedAt.setText(createdAtText+model.getCreated_at());
            }else{
                holder.tvCreatedAt.setText(noDataText);
            }
            if(model.getUpdated_at()!=null) {
                holder.tvUpdatedAt.setText(updatedAtText+model.getUpdated_at());
            }else{
                holder.tvUpdatedAt.setText(noDataText);
            }
            return view;
        }

    class ViewHolder
    {
        TextView tvUpdatedAt;
        TextView tvCreatedAt;
        TextView tvMedicalHistoryName;
    }
}
