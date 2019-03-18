package com.rehapp.rehappmovil.rehapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AndroidException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.PhysiologicalParameterApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.PhysiologicalParameterTherapyViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PhysiologicalParameterViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;
import com.rehapp.rehappmovil.rehapp.Utils.UserMethods;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhysiologicalParameterTherapy extends AppCompatActivity {

    private TextView title;
    private TextView tvFC;
    private EditText etFC;
    private TextView tvDyspneaMmrc;
    private EditText etDyspneaMmrc;
    private TextView tvDiastolicPressure;
    private EditText etDiastolicPressure;
    private TextView tvSaturation;
    private EditText etSaturation;
    private TextView tvOxygenSaturation;
    private EditText etOxygenSaturation;
    private TextView tvBorgE;
    private EditText etBorgE;
    private TextView tvBorgD;
    private EditText etBorgD;
    private TextView tvHeartRate;
    private EditText etHeartRate;
    private String physiologicalParameterAction;
    private String action;
    private TherapyViewModel therapySelected;
    private TableLayout tableContent;
    private TableRow row;
    private EditText editText;
    private TextView textView;
    private GridView grid;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physiological_parameter_therapy);

         title = findViewById(R.id.title);
                 grid = findViewById(R.id.grid);


        ArrayList<PhysiologicalParameterViewModel> options= new ArrayList<PhysiologicalParameterViewModel>();

        options.add(new PhysiologicalParameterViewModel("FC"));

        options.add(new PhysiologicalParameterViewModel("Heart rate"));

        options.add(new PhysiologicalParameterViewModel("BorgD"));

        final PhysiologicalParameterRowAdapter arrayAdapter = new PhysiologicalParameterRowAdapter(PhysiologicalParameterTherapy.this, options);

        grid.setAdapter(arrayAdapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu,  menu);
            showHideItems(menu);

            return true;
    }


   private void fillTablePhysiologicalParams()
   {

       Call<ArrayList<PhysiologicalParameterViewModel>> call = PhysiologicalParameterApiAdapter.getApiService().getPhysiologicalParams();
       call.enqueue(new Callback<ArrayList<PhysiologicalParameterViewModel>>() {
           int indexOfTherapist=-1;

           ArrayList<PhysiologicalParameterViewModel> physiologicalParameters= new ArrayList<PhysiologicalParameterViewModel>();
           ArrayList<String> physiologicalParametersNames  = new ArrayList<String>();

           @Override
           public void onResponse(Call<ArrayList<PhysiologicalParameterViewModel>> call, Response<ArrayList<PhysiologicalParameterViewModel>> response) {
               if(response.isSuccessful())
               {
                   physiologicalParameters = response.body();
                   for(PhysiologicalParameterViewModel physiologicalParameterViewModel : physiologicalParameters)
                   {
                       row = new TableRow(PhysiologicalParameterTherapy.this);

                       textView = new TextView(PhysiologicalParameterTherapy.this);

                       ViewGroup.LayoutParams params= textView.getLayoutParams();
                       params.width= LinearLayout.LayoutParams.WRAP_CONTENT;
                       params.height= LinearLayout.LayoutParams.WRAP_CONTENT;

                       textView.setLayoutParams(params);

                       row.addView(textView);

                       params= editText.getLayoutParams();

                       params.height= LinearLayout.LayoutParams.WRAP_CONTENT;


                       row.addView(editText);

                       tableContent.addView(row);
                   }

               }
           }
           @Override
           public void onFailure(Call<ArrayList<PhysiologicalParameterViewModel>> call, Throwable t) {
               Toast.makeText(getApplicationContext(), PreferencesData.therapyDetailTherapistListMsg,Toast.LENGTH_LONG).show();
           }
       });
   }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.logout:
                UserMethods.Do().Logout(this);
                break;
            case R.id.save_therapy:
            savePhysiologicalParameterTherapy();
                break;
        }



        return super.onOptionsItemSelected(item);
    }

    public void showHideItems(Menu menu)
    {
            MenuItem item;
            item = menu.findItem(R.id.create_therapy);
            item.setVisible(false);
    }

    public void savePhysiologicalParameterTherapy()
    {
        String FC=etFC.getText().toString();
        String DyspneaMmrc=etDyspneaMmrc.getText().toString();
        String DiastolicPressure=etDiastolicPressure.getText().toString();
        String Saturation=etSaturation.getText().toString();
        String OxygenSaturation=etOxygenSaturation.getText().toString();
        String BorgE=etBorgE.getText().toString();
        String BorgD=etBorgD.getText().toString();
        String HeartRate=etHeartRate.getText().toString();
        PhysiologicalParameterViewModel physiologicalParameter= new PhysiologicalParameterViewModel();


        Gson gson = new  Gson();

        String json = gson.toJson(physiologicalParameter);
        sharedpreferences = getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        if(physiologicalParameterAction.equals("IN")) {
            editor.putString(PreferencesData.PhysiologicalParameterIn, json);
            editor.commit();
            Toast.makeText(getApplicationContext(), PreferencesData.PhysiologicalParameterTherapySuccessMgs,   Toast.LENGTH_LONG).show();
            Intent intent = new Intent(PhysiologicalParameterTherapy.this, TherapyDetail.class);
            intent.putExtra(PreferencesData.TherapyAction,action);
            intent.putExtra(PreferencesData.TherapySelected, therapySelected);
            startActivity(intent);
        }else if(physiologicalParameterAction.equals("OUT"))
        {
            editor.putString(PreferencesData.PhysiologicalParameterOut, json);
            editor.commit();
            Toast.makeText(getApplicationContext(), PreferencesData.PhysiologicalParameterTherapySuccessMgs,   Toast.LENGTH_LONG).show();
            Intent intent = new Intent(PhysiologicalParameterTherapy.this, TherapyDetail.class);
            intent.putExtra(PreferencesData.TherapyAction,action);
            intent.putExtra(PreferencesData.TherapySelected, therapySelected);
            startActivity(intent);
        }else
        {
            Toast.makeText(getApplicationContext(), PreferencesData.PhysiologicalParameterTherapyDataMgsError,   Toast.LENGTH_LONG).show();
        }
    }

    private void recoverySendData()
    {
        if( getIntent().getExtras()!=null)
        {
            Bundle extras = getIntent().getExtras();
            physiologicalParameterAction= extras.getString(PreferencesData.PhysiologicalParameterAction);
            action=extras.getString(PreferencesData.TherapyAction);
            therapySelected = (TherapyViewModel) getIntent().getSerializableExtra(PreferencesData.TherapySelected);
        }
    }
}

