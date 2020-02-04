package com.rehapp.rehappmovil.rehapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.TherapyApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TherapyAdditionalInformationDialog extends AppCompatDialogFragment {

    EditText etAdditionalInformacion;
    private String therapyId;
    private Context mContext;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        therapyId=getArguments().getString(PreferencesData.TherapyId);
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

    public void LoadData() {
        getAdditionalInformation();
    }

    private void getAdditionalInformation(){
        Call<TherapyViewModel> call = TherapyApiAdapter.getApiService().getTherapyAdditionalInformation(therapyId);
        call.enqueue(new Callback<TherapyViewModel>() {
            @Override
            public void onResponse(Call<TherapyViewModel> call, Response<TherapyViewModel> response) {
                if(response.isSuccessful()){
                    TherapyViewModel therapy=response.body();
                    etAdditionalInformacion.setText(therapy.getTherapy_additional_information()!=null?therapy.getTherapy_additional_information():"");
                }
            }
            @Override
            public void onFailure(Call<TherapyViewModel> call, Throwable t) {
                String error = "Error "+t.getMessage();
                Toast.makeText(mContext,  error, Toast.LENGTH_LONG).show();

            }
        });
    }

        public void addAdditionalInformation(){
        TherapyViewModel therapy = new TherapyViewModel();
        therapy.setTherapy_additional_information(etAdditionalInformacion.getText().toString());
        Call<TherapyViewModel> call = TherapyApiAdapter.getApiService().addTherapyAdditionalInformation(therapy,therapyId);
        call.enqueue(new Callback<TherapyViewModel>() {
            @Override
            public void onResponse(Call<TherapyViewModel> call, Response<TherapyViewModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(mContext, PreferencesData.therapyAddAdditionalInformationSuccessMsg, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(mContext, PreferencesData.therapyAddAdditionalInformationFailedMsg, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<TherapyViewModel> call, Throwable t) {
                String error = "Error "+t.getMessage();
                Toast.makeText(mContext,  error, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

}

