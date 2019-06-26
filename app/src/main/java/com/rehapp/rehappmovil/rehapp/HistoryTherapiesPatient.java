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
import com.rehapp.rehappmovil.rehapp.Models.InstitutionViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PatientViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapistViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyMasterDetailViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryTherapiesPatient extends AppCompatActivity implements Callback<TherapyViewModel> {

    private ListView lvTherapies;
    private ArrayList<TherapyViewModel> therapies = new ArrayList<TherapyViewModel>();
    private ArrayList<String> therapiesNames= new ArrayList<String>();
    private String documentPatient;
    private PatientViewModel patientViewModel;
    private int documentTypePatientId;
    private TherapyMasterDetailViewModel therapy;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_therapies_patient);


        lvTherapies = findViewById(R.id.lvTherapies);


for (int i=1;i<5;i++) {

    therapy = new TherapyMasterDetailViewModel();
    therapy.setTherapy_id(1);
    therapy.setTherapist_id(1);
    therapy.setPatient_id(1);
    therapy.setInstitution_id(2);
    therapy.setTherapy_description("Terapia cardiovascular sesión "+ i);
    therapy.setCreated_at("");
    therapy.setUpdated_at("");
    therapy.setTherapy_total_duration(2.2);
    therapy.setTherapy_observation("");
    therapy.setTherapy_sequence(i);
    therapy.setTherapy_achieved_the_goal(true);

    therapies.add(therapy);

}
        loadTherapies();

        lvTherapies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TherapyViewModel selectedTherapy = therapies.get(position);
                Toast.makeText(getApplicationContext(), "therapy selected : " + selectedTherapy.getTherapy_description(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(HistoryTherapiesPatient.this, TherapyDetail.class);
                intent.putExtra(PreferencesData.TherapySelected, selectedTherapy);
                intent.putExtra(PreferencesData.TherapyAction, "DETAIL");
                startActivity(intent);
            }
        });


    }

    public void loadTherapies()
    {

        for(TherapyViewModel therapy: therapies)
        {
            therapiesNames.add(therapy.getTherapy_description() + " "+ therapy.getTherapy_sequence());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, therapiesNames);

        lvTherapies.setAdapter(arrayAdapter);
    }



    @Override
    public void onResponse(Call<TherapyViewModel> call, Response<TherapyViewModel> response) {

    }

    @Override
    public void onFailure(Call<TherapyViewModel> call, Throwable t) {

    }

    private void recoverySendData()
    {
        if(getIntent().getExtras()!=null)
        {
            Bundle extras = getIntent().getExtras();
            documentPatient = extras.getString(PreferencesData.PatientDocument);
            documentTypePatientId = Integer.parseInt(extras.getString(PreferencesData.PatientTpoDocument));
            patientViewModel.setPatient_document(documentPatient);
            patientViewModel.setDocument_type_id(documentTypePatientId);
        }
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
                createTherapyJson();
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

        item= menu.findItem(R.id.save_therapy);
        item.setVisible(false);
    }

    public void createTherapyJson()
    {
        TherapyMasterDetailViewModel  therapyMasterDetailViewModel;

        therapyMasterDetailViewModel = new TherapyMasterDetailViewModel();

        Gson gson = new  Gson();
        String json = gson.toJson(therapyMasterDetailViewModel);
        sharedpreferences = getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(PreferencesData.TherapyMasterDetailViewModel, json);
        editor.commit();
    }
}
