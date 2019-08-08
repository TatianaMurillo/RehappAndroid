package com.rehapp.rehappmovil.rehapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import com.rehapp.rehappmovil.rehapp.Models.PhysiologicalParameterViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;

import java.util.ArrayList;


public class PhysiologicalParameterTherapyDialog extends AppCompatDialogFragment {


    private EditText editText;
    private TextView textView;
    private GridLayout grid;


    ArrayList<PhysiologicalParameterViewModel> options= new ArrayList<PhysiologicalParameterViewModel>();



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_physiological_parameter_therapy,null);

        builder
                .setView(view)
                .setTitle(R.string.phisiologicalParametersIn)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        grid = view.findViewById(R.id.grid);
        LoadData();
        return builder.create();
    }

    public void LoadData()
    {
        options.add(new PhysiologicalParameterViewModel("FC"));

        options.add(new PhysiologicalParameterViewModel("Heart rate"));

        options.add(new PhysiologicalParameterViewModel("BorgD"));

        options.add(new PhysiologicalParameterViewModel("BorgE"));

        addPhysiologicalParametersView(options);
    }


    private void addPhysiologicalParametersView( ArrayList<PhysiologicalParameterViewModel> physiologicalParameters)
    {
        for(PhysiologicalParameterViewModel physiologicalParameterViewModel : physiologicalParameters)
        {

            textView = new TextView(getContext());
            editText = new EditText(getContext());

            textView.setText(physiologicalParameterViewModel.getPhysiological_parameter_name());
            editText.setEms(PreferencesData.PhysiologicalParameterTherapyValueSize);
            editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(PreferencesData.PhysiologicalParameterTherapyValueSize) });
            editText.setSingleLine(true);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editText.setGravity(Gravity.CENTER);
            grid.addView(textView);
            grid.addView(editText);
        }
    }

}
