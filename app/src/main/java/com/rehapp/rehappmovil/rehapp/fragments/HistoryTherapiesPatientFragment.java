package com.rehapp.rehappmovil.rehapp.fragments;

import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.TherapyApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;
import com.rehapp.rehappmovil.rehapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryTherapiesPatientFragment extends Fragment implements Callback<TherapyViewModel> {

    private ListView lvTherapies;
    private ArrayList<TherapyViewModel> therapies = new ArrayList<>();
    private ArrayList<String> therapiesNames= new ArrayList<>();
    private String patientId;
    private int therapySequence;
    SharedPreferences sharedpreferences;

    private Context mContext;
    View view;
    FragmentManager manager;

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Calendar cal = Calendar.getInstance();

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
                Toast.makeText(mContext, PreferencesData.therapySelectionSuccessMsg + selectedTherapy.getTherapy_description(), Toast.LENGTH_LONG).show();
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
                    therapySequence=therapies.size()==0?1:therapies.size()+1;
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

    private void recoverySendData() {
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
                createTherapyId();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    /**
     *
     * Metodo creado por si el usuario decide ejecutar cualquier otra opcion antes de grabar como
     * tal la terapia. Ya que opciones como crear rutinas, parametros fisiologicos u observaciones requieren
     * de una terapia creada.
     */

    public void createTherapyId() {
        int patientId = Integer.parseInt(sharedpreferences.getString(PreferencesData.PatientId, "0"));
        String therapyDescription=PreferencesData.therapyCreationDescriptionFieldValue.concat(sdf.format(cal.getTime()));
        TherapyViewModel therapy = new TherapyViewModel();
        therapy.setPatient_id(patientId);
        therapy.setTherapy_description(therapyDescription);
        therapy.setTherapy_sequence(therapySequence);
        Call<TherapyViewModel> call = TherapyApiAdapter.getApiService().createTherapyId(therapy);
        call.enqueue(new Callback<TherapyViewModel>() {
            @Override
            public void onResponse(Call<TherapyViewModel> call, Response<TherapyViewModel> response) {
                if (response.isSuccessful()) {
                    TherapyViewModel therapyViewModel=response.body();
                    Toast.makeText(mContext, PreferencesData.therapyCreationIdSuccessMsg, Toast.LENGTH_LONG).show();
                    TherapyDetailFragment fragment = new TherapyDetailFragment();
                    Bundle extras = new Bundle();
                    extras.putString(PreferencesData.TherapyAction, "ADD");
                    extras.putInt(PreferencesData.TherapySelectedId, therapyViewModel.getTherapy_id());
                    fragment.setArguments(extras);
                    loadFragment(fragment );

                } else {
                    Toast.makeText(mContext, PreferencesData.therapyCreationIdFailedMsg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TherapyViewModel> call, Throwable t) {
                Toast.makeText(mContext, PreferencesData.therapyCreationIdFailedMsg, Toast.LENGTH_LONG).show();
            }
        });
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
