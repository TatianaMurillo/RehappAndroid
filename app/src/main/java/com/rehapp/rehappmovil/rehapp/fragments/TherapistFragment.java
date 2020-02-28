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
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.DocumentTypeApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.GenderApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.NeighborhoodApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.PatientApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.TherapistApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.DocumentTypeViewModel;
import com.rehapp.rehappmovil.rehapp.Models.GenderViewModel;
import com.rehapp.rehappmovil.rehapp.Models.NeighborhoodViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PatientViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapistViewModel;
import com.rehapp.rehappmovil.rehapp.R;
import com.rehapp.rehappmovil.rehapp.Utils.ValidateInputs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TherapistFragment extends Fragment {


    private EditText etFirstName;
    private EditText etSecondName;
    private EditText etFirstLastName;
    private EditText etSecondLastName;
    private EditText etDocument;
    private EditText etAge;
    private EditText etEmail;

    private Spinner  spnGender;
    private Spinner  spnDocumentType;
    private Spinner  spnNeighborhood;
    List<String> dataInputString;
    List<Integer> dataInputInteger;
    private int documentTypeSelectedId=-1,indexDocumentTypeSelected=-1;
    private int genderSelectedId=-1,indexGenderSelected=-1;
    private int neighborhoodSelectedId=-1,indexNeighborhoodSelected=-1;

    ArrayList<DocumentTypeViewModel> documentTypes =new ArrayList<>();
    ArrayList<String> documentTypeNames= new ArrayList<>();

    ArrayList<NeighborhoodViewModel> neighborhoods =new ArrayList<>();
    ArrayList<String> neighborhoodNames= new ArrayList<>();

    ArrayList<GenderViewModel> genders =new ArrayList<>();
    ArrayList<String> genderNames= new ArrayList<>();
    TherapistViewModel therapist;

    String therapistId;
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



        etFirstName=view.findViewById(R.id.etFirstName);
        etSecondName=view.findViewById(R.id.etSecondName);
        etFirstLastName=view.findViewById(R.id.etFirstLastName);
        etSecondLastName=view.findViewById(R.id.etSecondLastName);
        etDocument=view.findViewById(R.id.etDocument);
        etEmail=view.findViewById(R.id.etEmail);
        etAge=view.findViewById(R.id.etAge);


        spnNeighborhood = view.findViewById(R.id.spnNeighborhood);
        spnDocumentType = view.findViewById(R.id.spnDocumentType);
        spnGender = view.findViewById(R.id.spnGender);

        spnDocumentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                /**se le resta  uno porque se esta agregando una opción por defecto al spinner cuando se  llena**/
                int selectedOption=position-1;
                if(selectedOption>-1) {
                    documentTypeSelectedId = documentTypes.get(selectedOption).getDocument_type_id();
                }
                indexDocumentTypeSelected = selectedOption;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /**se le resta  uno porque se esta agregando una opción por defecto al spinner cuando se  llena**/
                int selectedOption=position-1;
                if(selectedOption>-1) {
                    genderSelectedId = genders.get(selectedOption).getGender_id();
                }
                indexGenderSelected = selectedOption;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        spnNeighborhood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /**se le resta  uno porque se esta agregando una opción por defecto al spinner cuando se  llena**/
                int selectedOption=position-1;
                if(selectedOption>-1) {
                    neighborhoodSelectedId = neighborhoods.get(selectedOption).getNeighborhood_id();
                }
                indexNeighborhoodSelected = selectedOption;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        recoverySendData();

        loadData();
        return view;
    }

    private void loadData(){
        loadNeigborhoods();
    }
    private void recoverySendData() {
        therapistId=String.valueOf(sharedpreferences.getInt(PreferencesData.TherapistId,0));
    }

    public  void searchTherapist() {
        Call<TherapistViewModel> call = TherapistApiAdapter.getApiService().getTherapist(therapistId);
        call.enqueue(new Callback<TherapistViewModel>() {
            @Override
            public void onResponse(Call<TherapistViewModel> call, Response<TherapistViewModel> response) {
                if(response.isSuccessful())
                {
                    therapist = response.body();
                    selectDocumentType(therapist);
                    selectGender(therapist);
                    selectNeighborhood();
                    setTherapistViewModelToView(therapist);

                }else{
                    if(response.raw().code()==404) {
                        Toast.makeText(mContext, PreferencesData.searchTherapistPatient,Toast.LENGTH_LONG).show();
                        redirectToSearchCreatePatient();
                    }
                }
            }
            @Override
            public void onFailure(Call<TherapistViewModel> call, Throwable t)
            {
                Toast.makeText(mContext, PreferencesData.searchTherapistPatient +" "+ t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateTherapist() {

        setInputData();

        if(ValidateInputs.validate().ValidateString(dataInputString))

        {
            if(ValidateInputs.validate().validateNonAcceptableValueInInteger(dataInputInteger)) {

                final TherapistViewModel therapist = getTherapistViewModelFromView();
                Call<TherapistViewModel> call = TherapistApiAdapter.getApiService().updateTherapist(therapist,therapistId);
                call.enqueue(new Callback<TherapistViewModel>() {
                    @Override
                    public void onResponse(Call<TherapistViewModel> call, Response<TherapistViewModel> response) {
                        if(response.isSuccessful())
                        {
                            Toast.makeText(mContext, PreferencesData.updateTherapistSuccess, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TherapistViewModel> call, Throwable t) {
                        Toast.makeText(mContext, PreferencesData.udpateTherapistFailed, Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                Toast.makeText(mContext, PreferencesData.storeTherapistEmptyDataMsg, Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(mContext, PreferencesData.storeTherapistEmptyDataMsg, Toast.LENGTH_LONG).show();

        }

    }

    private void loadDocumentTypes() {
        Call<ArrayList<DocumentTypeViewModel>> call = DocumentTypeApiAdapter.getApiService().getDocumentTypes();
        call.enqueue(new Callback<ArrayList<DocumentTypeViewModel>>() {

                         @Override
                         public void onResponse(Call<ArrayList<DocumentTypeViewModel>> call, Response<ArrayList<DocumentTypeViewModel>> response) {
                             if(response.isSuccessful())
                             {
                                 documentTypes= response.body();
                                 documentTypeNames.add(getResources().getString(R.string.DocumentTypeNonSelected));
                                     for (DocumentTypeViewModel documentTypeViewModel : documentTypes) {
                                         documentTypeNames.add(documentTypeViewModel.getDocument_type_name().concat(" - ").concat(documentTypeViewModel.getDocument_type_description()));
                                     }
                                 ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, documentTypeNames);
                                 spnDocumentType.setAdapter(arrayAdapter);
                                 loadGenders();
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

    private void loadNeigborhoods() {
        Call<ArrayList<NeighborhoodViewModel>> call = NeighborhoodApiAdapter.getApiService().getNeighborhoods();
        call.enqueue(new Callback<ArrayList<NeighborhoodViewModel>>() {

                         @Override
                         public void onResponse(Call<ArrayList<NeighborhoodViewModel>> call, Response<ArrayList<NeighborhoodViewModel>> response) {
                             if(response.isSuccessful())
                             {
                                 neighborhoods= response.body();
                                 neighborhoodNames.add(getResources().getString(R.string.NeighborhoodNonSelected));
                                 for (NeighborhoodViewModel neighborhoodViewModel : neighborhoods) {
                                     neighborhoodNames.add(neighborhoodViewModel.getNeighborhood_name());
                                 }
                                 ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, neighborhoodNames);
                                 spnNeighborhood.setAdapter(arrayAdapter);
                                 loadDocumentTypes();
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

    private void loadGenders() {
        Call<ArrayList<GenderViewModel>> call = GenderApiAdapter.getApiService().getGenders();
        call.enqueue(new Callback<ArrayList<GenderViewModel>>() {

                         @Override
                         public void onResponse(Call<ArrayList<GenderViewModel>> call, Response<ArrayList<GenderViewModel>> response) {
                             if(response.isSuccessful())
                             {
                                 genders= response.body();
                                 genderNames.add(getResources().getString(R.string.GenderNonSelected));
                                 for (GenderViewModel genderViewModel : genders) {

                                     genderNames.add(genderViewModel.getGender_name().concat(" - ").concat(genderViewModel.getGender_description()));
                                 }
                                 ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, genderNames);
                                 spnGender.setAdapter(arrayAdapter);
                                 searchTherapist();
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

    /**
     **Se selecciona el genero
     **/
    private void selectGender(TherapistViewModel therapist){
        int indexOfGender=-1;

        Object therapistGender=therapist.getNeighborhoodId();
        int therapistGenderInt=((Integer) therapistGender).intValue();

        if(therapistGender!=null) {
            for (GenderViewModel genderViewModel : genders) {
                if (genderViewModel.getGender_id() == therapistGenderInt) {
                    /**se le suma  uno porque se esta agregando una opciòn por defecto al spinner cuando se  llena**/
                    indexOfGender = genders.indexOf(genderViewModel) + 1;
                }
            }
        }
        if (indexOfGender != -1) {
            spnGender.setSelection(indexOfGender);
        }else {
            /**se selecciona al menos la opcion que se crea por defecto**/
            spnGender.setSelection(0);
            Toast.makeText(mContext, "no seleccionó género", Toast.LENGTH_LONG).show();
        }
    }

    /**
     **Se selecciona el barrio
     **/
    private void selectNeighborhood(){
        int indexOfNeighborhood=-1;

        Object therapistNeighborhood=therapist.getNeighborhoodId();
        int therapistNeighborhoodInt=((Integer) therapistNeighborhood).intValue();

        if(therapistNeighborhood!=null) {
            for (NeighborhoodViewModel neighborhoodViewModel : neighborhoods) {
                if (neighborhoodViewModel.getNeighborhood_id() == therapistNeighborhoodInt) {
                    /**se le suma  uno porque se esta agregando una opciòn por defecto al spinner cuando se  llena**/
                    indexOfNeighborhood = neighborhoods.indexOf(neighborhoodViewModel) + 1;
                }
            }
        }
        if (indexOfNeighborhood != -1) {
            spnNeighborhood.setSelection(indexOfNeighborhood);
        }else {
            /**se selecciona al menos la opcion que se crea por defecto**/
            spnNeighborhood.setSelection(0);
        }
    }

    /**
     **Se selecciona el documentType
     **/
    private void selectDocumentType(TherapistViewModel therapist){
        int indexOfDocumentType=-1;
        Object therapistDocumentType=therapist.getDocumentTypeId();
        int therapistDocumentTypeInt=((Integer) therapistDocumentType).intValue();

        if(therapistDocumentType!=null){
            for(DocumentTypeViewModel documentTypeViewModel : documentTypes) {
                if (documentTypeViewModel.getDocument_type_id() == therapistDocumentTypeInt) {
                    /**se le suma  uno porque se esta agregando una opciòn por defecto al spinner cuando se  llena**/
                    indexOfDocumentType = documentTypes.indexOf(documentTypeViewModel)+1;
                }
            }
        }
        if (indexOfDocumentType != -1) {
            spnDocumentType.setSelection(indexOfDocumentType);
        }else {
            /**se selecciona al menos la opcion que se crea por defecto**/
            spnDocumentType.setSelection(0);
        }
    }

    private void setTherapistViewModelToView(TherapistViewModel therapist) {
        Object getTherapist_first_name=therapist.getTherapist_first_name();
        Object getTherapist_second_name=therapist.getTherapist_second_name();
        Object getTherapist_first_lastname=therapist.getTherapist_first_lastname();
        Object getTherapist_second_lastname=therapist.getTherapist_second_lastname();
        Object getTherapist_document=therapist.getTherapist_document();
        Object getTherapist_email = therapist.getTherapist_email();
        Object getTherapist_age=therapist.getTherapist_age();



        etFirstName.setText(getTherapist_first_name!=null?getTherapist_first_name.toString():"");
        etSecondName.setText(getTherapist_second_name!=null?getTherapist_second_name.toString():"");
        etFirstLastName.setText(getTherapist_first_lastname!=null?getTherapist_first_lastname.toString():"");
        etSecondLastName.setText(getTherapist_second_lastname!=null?getTherapist_second_lastname.toString():"");
        etDocument.setText(getTherapist_document!=null?getTherapist_document.toString():"");
        etEmail.setText(getTherapist_email!=null?getTherapist_email.toString():"");
        etAge.setText(getTherapist_age.toString());

    }

    private TherapistViewModel getTherapistViewModelFromView() {

        TherapistViewModel therapist = new TherapistViewModel();

        therapist.setTherapist_first_name(etFirstName.getText().toString());
        therapist.setTherapist_second_name(etSecondName.getText().toString());
        therapist.setTherapist_first_lastname(etFirstLastName.getText().toString());
        therapist.setTherapist_second_lastname(etSecondLastName.getText().toString());
        therapist.setTherapist_email(etEmail.getText().toString());
        therapist.setTherapist_age(Integer.parseInt(etAge.getText().toString()));
        therapist.setGender(genderSelectedId);
        therapist.setNeighborhood(neighborhoodSelectedId);

        return  therapist;
    }

    private void setInputData() {
        dataInputString =new ArrayList();
        dataInputString.add(etFirstName.getText().toString());
        dataInputString.add(etSecondName.getText().toString());
        dataInputString.add(etFirstLastName.getText().toString());
        dataInputString.add(etSecondLastName.getText().toString());
        dataInputString.add(etEmail.getText().toString());
        dataInputString.add(etAge.getText().toString());


        dataInputInteger =new ArrayList();
        dataInputInteger.add(genderSelectedId);
        dataInputInteger.add(neighborhoodSelectedId);
        dataInputInteger.add(documentTypeSelectedId);
    }

    private void redirectToSearchCreatePatient() {
        loadFragment(new SearchCreatePatientFragment());
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
            case  R.id.save:
                    updateTherapist();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    public void showHideItems(Menu menu) {
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
