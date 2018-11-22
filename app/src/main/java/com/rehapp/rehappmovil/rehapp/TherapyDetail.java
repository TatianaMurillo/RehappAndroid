package com.rehapp.rehappmovil.rehapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public void therapyAdditionalInfo(View view) {

        //Intent intent = new Intent(TherapyDetail.this, TherapyAdditionalInfo.class);
    }

    public void watchExercises(View view) {
        Intent intent = new Intent(TherapyDetail.this,TherapyExercises.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId())
        {
            case R.id.back:
                intent = new Intent(this, TherapyDetail.class);
                startActivity(intent);
                break;
            case R.id.future:
                 intent = new Intent(this, ExerciseVideo.class);
                startActivity(intent);
                break;
            case R.id.home:
                intent = new Intent(this, TherapyDetail.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
