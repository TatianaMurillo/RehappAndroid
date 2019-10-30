package com.rehapp.rehappmovil.rehapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.DocumentTypeApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.PatientApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.DocumentTypeViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PatientViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Utils.UserMethods;

import java.security.cert.CRLReason;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPatient extends AppCompatActivity{
    String documentPatient;
    PatientViewModel patientViewModel;
    private int documentTypePatientId, indexOfPatientDocument=-1;
    private int documentTypeSelected, indexDocumentTypeSelected=0;
    EditText etDocument;
    ImageButton ibtnSearchPatient;
    Spinner spnDocumentType;
    EditText etPatientName;
    ArrayList<DocumentTypeViewModel> documentTypes;
    ArrayList<String> documentTypeNames= new ArrayList<>();
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_patient);

        sharedpreferences=getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);

        etDocument = findViewById(R.id.etDocument);
        spnDocumentType = findViewById(R.id.spnDocumentType);
        etPatientName= findViewById(R.id.etPatientName);
        patientViewModel = ViewModelProviders.of(this).get(PatientViewModel.class);
        recoverySendData();

        spnDocumentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                documentTypeSelected = documentTypes.get(position).getDocument_type_id();
                indexDocumentTypeSelected=position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
          listDocumentTypes();
          searchPatient();
    }

    public void listDocumentTypes() {
        Call<ArrayList<DocumentTypeViewModel>> call = DocumentTypeApiAdapter.getApiService().getDocumentTypes();
        call.enqueue(new Callback<ArrayList<DocumentTypeViewModel>>() {
            @Override
            public void onResponse(Call<ArrayList<DocumentTypeViewModel>> call, Response<ArrayList<DocumentTypeViewModel>> response) {
                if(response.isSuccessful())
                {
                    documentTypes= response.body();
                    for(DocumentTypeViewModel documentTypeViewModel : documentTypes)
                    {
                        if(documentTypeViewModel.getDocument_type_id()==documentTypePatientId)
                        {
                            indexOfPatientDocument=documentTypes.indexOf(documentTypeViewModel);
                        }
                        documentTypeNames.add(documentTypeViewModel.getDocument_type_name());
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SearchPatient.this,android.R.layout.simple_list_item_1,documentTypeNames);
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
            public void onFailure(Call<ArrayList<DocumentTypeViewModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), PreferencesData.documentTypeList,Toast.LENGTH_LONG).show();
            }
        });
    }

    public  void searchPatient() {
        Call<PatientViewModel> call = PatientApiAdapter.getApiService().getPatient(documentPatient);
        call.enqueue(new Callback<PatientViewModel>() {
            @Override
            public void onResponse(Call<PatientViewModel> call, Response<PatientViewModel> response) {
                if(response.isSuccessful())
                {
                        patientViewModel = response.body();
                        String fullName = patientViewModel.getPatient_first_name() + " " + patientViewModel.getPatient_first_lastname();
                        etDocument.setText(patientViewModel.getPatient_document());
                        etPatientName.setText(fullName);
                    }else{
                        if(response.raw().code()==404) {
                            Toast.makeText(getApplicationContext(), PreferencesData.searchPatientPatientNonExist, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SearchPatient.this, SearchCreatePatient.class);
                            startActivity(intent);
                        }
                    }
            }
            @Override
            public void onFailure(Call<PatientViewModel> call, Throwable t)
            {
                Toast.makeText(getApplicationContext(), PreferencesData.searchPatientPatient +" "+ t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void watchTherapies(View view) {
        Intent intent = new Intent(SearchPatient.this,HistoryTherapiesPatient.class);
        Bundle extras = new Bundle();
        extras.putString(PreferencesData.PatientDocument, etDocument.getText().toString());
        extras.putString(PreferencesData.PatientTpoDocument, String.valueOf(documentTypeSelected));
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void watchMedicalHistories(View view) {
        Intent intent = new Intent(SearchPatient.this,MedicalHistoriesPatient.class);
        Bundle extras = new Bundle();
        extras.putString(PreferencesData.PatientDocument, etDocument.getText().toString());
        extras.putString(PreferencesData.PatientTpoDocument, String.valueOf(documentTypeSelected));
        intent.putExtras(extras);
        startActivity(intent);
    }

    private void recoverySendData() {
        documentPatient=sharedpreferences.getString(PreferencesData.PatientDocument,"");
        documentTypePatientId=Integer.parseInt(sharedpreferences.getString(PreferencesData.PatientTpoDocument,""));
    }

    public void searchPatient(View view) {
        if (!documentTypes.get(indexDocumentTypeSelected).getDocument_type_name().isEmpty() & !etDocument.getText().toString().isEmpty()) {
            documentPatient = etDocument.getText().toString();
            documentTypePatientId=documentTypes.get(indexDocumentTypeSelected).getDocument_type_id();
            searchPatient();
        }else
        {
            Toast.makeText(getApplicationContext(), PreferencesData.searchCreatePatientDataMsg,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,  menu);

        showHideItems(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                UserMethods.getInstance().Logout(this);
                break;
        }



        return super.onOptionsItemSelected(item);
    }

    public void showHideItems(Menu menu) {
        MenuItem item;
        item= menu.findItem(R.id.create_therapy);
        item.setVisible(false);
        item= menu.findItem(R.id.save);
        item.setVisible(false);
    }

    public void editPatient(View view) {

        Intent intent = new Intent(SearchPatient.this, CreatePatient.class);
        intent.putExtra(PreferencesData.PatientAction, "DETAIL");
        startActivity(intent);

    }
}
