package com.rehapp.rehappmovil.rehapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class SearchPatient extends AppCompatActivity {
String documentPatient;
EditText etDocument;
ImageButton   ibtnSearchPatient;
Spinner spnDocumentType;
 EditText etPatientName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_patient);

        etDocument = findViewById(R.id.etDocument);
        spnDocumentType = findViewById(R.id.spnDocumentType);
        etPatientName= findViewById(R.id.etPatientName);


        if(savedInstanceState!=null) {
            documentPatient = savedInstanceState.getString("document");
        }else
        {
            documentPatient = getIntent().getSerializableExtra("document").toString();
        }

        etDocument.setText(documentPatient);
        etPatientName.setText("Alvaro Roena");



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
            Intent intent = new Intent(SearchPatient.this,SearchCreatePatient.class);
            startActivity(intent);
            }
        });

        ImageButton rightPage = (ImageButton) customView.findViewById(R.id.right);
        rightPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchPatient.this,HistoryTherapiesPatient.class);
                startActivity(intent);
            }
        });




    }

    public void watchTherapies(View view) {

        Intent intent = new Intent(SearchPatient.this,HistoryTherapiesPatient.class);
        startActivity(intent);
    }
}
