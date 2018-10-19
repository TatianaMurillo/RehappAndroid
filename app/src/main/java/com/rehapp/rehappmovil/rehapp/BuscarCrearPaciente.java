package com.rehapp.rehappmovil.rehapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.DocumentTypeApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.DocumentType;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuscarCrearPaciente extends AppCompatActivity implements Callback<ArrayList<DocumentType>> {

    private ImageButton ibtnSearchPatient ;
    private ImageButton ibtnAddPatient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_crear_paciente);

        ibtnSearchPatient= findViewById(R.id.ibtnSearchPatient);
        ibtnAddPatient= findViewById(R.id.ibtnAddPatient);

        ibtnSearchPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuscarCrearPaciente.this,HistorialTerapiasPaciente.class);
                startActivity(intent);
            }
        });

        ibtnAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuscarCrearPaciente.this,CrearPaciente.class);
                startActivity(intent);
            }
        });


        Call<ArrayList<DocumentType>> call = DocumentTypeApiAdapter.getApiService().getDocumentTypes();

        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<ArrayList<DocumentType>> call, Response<ArrayList<DocumentType>> response) {

        if(response.isSuccessful())
        {

            ArrayList<DocumentType> documentTypes= response.body();

            Log.d("","el tamaño es " + documentTypes.size());

        }
    }

    @Override
    public void onFailure(Call<ArrayList<DocumentType>> call, Throwable t) {

    }
}
