package com.rehapp.rehappmovil.rehapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.InstitutionApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.TherapistApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.Institution;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.Therapist;
import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;
import com.rehapp.rehappmovil.rehapp.Utils.UserMethods;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TherapyDetail extends AppCompatActivity {
private TherapyViewModel therapySelected;
private TextView tvTherapySequence;
private String action;
private Spinner spnTherapist;
private Spinner spnInstitution;
TherapyViewModel therapyViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapy_detail);
        tvTherapySequence = findViewById(R.id.tvTherapySequence);
        spnInstitution = findViewById(R.id.spnInstitution);
        spnTherapist = findViewById(R.id.spnTherapist);
        therapyViewModel = ViewModelProviders.of(this).get(TherapyViewModel.class);
        recoverySendData();

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
                    therapySelected = (TherapyViewModel) getIntent().getSerializableExtra(PreferencesData.TherapySelected);
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
        Call<ArrayList<Therapist>> call = TherapistApiAdapter.getApiService().getTherapists();
        call.enqueue(new Callback<ArrayList<Therapist>>() {
            int indexOfTherapist=-1;

            ArrayList<Therapist> therapists= new ArrayList<Therapist>();
            ArrayList<String> therapistNames  = new ArrayList<String>();

            @Override
            public void onResponse(Call<ArrayList<Therapist>> call, Response<ArrayList<Therapist>> response) {
                if(response.isSuccessful())
                {
                    therapists = response.body();
                    for(Therapist therapist: therapists)
                    {
                        if(therapyViewModel.getAction().equals("DETAIL")) {
                            if (therapist.getTherapist_id() == therapySelected.getTherapist_id()) {
                                indexOfTherapist = therapists.indexOf(therapist);
                            }
                        }
                        therapistNames.add(therapist.getTherapist_first_lastname()+" " +therapist.getTherapist_first_name());
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
            public void onFailure(Call<ArrayList<Therapist>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), PreferencesData.therapyDetailTherapistListMsg,Toast.LENGTH_LONG).show();
            }
        });
    }
    public void listInstitutions()
    {
        Call<ArrayList<Institution>> call = InstitutionApiAdapter.getApiService().getInstitutions();
        call.enqueue(new Callback<ArrayList<Institution>>() {
            int indexOfInstitution=-1;
            ArrayList<Institution> institutions= new ArrayList<Institution>();
            ArrayList<String> institutionNames  = new ArrayList<String>();

            @Override
            public void onResponse(Call<ArrayList<Institution>> call, Response<ArrayList<Institution>> response) {
                if(response.isSuccessful())
                {
                    institutions = response.body();
                    for(Institution institution: institutions)
                    {
                        if(therapyViewModel.getAction().equals("DETAIL")) {
                            if (institution.getInstitution_id() == therapySelected.getTherapy_institution_id()) {
                                indexOfInstitution = institutions.indexOf(institution);
                            }
                        }
                        institutionNames.add(institution.getInstitution_name());
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
            public void onFailure(Call<ArrayList<Institution>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), PreferencesData.therapyDetailInstitutionListMsg,Toast.LENGTH_LONG).show();
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,  menu);

        ocultarItems(menu);
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

    public void ocultarItems(Menu menu)
    {
        MenuItem item;
        if(action.equals("DETAIL")) {
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


}
