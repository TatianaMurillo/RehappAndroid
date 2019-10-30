package com.rehapp.rehappmovil.rehapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.QuestionariesApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.OptionViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PatientMedicalHistoryQuestionaireOptionViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.QuestionaryOptionViewModel;
import com.rehapp.rehappmovil.rehapp.Utils.UserMethods;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientMedicalHistoryQuestionaries extends AppCompatActivity {

    private ListView lvQuestionaries;
    private String medicalHistorySelectedId;
    ArrayList<QuestionaryOptionViewModel> questionariesOptions;
    ArrayList<QuestionaryOptionViewModel> patientQuestionaries;
    private SharedPreferences sharedpreferences;
    ArrayList<String> optionNames= new ArrayList<>();
    MedicalHistoryQuestionariesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionaries_patient);

        adapter=null;
        sharedpreferences=getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);
        lvQuestionaries = findViewById(R.id.lvQuestionaries);


        recoverySendData();
        loadQuestionaries();

    }

    private void loadQuestionaries() {
        Call<ArrayList<QuestionaryOptionViewModel>> call = QuestionariesApiAdapter.getApiService().getQuestionariesOptions();
        call.enqueue(new Callback<ArrayList<QuestionaryOptionViewModel>>() {
            @Override
            public void onResponse(Call<ArrayList<QuestionaryOptionViewModel>> call, Response<ArrayList<QuestionaryOptionViewModel>> response) {
                if (response.isSuccessful()) {
                    questionariesOptions = response.body();
                    adapter = new MedicalHistoryQuestionariesAdapter(PatientMedicalHistoryQuestionaries.this,questionariesOptions);
                    lvQuestionaries.setAdapter(adapter);
                    loadPatientMedicalHistoryQuestionaireOption();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<QuestionaryOptionViewModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), PreferencesData.questionairesLoadFailed, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadPatientMedicalHistoryQuestionaireOption() {
        Call<ArrayList<PatientMedicalHistoryQuestionaireOptionViewModel>> call = QuestionariesApiAdapter.getApiService().getPatienHistoryMedicalQuestionariesOptions(medicalHistorySelectedId);
        call.enqueue(new Callback<ArrayList<PatientMedicalHistoryQuestionaireOptionViewModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PatientMedicalHistoryQuestionaireOptionViewModel>> call, Response<ArrayList<PatientMedicalHistoryQuestionaireOptionViewModel>> response) {
                if (response.isSuccessful()) {
                    ArrayList<PatientMedicalHistoryQuestionaireOptionViewModel> options = response.body();
                    setQuestionaryOptionToView(options);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PatientMedicalHistoryQuestionaireOptionViewModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), PreferencesData.questionairesOptionSelectedLoadFailed, Toast.LENGTH_LONG).show();
            }
        });


    }

    private void recoverySendData() {
        medicalHistorySelectedId =sharedpreferences.getString(PreferencesData.MedicalHistorySelectedId,"");
    }

    private void saveQuestionaireOptions()
    {
        List<PatientMedicalHistoryQuestionaireOptionViewModel> data;
        data=getQuestionaryOptionIdFromView();
        if(data!=null) {
            Call<List<PatientMedicalHistoryQuestionaireOptionViewModel>> call = QuestionariesApiAdapter.getApiService().saveQuestionairesOptionsInMedicalHistory(data, medicalHistorySelectedId);
            call.enqueue(new Callback<List<PatientMedicalHistoryQuestionaireOptionViewModel>>() {
                @Override
                public void onResponse(Call<List<PatientMedicalHistoryQuestionaireOptionViewModel>> call, Response<List<PatientMedicalHistoryQuestionaireOptionViewModel>> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), PreferencesData.questionairesOptionCreateSuccessMsg, Toast.LENGTH_LONG).show();
                        goToMedicalHistoryDetail();
                    }else if(response.raw().code()==500)
                    {
                        Toast.makeText(getApplicationContext(), response.raw().message(), Toast.LENGTH_LONG).show();
                        goToMedicalHistoryDetail();
                    }
                }
                @Override
                public void onFailure(Call<List<PatientMedicalHistoryQuestionaireOptionViewModel>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), PreferencesData.questionairesOptionCreateFailedMsg, Toast.LENGTH_LONG).show();

                }
            });
        }else{
            Intent intent = new Intent(PatientMedicalHistoryQuestionaries.this, MedicalHistoryDetail.class);
            startActivity(intent);
        }

    }


    private List<PatientMedicalHistoryQuestionaireOptionViewModel> getQuestionaryOptionIdFromView() {

        List<PatientMedicalHistoryQuestionaireOptionViewModel> data = new ArrayList<>();

        int childCount = lvQuestionaries.getChildCount();


        for ( int i=0;i<childCount;i++) {

            LinearLayout children = (LinearLayout)lvQuestionaries.getChildAt(i);
            TableLayout children2 = (TableLayout) children.getChildAt(0);
            TableRow children3 = (TableRow) children2.getChildAt(0);
            Spinner spn = (Spinner) children3.getChildAt(1);
            TextView tv = (TextView) children3.getChildAt(0);


            Object tagSelectedOptionId = spn.getTag();
            Object tagQuestionaireIdId = tv.getTag();
            if(tagSelectedOptionId==null || tagQuestionaireIdId==null)
            {
                Toast.makeText(getApplicationContext(), PreferencesData.questionairesOptionResponseMsg, Toast.LENGTH_LONG).show();
                return null;
            }
            String questionaireId=tv.getTag().toString();
            String selectedOptionId = spn.getTag().toString();

            PatientMedicalHistoryQuestionaireOptionViewModel model = new PatientMedicalHistoryQuestionaireOptionViewModel();
            model.setQuestionnaire_id(questionaireId);
            model.setOption_id(selectedOptionId);
            model.setPtnt_mdcl_hstry_id(medicalHistorySelectedId);

            data.add(model);
        }

        return data;
    }

    private void setQuestionaryOptionToView( List<PatientMedicalHistoryQuestionaireOptionViewModel> patientQuestionaireOption) {

        int childCount = lvQuestionaries.getChildCount();

        for(PatientMedicalHistoryQuestionaireOptionViewModel patientMedicalHistoryQuestionaireOption:patientQuestionaireOption ){

            OptionViewModel option = new OptionViewModel();
            option.setOption_name(patientMedicalHistoryQuestionaireOption.getOption_name());
            option.setOption_description(patientMedicalHistoryQuestionaireOption.getOption_description());
            option.setOption_id(patientMedicalHistoryQuestionaireOption.getOption_id());


            for ( int i=0;i<childCount;i++) {

                LinearLayout children = (LinearLayout)lvQuestionaries.getChildAt(i);
                TableLayout children2 = (TableLayout) children.getChildAt(0);
                TableRow children3 = (TableRow) children2.getChildAt(0);
                Spinner spn = (Spinner) children3.getChildAt(1);
                TextView tv = (TextView) children3.getChildAt(0);


                String questionaireId=tv.getTag().toString();
                if(questionaireId.equals(patientMedicalHistoryQuestionaireOption.getQuestionnaire_id()))
                {
                    spn.setSelection(getIndexOfOption(option,questionaireId));
                }
            }
        }

    }

    private int getIndexOfOption(OptionViewModel selectedOption,String questionaireId){
        int index=-1;
        List<OptionViewModel> options;
        for(QuestionaryOptionViewModel questionaireOption:questionariesOptions){
            if(questionaireId.equals(questionaireOption.getQuestionnaire_id())) {
                options = questionaireOption.getOptions();
                for (OptionViewModel item:options)
                {
                    if(item.getOption_id().equals(selectedOption.getOption_id())){
                        index = options.indexOf(item);
                        return index;
                    }
                }

            }
        }
        return index;
    }

    private void goToMedicalHistoryDetail(){
        Intent intent = new Intent(PatientMedicalHistoryQuestionaries.this, MedicalHistoryDetail.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,  menu);
        showHideItems(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                UserMethods.getInstance().Logout(this);
                break;
            case R.id.save:
                saveQuestionaireOptions();
                break;
        }



        return super.onOptionsItemSelected(item);
    }

    public void showHideItems(Menu menu) {
        MenuItem item;
        item = menu.findItem(R.id.save);
        item.setVisible(true);
        item = menu.findItem(R.id.create_therapy);
        item.setVisible(false);

    }



}
