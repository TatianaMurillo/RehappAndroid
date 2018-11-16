package com.rehapp.rehappmovil.rehapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SearchPatient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_patient);

        // aqui se recibe la informacion del paciente y se pinta en la vista






    }

    public void watchTherapies(View view) {

        Intent intent = new Intent(SearchPatient.this,HistoryTherapiesPatient.class);
        startActivity(intent);
    }
}
