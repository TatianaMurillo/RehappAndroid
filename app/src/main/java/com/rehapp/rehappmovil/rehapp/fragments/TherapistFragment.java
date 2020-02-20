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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.DocumentTypeApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.GenderApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.NeighborhoodApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.PatientApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.DocumentTypeViewModel;
import com.rehapp.rehappmovil.rehapp.Models.GenderViewModel;
import com.rehapp.rehappmovil.rehapp.Models.NeighborhoodViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PatientViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.R;
import com.rehapp.rehappmovil.rehapp.Utils.ValidateInputs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TherapistFragment extends Fragment {


    private EditText etfirstName;
    private EditText etSecondName;
    private EditText etFirstLastName;
    private EditText etSecondLastName;
    private EditText etDocument;
    private EditText etAddress;
    private EditText etAge;
    private EditText etCellPhone;
    private EditText etLandLinePhone;
    private Spinner  spnGender;
    private Spinner  spnDocumentType;
    private Spinner  spnNeighborhood;
    List<String> dataInputString;
    List<Integer> dataInputInteger;
    private int documentTypeSelectedId=-1,indexDocumentTypeSelected=-1;
    private int genderSelectedId=-1,indexGenderSelected=-1;
    private int neighborhoodSelectedId=-1,indexNeighborhoodSelected=-1;
    PatientViewModel patientResponse;

    TextView tvSiguientePagina;
    ArrayList<DocumentTypeViewModel> documentTypes =new ArrayList<>();
    ArrayList<String> documentTypeNames= new ArrayList<>();

    ArrayList<NeighborhoodViewModel> neighborhoods =new ArrayList<>();
    ArrayList<String> neighborhoodNames= new ArrayList<>();

    ArrayList<GenderViewModel> genders =new ArrayList<>();
    ArrayList<String> genderNames= new ArrayList<>();

    String documentPatient;
    String patientTypeDocument;
    String action;
    PatientViewModel patient;
    SharedPreferences sharedpreferences;
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
        view =inflater.inflate(R.layout.activity_therapist_detail,container,false);
        sharedpreferences =mContext.getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);



        etfirstName=view.findViewById(R.id.etFirstName);
        etSecondName=view.findViewById(R.id.etSecondName);
        etFirstLastName=view.findViewById(R.id.etFirstLastName);
        etSecondLastName=view.findViewById(R.id.etSecondLastName);
        etDocument=view.findViewById(R.id.etDocument);
        etAddress=view.findViewById(R.id.etAddress);
        etCellPhone=view.findViewById(R.id.etCellphone);
        etLandLinePhone=view.findViewById(R.id.etLandLinePhone);
        etAge=view.findViewById(R.id.etAge);
        spnNeighborhood = view.findViewById(R.id.spnNeighborhood);
        spnDocumentType = view.findViewById(R.id.spnDocumentType);
        spnGender = view.findViewById(R.id.spnGender);

        spnDocumentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                documentTypeSelectedId = documentTypes.get(position).getDocument_type_id();
                indexDocumentTypeSelected=position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                genderSelectedId = genders.get(position).getGender_id();
                indexGenderSelected=position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        spnNeighborhood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                neighborhoodSelectedId = neighborhoods.get(position).getNeighborhood_id();
                indexNeighborhoodSelected=position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        recoverySendData();

        return view;
    }


    private void recoverySendData()
    {
        documentPatient=sharedpreferences.getString(PreferencesData.PatientDocument,"");
        patientTypeDocument=sharedpreferences.getString(PreferencesData.PatientTpoDocument,"");
        if( getArguments()!=null)
        {
            Bundle extras = getArguments();
            action= extras.getString(PreferencesData.PatientAction);

            storeStringSharepreferences(PreferencesData.PatientAction, action);

            if(action.equals("DETAIL")) {
                searchPatient();
            }else{
                loadNeigborhoods();
                loadDocumentTypes();
                loadGenders();
            }
        }
    }


    public  void searchPatient()
    {
        Call<PatientViewModel> call = PatientApiAdapter.getApiService().getPatient(documentPatient);
        call.enqueue(new Callback<PatientViewModel>() {
            @Override
            public void onResponse(Call<PatientViewModel> call, Response<PatientViewModel> response) {
                if(response.isSuccessful())
                {
                    patient = response.body();
                    setPatientViewModelToView(patient);
                    loadNeigborhoods();
                    loadDocumentTypes();
                    loadGenders();

                }else{
                    if(response.raw().code()==404) {
                        Toast.makeText(mContext, PreferencesData.searchPatientPatient,Toast.LENGTH_LONG).show();
                        redirectToSearchPatient();
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

    public void savePatient() {

        setInputData();

        if(ValidateInputs.validate().ValidateString(dataInputString))

        {
                if(ValidateInputs.validate().ValidateIntegers(dataInputInteger)) {

                    PatientViewModel patient = getPatientViewModelFromView();

                    Call<PatientViewModel> call = PatientApiAdapter.getApiService().createPatient(patient);
                    call.enqueue(new Callback<PatientViewModel>() {
                    @Override
                    public void onResponse(Call<PatientViewModel> call, Response<PatientViewModel> response) {
                        if(response.isSuccessful())
                        {
                            patientResponse= response.body();
                            Toast.makeText(mContext, PreferencesData.storePatientSuccess, Toast.LENGTH_LONG).show();
                            redirectToSearchCreatePatient();
                        }
                    }

                    @Override
                    public void onFailure(Call<PatientViewModel> call, Throwable t) {
                        Toast.makeText(mContext, PreferencesData.storePatientFailed, Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                    Toast.makeText(mContext, PreferencesData.storePatientEmptyDataMsg, Toast.LENGTH_LONG).show();

                }

        }else{
            Toast.makeText(mContext, PreferencesData.storePatientEmptyDataMsg, Toast.LENGTH_LONG).show();

        }

    }


    public void updatePatient() {

        setInputData();

        if(ValidateInputs.validate().ValidateString(dataInputString))

        {
            if(ValidateInputs.validate().ValidateIntegers(dataInputInteger)) {
                String patientId=String.valueOf(patient.getPatient_id());
                final PatientViewModel patient = getPatientViewModelFromView();
                Call<PatientViewModel> call = PatientApiAdapter.getApiService().updatePatient(patient,patientId);
                call.enqueue(new Callback<PatientViewModel>() {
                    @Override
                    public void onResponse(Call<PatientViewModel> call, Response<PatientViewModel> response) {
                        if(response.isSuccessful())
                        {
                            patientResponse= response.body();
                            Toast.makeText(mContext, PreferencesData.updatePatientSuccess, Toast.LENGTH_LONG).show();

                            storeStringSharepreferences(PreferencesData.PatientDocument, patientResponse.getPatient_document());
                            storeStringSharepreferences(PreferencesData.PatientTpoDocument, String.valueOf(patientResponse.getDocument_type_id()));
                            redirectToSearchPatient();
                        }
                    }

                    @Override
                    public void onFailure(Call<PatientViewModel> call, Throwable t) {
                        Toast.makeText(mContext, PreferencesData.udpatePatientFailed, Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                Toast.makeText(mContext, PreferencesData.storePatientEmptyDataMsg, Toast.LENGTH_LONG).show();

            }

        }else{
            Toast.makeText(mContext, PreferencesData.storePatientEmptyDataMsg, Toast.LENGTH_LONG).show();

        }

    }


    private void loadDocumentTypes()
    {
        Call<ArrayList<DocumentTypeViewModel>> call = DocumentTypeApiAdapter.getApiService().getDocumentTypes();
        call.enqueue(new Callback<ArrayList<DocumentTypeViewModel>>() {

                         @Override
                         public void onResponse(Call<ArrayList<DocumentTypeViewModel>> call, Response<ArrayList<DocumentTypeViewModel>> response) {
                             if(response.isSuccessful())
                             {
                                 documentTypes= response.body();
                                     for (DocumentTypeViewModel documentTypeViewModel : documentTypes) {
                                         if(action.equals("DETAIL")) {
                                             if (documentTypeViewModel.getDocument_type_id() == patient.getDocument_type_id()) {
                                                 indexDocumentTypeSelected = documentTypes.indexOf(documentTypeViewModel);
                                             }
                                         }

                                         documentTypeNames.add(documentTypeViewModel.getDocument_type_name());
                                     }
                                 ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, documentTypeNames);
                                 spnDocumentType.setAdapter(arrayAdapter);
                                 if(action.equals("DETAIL")) {
                                     spnDocumentType.setSelection(indexDocumentTypeSelected);
                                 }else
                                 {
                                     spnDocumentType.setSelection(0);
                                 }
                             }
                         }
                         @Override
                         public void onFailure(Call<ArrayList<DocumentTypeViewModel>> call, Throwable t) {
                             t.printStackTrace();
                             Toast.makeText(mContext, PreferencesData.listDocumentTypesFailed, Toast.LENGTH_LONG).show();
                         }
                     }
        );
    }

    private void loadNeigborhoods()
    {
        Call<ArrayList<NeighborhoodViewModel>> call = NeighborhoodApiAdapter.getApiService().getNeighborhoods();
        call.enqueue(new Callback<ArrayList<NeighborhoodViewModel>>() {

                         @Override
                         public void onResponse(Call<ArrayList<NeighborhoodViewModel>> call, Response<ArrayList<NeighborhoodViewModel>> response) {
                             if(response.isSuccessful())
                             {
                                 neighborhoods= response.body();
                                 for (NeighborhoodViewModel neighborhoodViewModel : neighborhoods) {

                                     if(action.equals("DETAIL")) {
                                         if (neighborhoodViewModel.getNeighborhood_id() == patient.getNeighborhood_id()) {
                                             indexNeighborhoodSelected = neighborhoods.indexOf(neighborhoodViewModel);
                                         }
                                     }

                                     neighborhoodNames.add(neighborhoodViewModel.getNeighborhood_name());
                                 }
                                 ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, neighborhoodNames);
                                 spnNeighborhood.setAdapter(arrayAdapter);
                                 if("DETAIL".equals(action)){
                                     spnNeighborhood.setSelection(indexNeighborhoodSelected);
                                 }else{
                                     spnNeighborhood.setSelection(0);
                                 }

                             }
                         }
                         @Override
                         public void onFailure(Call<ArrayList<NeighborhoodViewModel>> call, Throwable t) {
                             t.printStackTrace();
                             Toast.makeText(mContext, PreferencesData.listNeighborhoodFailed, Toast.LENGTH_LONG).show();
                         }
                     }
        );
    }

    private void loadGenders()
    {
        Call<ArrayList<GenderViewModel>> call = GenderApiAdapter.getApiService().getGenders();
        call.enqueue(new Callback<ArrayList<GenderViewModel>>() {

                         @Override
                         public void onResponse(Call<ArrayList<GenderViewModel>> call, Response<ArrayList<GenderViewModel>> response) {
                             if(response.isSuccessful())
                             {
                                 genders= response.body();
                                 for (GenderViewModel genderViewModel : genders) {
                                     if(action.equals("DETAIL")) {
                                         if (genderViewModel.getGender_id() == patient.getGender_id()) {
                                             indexGenderSelected = genders.indexOf(genderViewModel);
                                         }
                                     }

                                     genderNames.add(genderViewModel.getGender_name());
                                 }
                                 ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, genderNames);
                                 spnGender.setAdapter(arrayAdapter);
                                 if("DETAIL".equals(action)){
                                     spnGender.setSelection(indexGenderSelected);
                                 }else{
                                     spnGender.setSelection(0);

                                 }
                             }
                         }
                         @Override
                         public void onFailure(Call<ArrayList<GenderViewModel>> call, Throwable t) {
                             t.printStackTrace();
                             Toast.makeText(mContext, PreferencesData.listGenderFailed, Toast.LENGTH_LONG).show();
                         }
                     }
        );
    }

    private void setPatientViewModelToView(PatientViewModel patient)
    {
        etfirstName.setText(patient.getPatient_first_name());
        etSecondName.setText(patient.getPatient_second_name());
        etFirstLastName.setText(patient.getPatient_first_lastname());
        etSecondLastName.setText(patient.getPatient_second_lastname());
        etDocument.setText(patient.getPatient_document());
        etAge.setText(String.valueOf(patient.getPatient_age()));
        etAddress.setText(patient.getPatient_address());
        etCellPhone.setText(patient.getPatient_mobile_number());
        etLandLinePhone.setText(patient.getPatient_landline_phone());

    }

    private PatientViewModel getPatientViewModelFromView()
    {
        PatientViewModel patient = new PatientViewModel();

        patient.setPatient_first_name(etfirstName.getText().toString());
        patient.setPatient_second_name(etSecondName.getText().toString());
        patient.setPatient_first_lastname(etFirstLastName.getText().toString());
        patient.setPatient_second_lastname(etSecondLastName.getText().toString());
        patient.setPatient_document(etDocument.getText().toString());
        patient.setPatient_age(Integer.parseInt(etAge.getText().toString()));
        patient.setPatient_address(etAddress.getText().toString());
        patient.setPatient_mobile_number(etCellPhone.getText().toString());
        patient.setPatient_landline_phone(etLandLinePhone.getText().toString());
        patient.setDocument_type_id(documentTypeSelectedId);
        patient.setGender_id(genderSelectedId);
        patient.setNeighborhood_id(neighborhoodSelectedId);

        return  patient;
    }

    private void setInputData()
    {
        dataInputString =new ArrayList();
        dataInputString.add(etfirstName.getText().toString());
        dataInputString.add(etSecondName.getText().toString());
        dataInputString.add(etFirstLastName.getText().toString());
        dataInputString.add(etSecondLastName.getText().toString());
        dataInputString.add(etDocument.getText().toString());
        dataInputString.add(etAddress.getText().toString());
        dataInputString.add(etCellPhone.getText().toString());
        dataInputString.add(etLandLinePhone.getText().toString());
        dataInputString.add(etAge.getText().toString());

        dataInputInteger =new ArrayList();
        dataInputInteger.add(genderSelectedId);
        dataInputInteger.add(neighborhoodSelectedId);
        dataInputInteger.add(documentTypeSelectedId);
    }


    private void redirectToSearchPatient()
    {
        loadFragment(new SearchPatientFragment());
    }

    private void redirectToSearchCreatePatient()
    {
        loadFragment(new SearchCreatePatientFragment());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        showHideItems(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case  R.id.save:
                if("DETAIL".equals(action))
                {
                    updatePatient();
                }else{
                    savePatient();
                }

                break;
        }



        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    public void showHideItems(Menu menu)
    {
        MenuItem item;
        item= menu.findItem(R.id.create_therapy);
        item.setVisible(false);
        item= menu.findItem(R.id.save);
        item.setVisible(true);
    }


    public void loadFragment(Fragment fragment){
        manager.beginTransaction().replace(R.id.content,fragment).commit();
    }


    private  void storeStringSharepreferences(String key, String value){

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();

    }


}
