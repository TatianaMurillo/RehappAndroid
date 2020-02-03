package com.rehapp.rehappmovil.rehapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;


public class TherapyAdditionalInformationDialog extends AppCompatDialogFragment {

    EditText etAdditionalInformacion;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_therapy_additional_information_dialog,null);

        builder
                .setView(view)
                .setTitle(getTitle())
                .setNegativeButton(R.string.CancelButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(R.string.SaveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addAdditionalInformation();
                        dialog.dismiss();
                    }
                });
        LoadData();
        etAdditionalInformacion=view.findViewById(R.id.etAdditionalInformacion);
        return builder.create();
    }

    private int getTitle() {
        return R.string.additionalInfo;
    }

    public void LoadData() { }

    public void addAdditionalInformation()
    {
        TherapyViewModel therapy = new TherapyViewModel();
        therapy.setTherapy_additional_information(etAdditionalInformacion.getText().toString());


        Toast.makeText(getContext(), PreferencesData.therapyAddAdditionalInformationSuccessMsg, Toast.LENGTH_LONG).show();
    }


}

