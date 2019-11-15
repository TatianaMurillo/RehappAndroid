package com.rehapp.rehappmovil.rehapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.HistoryTherapiesPatient;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.InstitutionApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.TherapistApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.TherapyApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.InstitutionViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapistViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyMasterDetailViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;
import com.rehapp.rehappmovil.rehapp.PhysiologicalParameterTherapy;
import com.rehapp.rehappmovil.rehapp.R;
import com.rehapp.rehappmovil.rehapp.TherapyAdditionalInformationDialog;
import com.rehapp.rehappmovil.rehapp.TherapyExercisesDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TherapyDetailFragment extends Fragment {
private int therapySelectedId;
private String therapyCreatedId;
private TextView tvTherapySequence;
private String action;
private TextView tvDateAndTime;
private Spinner spnTherapist;
private Spinner spnInstitution;
private TherapyMasterDetailViewModel therapyViewModel;
private TextView tvWatchExercises;
private TextView tvPhisiologicalParametersIn;
private TextView tvPhisiologicalParametersOut;
private TextView tvAdditionalInfo;
private TherapyViewModel therapySelected;
private SharedPreferences sharedpreferences;
private String json;

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
        view =inflater.inflate(R.layout.activity_therapy_detail,container,false);
        sharedpreferences =mContext.getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);

        tvTherapySequence = view.findViewById(R.id.tvTherapySequence);
        tvWatchExercises=view.findViewById(R.id.tvWatchExercises);
        tvAdditionalInfo=view.findViewById(R.id.tvAdditionalInfo);
        tvPhisiologicalParametersIn=view.findViewById(R.id.tvPhisiologicalParametersIn);
        tvPhisiologicalParametersOut=view.findViewById(R.id.tvPhisiologicalParametersOut);
        spnInstitution = view.findViewById(R.id.spnInstitution);
        tvDateAndTime=view.findViewById(R.id.tvDateAndTime);
        spnTherapist = view.findViewById(R.id.spnTherapist);
        therapyViewModel = ViewModelProviders.of(this).get(TherapyMasterDetailViewModel.class);
        therapyCreatedId="";
        recoverySendData();
        loadData();

        tvWatchExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                watchExercises();
            }
        });

        tvPhisiologicalParametersIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPhysiologicalParametersIn();
            }
        });

        tvPhisiologicalParametersOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPhysiologicalParametersOut();
            }
        });

        tvAdditionalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAdditionalInformation();
            }
        });

        return view;
    }

    private void loadData(){
        tvDateAndTime.setText(sdf.format(cal.getTime()));
        listTherapists();
        listInstitutions();
    }
    private void recoverySendData()
    {
        if( getArguments()!=null)
            {

                Bundle extras = getArguments();
                action= extras.getString(PreferencesData.TherapyAction);

                storeStringSharepreferences(PreferencesData.TherapyAction, action);

                if(action.equals("ADD")) {
                    UnBlockData();

                }else
                {
                    therapySelectedId = extras.getInt(PreferencesData.TherapySelectedId);
                    storeIntSharepreferences(PreferencesData.TherapyId, therapySelectedId);
                    searchTherapy();
                }
        }
    }

    public void blockData()
    {

    }
    public void UnBlockData()
    {

    }
    public void listTherapists() {
        Call<ArrayList<TherapistViewModel>> call = TherapistApiAdapter.getApiService().getTherapists();
        call.enqueue(new Callback<ArrayList<TherapistViewModel>>() {
            int indexOfTherapist=-1;

            ArrayList<TherapistViewModel> therapists= new ArrayList<TherapistViewModel>();
            ArrayList<String> therapistNames  = new ArrayList<String>();

            @Override
            public void onResponse(Call<ArrayList<TherapistViewModel>> call, Response<ArrayList<TherapistViewModel>> response) {
                if(response.isSuccessful())
                {
                    String action=sharedpreferences.getString(PreferencesData.TherapyAction,"");
                    therapists = response.body();
                    for(TherapistViewModel therapistViewModel : therapists)
                    {
                        if(action.equals("DETAIL")) {
                            if (therapistViewModel.getTherapist_id() == therapySelected.getTherapist_id()) {
                                indexOfTherapist = therapists.indexOf(therapistViewModel);
                            }
                        }
                        therapistNames.add(therapistViewModel.getTherapist_first_lastname()+" " + therapistViewModel.getTherapist_first_name());
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,therapistNames);
                    spnTherapist.setAdapter(arrayAdapter);

                    if(action.equals("DETAIL")) {
                        if (indexOfTherapist != -1) {
                            spnTherapist.setSelection(indexOfTherapist);
                        } else {
                            Toast.makeText(mContext, PreferencesData.therapyDetailTherapistNonExist, Toast.LENGTH_LONG).show();
                            spnTherapist.setSelection(0);
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<ArrayList<TherapistViewModel>> call, Throwable t) {
                Toast.makeText(mContext, PreferencesData.therapyDetailTherapistListMsg,Toast.LENGTH_LONG).show();
            }
        });
    }

    public void listInstitutions() {
        Call<ArrayList<InstitutionViewModel>> call = InstitutionApiAdapter.getApiService().getInstitutions();
        call.enqueue(new Callback<ArrayList<InstitutionViewModel>>() {
            int indexOfInstitution=-1;
            ArrayList<InstitutionViewModel> institutions= new ArrayList<InstitutionViewModel>();
            ArrayList<String> institutionNames  = new ArrayList<String>();

            @Override
            public void onResponse(Call<ArrayList<InstitutionViewModel>> call, Response<ArrayList<InstitutionViewModel>> response) {
                if(response.isSuccessful())
                {
                    String action=sharedpreferences.getString(PreferencesData.TherapyAction,"");

                    institutions = response.body();
                    for(InstitutionViewModel institutionViewModel : institutions)
                    {
                        if(action.equals("DETAIL")) {
                            if (institutionViewModel.getInstitution_id() == therapySelected.getInstitution_id()) {
                                indexOfInstitution = institutions.indexOf(institutionViewModel);
                            }
                        }
                        institutionNames.add(institutionViewModel.getInstitution_name());
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,institutionNames);
                    spnInstitution.setAdapter(arrayAdapter);

                    if(action.equals("DETAIL")) {
                        if (indexOfInstitution != -1) {
                            spnInstitution.setSelection(indexOfInstitution);
                        } else {
                            Toast.makeText(mContext, PreferencesData.therapyDetailInstitutionNonExist, Toast.LENGTH_LONG).show();
                            spnInstitution.setSelection(0);
                        }
                    }
                }

            }
            @Override
            public void onFailure(Call<ArrayList<InstitutionViewModel>> call, Throwable t) {
                Toast.makeText(mContext, PreferencesData.therapyDetailInstitutionListMsg,Toast.LENGTH_LONG).show();
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

    private void selectTherapist(){

    }

    private void selectInstitution(){

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
        }else
       {
            item = menu.findItem(R.id.save);
            item.setVisible(false);
       }
    }

    public void addPhysiologicalParametersIn() {
        String action=sharedpreferences.getString(PreferencesData.TherapyAction,"");
        int therapyId=sharedpreferences.getInt(PreferencesData.TherapyId,0);


        if(action.equals("ADD")) {
            if(therapyId==0) {
                createTherapyIdForPhysiologicalParameters(PreferencesData.PhysiologicalParameterTherapySesionIN);
            }else {
                showPhysiologicalParameterTherapy(PreferencesData.PhysiologicalParameterTherapySesionIN,String.valueOf(therapyId));
            }
        } else {
            showPhysiologicalParameterTherapy(PreferencesData.PhysiologicalParameterTherapySesionIN,String.valueOf(therapyId));
        }

}

    public void addPhysiologicalParametersOut() {

        String action=sharedpreferences.getString(PreferencesData.TherapyAction,"");
        int therapyId=sharedpreferences.getInt(PreferencesData.TherapyId,0);

        if(action.equals("ADD")) {
            if(therapyId==0) {
                createTherapyIdForPhysiologicalParameters(PreferencesData.PhysiologicalParameterTherapySesionOUT);
            }else {
                showPhysiologicalParameterTherapy(PreferencesData.PhysiologicalParameterTherapySesionOUT,String.valueOf(therapyId));
            }
        } else {
            showPhysiologicalParameterTherapy(PreferencesData.PhysiologicalParameterTherapySesionOUT,String.valueOf(therapyId));
        }
    }

    public void addAdditionalInformation() {

        TherapyAdditionalInformationDialog therapyAdditionalInformationDialog = new  TherapyAdditionalInformationDialog();
        therapyAdditionalInformationDialog.show(getFragmentManager(),"");

    }

    public void watchExercises() {

        String action=sharedpreferences.getString(PreferencesData.TherapyAction,"");
        int therapyId=sharedpreferences.getInt(PreferencesData.TherapyId,0);

        if(action.equals("ADD")) {

            if (therapyId==0)
                createTherapyIdForTherapyRoutineExercises();
             else
                showRoutineExercisesTherapy(String.valueOf(therapyId));

        }else{
            showRoutineExercisesTherapy(String.valueOf(therapyId));
        }

    }

    public void createTherapyIdForPhysiologicalParameters(final String physiologicalParametersTherapyAction) {
        TherapyViewModel therapy = new TherapyViewModel();
        therapy.setTherapy_description(PreferencesData.therapyCreationDescriptionFieldValue);
        Call<TherapyViewModel> call = TherapyApiAdapter.getApiService().createTherapyId(therapy);
        call.enqueue(new Callback<TherapyViewModel>() {
            @Override
            public void onResponse(Call<TherapyViewModel> call, Response<TherapyViewModel> response) {
                if (response.isSuccessful()) {

                    TherapyViewModel therapyViewModel=response.body();
                    therapyCreatedId = String.valueOf(therapyViewModel.getTherapy_id());

                    Toast.makeText(mContext, PreferencesData.therapyCreationIdSuccessMsg, Toast.LENGTH_LONG).show();
                    showPhysiologicalParameterTherapy(physiologicalParametersTherapyAction,therapyCreatedId);

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

    public void showPhysiologicalParameterTherapy(String physiologicalParameterAction, String therapyId){

        tvTherapySequence.setText(getResources().getString(R.string.TherapySequence) + therapyId);

        storeIntSharepreferences(PreferencesData.TherapyId,Integer.parseInt(therapyId));
        storeStringSharepreferences(PreferencesData.PhysiologicalParameterAction,physiologicalParameterAction);


        loadFragment(new PhysiologicalParameterTherapyFragment());





    }

    public void showRoutineExercisesTherapy(String therapyId) {
        tvTherapySequence.setText(getResources().getString(R.string.TherapySequence) + therapyId);

        storeIntSharepreferences(PreferencesData.TherapyId,Integer.parseInt(therapyId));

        Bundle args = new Bundle();
        args.putString(PreferencesData.TherapyId,therapyId);

        TherapyExercisesDialog therapyExercisesDialog = new  TherapyExercisesDialog();
        therapyExercisesDialog.setArguments(args);
        therapyExercisesDialog.show(getFragmentManager(),"");

    }

    public void createTherapyIdForTherapyRoutineExercises() {
        TherapyViewModel therapy = new TherapyViewModel();
        therapy.setTherapy_description(PreferencesData.therapyCreationDescriptionFieldValue);
        Call<TherapyViewModel> call = TherapyApiAdapter.getApiService().createTherapyId(therapy);
        call.enqueue(new Callback<TherapyViewModel>() {
            @Override
            public void onResponse(Call<TherapyViewModel> call, Response<TherapyViewModel> response) {
                if (response.isSuccessful()) {
                    TherapyViewModel therapyViewModel=response.body();
                    therapyCreatedId = String.valueOf(therapyViewModel.getTherapy_id());
                    Toast.makeText(mContext, PreferencesData.therapyCreationIdSuccessMsg, Toast.LENGTH_LONG).show();
                    showRoutineExercisesTherapy(therapyCreatedId);
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

    public void saveTherapy() {

        String therapyId=String.valueOf(sharedpreferences.getInt(PreferencesData.TherapyId,0));

        Call<TherapyViewModel> call = TherapyApiAdapter.getApiService().getTherapy(therapyId);
        call.enqueue(new Callback<TherapyViewModel>() {
            @Override
            public void onResponse(Call<TherapyViewModel> call, Response<TherapyViewModel> response) {
                if(response.isSuccessful())
                {
                    therapySelected = response.body();

                    updateTherapy(therapySelected);

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

    public void updateTherapy(final TherapyViewModel therapy) {


        String therapyId=String.valueOf(sharedpreferences.getInt(PreferencesData.TherapyId,0));

        Call<TherapyViewModel> call = TherapyApiAdapter.getApiService().updateTherapy(therapy,therapyId);
        call.enqueue(new Callback<TherapyViewModel>() {
            @Override
            public void onResponse(Call<TherapyViewModel> call, Response<TherapyViewModel> response) {
                if(response.isSuccessful())
                {
                 String msg =PreferencesData.therapyUpdateSuccessMsg + " Id " +response.body().getTherapy_id();
                 Toast.makeText(mContext,msg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TherapyViewModel> call, Throwable t) {
                Toast.makeText(mContext, PreferencesData.therapyUpdateFailedMsg, Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
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

    public void loadFragment(Fragment fragment){
        manager.beginTransaction().replace(R.id.content,fragment).commit();
    }
}
