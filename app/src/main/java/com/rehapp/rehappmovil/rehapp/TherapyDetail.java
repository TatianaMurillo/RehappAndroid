package com.rehapp.rehappmovil.rehapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class TherapyDetail extends AppCompatActivity {
private int therapySelected;
private TextView tvTherapySequence;
private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapy_detail);

        tvTherapySequence = findViewById(R.id.tvTherapySequence);

        therapySelected = Integer.parseInt(getIntent().getSerializableExtra("therapySelected").toString());

        tvTherapySequence.setText("Terapia # " + (therapySelected+1));



    }

    public void therapyAdditionalInfo(View view) {

        //Intent intent = new Intent(TherapyDetail.this, TherapyAdditionalInfo.class);
    }

    public void watchExercises(View view) {
        Intent intent = new Intent(TherapyDetail.this,TherapyExercises.class);
        startActivity(intent);
    }

}
