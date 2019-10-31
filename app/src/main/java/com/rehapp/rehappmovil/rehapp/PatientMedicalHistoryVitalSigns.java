package com.rehapp.rehappmovil.rehapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.VitalSignApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.PatientMedicalHistoryVitalSignViewModel;
import com.rehapp.rehappmovil.rehapp.Models.VitalSignViewModel;
import com.rehapp.rehappmovil.rehapp.Utils.UserMethods;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientMedicalHistoryVitalSigns extends AppCompatActivity {

    GridLayout grid;
    String medicalHistorySelectedId;
    ArrayList<VitalSignViewModel> vitalSigns;
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vital_sign_patient);

        sharedpreferences=getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);

        grid = findViewById(R.id.grid);

        loadVitalSigns();
        recoverySendData();


    }

    private void loadVitalSigns() {
        Call<ArrayList<VitalSignViewModel>> call = VitalSignApiAdapter.getApiService().getVitalSigns();
        call.enqueue(new Callback<ArrayList<VitalSignViewModel>>() {
            @Override
            public void onResponse(Call<ArrayList<VitalSignViewModel>> call, Response<ArrayList<VitalSignViewModel>> response) {
                if (response.isSuccessful()) {
                   vitalSigns = response.body();
                    addVitalSignsToView(vitalSigns);
                    addVitalSignsMedicalHistoryToView(vitalSigns);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<VitalSignViewModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), PreferencesData.vitalSignsLoadFailed, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void recoverySendData()
    {
        medicalHistorySelectedId =sharedpreferences.getString(PreferencesData.MedicalHistorySelectedId,"");
    }

    private void addVitalSignsToView( ArrayList<VitalSignViewModel> vitalSigns) {
        EditText editText;
        TextView textView;

        for(VitalSignViewModel vitalSign : vitalSigns)
        {

            textView = new TextView(getApplicationContext());
            editText = new EditText(getApplicationContext());

            textView.setText(vitalSign.getVital_sign_name());
            editText.setEms(PreferencesData.VitalSignsTherapyValueSize);
            editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(PreferencesData.VitalSignsTherapyValueSize) });
            editText.setSingleLine(true);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editText.setGravity(Gravity.CENTER);
            grid.addView(textView);
            grid.addView(editText);
        }
    }


    private void addVitalSignsMedicalHistoryToView(final ArrayList<VitalSignViewModel> vitalSigns) {

        Call<ArrayList<PatientMedicalHistoryVitalSignViewModel>> call = VitalSignApiAdapter.getApiService().getVitalSignsByMedicalHistory(medicalHistorySelectedId);
        call.enqueue(new Callback<ArrayList<PatientMedicalHistoryVitalSignViewModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PatientMedicalHistoryVitalSignViewModel>> call, Response<ArrayList<PatientMedicalHistoryVitalSignViewModel>> response) {
                if (response.isSuccessful()) {
                    ArrayList<PatientMedicalHistoryVitalSignViewModel> lVitalSignsPatient = response.body();
                    for (PatientMedicalHistoryVitalSignViewModel vitalSignPatient : lVitalSignsPatient) {
                        for (VitalSignViewModel vitalSign : vitalSigns) {
                            if (vitalSign.getVital_sign_id().equals(vitalSignPatient.getVital_sign_id())) {
                                setVitalSignsToView(vitalSign.getVital_sign_name(), vitalSignPatient.getPatient_vital_signs_value());
                                break;
                            }
                        }
                    }

                } else if (response.raw().code() == 404) {
                    System.out.println(PreferencesData.medicalHistoryVitalSignEmptyListMsg);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PatientMedicalHistoryVitalSignViewModel>> call, Throwable t) {
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


    private void saveMedicalHistoryVitalSigns() {

        List<PatientMedicalHistoryVitalSignViewModel> data = setVitalSignsFromView();

        Call<List<PatientMedicalHistoryVitalSignViewModel>> call = VitalSignApiAdapter.getApiService().saveMedicalHistoryVitalSigns(data,medicalHistorySelectedId);
        call.enqueue(new Callback<List<PatientMedicalHistoryVitalSignViewModel>>() {
            @Override
            public void onResponse(Call<List<PatientMedicalHistoryVitalSignViewModel>> call, Response<List<PatientMedicalHistoryVitalSignViewModel>> response) {
                if (response.isSuccessful()) {
                    String mgs = response.body().size() + " " + PreferencesData.MedicalHistoryVitalSignsSuccessMgs;
                    Toast.makeText(getApplicationContext(), mgs, Toast.LENGTH_LONG).show();
                    goToMedicalHistoryDetail();
                }else if(response.raw().code()!=200)
                {
                    Toast.makeText(getApplicationContext(), response.raw().message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<PatientMedicalHistoryVitalSignViewModel>> call, Throwable t) {
                String msg = PreferencesData.MedicalHistoryVitalSignsDataMgsError+"\n"+t.getMessage();
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                goToMedicalHistoryDetail();

            }
        });
    }

    private List<PatientMedicalHistoryVitalSignViewModel> setVitalSignsFromView() {

        List<PatientMedicalHistoryVitalSignViewModel> data = new ArrayList<>();
        PatientMedicalHistoryVitalSignViewModel patientMedicalHistoryQuestionaireOption;
        int childCount = grid.getChildCount();
        String valueEditText;
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

            valueEditText = editText.getText().toString().trim();
            patientMedicalHistoryQuestionaireOption=  new PatientMedicalHistoryVitalSignViewModel();
            patientMedicalHistoryQuestionaireOption.setPtnt_mdcl_hstry_id(medicalHistorySelectedId);
            patientMedicalHistoryQuestionaireOption.setVital_sign_id(getIdVitalSign(valueTextView));
            patientMedicalHistoryQuestionaireOption.setPatient_vital_signs_value(valueEditText);
            data.add(patientMedicalHistoryQuestionaireOption);

        }

        return data;

    }

    private String getIdVitalSign(String name) {
        for (VitalSignViewModel vitalSign : vitalSigns) {
            if (vitalSign.getVital_sign_name().equals(name)) {
                return vitalSign.getVital_sign_id();
            }
        }
        return "";
    }

    private void goToMedicalHistoryDetail(){
        Intent intent = new Intent(PatientMedicalHistoryVitalSigns.this, MedicalHistoryDetail.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
                saveMedicalHistoryVitalSigns();
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
