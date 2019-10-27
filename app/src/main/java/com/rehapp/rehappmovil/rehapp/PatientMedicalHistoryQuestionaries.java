package com.rehapp.rehappmovil.rehapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.QuestionariesApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.VitalSignApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APISERVICES.QuestionariesApiService;
import com.rehapp.rehappmovil.rehapp.Models.MedicalHistoryVitalSignPatientViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.QuestionariesViewModel;
import com.rehapp.rehappmovil.rehapp.Models.VitalSignViewModel;
import com.rehapp.rehappmovil.rehapp.Utils.UserMethods;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientMedicalHistoryQuestionaries extends AppCompatActivity {

    private GridLayout grid;
    private String MedicalHistorySelectedId;
    ArrayList<QuestionariesViewModel> questionaries;
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionaries_patient);

        sharedpreferences=getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);

        grid = findViewById(R.id.grid);

        recoverySendData();
        loadQuestionaries();



    }

    private void loadQuestionaries() {
        Call<ArrayList<QuestionariesViewModel>> call = QuestionariesApiAdapter.getApiService().getQuestionaries();
        call.enqueue(new Callback<ArrayList<QuestionariesViewModel>>() {
            @Override
            public void onResponse(Call<ArrayList<QuestionariesViewModel>> call, Response<ArrayList<QuestionariesViewModel>> response) {
                if (response.isSuccessful()) {
                    questionaries = response.body();
                    addQuestionariesToView(questionaries);
                    //addVitalSignsMedicalHistoryToView(questionaries);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<QuestionariesViewModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), PreferencesData.vitalSignsLoadFailed, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void recoverySendData()
    {
        MedicalHistorySelectedId =sharedpreferences.getString(PreferencesData.MedicalHistorySelectedId,"");
    }

    private void addQuestionariesToView( ArrayList<QuestionariesViewModel> questionaries) {
        EditText editText;
        TextView textView;

        for(QuestionariesViewModel questionary : questionaries)
        {

            textView = new TextView(getApplicationContext());
            editText = new EditText(getApplicationContext());

            textView.setText(questionary.getQuestionnaire_name());
            editText.setEms(PreferencesData.VitalSignsTherapyValueSize);
            editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(PreferencesData.VitalSignsTherapyValueSize) });
            editText.setSingleLine(true);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editText.setGravity(Gravity.CENTER);
            grid.addView(textView);
            grid.addView(editText);
        }
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

        int childCount = grid.getChildCount();
        String valueTextView;
        Object childEditText;
        Object childTextView;
        TextView textView;
        EditText editText;

        for (int i = 1; i < childCount; i += 2) {
            childEditText = grid.getChildAt(i);
            childTextView = grid.getChildAt(i - 1);

            textView = (TextView) childTextView;
            valueTextView = textView.getText().toString().trim();
            editText = (EditText) childEditText;

            if(vitalSignName.equals(valueTextView)) {
                editText.setText(VitalSignValue);
            }
        }
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
