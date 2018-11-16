package com.rehapp.rehappmovil.rehapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TherapyExercises extends AppCompatActivity {

    private ListView lvExercises;
    private ArrayList<String> itemExample = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapy_exercises);

        itemExample.add("Ejercicio 1");
        itemExample.add("Ejercicio 2");
        itemExample.add("Ejercicio 3");
        itemExample.add("Ejercicio 4");


        lvExercises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedmovie = itemExample.get(position);
                Toast.makeText(getApplicationContext(), "Ejercicio selected : " + selectedmovie, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(TherapyExercises.this, TherapyExerciseDetail.class);
                intent.putExtra( "ejercicioSelected",position);
                startActivity(intent);
            }
        });

    }
}
