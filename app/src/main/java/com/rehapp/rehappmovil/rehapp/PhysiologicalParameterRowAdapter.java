package com.rehapp.rehappmovil.rehapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rehapp.rehappmovil.rehapp.Models.PhysiologicalParameterViewModel;

import java.util.List;

public class PhysiologicalParameterRowAdapter extends BaseAdapter {

    Context context;
    List<PhysiologicalParameterViewModel> ppt;
    LayoutInflater inflater;
    public PhysiologicalParameterRowAdapter(Context context, List<PhysiologicalParameterViewModel> physiologicalParameters) {
        this.context=context;
        inflater=LayoutInflater.from(context);
        ppt=physiologicalParameters;
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View grid, ViewGroup parent) {

        if(grid ==null)
        {

            grid = inflater.inflate(R.layout.activity_physiological_parameter_row, parent, false);

            TextView textView = (TextView) grid.findViewById(R.id.ppttv);

            EditText editText = (EditText) grid.findViewById(R.id.pptet);

            textView.setText(ppt.get(position).getPhysiological_parameter_name());
        }
        return grid;
    }

}

