package com.rehapp.rehappmovil.rehapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class BuscarCrearPaciente extends AppCompatActivity {

    private ImageButton ibtnSearchPatient ;
    private ImageButton ibtnAddPatient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_crear_paciente);

        ibtnSearchPatient=(ImageButton) findViewById(R.id.ibtnSearchPatient);
        ibtnAddPatient=(ImageButton) findViewById(R.id.ibtnAddPatient);

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


    }
}
