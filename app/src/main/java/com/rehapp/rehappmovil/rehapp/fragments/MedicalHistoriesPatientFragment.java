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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.PatientMedicalHistoryApiAdapter;
import com.rehapp.rehappmovil.rehapp.MedicalHistoriesAdapter;
import com.rehapp.rehappmovil.rehapp.Models.PatientMedicalHistoryViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicalHistoriesPatientFragment extends Fragment {

    private ListView lvMedicalHistoriesPatient;
    private ArrayList<PatientMedicalHistoryViewModel> medicalHistories = new ArrayList<>();
    private String patientId;
    SharedPreferences sharedpreferences;
    MedicalHistoriesAdapter adapter;

    private Context mContext;
    View view;
    FragmentManager manager;

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
        view =inflater.inflate(R.layout.activity_medical_histories_patient,container,false);
        sharedpreferences=mContext.getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);


        lvMedicalHistoriesPatient = view.findViewById(R.id.lvMedicalHistoriesPatient);
        recoverySendData();
        loadMedicalHistoriesPatient();

        return  view;
    }


    public void loadMedicalHistoriesPatient() {
        Call<ArrayList<PatientMedicalHistoryViewModel>> call = PatientMedicalHistoryApiAdapter.getApiService().getMedicalHistoriesByPatient(patientId);
        call.enqueue(new Callback<ArrayList<PatientMedicalHistoryViewModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PatientMedicalHistoryViewModel>> call, Response<ArrayList<PatientMedicalHistoryViewModel>> response) {
                if(response.isSuccessful())
                {
                    medicalHistories=response.body();
                    adapter = new MedicalHistoriesAdapter(getActivity(),medicalHistories);
                    lvMedicalHistoriesPatient.setAdapter(adapter);
                    lvMedicalHistoriesPatient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            PatientMedicalHistoryViewModel medicalHistory = medicalHistories.get(position);

                            Toast.makeText(mContext, "medical history selected : " + medicalHistory.getPtnt_mdcl_hstry_name(), Toast.LENGTH_LONG).show();

                            MedicalHistoryDetailFragment fragment = new MedicalHistoryDetailFragment();
                            Bundle extras = new Bundle();
                            extras.putString(PreferencesData.MedicalHistorySelectedId, medicalHistory.getPtnt_mdcl_hstry_id());
                            extras.putString(PreferencesData.MedicaHistoryAction, "DETAIL");
                            fragment.setArguments(extras);
                            loadFragment(fragment);


                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PatientMedicalHistoryViewModel>> call, Throwable t) {
                Toast.makeText(mContext, PreferencesData.patientMedicalHistoryListError +" "+ t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void recoverySendData() {
        patientId=sharedpreferences.getString(PreferencesData.PatientId,"");
        if(patientId.equals("")){
            loadFragment(new SearchCreatePatientFragment());
            Toast.makeText(mContext, PreferencesData.SearchCreatePatientFragment,Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Metodo para crear historial medica cuando se le de click al boton crear
     */

    public void createMedicalHistory(String medicalHistoryId){

        PatientMedicalHistoryViewModel object=getObjectToCreate();
        Call<PatientMedicalHistoryViewModel> call = PatientMedicalHistoryApiAdapter.getApiService().createOrUpdateMedicalHistory(object,medicalHistoryId);
        call.enqueue(new Callback<PatientMedicalHistoryViewModel>() {
            @Override
            public void onResponse(Call<PatientMedicalHistoryViewModel> call, Response<PatientMedicalHistoryViewModel> response) {
                if(response.isSuccessful()){
                    PatientMedicalHistoryViewModel medicalHistoryCreated=response.body();

                    MedicalHistoryDetailFragment fragment = new MedicalHistoryDetailFragment();
                    Bundle extras = new Bundle();
                    extras.putString(PreferencesData.MedicalHistorySelectedId, medicalHistoryCreated.getPtnt_mdcl_hstry_id());
                    extras.putString(PreferencesData.MedicaHistoryAction, "ADD");
                    fragment.setArguments(extras);
                    loadFragment(fragment);
                }
            }

            @Override
            public void onFailure(Call<PatientMedicalHistoryViewModel> call, Throwable t) {

            }
        });

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
            case R.id.create_therapy:
                cleanPreferenceData();
                createMedicalHistory("0");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     *
     * Metodos utiles
     */

    public void loadFragment(Fragment fragment){
        manager.beginTransaction().replace(R.id.content,fragment).commit();
    }

    private void cleanPreferenceData()
    {
        storeIntSharepreferences(PreferencesData.TherapyId,0);
    }

    private  void storeIntSharepreferences(String key, int value){

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void showHideItems(Menu menu) {
        MenuItem item;
        item= menu.findItem(R.id.save);
        item.setVisible(false);
        item= menu.findItem(R.id.create_therapy);
        item.setVisible(true);
    }

    public PatientMedicalHistoryViewModel getObjectToCreate(){
        PatientMedicalHistoryViewModel object= new PatientMedicalHistoryViewModel();

       object.setPatient_id(patientId);
       object.setPtnt_mdcl_hstry_addtnl_info(PreferencesData.medicalHistoryDetailDefaultDescription);
       object.setPtnt_mdcl_hstry_name(PreferencesData.medicalHistoryDetailDefaultName);

        return object;
    }




}
