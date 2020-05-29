package com.rehapp.rehappmovil.rehapp;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.rehapp.rehappmovil.rehapp.Models.PhysiologicalParameterViewModel;
import java.util.List;

public class PhysiologicalParameterAdapter extends BaseAdapter{

    Activity activity;
    List<PhysiologicalParameterViewModel> parameters;
    FragmentManager fragment;
    LayoutInflater inflater;

    public PhysiologicalParameterAdapter(Activity activity, List<PhysiologicalParameterViewModel> exercises, FragmentManager fragment) {
        this.activity = activity;
        this.parameters = exercises;
        inflater = activity.getLayoutInflater();
        this.fragment=fragment;
    }


    @Override
    public int getCount() {
        return parameters.size();
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
            view = inflater.inflate(R.layout.list_physiological_parameter_therapy_item, parent,false);
            holder = new ViewHolder();
            holder.tvName=view.findViewById(R.id.tvName);
            holder.tvValue=view.findViewById(R.id.tvValue);
            holder.tvUnitMeasure= view.findViewById(R.id.tvUnitMeasure);
            holder.tvCategory=view.findViewById(R.id.tvCategory);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
            final PhysiologicalParameterViewModel model = parameters.get(position);

            holder.tvName.setText(getValue(model.getPhysiological_parameter_name()));
            holder.tvValue.setText(getValue(model.getTherapy_value().size()>0?model.getTherapy_value().get(0).getPhysio_param_thrpy_value():"---"));
            holder.tvUnitMeasure.setText(getValue(model.getUnit_of_measure_name()));
            holder.tvCategory.setText(getValue(model.getOption_name().size()>0?model.getOption_name().get(0).getOption_name():"---"));

            return view;
        }

    class ViewHolder
    {
        TextView tvName;
        TextView tvValue;
        TextView tvUnitMeasure;
        TextView tvCategory;
    }


    public void loadFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=this.fragment.beginTransaction();
        fragmentTransaction.replace(R.id.content,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private String getValue(String value){
        if(value==null){
            return "---";
        }
        return value;
    }
}
