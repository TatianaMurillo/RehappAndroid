package com.rehapp.rehappmovil.rehapp;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.Models.PhysiologicalParameterTherapyViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PhysiologicalParameterViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapyExercise;
import com.rehapp.rehappmovil.rehapp.Models.TherapyMasterDetailViewModel;
import com.rehapp.rehappmovil.rehapp.Utils.DBHelper2;

import java.util.ArrayList;
import java.util.List;


public class TherapyExercisesDialog extends AppCompatDialogFragment {


    private ListView lvExercises;
    private ArrayList<TherapyExercise> exercises = new ArrayList<>();
    private boolean isSelected;
    private String action;
    TherapyMasterDetailViewModel therapyViewModel;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        action=getArguments().getString(PreferencesData.TherapyAction);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_therapy_exercises,null);

        //therapyViewModel = ViewModelProviders.of(this).get(TherapyMasterDetailViewModel.class);
        recoverySendData();
        loadData();

        lvExercises= view.findViewById(R.id.lvExercises);
        final TherapyExercisesAdapter adapter = new TherapyExercisesAdapter(getActivity(),exercises);
        lvExercises.setAdapter(adapter);

            lvExercises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedRoutine = exercises.get(position).getExerciseName();
                    Toast.makeText(getContext(), selectedRoutine, Toast.LENGTH_LONG).show();
                    TherapyExercise model = exercises.get(position);
                    if (model.isSelected()) {
                        model.setSelected(false);
                    } else {
                        model.setSelected(true);
                    }
                    exercises.set(position, model);
                    adapter.updateRecords(exercises);
                }
            });


        builder
                .setView(view)
                .setTitle(R.string.watchExercises)
                .setNegativeButton(R.string.CancelButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(R.string.SaveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();


    }
    private void recoverySendData()
    {
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

    public void hideShowItems(Menu menu)
    {
        MenuItem item;
        if(action.equals("DETAIL")) {
            item = menu.findItem(R.id.create_therapy);
            item.setVisible(false);

            item = menu.findItem(R.id.save_therapy);
            item.setVisible(false);
        }else
        {
            item = menu.findItem(R.id.create_therapy);
            item.setVisible(false);
        }
    }

    public  void saveExercises()
    {

    }

}
