package com.rehapp.rehappmovil.rehapp.fragments;

import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.TherapyExerciseRoutineApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.QuestionaryOptionViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyExcerciseRoutineDetailViewModel;
import com.rehapp.rehappmovil.rehapp.R;
import com.rehapp.rehappmovil.rehapp.Utils.DataValidation;
import com.rehapp.rehappmovil.rehapp.Utils.ValidateInputs;

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
    TextView tvValue;
    TextView tvUnitOfMeasure;

    String viewOptions;
    String therapyExerciseRoutineId;
    String selectedCategoryId;
    int indexCategorySelected=-1;

    TherapyExcerciseRoutineDetailViewModel therapyRoutineDetail;
    ArrayList< QuestionaryOptionViewModel > options= new ArrayList<>();
    ArrayList<String> optionNames= new ArrayList<>();

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
        tlCategory=view.findViewById(R.id.tlCategory);
        etValue=view.findViewById(R.id.etValue);
        spnCategory=view.findViewById(R.id.spnCategory);
        etPreConditions=view.findViewById(R.id.etPreConditions);
        etObservations=view.findViewById(R.id.etObservations);
        tvTitle=view.findViewById(R.id.tvTitle);
        tvUnitOfMeasure=view.findViewById(R.id.tvUnitOfMeasure);
        tvValue=view.findViewById(R.id.tvValue);

        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /**se le resta  uno porque se esta agregando una opción por defecto al spinner cuando se  llena**/
                int selectedOption=position-1;
                if(selectedOption>-1) {
                    selectedCategoryId = options.get(selectedOption).getQuestionnaire_option_id();
                }
                indexCategorySelected = selectedOption;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        recoverySendData();
        setView();
        searchRoutineDetail();

        return  view;
    }

    private void searchRoutineDetail(){
        Call<TherapyExcerciseRoutineDetailViewModel> call = TherapyExerciseRoutineApiAdapter.getApiService().getTherapyDetailRoutine(viewOptions,therapyExerciseRoutineId);
        call.enqueue(new Callback<TherapyExcerciseRoutineDetailViewModel>() {
            @Override
            public void onResponse(Call<TherapyExcerciseRoutineDetailViewModel> call, Response<TherapyExcerciseRoutineDetailViewModel> response) {

                if(response.isSuccessful())
                {
                    therapyRoutineDetail=response.body();
                    options=therapyRoutineDetail.getOptions();
                    loadQuestionnaireOptions(options);
                    storeIntSharepreferences(PreferencesData.TherapyExerciseRoutineDetailId,therapyRoutineDetail.getTherapy_exercise_routine_detail_id());
                    setDataToView(therapyRoutineDetail);
                }else{
                    Toast.makeText(mContext, response.raw().message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TherapyExcerciseRoutineDetailViewModel> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void recoverySendData() {
        if( getArguments()!=null)
        {
            Bundle extras = getArguments();
            therapyExerciseRoutineId=extras.getString(PreferencesData.TherapyExerciseRoutineId);
            viewOptions = extras.getString(PreferencesData.ViewOption);
        }
    }

    public void loadQuestionnaireOptions(ArrayList< QuestionaryOptionViewModel > options) {
        optionNames.add(getResources().getString(R.string.CategoryNonSelected));
        for (QuestionaryOptionViewModel option : options) {
            optionNames.add(option.getOption_name());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, optionNames);
        spnCategory.setAdapter(arrayAdapter);

    }

    private void saveExerciseRoutineDetailTherapy(){

        String therapyExerciseRoutineDetailId=String.valueOf(sharedpreferences.getInt(PreferencesData.TherapyExerciseRoutineDetailId,0));
        List<Object> dataToSave=getDataFromView();

        boolean isDataRight=(Boolean)dataToSave.get(1);
        TherapyExcerciseRoutineDetailViewModel objectData=(TherapyExcerciseRoutineDetailViewModel)dataToSave.get(0);
        String msg =dataToSave.get(2).toString();

        if(isDataRight) {
            Call<TherapyExcerciseRoutineDetailViewModel> call = TherapyExerciseRoutineApiAdapter.getApiService().updateRoutineDetail(objectData, therapyExerciseRoutineDetailId);
            call.enqueue(new Callback<TherapyExcerciseRoutineDetailViewModel>() {
                @Override
                public void onResponse(Call<TherapyExcerciseRoutineDetailViewModel> call, Response<TherapyExcerciseRoutineDetailViewModel> response) {

                    if (response.isSuccessful()) {
                        Toast.makeText(mContext, PreferencesData.therapyRoutineSuccessCreationMessage, Toast.LENGTH_LONG).show();
                        loadFragment(new TherapyExcerciseRoutineFragment());
                    }else if(response.raw().code()==404){
                        Toast.makeText(mContext, PreferencesData.therapyRoutineUpdateWithoutRowsMsg, Toast.LENGTH_LONG).show();
                        loadFragment(new TherapyDetailFragment());
                    }else{
                        Toast.makeText(mContext, PreferencesData.therapyRoutineUpdateFailedMsg, Toast.LENGTH_LONG).show();
                        loadFragment(new TherapyDetailFragment());
                    }
                }

                @Override
                public void onFailure(Call<TherapyExcerciseRoutineDetailViewModel> call, Throwable t) {
                    Toast.makeText(mContext, PreferencesData.therapyRoutineFailedCreationMessage, Toast.LENGTH_LONG).show();
                }
            });
        }else{
            Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
        }
    }

    private List<Object> getDataFromView(){
        TherapyExcerciseRoutineDetailViewModel therapyExcerciseRoutineDetail= new TherapyExcerciseRoutineDetailViewModel();
        String value="0.0";
        String preconditions; String observation;


        List<Object> rpta = validateDataFromView();
        boolean checked = Boolean.parseBoolean(rpta.get(0).toString());
        String  msg = rpta.get(1).toString();

        if(checked) {
            if(!"I".equals(viewOptions)) {
                value=  etValue.getText().toString();
            }
            preconditions = etPreConditions.getText().toString();
            observation = etObservations.getText().toString();

            int therapyExerciseRoutineDetailId=sharedpreferences.getInt(PreferencesData.TherapyExerciseRoutineDetailId,0);

            therapyExcerciseRoutineDetail.setTherapy_exercise_routine_value(value);

            therapyExcerciseRoutineDetail.setTherapy_exercise_routine_observation(observation);
            therapyExcerciseRoutineDetail.setTherapy_exercise_routine_preconditions(preconditions);
            therapyExcerciseRoutineDetail.setTherapy_exercise_routine_id(String.valueOf(therapyExerciseRoutineId));
            therapyExcerciseRoutineDetail.setTherapy_exercise_routine_detail_id(therapyExerciseRoutineDetailId);
            therapyExcerciseRoutineDetail.setQuestionnaireId(therapyRoutineDetail.getQuestionnaireId());

            if(indexCategorySelected>-1){
                therapyExcerciseRoutineDetail.setTherapy_exercise_routine_option_id(selectedCategoryId);
            }

        }else{
            return Arrays.asList(therapyExcerciseRoutineDetail,false,msg);
        }
        return Arrays.asList(therapyExcerciseRoutineDetail,true,msg);
    }

    private void setDataToView(TherapyExcerciseRoutineDetailViewModel therapyExcerciseDetailRoutine){

        tvTitle.setText(String.valueOf(therapyExcerciseDetailRoutine.getQuestionnaireName()));
        tvUnitOfMeasure.setText(therapyExcerciseDetailRoutine.getUnit_of_measure_name()==null?"":therapyExcerciseDetailRoutine.getUnit_of_measure_name());
        etValue.setText(String.valueOf(therapyExcerciseDetailRoutine.getTherapy_exercise_routine_value()));
        selectLevelOption(therapyExcerciseDetailRoutine.getTherapy_exercise_routine_option_id());
        etPreConditions.setText(therapyExcerciseDetailRoutine.getTherapy_exercise_routine_preconditions());
        etObservations.setText(therapyExcerciseDetailRoutine.getTherapy_exercise_routine_observation());
    }

    private void selectLevelOption(String questionnaireOptionId){
        int indexOfCategory=-1;

        if(questionnaireOptionId!=null) {
            for (QuestionaryOptionViewModel option : options) {
                if (option.getQuestionnaire_option_id().equals(questionnaireOptionId)) {
                    /**se le suma  uno porque se esta agregando una opciòn por defecto al spinner cuando se  llena**/
                    indexOfCategory = options.indexOf(option) + 1;
                }
            }
        }
        if (indexOfCategory != -1) {
            spnCategory.setSelection(indexOfCategory);
        }else {
            /**se selecciona al menos la opcion que se crea por defecto**/
            spnCategory.setSelection(0);
        }

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
        int therapyExerciseRoutineDetailId=sharedpreferences.getInt(PreferencesData.TherapyExerciseRoutineDetailId,0);
        String value="0.1";
        if(!"I".equals(viewOptions)) {
            value=  etValue.getText().toString();
        }
        dataInput.add(new DataValidation(value, "Valor").textMaxLength(10).notZeroValue().noEmptyValue());
        dataInput.add(new DataValidation(etPreConditions.getText().toString(),"Precondiciones").textMaxLength(200).noEmptyValue());
        dataInput.add(new DataValidation(etObservations.getText().toString(),"Observaciones").textMaxLength(200).noEmptyValue());
        dataInput.add(new DataValidation(String.valueOf(selectedCategoryId),"Nivel").noEmptyValue().notZeroValue().selectedValue());
        dataInput.add(new DataValidation(String.valueOf(therapyExerciseRoutineId),"Rutina de terapia").noEmptyValue().notZeroValue().selectedValue());
        dataInput.add(new DataValidation(String.valueOf(therapyExerciseRoutineDetailId),"Id detalle de rutina de terapia").noEmptyValue().selectedValue());
        dataInput.add(new DataValidation(therapyRoutineDetail.getQuestionnaireId(),"Id del questionario").notZeroValue().selectedValue().noEmptyValue());

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
                saveExerciseRoutineDetailTherapy();
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
