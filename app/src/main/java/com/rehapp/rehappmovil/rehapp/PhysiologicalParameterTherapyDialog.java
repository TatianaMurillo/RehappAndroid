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
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.Models.PhysiologicalParameterTherapyViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PhysiologicalParameterViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Utils.Constants.Constants;
import com.rehapp.rehappmovil.rehapp.Utils.Constants.PhysiologicalParameterTherapy;
import com.rehapp.rehappmovil.rehapp.Utils.DBHelper;
import com.rehapp.rehappmovil.rehapp.Utils.DBHelper2;
import com.rehapp.rehappmovil.rehapp.Utils.ReadCSVFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class PhysiologicalParameterTherapyDialog extends AppCompatDialogFragment {


    private EditText editText;
    private TextView textView;
    private GridLayout grid;
    private String physiologicalParameterAction;


    ArrayList<PhysiologicalParameterViewModel> options= new ArrayList<PhysiologicalParameterViewModel>();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        physiologicalParameterAction = getArguments().getString(PreferencesData.PhysiologicalParameterAction);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_physiological_parameter_therapy,null);

        builder
                .setView(view)
                .setTitle(getTitle())
                .setNegativeButton(R.string.CancelButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(R.string.SaveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setPhysiolocalParametersFromViewToTmpFile();
                    }
                });
        grid = view.findViewById(R.id.grid);
        LoadData();
        return builder.create();
    }

    private int getTitle() {
        if (physiologicalParameterAction.equals(PreferencesData.PhysiologicalParameterTherapySesionIN))
        { return R.string.phisiologicalParametersIn;
        }else if(physiologicalParameterAction.equals(PreferencesData.PhysiologicalParameterTherapySesionOUT))
        { return R.string.phisiologicalParametersOut; }
        return -1;
    }

    public void LoadData()
    {
        options.add(new PhysiologicalParameterViewModel("FC"));

        options.add(new PhysiologicalParameterViewModel("Heart rate"));

        options.add(new PhysiologicalParameterViewModel("BorgD"));

        options.add(new PhysiologicalParameterViewModel("BorgE"));

        addPhysiologicalParametersView(options);

        setPhysiolocalParametersToViewFromTmpFile();
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


    private  void setPhysiolocalParametersToViewFromTmpFile() {

        List<PhysiologicalParameterTherapyViewModel> LObj = DBHelper2.connect(this.getContext()).listPhysiologicalParametersInRegister();

        if(LObj!=null){
                int childCount = grid.getChildCount();
                String valueEditText;
                String valueTextView;
                Object childEditText;
                Object childTextView;
                TextView textView;
                EditText editText;

        for (int i = 1; i < childCount; i += 2) {
            childEditText = grid.getChildAt(i);
            childTextView = grid.getChildAt(i - 1);

            textView = (TextView) childTextView;
            valueTextView = textView.getText().toString().trim();
            editText = (EditText) childEditText;


            for (PhysiologicalParameterTherapyViewModel obj : LObj) {
                editText.setText(obj.getPhysio_param_thrpy_value());
            }
        }
    }

    }

    private void setPhysiolocalParametersFromViewToTmpFile() {

        List<PhysiologicalParameterTherapyViewModel> data= new ArrayList<>();

        int childCount = grid.getChildCount();
        String valueEditText;
        String valueTextView;
        Object childEditText;
        Object childTextView;
        TextView textView;
        EditText editText;

        for(int i=1;i<childCount; i+=2)
        {
            childEditText = grid.getChildAt(i);
            childTextView = grid.getChildAt(i-1);

            textView=(TextView) childTextView;
            valueTextView=textView.getText().toString().trim();
            editText = (EditText)childEditText;

            valueEditText = editText.getText().toString().trim();
            data.add(new PhysiologicalParameterTherapyViewModel(0,getIdPhysiologicalParameter(valueTextView),0,valueEditText,physiologicalParameterAction));

        }

        DBHelper2.connect(this.getContext()).addPhysiologicalParametersInRegister(data);

        //ReadCSVFile.writeTempTherapyInformation(this.getContext(), PhysiologicalParameterTherapy.DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_IN.getRowId(),data);

    }

    private int getIdPhysiologicalParameter(String name)
    {
        for(PhysiologicalParameterViewModel physiologicalParameterTherapy: options )
        {
            if(physiologicalParameterTherapy.getPhysiological_parameter_name().equals(name)){
                return physiologicalParameterTherapy.getPhysiological_parameter_id();
            }
        }
        return 0;
    }

}
