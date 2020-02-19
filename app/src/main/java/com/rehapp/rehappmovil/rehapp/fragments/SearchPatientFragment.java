package com.rehapp.rehappmovil.rehapp.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
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

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.DocumentTypeApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.PatientApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.DocumentTypeViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PatientViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPatientFragment extends  Fragment {
    String documentPatient;
    PatientViewModel patientViewModel;
    private int documentTypePatientId, indexOfPatientDocument=-1;
    private int documentTypeSelected, indexDocumentTypeSelected=0;
    EditText etDocument;
    ImageButton ibtnSearchPatient;
    ImageButton ibtnEditPatient;
    TextView tvMedicalHistories;
    TextView tvTherapiesHistory;
    Spinner spnDocumentType;
    EditText etPatientName;
    ArrayList<DocumentTypeViewModel> documentTypes;
    ArrayList<String> documentTypeNames= new ArrayList<>();
    SharedPreferences sharedpreferences;
    Menu menu;
    View view;
    FragmentManager manager;
    private Context mContext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        manager = this.getFragmentManager();
        view =inflater.inflate(R.layout.activity_search_patient,container,false);
        sharedpreferences =mContext.getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);


        etDocument = view.findViewById(R.id.etDocument);
        spnDocumentType = view.findViewById(R.id.spnDocumentType);
        etPatientName= view.findViewById(R.id.etPatientName);
        ibtnSearchPatient=view.findViewById(R.id.ibtnSearchPatient);
        ibtnEditPatient=view.findViewById(R.id.ibtnEditPatient);
        tvTherapiesHistory=view.findViewById(R.id.tvTherapiesHistory);
        tvMedicalHistories=view.findViewById(R.id.tvMedicalHistories);

        patientViewModel = ViewModelProviders.of(this).get(PatientViewModel.class);
        recoverySendData();

        ibtnSearchPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDataSearchPatient();
            }
        });

        ibtnEditPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPatient();
            }
        });

        tvMedicalHistories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watchMedicalHistories();
            }
        });

        tvTherapiesHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watchTherapies();
            }
        });

        spnDocumentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
          return  view;
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
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,documentTypeNames);
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
                Toast.makeText(mContext, PreferencesData.documentTypeList,Toast.LENGTH_LONG).show();
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
                            Toast.makeText(mContext, PreferencesData.searchPatientPatientNonExist, Toast.LENGTH_LONG).show();
                            loadFragment(new SearchCreatePatientFragment());
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

    public void watchTherapies() {
        HistoryTherapiesPatientFragment fragment = new HistoryTherapiesPatientFragment();
        Bundle extras = new Bundle();
        extras.putString(PreferencesData.PatientDocument, etDocument.getText().toString());
        extras.putString(PreferencesData.PatientTpoDocument, String.valueOf(documentTypeSelected));
        fragment.setArguments(extras);
        loadFragment(fragment );
    }

    public void watchMedicalHistories() {
        MedicalHistoriesPatientFragment fragment = new MedicalHistoriesPatientFragment();
        Bundle extras = new Bundle();
        extras.putString(PreferencesData.PatientDocument, etDocument.getText().toString());
        extras.putString(PreferencesData.PatientTpoDocument, String.valueOf(documentTypeSelected));
        fragment.setArguments(extras);
        loadFragment(fragment );
    }

    private void recoverySendData() {
        documentPatient=sharedpreferences.getString(PreferencesData.PatientDocument,"");
        documentTypePatientId=Integer.parseInt(sharedpreferences.getString(PreferencesData.PatientTpoDocument,"0"));

        if(documentPatient.equals("") || documentTypePatientId==0){
            loadFragment(new SearchCreatePatientFragment());
            Toast.makeText(mContext, PreferencesData.SearchCreatePatientFragment,Toast.LENGTH_LONG).show();
        }
    }

    public void checkDataSearchPatient() {
        if (!documentTypes.get(indexDocumentTypeSelected).getDocument_type_name().isEmpty() & !etDocument.getText().toString().isEmpty()) {
            documentPatient = etDocument.getText().toString();
            documentTypePatientId=documentTypes.get(indexDocumentTypeSelected).getDocument_type_id();
            searchPatient();
        }else
        {
            Toast.makeText(mContext, PreferencesData.searchCreatePatientDataMsg,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
        }



        return super.onOptionsItemSelected(item);
    }

    public void showHideItems() {

        MenuItem item;
        item= menu.findItem(R.id.create_therapy);
        item.setVisible(false);
        item= menu.findItem(R.id.save);
        item.setVisible(false);
    }

    public void editPatient() {
        CreatePatientFragment fragment = new CreatePatientFragment();
        Bundle extras = new Bundle();
        extras.putString(PreferencesData.PatientAction, PreferencesData.DETAIL);
        fragment.setArguments(extras);
        loadFragment(fragment );
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    public void loadFragment(Fragment fragment){
        manager.beginTransaction().replace(R.id.content,fragment).commit();
    }
}
