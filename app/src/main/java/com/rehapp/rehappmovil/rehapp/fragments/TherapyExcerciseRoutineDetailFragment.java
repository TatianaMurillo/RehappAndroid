package com.rehapp.rehappmovil.rehapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.TherapyExerciseRoutineApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapyExcerciseRoutineViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;
import com.rehapp.rehappmovil.rehapp.R;
import com.rehapp.rehappmovil.rehapp.Utils.DataValidation;
import com.rehapp.rehappmovil.rehapp.Utils.ValidateInputs;
import com.rehapp.rehappmovil.rehapp.YoutubeVideo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TherapyExcerciseRoutineDetailFragment extends Fragment {


    private Context mContext;
    View view;
    FragmentManager manager;
    private SharedPreferences sharedpreferences;


    TableLayout tlValue;
    EditText etValue;
    TableLayout tlCategory;
    Spinner spnCategory;
    EditText etPreConditions;
    EditText etObservations;
    TextView tvTitle;
    TextView tvUnitOfMeasure;

    String viewOptions;
    String routineUrl;
    int routineId;
    int selectedCategoryId;

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
        view = inflater.inflate(R.layout.activity_therapy_exercise_routine_detail, container, false);
        sharedpreferences = mContext.getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);



        tlValue=view.findViewById(R.id.tlValue);
        etValue=view.findViewById(R.id.etValue);
        tlCategory=view.findViewById(R.id.tlCategory);
        spnCategory=view.findViewById(R.id.spnCategory);
        etPreConditions=view.findViewById(R.id.etPreConditions);
        etObservations=view.findViewById(R.id.etObservations);
        tvTitle=view.findViewById(R.id.tvTitle);
        tvUnitOfMeasure=view.findViewById(R.id.tvUnitOfMeasure);



        recoverySendData();
        setView();
        searchRoutineDetail();

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
                    int therapyExerciseRoutineId=therapyRoutine.getTherapyExcerciseRoutineId();
                    storeIntSharepreferences(PreferencesData.TherapyExerciseRoutineId,therapyExerciseRoutineId);
                    setDataToView(therapyRoutine);
                }else{

                }
            }

            @Override
            public void onFailure(Call<TherapyExcerciseRoutineViewModel> call, Throwable t) {

            }
        });
    }

    private void recoverySendData() {
        if( getArguments()!=null)
        {
            Bundle extras = getArguments();
            routineUrl =extras.getString(PreferencesData.ExerciseRoutineUrl);
            routineId=extras.getInt(PreferencesData.ExerciseRoutineId);
            viewOptions = extras.getString(PreferencesData.ViewOption);
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

    private void saveExerciseRoutineTherapy(){
        String therapyId=String.valueOf(sharedpreferences.getInt(PreferencesData.TherapyId,0));
        List<Object> dataToSave=getDataFromView();

        boolean isDataRight=(Boolean)dataToSave.get(1);
        TherapyExcerciseRoutineViewModel objectData=(TherapyExcerciseRoutineViewModel)dataToSave.get(0);
        String msg =dataToSave.get(2).toString();

        if(isDataRight) {
            Call<TherapyExcerciseRoutineViewModel> call = TherapyExerciseRoutineApiAdapter.getApiService().updateRoutine(objectData, therapyId);
            call.enqueue(new Callback<TherapyExcerciseRoutineViewModel>() {
                @Override
                public void onResponse(Call<TherapyExcerciseRoutineViewModel> call, Response<TherapyExcerciseRoutineViewModel> response) {

                    if (response.isSuccessful()) {
                        saveTherapyTotalDuration();
                    }else if(response.raw().code()==404){
                        Toast.makeText(mContext, PreferencesData.therapyRoutineUpdateWithoutRowsMsg, Toast.LENGTH_LONG).show();
                        loadFragment(new TherapyDetailFragment());
                    }else{
                        Toast.makeText(mContext, PreferencesData.therapyRoutineUpdateFailedMsg, Toast.LENGTH_LONG).show();
                        loadFragment(new TherapyDetailFragment());
                    }
                }

                @Override
                public void onFailure(Call<TherapyExcerciseRoutineViewModel> call, Throwable t) {
                    Toast.makeText(mContext, PreferencesData.therapyRoutineFailedCreationMessage, Toast.LENGTH_LONG).show();
                }
            });
        }else{
            Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
        }
    }

    private void saveTherapyTotalDuration(){
        String therapyId= String.valueOf(sharedpreferences.getInt(PreferencesData.TherapyId,0));
        Call<TherapyViewModel> call = TherapyExerciseRoutineApiAdapter.getApiService().updateTherapyDuration(therapyId);
        call.enqueue(new Callback<TherapyViewModel>() {
            @Override
            public void onResponse(Call<TherapyViewModel> call, Response<TherapyViewModel> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(mContext, PreferencesData.therapyRoutineSuccessCreationMessage, Toast.LENGTH_LONG).show();
                    loadFragment(new TherapyDetailFragment());
                }else if(response.raw().code()==404){
                    Toast.makeText(mContext, PreferencesData.therapyNotFoundMsg, Toast.LENGTH_LONG).show();
                    loadFragment(new TherapyDetailFragment());
                }else{
                    Toast.makeText(mContext, PreferencesData.therapyRoutineFailedCreationMessage, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<TherapyViewModel> call, Throwable t) {
                Toast.makeText(mContext, PreferencesData.therapyDetailSaveExerciseRoutineFailedMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    private List<Object> getDataFromView(){
        TherapyExcerciseRoutineViewModel therapyExcerciseRoutine= new TherapyExcerciseRoutineViewModel();
        String value;float frequent;float intensity;float duration;
        String preconditions; String observation;


        List<Object> rpta = validateDataFromView();
        boolean checked = Boolean.parseBoolean(rpta.get(0).toString());
        String  msg = rpta.get(1).toString();

        if(checked) {
            value = etValue.getText().toString();
            intensity =selectedCategoryId;
            preconditions = etPreConditions.getText().toString();
            observation = etObservations.getText().toString();

            int routineId=sharedpreferences.getInt(PreferencesData.ExerciseRoutineId,0);

            therapyExcerciseRoutine.setFrequency(0);
            therapyExcerciseRoutine.setExerciseRoutineId(routineId);
            therapyExcerciseRoutine.setFrequency(0);
            therapyExcerciseRoutine.setTherapyExcerciseRoutineIntensity(intensity);
            therapyExcerciseRoutine.setPreconditions(preconditions);
            therapyExcerciseRoutine.setTherapyExcerciseRoutineObservation(observation);
        }else{
            return Arrays.asList(therapyExcerciseRoutine,false,msg);
        }
        return Arrays.asList(therapyExcerciseRoutine,true,msg);
    }

    private void setDataToView(TherapyExcerciseRoutineViewModel therapyExcerciseRoutine){

        etValue.setText(String.valueOf(therapyExcerciseRoutine.getTherapyExcerciseRoutineSpeed()));
        selectLevelOption();
        etPreConditions.setText(therapyExcerciseRoutine.getPreConditions());
        etObservations.setText(therapyExcerciseRoutine.getTherapyExcerciseRoutineObservation());
    }

    private void selectLevelOption(){

    }

    private void setView(){

        switch (viewOptions){
            case PreferencesData.Intensity:
                tlValue.setVisibility(View.INVISIBLE);
                break;
            case PreferencesData.Frequency:
                tlCategory.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private List<Object> validateDataFromView(){

        List<DataValidation> dataInput= new ArrayList<>();
        int routineId=sharedpreferences.getInt(PreferencesData.ExerciseRoutineId,0);

        dataInput.add(new DataValidation(etValue.getText().toString(),"Valor").textMaxLength(10).notZeroValue().noEmptyValue());
        dataInput.add(new DataValidation(etPreConditions.getText().toString(),"Precondiciones").textMaxLength(200).noEmptyValue());
        dataInput.add(new DataValidation(etObservations.getText().toString(),"Observaciones").textMaxLength(200).noEmptyValue());
        dataInput.add(new DataValidation(String.valueOf(selectedCategoryId),"Nivel").noEmptyValue().notZeroValue().selectedValue());

        return ValidateInputs.validate().ValidateDataObject(dataInput);
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
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    /**
     *
     * Metodos utiles
     */
    public void showHideItems(Menu menu) {
        MenuItem item;

            item = menu.findItem(R.id.save);
            item.setVisible(true);
            item = menu.findItem(R.id.create_therapy);
            item.setVisible(false);
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

    public void loadFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=manager.beginTransaction();
        fragmentTransaction.replace(R.id.content,fragment);
        fragmentTransaction.commit();
    }
}
