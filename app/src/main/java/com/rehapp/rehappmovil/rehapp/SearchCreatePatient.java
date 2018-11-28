package com.rehapp.rehappmovil.rehapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.Models.DocumentType;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchCreatePatient extends AppCompatActivity implements Callback<ArrayList<DocumentType>>{

        private ImageButton ibtnSearchPatient ;
        private ImageButton ibtnAddPatient;
        private TextView tvWelcome;
        private EditText etDocument;
        private String userActive;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_search_create_patient);

            ibtnSearchPatient= findViewById(R.id.ibtnSearchPatient);
            ibtnAddPatient= findViewById(R.id.ibtnAddPatient);
            etDocument= findViewById(R.id.etDocument);
            tvWelcome = findViewById(R.id.tvWelcome);

            userActive = getIntent().getSerializableExtra("userActive").toString();
            tvWelcome.setText( tvWelcome.getText()  +  userActive +"!");




            //  Call<ArrayList<DocumentType>> call = DocumentTypeApiAdapter.getApiService().getDocumentTypes();

            //call.enqueue(this);
            ActionBar mActionBar = getSupportActionBar();
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater li = LayoutInflater.from(this);
            View customView = li.inflate(R.layout.activity_menu_items, null);
            mActionBar.setCustomView(customView);
            mActionBar.setDisplayShowCustomEnabled(true);
            ImageButton leftPage = (ImageButton)    customView.findViewById(R.id.left);
            leftPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Debe preguntar si cierra la sesion
                }
            });

            ImageButton rightPage = (ImageButton) customView.findViewById(R.id.right);
            rightPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // debe antes validar que se haya seleccionado tipo de documento y documento para redirigir
                    String document = etDocument.getText().toString();
                    if(!document.isEmpty()) {

                        Intent intent = new Intent(SearchCreatePatient.this, SearchPatient.class);
                        intent.putExtra("document",document);
                        startActivity(intent);
                    }else
                    {
                        Toast.makeText(getApplicationContext(),"Debe ingresar el documento del paciente.",Toast.LENGTH_LONG).show();

                    }

                }
            });



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





    public void searchPatient(View view) {
        Intent intent = new Intent(SearchCreatePatient.this, SearchPatient.class);
        intent.putExtra( "document",etDocument.getText().toString());
        //aqui se envia el objeto del paciente
        startActivity(intent);

    }


    public void createPatient(View view) {

        Intent intent = new Intent(SearchCreatePatient.this,CreatePatient.class);
        startActivity(intent);

    }
}
