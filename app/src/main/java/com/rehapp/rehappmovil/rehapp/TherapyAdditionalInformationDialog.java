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

import com.google.gson.Gson;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.TherapyApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.DocumentTypeViewModel;
import com.rehapp.rehappmovil.rehapp.Models.GenderViewModel;
import com.rehapp.rehappmovil.rehapp.Models.InstitutionViewModel;
import com.rehapp.rehappmovil.rehapp.Models.NeighborhoodViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PatientViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapistViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;
import com.rehapp.rehappmovil.rehapp.Utils.Constants.DocumentType;
import com.rehapp.rehappmovil.rehapp.Utils.Constants.Therapy;
import com.rehapp.rehappmovil.rehapp.Utils.UserMethods;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        Gson gson = new Gson();
        String json = gson.toJson(therapy);

        Toast.makeText(getContext(), PreferencesData.therapyAddAdditionalInformationSuccessMsg, Toast.LENGTH_LONG).show();
    }


    }

