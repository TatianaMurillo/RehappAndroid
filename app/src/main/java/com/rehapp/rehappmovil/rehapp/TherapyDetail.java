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
import com.rehapp.rehappmovil.rehapp.Models.InstitutionViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapistViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyMasterDetailViewModel;
import com.rehapp.rehappmovil.rehapp.Utils.UserMethods;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TherapyDetail extends AppCompatActivity {
private TherapyMasterDetailViewModel therapySelected;
private TextView tvTherapySequence;
private String action;
private Spinner spnTherapist;
private Spinner spnInstitution;
private TherapyMasterDetailViewModel therapyViewModel;
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
        LoadData();
        recoverySendData();
    }

    private void LoadData() {

        Gson gson = new  Gson();
        json = gson.toJson(therapyViewModel);
        sharedpreferences = getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(PreferencesData.TherapyMasterDetailViewModel, json);
        editor.commit();

    }

    public void therapyAdditionalInfo(View view) {

        //Intent intent = new Intent(TherapyDetail.this, TherapyAdditionalInfo.class);
    }

    public void watchExercises(View view) {

        Intent intent = new Intent(TherapyDetail.this, TherapyExercises.class);
        Bundle extras = new Bundle();
        extras.putString(PreferencesData.TherapyAction,therapyViewModel.getAction());
        intent.putExtras(extras);
        startActivity(intent);

    }

    private void recoverySendData()
    {
        if( getIntent().getExtras()!=null)
            {
                Bundle extras = getIntent().getExtras();
                action= extras.getString(PreferencesData.TherapyAction);
                if(action.equals("ADD")) {
                    UnBlockData();
                }else
                {
                    therapySelected = (TherapyMasterDetailViewModel) getIntent().getSerializableExtra(PreferencesData.TherapySelected);
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
        Intent intent = new Intent(TherapyDetail.this, PhysiologicalParameterTherapy.class);
        Bundle extras = new Bundle();
        extras.putString(PreferencesData.PhysiologicalParameterAction,PreferencesData.PhysiologicalParameterTherapySesionIN);
        extras.putString(PreferencesData.TherapyAction, action);
        intent.putExtra(PreferencesData.TherapySelected, therapySelected);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void addPhysiologicalParametersOut(View view) {
        Intent intent = new Intent(TherapyDetail.this, PhysiologicalParameterTherapy.class);
        Bundle extras = new Bundle();
        extras.putString(PreferencesData.PhysiologicalParameterAction, PreferencesData.PhysiologicalParameterTherapySesionOUT);
        extras.putString(PreferencesData.TherapyAction, action);
        intent.putExtra(PreferencesData.TherapySelected, therapySelected);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
