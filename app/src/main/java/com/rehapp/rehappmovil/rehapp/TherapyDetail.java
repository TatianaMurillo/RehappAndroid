package com.rehapp.rehappmovil.rehapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.InstitutionApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.TherapistApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.TherapyApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.InstitutionViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PatientViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapistViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyMasterDetailViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;
import com.rehapp.rehappmovil.rehapp.Utils.UserMethods;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TherapyDetail extends AppCompatActivity {
private String therapySelectedId;
private String therapyCreatedId;
private TextView tvTherapySequence;
private String action;
private Spinner spnTherapist;
private Spinner spnInstitution;
private TherapyMasterDetailViewModel therapyViewModel;
private TherapyViewModel therapySelected;
private SharedPreferences sharedpreferences;
private String json;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapy_detail);
        tvTherapySequence = findViewById(R.id.tvTherapySequence);
        spnInstitution = findViewById(R.id.spnInstitution);
        spnTherapist = findViewById(R.id.spnTherapist);
        therapyViewModel = ViewModelProviders.of(this).get(TherapyMasterDetailViewModel.class);
        //LoadData();
        recoverySendData();
    }

    private void LoadData() {

    }




    private void recoverySendData()
    {
        if( getIntent().getExtras()!=null)
            {
                Bundle extras = getIntent().getExtras();
                action= extras.getString(PreferencesData.TherapyAction);
                if(action.equals("ADD")) {
                    UnBlockData();
                    createTherapyId();
                }else
                {
                    therapySelectedId = getIntent().getSerializableExtra(PreferencesData.TherapySelectedId).toString();
                    searchTherapy();
                    tvTherapySequence.setText("Terapia # " + therapySelected.getTherapy_sequence());
                    blockData();
                }
            therapyViewModel.setAction(action);
            listTherapists();
            listInstitutions();
        }
    }

    public void blockData()
    {

    }
    public void UnBlockData()
    {

    }
    public void listTherapists()
    {
        Call<ArrayList<TherapistViewModel>> call = TherapistApiAdapter.getApiService().getTherapists();
        call.enqueue(new Callback<ArrayList<TherapistViewModel>>() {
            int indexOfTherapist=-1;

            ArrayList<TherapistViewModel> therapists= new ArrayList<TherapistViewModel>();
            ArrayList<String> therapistNames  = new ArrayList<String>();

            @Override
            public void onResponse(Call<ArrayList<TherapistViewModel>> call, Response<ArrayList<TherapistViewModel>> response) {
                if(response.isSuccessful())
                {
                    therapists = response.body();
                    for(TherapistViewModel therapistViewModel : therapists)
                    {
                        if(therapyViewModel.getAction().equals("DETAIL")) {
                            if (therapistViewModel.getTherapist_id() == therapySelected.getTherapist_id()) {
                                indexOfTherapist = therapists.indexOf(therapistViewModel);
                            }
                        }
                        therapistNames.add(therapistViewModel.getTherapist_first_lastname()+" " + therapistViewModel.getTherapist_first_name());
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(TherapyDetail.this,android.R.layout.simple_list_item_1,therapistNames);
                    spnTherapist.setAdapter(arrayAdapter);

                    if(therapyViewModel.getAction().equals("DETAIL")) {
                        if (indexOfTherapist != -1) {
                            spnTherapist.setSelection(indexOfTherapist);
                        } else {
                            Toast.makeText(getApplicationContext(), PreferencesData.therapyDetailTherapistNonExist, Toast.LENGTH_LONG).show();
                            spnTherapist.setSelection(0);
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<TherapistViewModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), PreferencesData.therapyDetailTherapistListMsg,Toast.LENGTH_LONG).show();
            }
        });
    }
    public void listInstitutions()
    {
        Call<ArrayList<InstitutionViewModel>> call = InstitutionApiAdapter.getApiService().getInstitutions();
        call.enqueue(new Callback<ArrayList<InstitutionViewModel>>() {
            int indexOfInstitution=-1;
            ArrayList<InstitutionViewModel> institutions= new ArrayList<InstitutionViewModel>();
            ArrayList<String> institutionNames  = new ArrayList<String>();

            @Override
            public void onResponse(Call<ArrayList<InstitutionViewModel>> call, Response<ArrayList<InstitutionViewModel>> response) {
                if(response.isSuccessful())
                {
                    institutions = response.body();
                    for(InstitutionViewModel institutionViewModel : institutions)
                    {
                        if(therapyViewModel.getAction().equals("DETAIL")) {
                            if (institutionViewModel.getInstitution_id() == therapySelected.getInstitution_id()) {
                                indexOfInstitution = institutions.indexOf(institutionViewModel);
                            }
                        }
                        institutionNames.add(institutionViewModel.getInstitution_name());
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(TherapyDetail.this,android.R.layout.simple_list_item_1,institutionNames);
                    spnInstitution.setAdapter(arrayAdapter);

                    if(therapyViewModel.getAction().equals("DETAIL")) {
                        if (indexOfInstitution != -1) {
                            spnInstitution.setSelection(indexOfInstitution);
                        } else {
                            Toast.makeText(getApplicationContext(), PreferencesData.therapyDetailInstitutionNonExist, Toast.LENGTH_LONG).show();
                            spnInstitution.setSelection(0);
                        }
                    }
                }

            }
            @Override
            public void onFailure(Call<ArrayList<InstitutionViewModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), PreferencesData.therapyDetailInstitutionListMsg,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void searchTherapy(){
        Call<TherapyViewModel> call = TherapyApiAdapter.getApiService().getTherapy(therapySelectedId);
        call.enqueue(new Callback<TherapyViewModel>() {
            @Override
            public void onResponse(Call<TherapyViewModel> call, Response<TherapyViewModel> response) {
                if(response.isSuccessful())
                {
                    therapySelected = response.body();

                }else{
                    if(response.raw().code()==404) {
                        Toast.makeText(getApplicationContext(), PreferencesData.therapyDetailTherapyNonExist, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(TherapyDetail.this, HistoryTherapiesPatient.class);
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
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,  menu);
        showHideItems(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.logout:
                UserMethods.Do().Logout(this);
                break;
        }



        return super.onOptionsItemSelected(item);
    }

    public void showHideItems(Menu menu)
    {
        MenuItem item;
        if(therapyViewModel.getAction().equals("DETAIL")) {
            item = menu.findItem(R.id.create_therapy);
            item.setVisible(false);

            item = menu.findItem(R.id.save_therapy);
            item.setVisible(false);
        }else
       {
            item = menu.findItem(R.id.create_therapy);
            item.setVisible(false);
       }
    }

    public void addPhysiologicalParametersIn(View view) {
        Bundle args = new Bundle();
        args.putString(PreferencesData.PhysiologicalParameterAction,PreferencesData.PhysiologicalParameterTherapySesionIN);

        PhysiologicalParameterTherapyDialog physiologicalParameterTherapyDialog = new  PhysiologicalParameterTherapyDialog();
        physiologicalParameterTherapyDialog.setArguments(args);
        physiologicalParameterTherapyDialog.show(getSupportFragmentManager(),"");

    }

    public void addPhysiologicalParametersOut(View view) {
        Bundle args = new Bundle();
        args.putString(PreferencesData.PhysiologicalParameterAction,PreferencesData.PhysiologicalParameterTherapySesionOUT);

        PhysiologicalParameterTherapyDialog physiologicalParameterTherapyDialog = new  PhysiologicalParameterTherapyDialog();
        physiologicalParameterTherapyDialog.setArguments(args);
        physiologicalParameterTherapyDialog.show(getSupportFragmentManager(),"");
    }

    public void addAdditionalInformation(View view) {

        TherapyAdditionalInformationDialog therapyAdditionalInformationDialog = new  TherapyAdditionalInformationDialog();
        therapyAdditionalInformationDialog.show(getSupportFragmentManager(),"");

    }

    public void watchExercises(View view) {

        Bundle args = new Bundle();
        args.putString(PreferencesData.TherapyAction,action);
        args.putString(PreferencesData.TherapyId,therapySelectedId);

        TherapyExercisesDialog therapyExercisesDialog = new  TherapyExercisesDialog();
        therapyExercisesDialog.setArguments(args);
        therapyExercisesDialog.show(getSupportFragmentManager(),"");

    }

    public void createTherapyId()
    {
        TherapyViewModel therapy = new TherapyViewModel();
        therapy.setTherapy_description(PreferencesData.therapyCreationDescriptionFieldValue);
        Call<TherapyViewModel> call = TherapyApiAdapter.getApiService().createTherapyId(therapy);
        call.enqueue(new Callback<TherapyViewModel>() {
            @Override
            public void onResponse(Call<TherapyViewModel> call, Response<TherapyViewModel> response) {
                if(response.isSuccessful())
                {
                    TherapyViewModel therapyViewModel=response.body();
                    therapyCreatedId=String.valueOf(therapyViewModel.getTherapy_id());
                    tvTherapySequence.setText("Terapia # " + therapyCreatedId);
                    Toast.makeText(getApplicationContext(), PreferencesData.therapyCreationIdSuccessMsg, Toast.LENGTH_LONG).show();
                }else
                    {
                        Toast.makeText(getApplicationContext(), PreferencesData.therapyCreationIdFailedMsg, Toast.LENGTH_LONG).show();
                    }

            }

            @Override
            public void onFailure(Call<TherapyViewModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), PreferencesData.therapyCreationIdFailedMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

}
