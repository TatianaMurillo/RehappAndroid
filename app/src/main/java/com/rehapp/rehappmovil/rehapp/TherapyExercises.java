package com.rehapp.rehappmovil.rehapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.ExerciseRoutinesViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyMasterDetailViewModel;

import java.util.ArrayList;

public class TherapyExercises extends AppCompatActivity {

    private ListView lvExercises;
    private ArrayList<ExerciseRoutinesViewModel> exercises = new ArrayList<>();
    private boolean isSelected;
    private String action;
    TherapyMasterDetailViewModel therapyViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_therapy_exercises);


        //therapyViewModel = ViewModelProviders.of(this).get(TherapyMasterDetailViewModel.class);
        recoverySendData();
        loadData();

    }
    private void recoverySendData()
    {
        if( getIntent().getExtras()!=null)
        {
            Bundle extras = getIntent().getExtras();
            action=extras.getString(PreferencesData.TherapyAction);
            if(action.equals("ADD")) {
                isSelected=false;
            }else
            {
                isSelected=true;
            }
            therapyViewModel.setAction(action);
        }
    }
    private void loadData()
    {
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        hideShowItems(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.save_therapy:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void hideShowItems(Menu menu)
    {
        MenuItem item;
        if(action.equals("DETAIL")) {
            item = menu.findItem(R.id.create_therapy);
            item.setVisible(false);

            item = menu.findItem(R.id.save_therapy);
            item.setVisible(false);
        }else
        {
            item = menu.findItem(R.id.create_therapy);
            item.setVisible(false);
        }
    }


}
