package com.rehapp.rehappmovil.rehapp;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.DocumentTypeApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.PatientApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.DocumentType;
import com.rehapp.rehappmovil.rehapp.Models.PatientViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.SearchCreatePatientViewModel;
import com.rehapp.rehappmovil.rehapp.Utils.UserMethods;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchCreatePatient extends AppCompatActivity implements Callback<ArrayList<DocumentType>>{

        private ImageButton ibtnSearchPatient ;
        private ImageButton ibtnAddPatient;

        private TextView tvWelcome;
        private EditText etDocument;
        private Spinner spnDocumentType;
        private String userActive;
        private SearchCreatePatientViewModel searchCreatePatientViewModel;
        private int documentTypeSelected, indexDocumentTypeSelected=0;

        ArrayList<DocumentType> documentTypes;
        ArrayList<String> documentTypeNames= new ArrayList<String>();

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search_create_patient);

            ibtnSearchPatient= findViewById(R.id.ibtnSearchPatient);
            ibtnAddPatient= findViewById(R.id.ibtnAddPatient);
            etDocument= findViewById(R.id.etDocument);
            tvWelcome = findViewById(R.id.tvWelcome);
            spnDocumentType= findViewById(R.id.spnDocumentType);
            searchCreatePatientViewModel = ViewModelProviders.of(this).get(SearchCreatePatientViewModel.class);
            recoverySendData();
            loadCurrentData();
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
            Call<ArrayList<DocumentType>> call = DocumentTypeApiAdapter.getApiService().getDocumentTypes();
            call.enqueue(this);
        }

    private void loadCurrentData()
    {
        tvWelcome.setText( searchCreatePatientViewModel.getActiveUser() );
    }
    private void recoverySendData()
    {
        if(getIntent().getSerializableExtra(PreferencesData.userActive)!=null) {
            userActive = getIntent().getSerializableExtra(PreferencesData.userActive).toString();
            searchCreatePatientViewModel.setActiveUser(userActive);
        }
    }

    @Override
    public void onResponse(Call<ArrayList<DocumentType>> call, Response<ArrayList<DocumentType>> response)
    {
            if(response.isSuccessful())
            {
                documentTypes= response.body();
                for(DocumentType documentType: documentTypes)
                {
                    documentTypeNames.add(documentType.getDocument_type_name());
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, documentTypeNames);
                spnDocumentType.setAdapter(arrayAdapter);
                spnDocumentType.setSelection(0);
            }
        }
        @Override
        public void onFailure(Call<ArrayList<DocumentType>> call, Throwable t)
        {
            Toast.makeText(getApplicationContext(), PreferencesData.searchPatientListDocumentTypesMgs + t.getMessage(),   Toast.LENGTH_LONG).show();
        }

    public void searchPatient(View view)
    {
        if(!documentTypes.get(indexDocumentTypeSelected).getDocument_type_name().isEmpty() & !etDocument.getText().toString().isEmpty() ) {

            Call<PatientViewModel> call = PatientApiAdapter.getApiService().getPatient(etDocument.getText().toString());

            call.enqueue(new Callback<PatientViewModel>() {
                @Override
                public void onResponse(Call<PatientViewModel> call, Response<PatientViewModel> response) {

                    if(response.isSuccessful())
                    {
                        Intent intent = new Intent(SearchCreatePatient.this, SearchPatient.class);
                        Bundle extras = new Bundle();
                        extras.putString(PreferencesData.PatientDocument, etDocument.getText().toString());
                        extras.putString(PreferencesData.PatientTpoDocument, String.valueOf(documentTypeSelected));
                        intent.putExtras(extras);
                        startActivity(intent);
                    }else{
                        if(response.raw().code()==404) {
                            Toast.makeText(getApplicationContext(), PreferencesData.searchPatientPatientNonExist, Toast.LENGTH_LONG).show();
                        }
                    }
                }
                @Override
                public void onFailure(Call<PatientViewModel> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), PreferencesData.searchPatientPatient +" "+ t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }else
        {
            Toast.makeText(getApplicationContext(), PreferencesData.searchCreatePatientDataMsg,Toast.LENGTH_LONG).show();
        }
    }

    public void createPatient(View view)
    {
        Intent intent = new Intent(SearchCreatePatient.this,CreatePatient.class);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,  menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
            switch (item.getItemId())
            {
                case R.id.logout:
                    UserMethods.Do().Logout(this);
                    break;
            }



        return super.onOptionsItemSelected(item);
    }
}
