package com.rehapp.rehappmovil.rehapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.PatientMedicalHistoryApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.TherapyApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.PatientMedicalHistoryDiseaseViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PatientMedicalHistoryViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PatientViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapyMasterDetailViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;
import com.rehapp.rehappmovil.rehapp.Utils.UserMethods;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicalHistoriesPatient extends AppCompatActivity {

    private ListView lvMedicalHistoriesPatient;
    private ArrayList<PatientMedicalHistoryViewModel> medicalHistories = new ArrayList<>();
    private String patientId;
    SharedPreferences sharedpreferences;
    MedicalHistoriesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_therapies_patient);


        sharedpreferences=getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);


        lvMedicalHistoriesPatient = findViewById(R.id.lvMedicalHistoriesPatient);
        recoverySendData();
        loadMedicalHistoriesPatient();




    }

    public void loadMedicalHistoriesPatient()
    {
        Call<ArrayList<PatientMedicalHistoryViewModel>> call = PatientMedicalHistoryApiAdapter.getApiService().getMedicalHistoriesByPatient(patientId);
        call.enqueue(new Callback<ArrayList<PatientMedicalHistoryViewModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PatientMedicalHistoryViewModel>> call, Response<ArrayList<PatientMedicalHistoryViewModel>> response) {
                if(response.isSuccessful())
                {
                    medicalHistories=response.body();
                    adapter = new MedicalHistoriesAdapter(MedicalHistoriesPatient.this,medicalHistories);
                    lvMedicalHistoriesPatient.setAdapter(adapter);
                    lvMedicalHistoriesPatient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            PatientMedicalHistoryViewModel medicalHistory = medicalHistories.get(position);

                            Toast.makeText(getApplicationContext(), "medical history selected : " + medicalHistory.getPtnt_mdcl_hstry_name(), Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(MedicalHistoriesPatient.this, MedicalHistoryDetail.class);
                            intent.putExtra(PreferencesData.TherapySelectedId, medicalHistory.getPtnt_mdcl_hstry_id());
                            intent.putExtra(PreferencesData.MedicaHistoryAction, "DETAIL");
                            startActivity(intent);

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PatientMedicalHistoryViewModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), PreferencesData.patientMedicalHistoryListError +" "+ t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }


    private void recoverySendData()
    {
        patientId=sharedpreferences.getString(PreferencesData.PatientId,"");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        showHideItems(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.create_therapy:
                cleanPreferenceData();
                Intent intent = new Intent(MedicalHistoriesPatient.this, MedicalHistoryDetail.class);
                Bundle extras = new Bundle();
                extras.putString(PreferencesData.MedicaHistoryAction, "ADD");
                intent.putExtras(extras);
                startActivity(intent);
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    public void showHideItems(Menu menu)
    {
        MenuItem item;

        item= menu.findItem(R.id.save);
        item.setVisible(false);
    }

    private void cleanPreferenceData()
    {
        UserMethods.getInstance().storeIntSharepreferences(PreferencesData.TherapyId,0);
    }

}
