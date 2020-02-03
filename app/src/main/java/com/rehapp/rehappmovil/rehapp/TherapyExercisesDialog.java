package com.rehapp.rehappmovil.rehapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.ExerciseRoutineApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.TherapyApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.TherapyExerciseRoutineApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.ExerciseRoutinesViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyExcerciseRoutineViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyMasterDetailViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;
import com.rehapp.rehappmovil.rehapp.fragments.TherapyDetailFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TherapyExercisesDialog extends AppCompatDialogFragment {


    private ListView lvExercises;
    private ArrayList<ExerciseRoutinesViewModel> exercises = new ArrayList<>();
    private ArrayList<TherapyExcerciseRoutineViewModel> therapyExerciseRoutines = new ArrayList<>();
    private int  exerciseRoutineSelectedIndex=-1;
    private String therapyId;
    TherapyMasterDetailViewModel therapyViewModel;
    TherapyExercisesAdapter adapter;
    FragmentManager manager;

    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        manager = this.getFragmentManager();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        therapyId=getArguments().getString(PreferencesData.TherapyId);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        adapter=null;
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_therapy_exercises,null);

        recoverySendData();
        loadData();

        lvExercises= view.findViewById(R.id.lvExercises);



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
                        saveExercises();
                    }
                });

        return builder.create();


    }
    private void recoverySendData()
    {
    }
    private void loadData()
    {
        listExercisesRoutines();
    }


    private void listExercisesRoutines()
    {
        Call<ArrayList<ExerciseRoutinesViewModel>> call = ExerciseRoutineApiAdapter.getApiService().getExerciseRoutines();
        call.enqueue(new Callback<ArrayList<ExerciseRoutinesViewModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ExerciseRoutinesViewModel>> call, Response<ArrayList<ExerciseRoutinesViewModel>> response) {
                if (response.isSuccessful()) {
                    exercises = response.body();
                    adapter = new TherapyExercisesAdapter(getActivity(),exercises,getFragmentManager(),TherapyExercisesDialog.this);
                    lvExercises.setAdapter(adapter);
                    listTherapyExercisesRoutines();

                    lvExercises.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            selectExerciseRoutinesItem(position);
                        }
                    });

                } else {
                        Toast.makeText(mContext, PreferencesData.therapyDetailExerciseRoutineListMsg, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<ExerciseRoutinesViewModel>> call, Throwable t) {
                Toast.makeText(mContext, PreferencesData.therapyDetailExerciseRoutineListMsg.concat(t.getMessage()), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void listTherapyExercisesRoutines()
    {
        try {
            Call<ArrayList<TherapyExcerciseRoutineViewModel>> callTherapyExcerciseRoutines = TherapyExerciseRoutineApiAdapter.getApiService().getTherapyExerciseRoutines(therapyId);
            callTherapyExcerciseRoutines.enqueue(new Callback<ArrayList<TherapyExcerciseRoutineViewModel>>() {
                @Override
                public void onResponse(Call<ArrayList<TherapyExcerciseRoutineViewModel>> call, Response<ArrayList<TherapyExcerciseRoutineViewModel>> response) {
                    if (response.isSuccessful()) {

                        therapyExerciseRoutines = response.body();
                        for (TherapyExcerciseRoutineViewModel therapyExerciseRoutines:therapyExerciseRoutines ) {
                            for (ExerciseRoutinesViewModel exercise:exercises )
                            {
                                if(exercise.getExercise_routine_id()==therapyExerciseRoutines.getExerciseRoutineId())
                                {
                                    selectExerciseRoutinesItem(exercises.indexOf(exercise));
                                }
                            }
                        }
                    }else if(response.raw().code()==404){
                        System.out.println(PreferencesData.therapyDetailTherapyExerciseRoutinesEmptyListMsg);
                    }
                }
                @Override
                public void onFailure(Call<ArrayList<TherapyExcerciseRoutineViewModel>> call, Throwable t) {
                    Toast.makeText(mContext, PreferencesData.therapyDetailTherapyExerciseRoutinesListMsg.concat(t.getMessage()), Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception ex){}
    }
    private void selectExerciseRoutinesItem(int position)
    {
        ExerciseRoutinesViewModel model = exercises.get(position);
        exerciseRoutineSelectedIndex=position;
        if (model.isSelected()) {
            model.setSelected(false);
        } else {
            model.setSelected(true);
        }
        exercises.set(position, model);
        adapter.updateRecords(exercises);
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

    public  void saveExercises()
    {
        List<TherapyExcerciseRoutineViewModel> therapyExcerciseRoutines=new ArrayList<>();
        TherapyExcerciseRoutineViewModel therapyExcerciseRoutine;
        for (ExerciseRoutinesViewModel exerciseRoutine: exercises) {
            if(exerciseRoutine.isSelected()) {
                therapyExcerciseRoutine = new TherapyExcerciseRoutineViewModel();
                therapyExcerciseRoutine.setTherapyExcerciseRoutineIntensity(exerciseRoutine.getIntensity());
                therapyExcerciseRoutine.setTherapyExcerciseRoutineDuration(exerciseRoutine.getDuration());
                therapyExcerciseRoutine.setTherapy_excercise_routine_speed(exerciseRoutine.getSpeed());
                therapyExcerciseRoutine.setConditions(exerciseRoutine.getConditions());
                therapyExcerciseRoutine.setPreconditions(exerciseRoutine.getPreconditions());
                therapyExcerciseRoutine.setFrequency(exerciseRoutine.getFrequency());
                therapyExcerciseRoutine.setTherapyExcerciseRoutineObservation(exerciseRoutine.getObservations());
                therapyExcerciseRoutine.setTherapyId(Integer.parseInt(therapyId));
                therapyExcerciseRoutine.setExerciseRoutineId(exerciseRoutine.getExercise_routine_id());
                therapyExcerciseRoutines.add(therapyExcerciseRoutine);
            }
        }

            Call<List<TherapyExcerciseRoutineViewModel>> call = TherapyExerciseRoutineApiAdapter.getApiService().saveTherapyExerciseRoutines(therapyExcerciseRoutines,therapyId);
            call.enqueue(new Callback<List<TherapyExcerciseRoutineViewModel>>() {
                @Override
                public void onResponse(Call<List<TherapyExcerciseRoutineViewModel>> call, Response<List<TherapyExcerciseRoutineViewModel>> response) {
                    if(response.isSuccessful())
                    {
                        saveTherapyTotalDuration();
                    }
                }
                @Override
                public void onFailure(Call<List<TherapyExcerciseRoutineViewModel>> call, Throwable t) {
                    Toast.makeText(mContext, PreferencesData.therapyDetailSaveExerciseRoutineFailedMsg, Toast.LENGTH_LONG).show();

                }
            });

    }

    private void saveTherapyTotalDuration(){
        Call<TherapyViewModel> call = TherapyExerciseRoutineApiAdapter.getApiService().updateTherapyDuration(therapyId);
        call.enqueue(new Callback<TherapyViewModel>() {
            @Override
            public void onResponse(Call<TherapyViewModel> call, Response<TherapyViewModel> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(mContext, PreferencesData.therapyDetailSaveExerciseRoutineSuccessMsg, Toast.LENGTH_LONG).show();
                    TherapyDetailFragment fragment = new TherapyDetailFragment();
                    loadFragment(fragment);
                }else{
                    Toast.makeText(mContext, response.raw().code(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<TherapyViewModel> call, Throwable t) {
                Toast.makeText(mContext, PreferencesData.therapyDetailSaveExerciseRoutineFailedMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loadFragment(Fragment fragment){
        manager.beginTransaction().replace(R.id.content,fragment).commit();
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }
}
