package com.rehapp.rehappmovil.rehapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HistorialTerapiasPaciente extends AppCompatActivity {

    TextView tvTherapiesHistory ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_terapias_paciente);

        tvTherapiesHistory = (TextView) findViewById(R.id.tvTherapiesHistory);

        tvTherapiesHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistorialTerapiasPaciente.this,TerapiasPaciente.class);
                startActivity(intent);
            }
        });

    }

    public void listPatientTherapies(View view) {

    }
}
