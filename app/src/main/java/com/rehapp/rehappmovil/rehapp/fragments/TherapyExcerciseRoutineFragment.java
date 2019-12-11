package com.rehapp.rehappmovil.rehapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.ExerciseRoutineApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.TherapyExerciseRoutineApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.ExerciseRoutinesViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapyExcerciseRoutineViewModel;
import com.rehapp.rehappmovil.rehapp.R;
import com.rehapp.rehappmovil.rehapp.YoutubeVideo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TherapyExcerciseRoutineFragment extends Fragment {


    private Context mContext;
    View view;
    FragmentManager manager;
    private SharedPreferences sharedpreferences;
    TextView tvExerciseVideo;
    EditText etExerciseSpeed;
    EditText etExerciseFrequent;
    EditText etExerciseIntensity;
    EditText etConditions;
    EditText etPreConditions;
    EditText etObservations;


    String routineUrl;
    int routineId;

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
        view = inflater.inflate(R.layout.activity_therapy_exercise_detail, container, false);
        sharedpreferences = mContext.getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);

        tvExerciseVideo=view.findViewById(R.id.tvExerciseVideo);
        etExerciseSpeed=view.findViewById(R.id.etExerciseSpeed);
        etExerciseFrequent=view.findViewById(R.id.etExerciseFrequent);
        etExerciseIntensity=view.findViewById(R.id.etExerciseIntensity);
        etConditions=view.findViewById(R.id.etConditions);
        etPreConditions=view.findViewById(R.id.etPreConditions);
        etObservations=view.findViewById(R.id.etObservations);

        recoverySendData();
        searchRoutineDetail();

        tvExerciseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                watchVideo();
            }
        });
        return  view;
    }

    private void searchRoutineDetail(){
        String routineId=String.valueOf(sharedpreferences.getInt(PreferencesData.ExerciseRoutineId,0));
        String therapyId=String.valueOf(sharedpreferences.getInt(PreferencesData.TherapyId,0));
        Call<TherapyExcerciseRoutineViewModel> call = TherapyExerciseRoutineApiAdapter.getApiService().getTherapyExerciseRoutine(therapyId,routineId);
        call.enqueue(new Callback<TherapyExcerciseRoutineViewModel>() {
            @Override
            public void onResponse(Call<TherapyExcerciseRoutineViewModel> call, Response<TherapyExcerciseRoutineViewModel> response) {

                if(response.isSuccessful())
                {
                    TherapyExcerciseRoutineViewModel therapyRoutine=response.body();
                    setDataToView(therapyRoutine);
                }
            }

            @Override
            public void onFailure(Call<TherapyExcerciseRoutineViewModel> call, Throwable t) {

            }
        });
    }

    private void saveExerciseRoutineTherapy(){
        TherapyExcerciseRoutineViewModel therapyExcerciseRoutine=getDataFromView();
        Call<TherapyExcerciseRoutineViewModel> call = TherapyExerciseRoutineApiAdapter.getApiService().saveTherapyExerciseRoutine(therapyExcerciseRoutine,"");
        call.enqueue(new Callback<TherapyExcerciseRoutineViewModel>() {
            @Override
            public void onResponse(Call<TherapyExcerciseRoutineViewModel> call, Response<TherapyExcerciseRoutineViewModel> response) {

                if(response.isSuccessful())
                {
                    TherapyExcerciseRoutineViewModel routine=response.body();

                }
            }

            @Override
            public void onFailure(Call<TherapyExcerciseRoutineViewModel> call, Throwable t) {

            }
        });
    }

    private TherapyExcerciseRoutineViewModel getDataFromView(){
        TherapyExcerciseRoutineViewModel therapyExcerciseRoutine= new TherapyExcerciseRoutineViewModel();

        float speed = Float.parseFloat(etExerciseSpeed.getText().toString());
        float frequent = Float.parseFloat(etExerciseFrequent.getText().toString());
        float intensity = Float.parseFloat(etExerciseIntensity.getText().toString());
        String conditions = etConditions.getText().toString();
        String preconditions = etPreConditions.getText().toString();
        String observation = etObservations.getText().toString();
        int routineId=sharedpreferences.getInt(PreferencesData.ExerciseRoutineId,0);

        therapyExcerciseRoutine.setExerciseRoutineId(routineId);
        therapyExcerciseRoutine.setTherapy_excercise_routine_speed(speed);
        therapyExcerciseRoutine.setTherapy_excercise_routine_frequent(frequent);
        therapyExcerciseRoutine.setTherapyExcerciseRoutineIntensity(intensity);
        therapyExcerciseRoutine.setConditions(conditions);
        therapyExcerciseRoutine.setPreconditions(preconditions);
        therapyExcerciseRoutine.setTherapyExcerciseRoutineObservation(observation);

        return therapyExcerciseRoutine;
    }

    private void setDataToView(TherapyExcerciseRoutineViewModel therapyExcerciseRoutine){

        etExerciseSpeed.setText(String.valueOf(therapyExcerciseRoutine.getTherapyExcerciseRoutineSpeed()));
        etExerciseFrequent.setText(String.valueOf(therapyExcerciseRoutine.getFrequency()));
        etExerciseIntensity.setText(String.valueOf(therapyExcerciseRoutine.getTherapyExcerciseRoutineIntensity()));
        etConditions.setText(therapyExcerciseRoutine.getConditions());
        etPreConditions.setText(therapyExcerciseRoutine.getConditions());
        etObservations.setText(therapyExcerciseRoutine.getTherapyExcerciseRoutineObservation());
    }

    private void recoverySendData() {
        if( getArguments()!=null)
        {

            Bundle extras = getArguments();
            routineUrl =extras.getString(PreferencesData.ExerciseRoutineUrl);
            routineId=extras.getInt(PreferencesData.ExerciseRoutineId);
            storeStringSharepreferences(PreferencesData.ExerciseRoutineUrl, routineUrl);
            storeIntSharepreferences(PreferencesData.ExerciseRoutineId, routineId);

        }
    }

    public void watchVideo() {
        String routineUrl=sharedpreferences.getString(PreferencesData.ExerciseRoutineUrl,"");
        Bundle extras = new Bundle();
        Intent intent = new Intent(mContext, YoutubeVideo.class);
        extras.putString(PreferencesData.ExerciseRoutineUrl,routineUrl);
        intent.putExtras(extras);
        startActivity(intent);

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
                saveExerciseRoutineTherapy();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showHideItems(Menu menu) {
        MenuItem item;

        String action=sharedpreferences.getString(PreferencesData.TherapyAction,"");

        if(action.equals("DETAIL")) {
            item = menu.findItem(R.id.create_therapy);
            item.setVisible(false);
            item = menu.findItem(R.id.save);
            item.setVisible(true);
        }else
        {
            item = menu.findItem(R.id.save);
            item.setVisible(false);
            item = menu.findItem(R.id.create_therapy);
            item.setVisible(true);
        }
    }

    private  void storeStringSharepreferences(String key, String value){

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();

    }

    private  void storeIntSharepreferences(String key, int value){

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(key, value);
        editor.commit();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }
}
