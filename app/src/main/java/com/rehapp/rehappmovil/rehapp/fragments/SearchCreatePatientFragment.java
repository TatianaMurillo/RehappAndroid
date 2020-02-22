package com.rehapp.rehappmovil.rehapp.fragments;

import android.app.Activity;
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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.CreatePatient;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.DocumentTypeApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.PatientApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.DocumentTypeViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PatientViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.SearchCreatePatientViewModel;
import com.rehapp.rehappmovil.rehapp.R;
import com.rehapp.rehappmovil.rehapp.SearchPatient;
import java.util.ArrayList;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchCreatePatientFragment  extends Fragment {
    private ImageButton ibtnSearchPatient ;
    private ImageButton ibtnAddPatient;

    private TextView tvWelcome;
    private EditText etDocument;
    private Spinner spnDocumentType;
    private SearchCreatePatientViewModel searchCreatePatientViewModel;
    private int documentTypeSelected, indexDocumentTypeSelected=0;

    ArrayList<DocumentTypeViewModel> documentTypes;
    ArrayList<String> documentTypeNames= new ArrayList<>();

    private Context mContext;
    SharedPreferences sharedpreferences;
    View view;
    FragmentManager manager;
    public SearchCreatePatientFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

         manager = this.getFragmentManager();
         view =inflater.inflate(R.layout.activity_search_create_patient,container,false);
         sharedpreferences =mContext.getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);

        ibtnSearchPatient= view.findViewById(R.id.ibtnSearchPatient);
        ibtnAddPatient= view.findViewById(R.id.ibtnAddPatient);
        etDocument= view.findViewById(R.id.etDocument);
        tvWelcome = view.findViewById(R.id.tvWelcome);
        spnDocumentType= view.findViewById(R.id.spnDocumentType);
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

        ibtnSearchPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPatient();
            }
        });

        ibtnAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPatient();
            }
        });

         return view;
    }

    private void loadCurrentData()
    {
        String activeUser=sharedpreferences.getString(PreferencesData.TherapistName,"");

        tvWelcome.setText( "¡Bienvenido " +activeUser+"!" );

        loadDocumentTypes();
    }
    private void recoverySendData() { }

    private void loadDocumentTypes(){

        Call<ArrayList<DocumentTypeViewModel>> call = DocumentTypeApiAdapter.getApiService().getDocumentTypes();
        call.enqueue(new Callback<ArrayList<DocumentTypeViewModel>>() {

        @Override
        public void onResponse(Call<ArrayList<DocumentTypeViewModel>> call, Response<ArrayList<DocumentTypeViewModel>> response)
        {
            if(response.isSuccessful())
            {
                documentTypes= response.body();
                for(DocumentTypeViewModel documentTypeViewModel : documentTypes)
                {
                    documentTypeNames.add(documentTypeViewModel.getDocument_type_name());
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, documentTypeNames);
                spnDocumentType.setAdapter(arrayAdapter);
                spnDocumentType.setSelection(0);
            }
        }
        @Override
        public void onFailure(Call<ArrayList<DocumentTypeViewModel>> call, Throwable t)
        {
            Toast.makeText(mContext, PreferencesData.searchPatientListDocumentTypesMgs + t.getMessage(),   Toast.LENGTH_LONG).show();
        }
        });
    }

    public void searchPatient()
    {
        if(documentTypes!=null) {
            if (!documentTypes.get(indexDocumentTypeSelected).getDocument_type_name().isEmpty() & !etDocument.getText().toString().isEmpty()) {

                Call<PatientViewModel> call = PatientApiAdapter.getApiService().getPatient(etDocument.getText().toString());

                call.enqueue(new Callback<PatientViewModel>() {
                    @Override
                    public void onResponse(Call<PatientViewModel> call, Response<PatientViewModel> response) {

                        if (response.isSuccessful()) {
                            PatientViewModel patient = response.body();

                            storeStringSharepreferences(PreferencesData.PatientDocument, etDocument.getText().toString());
                            storeStringSharepreferences(PreferencesData.PatientTpoDocument, String.valueOf(documentTypeSelected));
                            storeStringSharepreferences(PreferencesData.PatientId, String.valueOf(patient.getPatient_id()));
                            loadFragment(new SearchPatientFragment());

                        } else {
                            if (response.raw().code() == 404) {
                                Toast.makeText(mContext, PreferencesData.searchPatientPatientNonExist, Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PatientViewModel> call, Throwable t) {
                        Toast.makeText(mContext, PreferencesData.searchPatientPatient + " " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(mContext, PreferencesData.searchCreatePatientDataMsg, Toast.LENGTH_LONG).show();
            }
        }


    }

    public void createPatient()
    {
        CreatePatientFragment fragment = new CreatePatientFragment();
        Bundle extras = new Bundle();
        extras.putString(PreferencesData.PatientAction, PreferencesData.ADD);
        fragment.setArguments(extras);
        loadFragment(fragment );
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
                break;
                default:
        }
        return super.onOptionsItemSelected(item);
    }

    public void showHideItems(Menu menu) {
            MenuItem item;
            item = menu.findItem(R.id.create_therapy);
            item.setVisible(false);
            item = menu.findItem(R.id.save);
            item.setVisible(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    public void loadFragment(Fragment fragment){
        manager.beginTransaction().replace(R.id.content,fragment).commit();
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
}
