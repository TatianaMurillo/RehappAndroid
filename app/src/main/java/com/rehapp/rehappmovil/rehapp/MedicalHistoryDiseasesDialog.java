package com.rehapp.rehappmovil.rehapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.DiseaseApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.DiseaseViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicalHistoryDiseasesDialog extends AppCompatDialogFragment {

    ListView lvDiseases;
    ArrayList<DiseaseViewModel> diseases = new ArrayList<>();
    ArrayList<DiseaseViewModel> medicalHistoryDiseases = new ArrayList<>();
    boolean isSelected;
    SharedPreferences sharedpreferences;
    String medicalHistorySelectedId;
    MedicalHistoryDiseaseAdapter adapter;
    int  diseaseSelectedIndex=-1;

    private Context mContext;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        adapter=null;
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_therapy_exercises,null);

        recoverySendData();
        loadData();

        lvDiseases= view.findViewById(R.id.lvExercises);

        builder
                .setView(view)
                .setTitle(R.string.watchExercises)
                .setNegativeButton(R.string.CancelButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(R.string.SaveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        return builder.create();
    }

    private void loadData()
    {
        listDiseases();
    }

    private void listDiseases()
    {
        Call<ArrayList<DiseaseViewModel>> call = DiseaseApiAdapter.getApiService().getDiseases();
        call.enqueue(new Callback<ArrayList<DiseaseViewModel>>() {
            @Override
            public void onResponse(Call<ArrayList<DiseaseViewModel>> call, Response<ArrayList<DiseaseViewModel>> response) {
                if (response.isSuccessful()) {
                    diseases = response.body();
                    adapter = new MedicalHistoryDiseaseAdapter(getActivity(),diseases);
                    lvDiseases.setAdapter(adapter);
                    listMedicalHistoryDiseases();

                    lvDiseases.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            selectDiseaseItem(position);
                        }
                    });

                } else {
                    Toast.makeText(mContext, PreferencesData.medicalHistoryDiseasesListMsg, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<DiseaseViewModel>> call, Throwable t) {
                Toast.makeText(mContext, PreferencesData.medicalHistoryDiseasesListMsg.concat(t.getMessage()), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void listMedicalHistoryDiseases() {
        try {
            Call<ArrayList<DiseaseViewModel>> call = DiseaseApiAdapter.getApiService().getDiseasesByMedicalHistory(medicalHistorySelectedId);
            call.enqueue(new Callback<ArrayList<DiseaseViewModel>>() {
                @Override
                public void onResponse(Call<ArrayList<DiseaseViewModel>> call, Response<ArrayList<DiseaseViewModel>> response) {
                    if (response.isSuccessful()) {

                        medicalHistoryDiseases = response.body();
                        for (DiseaseViewModel selectedDisease:medicalHistoryDiseases ) {

                            for (DiseaseViewModel disease:diseases )
                            {
                                if(disease.getDisease_id()==selectedDisease.getDisease_id())
                                {
                                    selectDiseaseItem(diseases.indexOf(disease));
                                }
                            }
                        }
                    }else if(response.raw().code()==404){
                        System.out.println(PreferencesData.medicalHistoryDiseaseEmptyListMsg);
                    }
                }
                @Override
                public void onFailure(Call<ArrayList<DiseaseViewModel>> call, Throwable t) {
                    Toast.makeText(mContext, PreferencesData.medicalHistoryDiseaseEmptyListMsg.concat(t.getMessage()), Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception ex){}
    }


    private void recoverySendData()
    {
        medicalHistorySelectedId =sharedpreferences.getString(PreferencesData.MedicalHistorySelectedId,"");
    }




    private void selectDiseaseItem(int position)
    {
        DiseaseViewModel model = diseases.get(position);
        diseaseSelectedIndex=position;
        if (model.isSelected()) {
            model.setSelected(false);
        } else {
            model.setSelected(true);
        }
        diseases.set(position, model);
        adapter.updateRecords(diseases);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

}
