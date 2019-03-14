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
    private ArrayList<String> therapiesTitles = new ArrayList<String>();
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
        therapiesTitles.add(therapy.getTherapy_description());
        therapies.add(therapy);
        therapiesTitles.add(therapy.getTherapy_description());
        therapies.add(therapy);
        therapiesTitles.add(therapy.getTherapy_description());
        therapies.add(therapy);




        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, therapiesTitles);
        // Set The Adapter
        lvTherapies.setAdapter(arrayAdapter);


        lvTherapies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Therapy selectedTherapy = therapies.get(position);
                Toast.makeText(getApplicationContext(), "therapy selected : " + selectedTherapy.getTherapy_description(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(HistoryTherapiesPatient.this, TherapyDetail.class);
                intent.putExtra("TherapySelected", selectedTherapy);

                startActivity(intent);
            }
        });


        //   Call<Therapy> call = TherapyApiAdapter.getApiService().getTherapies("malaria","json","30022715");

        // call.enqueue(this);


        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater li = LayoutInflater.from(this);
        View customView = li.inflate(R.layout.activity_menu_items, null);
        mActionBar.setCustomView(customView);
        mActionBar.setDisplayShowCustomEnabled(true);



        ImageButton leftPage = (ImageButton)    customView.findViewById(R.id.left);
        leftPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryTherapiesPatient.this, SearchPatient.class);
                startActivity(intent);

            }
        });

        ImageButton rightPage = (ImageButton) customView.findViewById(R.id.right);
        rightPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryTherapiesPatient.this, TherapyExercises.class);
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

/*
    @Override
   public void onResponse(Call<Therapy> call, Response<Therapy> response) {
        if (response.isSuccessful()) {

            Therapy therapy = response.body();
            List<String> therapiesTitle = new ArrayList<String>();


            for (int i = 0; i < therapy.getResultList().getResult().length; i++) {


                Log.d("", "el titulo es " + therapy.getResultList().getResult()[i].getTitle());
                therapiesTitle.add(therapy.getResultList().getResult()[i].getTitle());

            }

            ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_history_therapies_patient, therapiesTitle);

            lvTherapies.setAdapter(adapter);

        }
    }

    @Override
    public void onFailure(Call<Therapy> call, Throwable t) {

    }

*/
}
