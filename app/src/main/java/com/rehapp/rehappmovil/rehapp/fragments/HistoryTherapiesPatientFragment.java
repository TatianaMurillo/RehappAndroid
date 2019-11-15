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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.TherapyApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.PatientViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapyMasterDetailViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;
import com.rehapp.rehappmovil.rehapp.R;
import com.rehapp.rehappmovil.rehapp.TherapyDetail;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryTherapiesPatientFragment extends Fragment implements Callback<TherapyViewModel> {

    private ListView lvTherapies;
    private ArrayList<TherapyViewModel> therapies = new ArrayList<>();
    private ArrayList<String> therapiesNames= new ArrayList<>();
    private String documentPatient;
    private PatientViewModel patientViewModel;
    private int documentTypePatientId;
    private String patientId;
    private TherapyMasterDetailViewModel therapy;
    SharedPreferences sharedpreferences;

    private Context mContext;
    View view;
    FragmentManager manager;

    public HistoryTherapiesPatientFragment() { }

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
        view =inflater.inflate(R.layout.activity_history_therapies_patient,container,false);
        sharedpreferences=mContext.getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);


        lvTherapies = view.findViewById(R.id.lvTherapies);
        recoverySendData();
        loadTherapies();

        lvTherapies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TherapyViewModel selectedTherapy = therapies.get(position);
                Toast.makeText(mContext, "therapy selected : " + selectedTherapy.getTherapy_description(), Toast.LENGTH_LONG).show();
                TherapyDetailFragment fragment = new TherapyDetailFragment();
                Bundle extras = new Bundle();
                extras.putString(PreferencesData.TherapyAction, "DETAIL");
                extras.putInt(PreferencesData.TherapySelectedId, selectedTherapy.getTherapy_id());

                fragment.setArguments(extras);
                loadFragment(fragment);

            }
        });

        return  view;
    }


    public void loadTherapies()
    {
        Call<ArrayList<TherapyViewModel>> call = TherapyApiAdapter.getApiService().getTherapiesByPatient(patientId);
        call.enqueue(new Callback<ArrayList<TherapyViewModel>>() {
            @Override
            public void onResponse(Call<ArrayList<TherapyViewModel>> call, Response<ArrayList<TherapyViewModel>> response) {
                if(response.isSuccessful())
                {
                    therapies=response.body();
                    for(TherapyViewModel therapy: therapies)
                    {
                        therapiesNames.add(therapy.getTherapy_description() + " "+ therapy.getTherapy_sequence());
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mContext,android.R.layout.simple_list_item_1,therapiesNames);
                    lvTherapies.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TherapyViewModel>> call, Throwable t) {
                Toast.makeText(mContext, PreferencesData.therapyListError +" "+ t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }



    @Override
    public void onResponse(Call<TherapyViewModel> call, Response<TherapyViewModel> response) {

    }

    @Override
    public void onFailure(Call<TherapyViewModel> call, Throwable t) {

    }

    private void recoverySendData()
    {
        documentPatient=sharedpreferences.getString(PreferencesData.PatientDocument,"");
        documentTypePatientId=Integer.parseInt(sharedpreferences.getString(PreferencesData.PatientTpoDocument,""));
        patientId=sharedpreferences.getString(PreferencesData.PatientId,"");

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
                TherapyDetailFragment fragment = new TherapyDetailFragment();
                Bundle extras = new Bundle();
                extras.putString(PreferencesData.TherapyAction, "ADD");
                fragment.setArguments(extras);
                loadFragment(fragment );
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }


    public void showHideItems(Menu menu)
    {
        MenuItem item;
        item= menu.findItem(R.id.save);
        item.setVisible(false);
        item= menu.findItem(R.id.create_therapy);
        item.setVisible(true);
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

    public void loadFragment(Fragment fragment){
        manager.beginTransaction().replace(R.id.content,fragment).commit();
    }

}
