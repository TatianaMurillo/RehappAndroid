package com.rehapp.rehappmovil.rehapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.Models.Therapy;
import com.rehapp.rehappmovil.rehapp.Models.TherapyExercise;

import java.util.ArrayList;

public class TherapyExercises extends AppCompatActivity {

    private ListView lvExercises;
    private ArrayList<TherapyExercise> exercises = new ArrayList<TherapyExercise>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapy_exercises);




        exercises.add(new TherapyExercise(false,"Ejercicio 1"));
        exercises.add(new TherapyExercise(false,"Ejercicio 2"));
        exercises.add(new TherapyExercise(false,"Ejercicio 3"));
        exercises.add(new TherapyExercise(false,"Ejercicio 4"));

        lvExercises = findViewById(R.id.lvExercises);

        final TherapyExercisesAdapter adapter = new TherapyExercisesAdapter(this,exercises);
        lvExercises.setAdapter(adapter);



        lvExercises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(TherapyExercises.this, TherapyExerciseDetail.class);
                intent.putExtra( "ejercicioSelected",position);
                startActivity(intent);

            }
        });

        lvExercises.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedmovie = exercises.get(position).getExerciseName();
                Toast.makeText(getApplicationContext(), "Ejercicio selected : " + selectedmovie, Toast.LENGTH_LONG).show();


                TherapyExercise model = exercises.get(position);

                if(model.isSelected())
                {
                    model.setSelected(false);
                }else
                {
                    model.setSelected(true);
                }

                exercises.set(position, model);

                adapter.updateRecords(exercises);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
                // ...
            }
        });

        ImageButton rightPage = (ImageButton) customView.findViewById(R.id.right);
        rightPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ...
            }
        });




    }
}
