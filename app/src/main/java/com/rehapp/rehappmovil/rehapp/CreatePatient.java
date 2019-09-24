package com.rehapp.rehappmovil.rehapp;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.rehapp.rehappmovil.rehapp.Utils.ValidateInputs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePatient extends AppCompatActivity {


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
    private Button btnSavePatient;
    List<String> dataInputString;
    List<Integer> dataInputInteger;
    private int documentTypeSelectedId=-1,indexDocumentTypeSelected=-1;
    private int genderSelectedId=-1,indexGenderSelected=-1;
    private int neighborhoodSelectedId=-1,indexNeighborhoodSelected=-1;
    PatientViewModel patientResponse;

    ArrayList<DocumentTypeViewModel> documentTypes =new ArrayList<>();
    ArrayList<String> documentTypeNames= new ArrayList<>();

    ArrayList<NeighborhoodViewModel> neighborhoods =new ArrayList<>();
    ArrayList<String> neighborhoodNames= new ArrayList<>();

    ArrayList<GenderViewModel> genders =new ArrayList<>();
    ArrayList<String> genderNames= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_patient);

        etfirstName=findViewById(R.id.etFirstName);
        etSecondName=findViewById(R.id.etSecondName);
        etFirstLastName=findViewById(R.id.etFirstLastName);
        etSecondLastName=findViewById(R.id.etSecondLastName);
        etDocument=findViewById(R.id.etDocument);
        etAddress=findViewById(R.id.etAddress);
        etCellPhone=findViewById(R.id.etCellphone);
        etLandLinePhone=findViewById(R.id.etLandLinePhone);
        etAge=findViewById(R.id.etAge);
        spnNeighborhood = findViewById(R.id.spnNeighborhood);
        spnDocumentType = findViewById(R.id.spnDocumentType);
        spnGender = findViewById(R.id.spnGender);
        btnSavePatient=findViewById(R.id.btnSavePatient);

        loadDocumentTypes();
        loadGenders();
        loadNeigborhoods();

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





    }



    public void savePatient(View view) {

        setInputData();



        if(ValidateInputs.validate().ValidateString(dataInputString))

        {
                if(ValidateInputs.validate().ValidateIntegers(dataInputInteger)) {




                Call<PatientViewModel> call = PatientApiAdapter.getApiService().savePatient(
                        etfirstName.getText().toString(),
                        etSecondName.getText().toString(),
                        etFirstLastName.getText().toString(),
                        etSecondLastName.getText().toString(),
                        etDocument.getText().toString(),
                        Integer.parseInt(etAge.getText().toString()),
                        etAddress.getText().toString(),
                        etCellPhone.getText().toString(),
                        etLandLinePhone.getText().toString(),
                        "Aditional data test",
                        documentTypeSelectedId,
                        genderSelectedId,
                        neighborhoodSelectedId
                );
                call.enqueue(new Callback<PatientViewModel>() {
                    @Override
                    public void onResponse(Call<PatientViewModel> call, Response<PatientViewModel> response) {
                        if(response.isSuccessful())
                        {
                            patientResponse= response.body();
                            Toast.makeText(getApplicationContext(), PreferencesData.storePatientSuccess, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PatientViewModel> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), PreferencesData.storePatientFailed, Toast.LENGTH_LONG).show();
                    }
                });
            }

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
                                         documentTypeNames.add(documentTypeViewModel.getDocument_type_name());
                                     }
                                 ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, documentTypeNames);
                                 spnDocumentType.setAdapter(arrayAdapter);
                                 spnDocumentType.setSelection(0);

                             }
                         }
                         @Override
                         public void onFailure(Call<ArrayList<DocumentTypeViewModel>> call, Throwable t) {
                             t.printStackTrace();
                             Toast.makeText(getApplicationContext(), PreferencesData.listDocumentTypesFailed, Toast.LENGTH_LONG).show();
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
                                     neighborhoodNames.add(neighborhoodViewModel.getNeighborhood_name());
                                 }
                                 ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, neighborhoodNames);
                                 spnNeighborhood.setAdapter(arrayAdapter);
                                 spnNeighborhood.setSelection(0);

                             }
                         }
                         @Override
                         public void onFailure(Call<ArrayList<NeighborhoodViewModel>> call, Throwable t) {
                             t.printStackTrace();
                             Toast.makeText(getApplicationContext(), PreferencesData.listNeighborhoodFailed, Toast.LENGTH_LONG).show();
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
                                     genderNames.add(genderViewModel.getGender_name());
                                 }
                                 ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, genderNames);
                                 spnGender.setAdapter(arrayAdapter);
                                 spnGender.setSelection(0);

                             }
                         }
                         @Override
                         public void onFailure(Call<ArrayList<GenderViewModel>> call, Throwable t) {
                             t.printStackTrace();
                             Toast.makeText(getApplicationContext(), PreferencesData.listGenderFailed, Toast.LENGTH_LONG).show();
                         }
                     }
        );
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



}
