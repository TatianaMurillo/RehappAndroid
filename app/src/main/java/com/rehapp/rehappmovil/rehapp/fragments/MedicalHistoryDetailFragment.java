package com.rehapp.rehappmovil.rehapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.PatientApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.PatientMedicalHistoryApiAdapter;
import com.rehapp.rehappmovil.rehapp.MedicalHistoriesPatient;
import com.rehapp.rehappmovil.rehapp.MedicalHistoryDiseasesDialog;
import com.rehapp.rehappmovil.rehapp.Models.PatientMedicalHistoryViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PatientViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.R;
import com.rehapp.rehappmovil.rehapp.Utils.ValidateInputs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicalHistoryDetailFragment extends Fragment {


private TextView tvMedicalHistorySequence;
private String action;
private TextView tvPatientNameValue;
private TextView tvNeighborhoodValue;
private TextView tvAgeValue;
private String medicalHistorySelectedId;
private PatientViewModel patientViewModel;
private SharedPreferences sharedpreferences;
private TextView tvDateValue;
private TextView  tvVitalSigns;
private TextView  tvDiseases;
private TextView  tvQuestionaries;
private EditText etHistoryName;
private EditText etAdditionalInfo;


private Context mContext;
    View view;
    FragmentManager manager;

private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
Calendar cal = Calendar.getInstance();

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
        view =inflater.inflate(R.layout.activity_medical_history_detail,container,false);
        sharedpreferences=mContext.getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);


        sharedpreferences=mContext.getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);


        tvDateValue=view.findViewById(R.id.tvDateValue);
        tvPatientNameValue=view.findViewById(R.id.tvPatientNameValue);
        tvAgeValue=view.findViewById(R.id.tvAgeValue);
        tvVitalSigns=view.findViewById(R.id.tvVitalSigns);
        tvDiseases=view.findViewById(R.id.tvDiseases);
        tvQuestionaries=view.findViewById(R.id.tvQuestionaries);
        tvMedicalHistorySequence=view.findViewById(R.id.tvMedicalHistorySequence);
        tvNeighborhoodValue=view.findViewById(R.id.tvNeighborhoodValue);
        etHistoryName=view.findViewById(R.id.etHistoryName);
        etAdditionalInfo=view.findViewById(R.id.etAdditionalInfo);

        tvVitalSigns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVitalSigns(view);
            }
        });


        tvDiseases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDiseases(view);
            }
        });


        tvQuestionaries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addQuestionaries(view);
            }
        });

        recoverySendData();
        loadData();
        searchPatient();
        searchMedicalHistory();

        return view;
    }

    private void loadData(){
        tvDateValue.setText(sdf.format(cal.getTime()));
        tvMedicalHistorySequence.setText(getResources().getString(R.string.MedicalHistorySequence) + sharedpreferences.getString(PreferencesData.MedicalHistorySelectedId,""));
    }

    private void recoverySendData() {
        if(getArguments()!=null)
        {
                Bundle extras = getArguments();
                action= extras.getString(PreferencesData.MedicaHistoryAction);
                medicalHistorySelectedId=extras.getString(PreferencesData.MedicalHistorySelectedId);
                storeStringSharepreferences(PreferencesData.MedicalHistorySelectedId, medicalHistorySelectedId);
                storeStringSharepreferences(PreferencesData.MedicaHistoryAction, action);
        }
    }

    public  void searchPatient() {
        String documentPatient=sharedpreferences.getString(PreferencesData.PatientDocument,"");
        Call<PatientViewModel> call = PatientApiAdapter.getApiService().getPatient(documentPatient);
        call.enqueue(new Callback<PatientViewModel>() {
            @Override
            public void onResponse(Call<PatientViewModel> call, Response<PatientViewModel> response) {
                if(response.isSuccessful())
                {
                    patientViewModel = response.body();
                    tvPatientNameValue.setText(patientViewModel.getPatient_first_name() + " " + patientViewModel.getPatient_first_lastname());
                    tvAgeValue.setText(String.valueOf(patientViewModel.getPatient_age()));
                    tvNeighborhoodValue.setText(patientViewModel.getNeighborhood_name());

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

    public  void searchMedicalHistory() {
        String medicalHistory=sharedpreferences.getString(PreferencesData.MedicalHistorySelectedId,"");
        Call<PatientMedicalHistoryViewModel>  call = PatientMedicalHistoryApiAdapter.getApiService().getMedicalHistory(medicalHistory);
        call.enqueue(new Callback<PatientMedicalHistoryViewModel>() {
            @Override
            public void onResponse(Call<PatientMedicalHistoryViewModel> call, Response<PatientMedicalHistoryViewModel> response) {
                if(response.isSuccessful()){
                    PatientMedicalHistoryViewModel object=response.body();
                    etAdditionalInfo.setText(object.getPtnt_mdcl_hstry_addtnl_info());
                    etHistoryName.setText(object.getPtnt_mdcl_hstry_name());
                }
            }

            @Override
            public void onFailure(Call<PatientMedicalHistoryViewModel> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Metodo para actualizar historia medica
     */
    public void updateMedicalHistory(){

        PatientMedicalHistoryViewModel object=getData();
        if(object!=null) {
            String medicalHistoryId=sharedpreferences.getString(PreferencesData.MedicalHistorySelectedId,"");
            Call<PatientMedicalHistoryViewModel> call = PatientMedicalHistoryApiAdapter.getApiService().createOrUpdateMedicalHistory(object, medicalHistoryId);
            call.enqueue(new Callback<PatientMedicalHistoryViewModel>() {
                @Override
                public void onResponse(Call<PatientMedicalHistoryViewModel> call, Response<PatientMedicalHistoryViewModel> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(mContext, PreferencesData.medicalHistoryDetailUpdateSuccesfullMsg , Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<PatientMedicalHistoryViewModel> call, Throwable t) {
                    Toast.makeText(mContext, t.getMessage() , Toast.LENGTH_LONG).show();
                }
            });
        }else{
            Toast.makeText(mContext, PreferencesData.medicalHistoryDetailDataMessage , Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Metodo para consultar historia medica
     */


    /**
     *
     * Agregar items a la historia medica
     */


    public void addVitalSigns(View view) {

        medicalHistorySelectedId =sharedpreferences.getString(PreferencesData.MedicalHistorySelectedId,"");
        PatientMedicalHistoryVitalSignsFragment fragment = new PatientMedicalHistoryVitalSignsFragment();
        loadFragment(fragment);
    }

    public void addQuestionaries(View view) {

        medicalHistorySelectedId =sharedpreferences.getString(PreferencesData.MedicalHistorySelectedId,"");
        PatientMedicalHistoryQuestionariesFragment fragment = new PatientMedicalHistoryQuestionariesFragment();
        loadFragment(fragment);

    }

    public void addDiseases(View view) {

        medicalHistorySelectedId =sharedpreferences.getString(PreferencesData.MedicalHistorySelectedId,"");

        Bundle args = new Bundle();
        args.putString(PreferencesData.MedicalHistorySelectedId,medicalHistorySelectedId);

        MedicalHistoryDiseasesDialog medicalHistoryDiseasesDialog = new  MedicalHistoryDiseasesDialog();
        medicalHistoryDiseasesDialog.setArguments(args);
        medicalHistoryDiseasesDialog.show(getFragmentManager(),"");


    }

    /**
     *
     * Eventos
     */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
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
                    updateMedicalHistory();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     *
     * Metodos varios
     */

    public void loadFragment(Fragment fragment){
        manager.beginTransaction().replace(R.id.content,fragment).commit();
    }

    public void showHideItems(Menu menu) {
        MenuItem item;
        item= menu.findItem(R.id.create_therapy);
        item.setVisible(false);
        item= menu.findItem(R.id.save);
        item.setVisible(true);
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

    public PatientMedicalHistoryViewModel getData(){

        PatientMedicalHistoryViewModel object= new PatientMedicalHistoryViewModel();
        String medicalHistoryId=sharedpreferences.getString(PreferencesData.MedicalHistorySelectedId,"");
        String patientId=sharedpreferences.getString(PreferencesData.PatientId,"");
        String historyName=etHistoryName.getText().toString();
        String additionalInfo=etAdditionalInfo.getText().toString();

        if(!ValidateInputs.validate().ValidateString(Arrays.asList(medicalHistoryId,patientId)))
        {
            return null;
        }

        object.setPatient_id(patientId);
        object.setPtnt_mdcl_hstry_addtnl_info(additionalInfo);
        object.setPtnt_mdcl_hstry_name(historyName);

        return object;
    }
}
