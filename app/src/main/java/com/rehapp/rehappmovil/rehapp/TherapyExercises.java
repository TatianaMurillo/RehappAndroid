package com.rehapp.rehappmovil.rehapp;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapyExercise;
import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;

import java.util.ArrayList;

public class TherapyExercises extends AppCompatActivity {

    private ListView lvExercises;
    private ArrayList<TherapyExercise> exercises = new ArrayList<>();
    private boolean isSelected;
    private String action;
    TherapyViewModel therapyViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapy_exercises);
        lvExercises = findViewById(R.id.lvExercises);
        therapyViewModel = ViewModelProviders.of(this).get(TherapyViewModel.class);
        recoverySendData();
        loadData();
        final TherapyExercisesAdapter adapter = new TherapyExercisesAdapter(this,exercises);
        lvExercises.setAdapter(adapter);

        lvExercises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedmovie = exercises.get(position).getExerciseName();
                Toast.makeText(getApplicationContext(),selectedmovie, Toast.LENGTH_LONG).show();
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
        });


        blockRowsInExercisesList();
    }
    private void recoverySendData()
    {
        if( getIntent().getExtras()!=null)
        {
            Bundle extras = getIntent().getExtras();
            action=extras.getString(PreferencesData.TherapyAction);
            if(action.equals("ADD")) {
                isSelected=false;
            }else
            {
                isSelected=true;
            }
            therapyViewModel.setAction(action);
        }
    }
    private void loadData()
    {
        exercises.add(new TherapyExercise(isSelected,"Ejercicio 1"));
        exercises.add(new TherapyExercise(isSelected,"Ejercicio 2"));
        exercises.add(new TherapyExercise(isSelected,"Ejercicio 3"));
        exercises.add(new TherapyExercise(isSelected,"Ejercicio 4"));
    }

    public void blockRowsInExercisesList()
    {
        if(therapyViewModel.getAction().equals("ADD"))
        {
            unBlockItems();
        }else
        {
            blockItems();
        }
    }
    public void blockItems()
    {
        for (int i=0;i< exercises.size();i++)
        {
            lvExercises.setEnabled(false);
        }
    }
    public void unBlockItems()
    {
        for (int i=0;i< exercises.size();i++)
        {
            lvExercises.setEnabled(true);
        }
    }

}
