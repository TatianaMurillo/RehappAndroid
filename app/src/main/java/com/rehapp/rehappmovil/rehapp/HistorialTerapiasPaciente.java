package com.rehapp.rehappmovil.rehapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class HistorialTerapiasPaciente extends AppCompatActivity {

    TextView tvTherapiesHistory ;
    EditText etDocument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_terapias_paciente);

        tvTherapiesHistory = findViewById(R.id.tvTherapiesHistory);
        etDocument = findViewById(R.id.etDocument);
        Intent intent = getIntent();
        etDocument.setText(intent.getStringExtra("document"));

    }

    public void listPatientTherapies(View view) {

       // tvTherapiesHistory.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
                Intent intent = new Intent(HistorialTerapiasPaciente.this,TerapiasPaciente.class);
                startActivity(intent);
          //  }
       // });
    }
}
