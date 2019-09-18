package com.rehapp.rehappmovil.rehapp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.DocumentTypeApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.PatientApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.DocumentTypeViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PatientViewModel;
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
    private Spinner  spnGenner;
    private Spinner  spnDocumentType;
    private Spinner  spnNeighborhood;
    private Button btnSavePatient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_patient);

        etfirstName=findViewById(R.id.etFirstName);
        etSecondName=findViewById(R.id.etSecondName);
        etFirstLastName=findViewById(R.id.etSecondLastName);
        etSecondLastName=findViewById(R.id.etSecondLastName);
        etDocument=findViewById(R.id.etDocument);
        etAddress=findViewById(R.id.etAddress);
        etCellPhone=findViewById(R.id.etCellphone);
        etLandLinePhone=findViewById(R.id.etLandLinePhone);
        etAge=findViewById(R.id.etAge);
        spnNeighborhood = findViewById(R.id.spnNeighborhood);
        spnDocumentType = findViewById(R.id.spnDocumentType);
        spnGenner = findViewById(R.id.spnGenner);
        btnSavePatient=findViewById(R.id.btnSavePatient);



    }



    public void savePatient(View view) {

        List<String> dataInput =new ArrayList();
        dataInput.add(etfirstName.getText().toString());
        dataInput.add(etSecondName.getText().toString());
        dataInput.add(etFirstLastName.getText().toString());
        dataInput.add(etSecondLastName.getText().toString());
        dataInput.add(etDocument.getText().toString());
        dataInput.add(etAddress.getText().toString());
        dataInput.add(etCellPhone.getText().toString());
        dataInput.add(etLandLinePhone.getText().toString());
        dataInput.add(etAge.getText().toString());


        if(ValidateInputs.validate().ValidateString(dataInput))
        {
            PatientViewModel patient = new PatientViewModel();


            Call<PatientViewModel> call = PatientApiAdapter.getApiService().savePatient(patient);
            call.enqueue(new Callback<PatientViewModel>() {
                @Override
                public void onResponse(Call<PatientViewModel> call, Response<PatientViewModel> response) {

                }

                @Override
                public void onFailure(Call<PatientViewModel> call, Throwable t) {

                }
            });


        }


    }
}
