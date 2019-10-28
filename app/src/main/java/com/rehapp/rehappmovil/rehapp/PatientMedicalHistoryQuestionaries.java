package com.rehapp.rehappmovil.rehapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.QuestionariesApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.VitalSignApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.MedicalHistoryVitalSignPatientViewModel;
import com.rehapp.rehappmovil.rehapp.Models.OptionViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PhysiologicalParameterTherapyViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.QuestionariesViewModel;
import com.rehapp.rehappmovil.rehapp.Models.QuestionaryOptionViewModel;
import com.rehapp.rehappmovil.rehapp.Models.VitalSignViewModel;
import com.rehapp.rehappmovil.rehapp.Utils.UserMethods;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientMedicalHistoryQuestionaries extends AppCompatActivity {

    private ListView lvQuestionaries;
    private String MedicalHistorySelectedId;
    ArrayList<QuestionaryOptionViewModel> questionariesOptions;
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
                }
            }
            @Override
            public void onFailure(Call<ArrayList<QuestionaryOptionViewModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), PreferencesData.vitalSignsLoadFailed, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void recoverySendData() {
        MedicalHistorySelectedId =sharedpreferences.getString(PreferencesData.MedicalHistorySelectedId,"");
    }

    private void addQuestionariesToView( ArrayList<QuestionaryOptionViewModel> questionaryOptions) {
        Spinner spinner;
        TextView textView;
        ArrayList<String>  optionNames;

        for(QuestionaryOptionViewModel questionaryOption : questionaryOptions)
        {

            spinner=new Spinner(getApplicationContext());
            textView = new TextView(getApplicationContext());
            ArrayList<OptionViewModel> options=questionaryOption.getOptions();

            optionNames=new ArrayList<>();

            for (OptionViewModel option:options )
            {
                optionNames.add(option.getOption_name());
            }
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(PatientMedicalHistoryQuestionaries.this,android.R.layout.simple_list_item_1,optionNames);
            spinner.setAdapter(arrayAdapter);
            textView.setText(questionaryOption.getQuestionnaire_name());
            textView.setTag(questionaryOption.getQuestionnaire_id());
            lvQuestionaries.addView(textView);
            lvQuestionaries.addView(spinner);
        }
    }

    private List<QuestionaryOptionViewModel> getQuestionaryOptionIdFromView() {

        List<QuestionaryOptionViewModel> data = new ArrayList<>();

        int childCount = lvQuestionaries.getChildCount();


        for ( int i=0;i<childCount;i++) {

            LinearLayout children = (LinearLayout)lvQuestionaries.getChildAt(i);
            TableLayout children2 = (TableLayout) children.getChildAt(0);
            TableRow children3 = (TableRow) children2.getChildAt(0);
            Spinner spn = (Spinner) children3.getChildAt(1);
            TextView tv = (TextView) children3.getChildAt(0);

            String questionaireId=tv.getTag().toString();
            String selectedOptionId = spn.getTag().toString();

            QuestionaryOptionViewModel model = new QuestionaryOptionViewModel();
            model.setQuestionnaire_id(questionaireId);
            model.setOption_id(selectedOptionId);
            data.add(model);
        }

        return data;
    }

    private void addVitalSignsMedicalHistoryToView(final ArrayList<QuestionariesViewModel> questionaries) {

        Call<ArrayList<MedicalHistoryVitalSignPatientViewModel>> call = VitalSignApiAdapter.getApiService().getVitalSignsByMedicalHistory(MedicalHistorySelectedId);
        call.enqueue(new Callback<ArrayList<MedicalHistoryVitalSignPatientViewModel>>() {
            @Override
            public void onResponse(Call<ArrayList<MedicalHistoryVitalSignPatientViewModel>> call, Response<ArrayList<MedicalHistoryVitalSignPatientViewModel>> response) {
                if (response.isSuccessful()) {
                    ArrayList<MedicalHistoryVitalSignPatientViewModel> lVitalSignsPatient = response.body();
                    for (MedicalHistoryVitalSignPatientViewModel vitalSignPatient : lVitalSignsPatient) {
                       /* for (VitalSignViewModel vitalSign : vitalSigns) {
                            if (vitalSign.getVital_sign_id() == vitalSignPatient.getPatient_vital_signs_id()) {
                                setVitalSignsToView(vitalSign.getVital_sign_name(), vitalSignPatient.getPatient_vital_signs_value());
                            }
                        }
                        */
                    }

                } else if (response.raw().code() == 404) {
                    System.out.println(PreferencesData.medicalHistoryVitalSignEmptyListMsg);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MedicalHistoryVitalSignPatientViewModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    private  void setVitalSignsToView(String vitalSignName,String VitalSignValue) {

        int childCount = lvQuestionaries.getChildCount();
        String valueTextView;
        Object childEditText;
        Object childTextView;
        TextView textView;
        EditText editText;

        for (int i = 1; i < childCount; i += 2) {
            childEditText = lvQuestionaries.getChildAt(i);
            childTextView = lvQuestionaries.getChildAt(i - 1);

            textView = (TextView) childTextView;
            valueTextView = textView.getText().toString().trim();
            editText = (EditText) childEditText;

            if(vitalSignName.equals(valueTextView)) {
                editText.setText(VitalSignValue);
            }
        }
    }

    private void saveQuestionariesOptionsSelected() {
        getQuestionaryOptionIdFromView();
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
                getQuestionaryOptionIdFromView();
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
