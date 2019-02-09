package com.rehapp.rehappmovil.rehapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.DocumentTypeApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.PatientApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.UserApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.DocumentType;
import com.rehapp.rehappmovil.rehapp.Models.Patient;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPatient extends AppCompatActivity{
String documentPatient;
int documentTypePatientId, indexOfPatientDocument=-1;
EditText etDocument;
ImageButton   ibtnSearchPatient;
Spinner spnDocumentType;
 EditText etPatientName;
    private int documentTypeSelected, indexDocumentTypeSelected=0;

    ArrayList<DocumentType> documentTypes;

    ArrayList<String> documentTypeNames= new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_patient);

        etDocument = findViewById(R.id.etDocument);
        spnDocumentType = findViewById(R.id.spnDocumentType);
        etPatientName= findViewById(R.id.etPatientName);


        Bundle extras = getIntent().getExtras();
          documentPatient = extras.getString(PreferencesData.PatientDocument);
          documentTypePatientId = Integer.parseInt(extras.getString(PreferencesData.PatientTpoDocument));


        spnDocumentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                documentTypeSelected = documentTypes.get(position).getDocument_type_id();
                indexDocumentTypeSelected=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


          listDocumentTypes();
          searchPatient();





    }

    public void listDocumentTypes(){

        Call<ArrayList<DocumentType>> call = DocumentTypeApiAdapter.getApiService().getDocumentTypes();

        call.enqueue(new Callback<ArrayList<DocumentType>>() {
            @Override
            public void onResponse(Call<ArrayList<DocumentType>> call, Response<ArrayList<DocumentType>> response) {

                if(response.isSuccessful())
                {

                    documentTypes= response.body();



                    for(DocumentType documentType: documentTypes)
                    {
                        if(documentType.getDocument_type_id()==documentTypePatientId)
                        {
                            indexOfPatientDocument=documentTypes.indexOf(documentType);
                        }
                        documentTypeNames.add(documentType.getDocument_type_name());
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SearchPatient.this,android.R.layout.simple_list_item_1,documentTypeNames);

                    spnDocumentType.setAdapter(arrayAdapter);
                    if(indexOfPatientDocument!=-1) {
                        spnDocumentType.setSelection(indexOfPatientDocument);
                    }else
                    {
                        spnDocumentType.setSelection(0);
                    }
                }

            }

            @Override
            public void onFailure(Call<ArrayList<DocumentType>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), PreferencesData.documentTypeList,Toast.LENGTH_LONG).show();

            }
        });

    }

    public void searchPatient()
    {
        Call<Patient> call = PatientApiAdapter.getApiService().getPatient(documentPatient);

        call.enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {

                if(response.isSuccessful())
                {

                    Patient patient;
                    patient= response.body();

                    String fullName= patient.getPatient_first_name() +" "+ patient.getPatient_first_lastname();

                    etDocument.setText(patient.getPatient_document());
                    etPatientName.setText(fullName);

                }else
                {

                }

            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Toast.makeText(getApplicationContext(), PreferencesData.searchPatientPatient,Toast.LENGTH_LONG).show();

            }
        });

    }

    public void watchTherapies(View view) {

        Intent intent = new Intent(SearchPatient.this,HistoryTherapiesPatient.class);
        startActivity(intent);
    }

    public void searchPatientByDocument(View view) {

        if(!documentTypes.get(indexDocumentTypeSelected).getDocument_type_name().isEmpty() & !etDocument.getText().toString().isEmpty() ) {

            documentPatient=etDocument.getText().toString();
            searchPatient();


        }else
        {
            Toast.makeText(getApplicationContext(), PreferencesData.searchCreatePatientDataMsg,Toast.LENGTH_LONG).show();

        }

    }
}
