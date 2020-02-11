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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.InstitutionApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.TherapistApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.TherapyApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.InstitutionViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapistViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyMasterDetailViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;
import com.rehapp.rehappmovil.rehapp.R;
import com.rehapp.rehappmovil.rehapp.TherapyExercisesDialog;
import com.rehapp.rehappmovil.rehapp.Utils.ValidateInputs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

private int institutionSelectedId=-1,indexInstitutionSelected=-1;
private int therapistSelectedId=-1,indexTherapistSelected=-1;

private EditText etTherapyDuration;
private  TherapyViewModel therapySelected;
private  SharedPreferences sharedpreferences;

    private Context mContext;
    View view;
    FragmentManager manager;

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Calendar cal = Calendar.getInstance();
    ArrayList<InstitutionViewModel> institutions= new ArrayList<>();
    ArrayList<TherapistViewModel> therapists= new ArrayList<>();


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
        etTherapyDuration=view.findViewById(R.id.etTherapyDuration);
        therapyViewModel = ViewModelProviders.of(this).get(TherapyMasterDetailViewModel.class);
        therapyCreatedId="";
        recoverySendData();
        loadData();

        tvPhisiologicalParametersIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectToPage(1,PreferencesData.PhysiologicalParameterTherapySesionIN);
            }
        });

        tvPhisiologicalParametersOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectToPage(1,PreferencesData.PhysiologicalParameterTherapySesionOUT);
            }
        });

        tvWatchExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectToPage(2,"");
            }
        });

        tvAdditionalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectToPage(3,"");
            }
        });

        spnTherapist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                /**se le resta  uno porque se esta agregando una opción por defecto al spinner cuando se  llena**/
                if(position == therapists.size()-1) {
                    therapistSelectedId = therapists.get(position-2).getTherapist_id();
                }else{
                    therapistSelectedId = therapists.get(position).getTherapist_id();
                }
                indexTherapistSelected=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnInstitution.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                /**se le resta  dos porque se esta agregando una opción por defecto al spinner cuando se  llena**/
                if(position == institutions.size()-1){
                    institutionSelectedId=institutions.get(position-2).getInstitution_id();
                }else{
                    institutionSelectedId=institutions.get(position).getInstitution_id();
                }
                indexInstitutionSelected=position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        return view;
    }

    private void loadData(){
        tvDateAndTime.setText(sdf.format(cal.getTime()));
        listTherapists();
    }
    private void recoverySendData() {
        if( getArguments()!=null)
            {

                Bundle extras = getArguments();
                action= extras.getString(PreferencesData.TherapyAction);

                storeStringSharepreferences(PreferencesData.TherapyAction, action);

                if("ADD".equals(action)) {
                    createTherapyId();
                }else
                {
                    therapySelectedId = extras.getInt(PreferencesData.TherapySelectedId);
                    storeIntSharepreferences(PreferencesData.TherapyId, therapySelectedId);
                }
        }
    }

    public void listTherapists() {
        Call<ArrayList<TherapistViewModel>> call = TherapistApiAdapter.getApiService().getTherapists();
        call.enqueue(new Callback<ArrayList<TherapistViewModel>>() {
            ArrayList<String> therapistNames  = new ArrayList<>();

            @Override
            public void onResponse(Call<ArrayList<TherapistViewModel>> call, Response<ArrayList<TherapistViewModel>> response) {
                if(response.isSuccessful())
                {
                    therapists = response.body();
                    therapistNames.add(getResources().getString(R.string.TherapistNonSelected));
                    for(TherapistViewModel therapistViewModel : therapists)
                    {
                        therapistNames.add(therapistViewModel.getTherapist_first_lastname()+" " + therapistViewModel.getTherapist_first_name());
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mContext,android.R.layout.simple_list_item_1,therapistNames);
                    spnTherapist.setAdapter(arrayAdapter);
                    listInstitutions();
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
            ArrayList<String> institutionNames  = new ArrayList<>();

            @Override
            public void onResponse(Call<ArrayList<InstitutionViewModel>> call, Response<ArrayList<InstitutionViewModel>> response) {
                if(response.isSuccessful())
                {
                    institutions = response.body();
                    institutionNames.add(getResources().getString(R.string.InstitutionNonSelected));
                    for(InstitutionViewModel institutionViewModel : institutions)
                    {
                        institutionNames.add(institutionViewModel.getInstitution_name());
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mContext,android.R.layout.simple_list_item_1,institutionNames);
                    spnInstitution.setAdapter(arrayAdapter);
                    searchTherapy();
                }
            }
            @Override
            public void onFailure(Call<ArrayList<InstitutionViewModel>> call, Throwable t) {
                Toast.makeText(mContext, PreferencesData.therapyDetailInstitutionListMsg,Toast.LENGTH_LONG).show();
            }
        });
    }

    public  void searchTherapy(){
        String therapySelectedId= String.valueOf(sharedpreferences.getInt(PreferencesData.TherapyId,0));
        Call<TherapyViewModel> call = TherapyApiAdapter.getApiService().getTherapy(therapySelectedId);
        call.enqueue(new Callback<TherapyViewModel>() {
            @Override
            public void onResponse(Call<TherapyViewModel> call, Response<TherapyViewModel> response) {
                if(response.isSuccessful())
                {
                    therapySelected = response.body();
                    tvTherapySequence.setText(getResources().getString(R.string.TherapySequence) + therapySelected.getTherapy_sequence());
                    etTherapyDuration.setText(String.valueOf(therapySelected.getTherapy_total_duration()));
                    selectInstitution(therapySelected);
                    selectTherapist(therapySelected);
                }else{
                    if(response.raw().code()==404) {
                        Toast.makeText(mContext, PreferencesData.therapyDetailTherapyNonExist, Toast.LENGTH_LONG).show();
                        HistoryTherapiesPatientFragment fragment = new HistoryTherapiesPatientFragment();
                        loadFragment(fragment);
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
            case R.id.save:
                updateTherapy();
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showHideItems(Menu menu) {

        MenuItem item;
        item= menu.findItem(R.id.create_therapy);

        item.setVisible(false);

        item= menu.findItem(R.id.save);
        item.setVisible(true);
    }

    /**
     **Se selecciona el terapeuta
     **/
    private void selectTherapist(TherapyViewModel therapySelected){
        int indexOfTherapist=-1;
        for(TherapistViewModel therapistViewModel : therapists)
        {
                if (therapistViewModel.getTherapist_id() == therapySelected.getTherapist_id()) {
                    /**se le suma  uno porque se esta agregando una opciòn por defecto al spinner cuando se  llena**/
                    indexOfTherapist = therapists.indexOf(therapistViewModel)+1;
            }
        }
            if (indexOfTherapist != -1) {
                spnTherapist.setSelection(indexOfTherapist);
            } else {
                /**se selecciona al menos la opcion que se crea por defecto**/
                spnTherapist.setSelection(0);
            }
    }
    /**
     **Se selecciona la institucion
     **/
    private void selectInstitution(TherapyViewModel therapySelected){
        int indexOfInstitution=-1;
        for(InstitutionViewModel institutionViewModel : institutions)
        {
                if (institutionViewModel.getInstitution_id() == therapySelected.getInstitution_id()) {
                    /**se le suma  uno porque se esta agregando una opciòn por defecto al spinner cuando se  llena**/
                    indexOfInstitution = institutions.indexOf(institutionViewModel)+1;
                }
        }
        if (indexOfInstitution != -1) {
              spnInstitution.setSelection(indexOfInstitution);
        }else {
            /**se selecciona al menos la opcion que se crea por defecto**/
            spnInstitution.setSelection(0);
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
        TherapyViewModel therapy = new TherapyViewModel();
        therapy.setPatient_id(patientId);
        therapy.setTherapy_description(PreferencesData.therapyCreationDescriptionFieldValue);
        Call<TherapyViewModel> call = TherapyApiAdapter.getApiService().createTherapyId(therapy);
        call.enqueue(new Callback<TherapyViewModel>() {
            @Override
            public void onResponse(Call<TherapyViewModel> call, Response<TherapyViewModel> response) {
                if (response.isSuccessful()) {

                    TherapyViewModel therapyViewModel=response.body();
                    therapyCreatedId = String.valueOf(therapyViewModel.getTherapy_id());
                    tvTherapySequence.setText(getResources().getString(R.string.TherapySequence) + therapyViewModel.getTherapy_sequence());
                    storeIntSharepreferences(PreferencesData.TherapyId,Integer.parseInt(therapyCreatedId));
                    Toast.makeText(mContext, PreferencesData.therapyCreationIdSuccessMsg, Toast.LENGTH_LONG).show();
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

    private void redirectToPage(int option,String physiologicalParameterActionKey){
        switch (option){
            case 1:
                showPhysiologicalParameterTherapy(physiologicalParameterActionKey);
                break;
            case 2:
                showRoutineExercisesTherapy();
                break;
            case 3:
                showAddAdditionalInformationTherapy();
                break;
            default:
                Toast.makeText(mContext, PreferencesData.therapyDetailOptionNotFound, Toast.LENGTH_LONG).show();
                break;
        }
    }

    /**
        * Metodos que redirigen a otros fragments
     **/
    public void showPhysiologicalParameterTherapy(String physiologicalParameterAction){
        storeStringSharepreferences(PreferencesData.PhysiologicalParameterAction,physiologicalParameterAction);
        loadFragment(new PhysiologicalParameterTherapyFragment());

    }

    public void showRoutineExercisesTherapy() {
        String therapyId=String.valueOf(sharedpreferences.getInt(PreferencesData.TherapyId,0));

        Bundle args = new Bundle();
        args.putString(PreferencesData.TherapyId,therapyId);

        TherapyExercisesDialog therapyExercisesDialog = new  TherapyExercisesDialog();
        therapyExercisesDialog.setArguments(args);
        therapyExercisesDialog.show(getFragmentManager(),"");

    }

    public void showAddAdditionalInformationTherapy() {
        TherapyAdditionalInformationFragment fragment = new  TherapyAdditionalInformationFragment();
        loadFragment(fragment);
    }

    /**
     *
     * Fin metodos que redirigen a otros fragments
     */

    public void updateTherapy() {

        TherapyViewModel therapy = getTherapyData();
        if(therapy!=null) {
            String therapyId = String.valueOf(sharedpreferences.getInt(PreferencesData.TherapyId, 0));

            Call<TherapyViewModel> call = TherapyApiAdapter.getApiService().updateTherapy(therapy, therapyId);
            call.enqueue(new Callback<TherapyViewModel>() {
                @Override
                public void onResponse(Call<TherapyViewModel> call, Response<TherapyViewModel> response) {
                    if (response.isSuccessful()) {
                        String msg = PreferencesData.therapyUpdateSuccessMsg + " Id " + response.body().getTherapy_id();
                        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
                    } else if (response.raw().code() == 404) {
                        Toast.makeText(mContext, PreferencesData.therapyNotFoundMsg, Toast.LENGTH_LONG).show();
                    } else {
                        String error = "Error código # " + response.raw().code();
                        Toast.makeText(mContext, error, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<TherapyViewModel> call, Throwable t) {
                    Toast.makeText(mContext, PreferencesData.therapyUpdateFailedMsg, Toast.LENGTH_LONG).show();
                }
            });
        }else{
            Toast.makeText(mContext, PreferencesData.therapyUpdateDataFailedMsg, Toast.LENGTH_LONG).show();
        }
    }

    private TherapyViewModel getTherapyData() {
        TherapyViewModel therapy = null;

        int patientId = Integer.parseInt(sharedpreferences.getString(PreferencesData.PatientId, "0"));
           if (ValidateInputs.validate().validateNonAcceptableValueInInteger(Arrays.asList(indexInstitutionSelected, indexTherapistSelected, patientId, institutionSelectedId, therapistSelectedId))) {
                therapy = new TherapyViewModel();
                therapy.setInstitution_id(institutionSelectedId);
                therapy.setTherapist_id(therapistSelectedId);
                therapy.setPatient_id(patientId);
            }
            return therapy;
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
