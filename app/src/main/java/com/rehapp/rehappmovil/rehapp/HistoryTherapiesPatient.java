package com.rehapp.rehappmovil.rehapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.Models.Therapy;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryTherapiesPatient extends AppCompatActivity implements Callback<Therapy> {

    private ListView lvTherapies;
    private ArrayList<Therapy> therapies = new ArrayList<Therapy>();
    final Therapy therapy= new Therapy();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_therapies_patient);


        lvTherapies = findViewById(R.id.lvTherapies);


        therapy.setTherapy_id(1);
        therapy.setTherapist_id(1);
        therapy.setPatient_id(1);
        therapy.setTherapy_institution_id(1);
        therapy.setTherapy_description("Terapia 1");
        therapy.setTherapy_date("");
        therapy.setTherapy_time("");
        therapy.setTherapy_total_duration(2.2);
        therapy.setTherapy_observation("");
        therapy.setTherapy_sequence(2);
        therapy.setTherapy_achieved_the_goal(true);


        therapies.add(therapy);
        therapies.add(therapy);
        therapies.add(therapy);
        therapies.add(therapy);


        ArrayAdapter<Therapy> arrayAdapter =
                new ArrayAdapter<Therapy>(this, android.R.layout.simple_list_item_1, therapies);

        lvTherapies.setAdapter(arrayAdapter);


        lvTherapies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Therapy selectedTherapy = therapies.get(position);
                Toast.makeText(getApplicationContext(), "therapy selected : " + selectedTherapy.getTherapy_description(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(HistoryTherapiesPatient.this, TherapyDetail.class);
                intent.putExtra("TherapySelected", therapy);

                startActivity(intent);
            }
        });


    }

    @Override
    public void onResponse(Call<Therapy> call, Response<Therapy> response) {

    }

    @Override
    public void onFailure(Call<Therapy> call, Throwable t) {

    }

}
