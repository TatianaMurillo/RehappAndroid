package com.rehapp.rehappmovil.rehapp;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.app.ActionBar.*;

public class PatientTherapy extends AppCompatActivity {

    private LinearLayout lyMain;
    LinearLayout lyParameter;
    TextView tvParameter;
    EditText etParameter;
    TableLayout tlParameter;
    TableRow tr;

    private ArrayList<String> itemExample= new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_therapy);

        lyMain = findViewById(R.id.lyMain);

        itemExample.add("FC");
        itemExample.add("Presion Arterial Diastolica");
        itemExample.add("Saturacion");
        itemExample.add("Saturacion Oxigeno");
        itemExample.add("Disnea mMRC");
        itemExample.add("Borg D");
        itemExample.add("Borg E");
        itemExample.add("Frecuencia Cardiaca Maxima");



        tlParameter = new TableLayout(PatientTherapy.this);
        for(String item: itemExample)
        {

            //lyParameter = new LinearLayout(PatientTherapy.this);
            tr = new TableRow(PatientTherapy.this);
            //lyParameter.setOrientation(LinearLayout.HORIZONTAL);

            //creo el textView
            tvParameter  = new TextView(PatientTherapy.this);
            tvParameter.setText(item);


            //creo el editText
            etParameter = new EditText(PatientTherapy.this);


            //Añado columnas a la fila
            tr.addView(tvParameter);
            tr.addView(etParameter);

            //Añado fila a la tabla
            tlParameter.addView(tr);



        }

        //lyParameter.addView(tvParameter);
        //lyParameter.addView(etParameter);



        //creo el textView
        TextView tvExercise  = new TextView(PatientTherapy.this);
        tvExercise.setText("Ver Ejercicios");
        tvExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientTherapy.this, ExerciseDetails.class);
            }
        });

        lyMain.addView(tvExercise);
        lyMain.addView(tlParameter, TableLayout.LayoutParams.MATCH_PARENT);

    }

}
