package com.rehapp.rehappmovil.rehapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    private ArrayList<String> itemExample = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_therapies_patient);


        lvTherapies = findViewById(R.id.lvTherapies);

        itemExample.add("Terapia 1");
        itemExample.add("Terapia 2");
        itemExample.add("Terapia 3");
        itemExample.add("Terapia 4");


        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemExample);
        // Set The Adapter
        lvTherapies.setAdapter(arrayAdapter);


        lvTherapies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedmovie = itemExample.get(position);
                Toast.makeText(getApplicationContext(), "therapy selected : " + selectedmovie, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(HistoryTherapiesPatient.this, TherapyDetail.class);
                intent.putExtra( "therapySelected",position);
                startActivity(intent);
            }
        });


        //   Call<Therapy> call = TherapyApiAdapter.getApiService().getTherapies("malaria","json","30022715");

        // call.enqueue(this);


    }


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


}
