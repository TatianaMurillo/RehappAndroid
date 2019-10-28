package com.rehapp.rehappmovil.rehapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.rehapp.rehappmovil.rehapp.Models.OptionViewModel;
import com.rehapp.rehappmovil.rehapp.Models.QuestionaryOptionViewModel;

import java.util.ArrayList;

public class MedicalHistoryQuestionariesAdapter extends BaseAdapter{

    Activity activity;
    ArrayList<QuestionaryOptionViewModel> questionaryOptions;
    LayoutInflater inflater;


    public MedicalHistoryQuestionariesAdapter(Activity activity, ArrayList<QuestionaryOptionViewModel> questionaryOptions) {
        this.activity = activity;
        this.questionaryOptions = questionaryOptions;
        inflater = activity.getLayoutInflater();
    }


    @Override
    public int getCount() {
        return questionaryOptions.size();
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
            view = inflater.inflate(R.layout.list_view_medical_history_questionary_item, parent,false);
            holder = new ViewHolder();

            holder.tvQuestionaryName=view.findViewById(R.id.tvQuestionaryName);
            holder.spnQuestionary=view.findViewById(R.id.spnQuestionary);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
            final QuestionaryOptionViewModel model = questionaryOptions.get(position);

            final Spinner spinner;
            final TextView textView;

            spinner=holder.spnQuestionary;
            textView= holder.tvQuestionaryName;

            textView.setText(model.getQuestionnaire_name());
            textView.setTag(model.getQuestionnaire_id());

            ArrayList<String> optionNames;
            final ArrayList<OptionViewModel> options=model.getOptions();

            optionNames=new ArrayList<>();

            for (OptionViewModel option:options )
            {
                optionNames.add(option.getOption_name());
            }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(activity.getApplicationContext(),android.R.layout.simple_list_item_1,optionNames);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int positionSpinner, long id) {
                spinner.setTag(options.get(positionSpinner).getOption_id());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return view;
    }

    class ViewHolder
    {
        TextView tvQuestionaryName;
        Spinner spnQuestionary;
    }
}
