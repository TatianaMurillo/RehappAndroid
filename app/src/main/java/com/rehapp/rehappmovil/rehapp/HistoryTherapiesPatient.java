package com.rehapp.rehappmovil.rehapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.DocumentTypeApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.TherapyApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.DocumentTypeViewModel;
import com.rehapp.rehappmovil.rehapp.Models.InstitutionViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PatientViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapistViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyMasterDetailViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;
import com.rehapp.rehappmovil.rehapp.Utils.UserMethods;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryTherapiesPatient extends AppCompatActivity implements Callback<TherapyViewModel> {

    private ListView lvTherapies;
    private ArrayList<TherapyViewModel> therapies = new ArrayList<>();
    private ArrayList<String> therapiesNames= new ArrayList<>();
    private String documentPatient;
    private PatientViewModel patientViewModel;
    private int documentTypePatientId;
    private String patientId;
    private TherapyMasterDetailViewModel therapy;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_therapies_patient);


        sharedpreferences=getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);


        lvTherapies = findViewById(R.id.lvTherapies);
        recoverySendData();
        loadTherapies();

        lvTherapies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TherapyViewModel selectedTherapy = therapies.get(position);
                Toast.makeText(getApplicationContext(), "therapy selected : " + selectedTherapy.getTherapy_description(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(HistoryTherapiesPatient.this, TherapyDetail.class);
                intent.putExtra(PreferencesData.TherapySelectedId, selectedTherapy.getTherapy_id());
                intent.putExtra(PreferencesData.TherapyAction, "DETAIL");
                startActivity(intent);
            }
        });


    }


    public void loadTherapies()
    {
        Call<ArrayList<TherapyViewModel>> call = TherapyApiAdapter.getApiService().getTherapiesByPatient(patientId);
        call.enqueue(new Callback<ArrayList<TherapyViewModel>>() {
            @Override
            public void onResponse(Call<ArrayList<TherapyViewModel>> call, Response<ArrayList<TherapyViewModel>> response) {
                if(response.isSuccessful())
                {
                    therapies=response.body();
                    for(TherapyViewModel therapy: therapies)
                    {
                        therapiesNames.add(therapy.getTherapy_description() + " "+ therapy.getTherapy_sequence());
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(HistoryTherapiesPatient.this,android.R.layout.simple_list_item_1,therapiesNames);
                    lvTherapies.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TherapyViewModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), PreferencesData.therapyListError +" "+ t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }



    @Override
    public void onResponse(Call<TherapyViewModel> call, Response<TherapyViewModel> response) {

    }

    @Override
    public void onFailure(Call<TherapyViewModel> call, Throwable t) {

    }

    private void recoverySendData()
    {
        documentPatient=sharedpreferences.getString(PreferencesData.PatientDocument,"");
        documentTypePatientId=Integer.parseInt(sharedpreferences.getString(PreferencesData.PatientTpoDocument,""));
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
                Intent intent = new Intent(HistoryTherapiesPatient.this, TherapyDetail.class);
                Bundle extras = new Bundle();
                extras.putString(PreferencesData.TherapyAction, "ADD");
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
