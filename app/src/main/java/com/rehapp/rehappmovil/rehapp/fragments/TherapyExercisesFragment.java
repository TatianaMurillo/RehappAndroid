package com.rehapp.rehappmovil.rehapp.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.TherapyExerciseRoutineApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.ExerciseRoutinesViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapyExcerciseRoutineViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;
import com.rehapp.rehappmovil.rehapp.R;
import com.rehapp.rehappmovil.rehapp.TherapyRoutinesListAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TherapyExercisesFragment extends Fragment implements TherapyRoutinesListAdapter.OnVideoClickListener{

    RecyclerView recyclerView;
    private ArrayList<ExerciseRoutinesViewModel> exercises = new ArrayList<>();
    private int  exerciseRoutineSelectedIndex=-1;
    private String therapyId;
    TherapyRoutinesListAdapter rvAdapter;
    FragmentManager manager;

    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        manager = this.getFragmentManager();
        therapyId=getArguments().getString(PreferencesData.TherapyId);
        rvAdapter=null;
        View view = inflater.inflate(R.layout.activity_therapy_exercises,null);

        recyclerView= view.findViewById(R.id.recyclerView);

        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        recoverySendData();
        loadData();



        return view;
    }

    private void recoverySendData() {}

    private void loadData()
    {
        listExercisesRoutines();
    }

    private void listExercisesRoutines() {
        Call<ArrayList<ExerciseRoutinesViewModel>> call = TherapyExerciseRoutineApiAdapter.getApiService().getTherapyExerciseRoutines(therapyId);
        call.enqueue(new Callback<ArrayList<ExerciseRoutinesViewModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ExerciseRoutinesViewModel>> call, Response<ArrayList<ExerciseRoutinesViewModel>> response) {
                if (response.isSuccessful()) {
                    exercises = response.body();
                    rvAdapter = new TherapyRoutinesListAdapter(getActivity(),exercises,getFragmentManager(),TherapyExercisesFragment.this);
                    recyclerView.setAdapter(rvAdapter);

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

    private void selectExerciseRoutinesItem(int position) {
        ExerciseRoutinesViewModel model = exercises.get(position);
        exerciseRoutineSelectedIndex=position;
        if (model.isSelected()) {
            model.setSelected(false);
        } else {
            model.setSelected(true);
        }
        exercises.set(position, model);
        rvAdapter.updateRecords(exercises);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        showHideItems(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.save:
                saveExercises();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showHideItems(Menu menu) {

        MenuItem item;
        item= menu.findItem(R.id.create_therapy);

        item.setVisible(false);

        item= menu.findItem(R.id.save);
        item.setVisible(true);
    }

    public  void saveExercises() {
        List<TherapyExcerciseRoutineViewModel> therapyExcerciseRoutines=new ArrayList<>();
        TherapyExcerciseRoutineViewModel therapyExcerciseRoutine;
        for (ExerciseRoutinesViewModel exerciseRoutine: exercises) {
            if(exerciseRoutine.isSelected() && exerciseRoutine.getTherapy_id()==null) {
                therapyExcerciseRoutine = new TherapyExcerciseRoutineViewModel();
                therapyExcerciseRoutine.setTherapyId(Integer.parseInt(therapyId));
                therapyExcerciseRoutine.setExerciseRoutineId(exerciseRoutine.getExercise_routine_id());
                therapyExcerciseRoutines.add(therapyExcerciseRoutine);
            }
        }

            Call<List<TherapyExcerciseRoutineViewModel>> call = TherapyExerciseRoutineApiAdapter.getApiService().saveTherapyExerciseRoutines(therapyExcerciseRoutines);
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

    @Override
    public void onVideoClick(int position, int option) {
        switch (option){
            case 1:
                selectExerciseRoutinesItem(position);
                break;
            case 2:
                redirectToTherapyExerciseRoutine(position);
                break;
            case 3:
                isDelete("Al borrar este elemento se borrará todo el detalle relacionado.");
                break;
        }
    }

    private void redirectToTherapyExerciseRoutine(int position){
        TherapyExcerciseRoutineFragment fragment=new TherapyExcerciseRoutineFragment();
        Bundle extras = new Bundle();
        extras.putString(PreferencesData.ExerciseRoutineUrl, exercises.get(position).getExercise_routine_url());
        extras.putInt(PreferencesData.ExerciseRoutineId, exercises.get(position).getExercise_routine_id());
        fragment.setArguments(extras);
        loadFragment(fragment);
    }

    public void isDelete(String alertMessage) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(alertMessage)
                .setPositiveButton("De acuerdo.", dialogClickListener).show();

    }
}
