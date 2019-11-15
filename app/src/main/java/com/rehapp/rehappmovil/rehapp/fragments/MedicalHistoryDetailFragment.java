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
import android.widget.TextView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.HistoryTherapiesPatient;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.PatientApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.TherapyApiAdapter;
import com.rehapp.rehappmovil.rehapp.MedicalHistoriesPatient;
import com.rehapp.rehappmovil.rehapp.MedicalHistoryDiseasesDialog;
import com.rehapp.rehappmovil.rehapp.Models.PatientViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;
import com.rehapp.rehappmovil.rehapp.PatientMedicalHistoryQuestionaries;
import com.rehapp.rehappmovil.rehapp.PatientMedicalHistoryVitalSigns;
import com.rehapp.rehappmovil.rehapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicalHistoryDetailFragment extends Fragment {

private String therapyCreatedId;
private TextView tvTherapySequence;
private String action;
private TextView tvPatientNameValue;
private TextView tvAgeValue;
private TextView tvAgeNeighborhoodValue;
private String documentPatient;
private String medicalHistorySelectedId;
private PatientViewModel patientViewModel;
private TherapyViewModel therapySelected;
private SharedPreferences sharedpreferences;
private TextView tvDateValue;

private Context mContext;
    View view;
    FragmentManager manager;


private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
Calendar cal = Calendar.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        manager = this.getFragmentManager();
        view =inflater.inflate(R.layout.activity_medical_history_detail,container,false);
        sharedpreferences=mContext.getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);


        sharedpreferences=mContext.getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);


        tvDateValue=view.findViewById(R.id.tvDateValue);
        tvPatientNameValue=view.findViewById(R.id.tvPatientNameValue);
        tvAgeValue=view.findViewById(R.id.tvAgeValue);

        recoverySendData();
        loadData();
        searchPatient();

        return view;
    }

    private void loadData(){
        tvDateValue.setText(sdf.format(cal.getTime()));
    }

    private void recoverySendData()
    {
        if(getArguments()!=null)
        {
                Bundle extras = getArguments();
                action= extras.getString(PreferencesData.MedicaHistoryAction);
                medicalHistorySelectedId=extras.getString(PreferencesData.MedicalHistorySelectedId);
                storeStringSharepreferences(PreferencesData.MedicalHistorySelectedId, medicalHistorySelectedId);
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
                        Toast.makeText(mContext, PreferencesData.searchPatientPatientNonExist, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(mContext, MedicalHistoriesPatient.class);
                        startActivity(intent);
                    }
                }
            }
            @Override
            public void onFailure(Call<PatientViewModel> call, Throwable t)
            {
                Toast.makeText(mContext, PreferencesData.searchPatientPatient +" "+ t.getMessage(),Toast.LENGTH_LONG).show();
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
                        Toast.makeText(mContext, PreferencesData.therapyDetailTherapyNonExist, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(mContext, HistoryTherapiesPatient.class);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        showHideItems(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
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

       medicalHistorySelectedId =sharedpreferences.getString(PreferencesData.MedicalHistorySelectedId,"");

        Bundle args = new Bundle();
        args.putString(PreferencesData.MedicalHistorySelectedId,medicalHistorySelectedId);
        Intent intent = new Intent(mContext, PatientMedicalHistoryVitalSigns.class);
        startActivity(intent);


    }

    public void addQuestionaries(View view) {

        Bundle args = new Bundle();
        args.putString(PreferencesData.MedicalHistorySelectedId,medicalHistorySelectedId);
        Intent intent = new Intent(mContext, PatientMedicalHistoryQuestionaries.class);
        startActivity(intent);
    }

    public void addDiseases(View view) {

        medicalHistorySelectedId =sharedpreferences.getString(PreferencesData.MedicalHistorySelectedId,"");

        Bundle args = new Bundle();
        args.putString(PreferencesData.MedicalHistorySelectedId,medicalHistorySelectedId);

        MedicalHistoryDiseasesDialog medicalHistoryDiseasesDialog = new  MedicalHistoryDiseasesDialog();
        medicalHistoryDiseasesDialog.setArguments(args);
        medicalHistoryDiseasesDialog.show(getFragmentManager(),"");


    }

    public void loadFragment(Fragment fragment){
        manager.beginTransaction().replace(R.id.content,fragment).commit();
    }


}