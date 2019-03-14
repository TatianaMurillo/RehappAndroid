package com.rehapp.rehappmovil.rehapp;

import android.content.Intent;
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

import com.rehapp.rehappmovil.rehapp.Models.PatientViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
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
    private TherapyViewModel therapy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_therapies_patient);


        lvTherapies = findViewById(R.id.lvTherapies);

for (int i=1;i<5;i++) {

    therapy = new TherapyViewModel();
    therapy.setTherapy_id(1);
    therapy.setTherapist_id(i);
    therapy.setPatient_id(1);
    therapy.setTherapy_institution_id(2);
    therapy.setTherapy_description("Terapia cardiovascular sesión "+ i);
    therapy.setTherapy_date("");
    therapy.setTherapy_time("");
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
        ocultarItems(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.create_therapy:
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

    public void ocultarItems(Menu menu)
    {
        MenuItem item;

        item= menu.findItem(R.id.save_therapy);
        item.setVisible(false);
    }

}
