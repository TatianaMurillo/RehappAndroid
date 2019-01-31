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

        documentPatient = getIntent().getSerializableExtra("document").toString();
        etDocument.setText(documentPatient);
        etPatientName.setText("Alvaro Roena");



    }

    public void watchTherapies(View view) {

        Intent intent = new Intent(SearchPatient.this,HistoryTherapiesPatient.class);
        startActivity(intent);
    }
}
