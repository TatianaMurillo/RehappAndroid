package com.rehapp.rehappmovil.rehapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.PatientApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.TherapyApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.PatientViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;
import com.rehapp.rehappmovil.rehapp.Utils.UserMethods;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicalHistoryDetail extends AppCompatActivity {

private String therapyCreatedId;
private TextView tvTherapySequence;
private String action;
private TextView tvPatientNameValue;
private TextView tvAgeValue;
private TextView tvAgeNeighborhoodValue;
private String documentPatient;
private String MedicalHistorySelectedId;
private PatientViewModel patientViewModel;
private TherapyViewModel therapySelected;
private SharedPreferences sharedpreferences;
private TextView tvDateValue;

private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history_detail);

        sharedpreferences=getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);


        tvDateValue=findViewById(R.id.tvDateValue);
        tvPatientNameValue=findViewById(R.id.tvPatientNameValue);
        tvAgeValue=findViewById(R.id.tvAgeValue);

        recoverySendData();
        loadData();
        searchPatient();
    }

    private void loadData(){
        tvDateValue.setText(sdf.format(cal.getTime()));
    }

    private void recoverySendData()
    {
        if(getIntent().getExtras()!=null)
        {
                Bundle extras = getIntent().getExtras();
                action= extras.getString(PreferencesData.MedicaHistoryAction);
                MedicalHistorySelectedId=extras.getString(PreferencesData.MedicalHistorySelectedId);
                storeStringSharepreferences(PreferencesData.MedicalHistorySelectedId, MedicalHistorySelectedId);
                storeStringSharepreferences(PreferencesData.MedicaHistoryAction, action);
        }

        documentPatient =sharedpreferences.getString(PreferencesData.PatientDocument,"");
    }

    public void blockData()
    {

    }

    public  void searchPatient() {
        Call<PatientViewModel> call = PatientApiAdapter.getApiService().getPatient(documentPatient);
        call.enqueue(new Callback<PatientViewModel>() {
            @Override
            public void onResponse(Call<PatientViewModel> call, Response<PatientViewModel> response) {
                if(response.isSuccessful())
                {
                    patientViewModel = response.body();
                    tvPatientNameValue.setText(patientViewModel.getPatient_first_name() + " " + patientViewModel.getPatient_first_lastname());
                    tvAgeValue.setText(String.valueOf(patientViewModel.getPatient_age()));
                }else{
                    if(response.raw().code()==404) {
                        Toast.makeText(getApplicationContext(), PreferencesData.searchPatientPatientNonExist, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MedicalHistoryDetail.this, MedicalHistoriesPatient.class);
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onFailure(Call<PatientViewModel> call, Throwable t)
            {
                Toast.makeText(getApplicationContext(), PreferencesData.searchPatientPatient +" "+ t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void searchTherapy(){
        String therapySelectedId= String.valueOf(sharedpreferences.getInt(PreferencesData.TherapyId,0));
        Call<TherapyViewModel> call = TherapyApiAdapter.getApiService().getTherapy(therapySelectedId);
        call.enqueue(new Callback<TherapyViewModel>() {
            @Override
            public void onResponse(Call<TherapyViewModel> call, Response<TherapyViewModel> response) {
                if(response.isSuccessful())
                {
                    therapySelected = response.body();
                    tvTherapySequence.setText(R.string.TherapySequence + therapySelected.getTherapy_sequence());
                    blockData();

                }else{
                    if(response.raw().code()==404) {
                        Toast.makeText(getApplicationContext(), PreferencesData.therapyDetailTherapyNonExist, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MedicalHistoryDetail.this, HistoryTherapiesPatient.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<TherapyViewModel> call, Throwable t) {

            }
        });
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
        }



        return super.onOptionsItemSelected(item);
    }

    public void showHideItems(Menu menu) {
        MenuItem item;

        String action=sharedpreferences.getString(PreferencesData.TherapyAction,"");

        if(action.equals("DETAIL")) {
            item = menu.findItem(R.id.create_therapy);
            item.setVisible(false);

            item = menu.findItem(R.id.save);
            item.setVisible(false);
        }else
       {
            item = menu.findItem(R.id.create_therapy);
            item.setVisible(false);
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

    public void addVitalSigns(View view) {

        MedicalHistorySelectedId =sharedpreferences.getString(PreferencesData.MedicalHistorySelectedId,"");

        Bundle args = new Bundle();
        args.putString(PreferencesData.MedicalHistorySelectedId,MedicalHistorySelectedId);
        Intent intent = new Intent(MedicalHistoryDetail.this, PatientMedicalHistoryVitalSigns.class);
        startActivity(intent);


    }

    public void addQuestionaries(View view) {

        Bundle args = new Bundle();
        args.putString(PreferencesData.MedicalHistorySelectedId,MedicalHistorySelectedId);
        Intent intent = new Intent(MedicalHistoryDetail.this, PatientMedicalHistoryQuestionaries.class);
        startActivity(intent);
    }

    public void addDiseases(View view) {

        MedicalHistoryDiseasesDialog medicalHistoryDiseasesDialog = new  MedicalHistoryDiseasesDialog();
        medicalHistoryDiseasesDialog.show(getSupportFragmentManager(),"");

    }

}
