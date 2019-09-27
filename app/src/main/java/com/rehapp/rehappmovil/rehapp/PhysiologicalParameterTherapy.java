package com.rehapp.rehappmovil.rehapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.PhysiologicalParameterApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.PhysiologicalParameterTherapyViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PhysiologicalParameterViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapyMasterDetailViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;
import com.rehapp.rehappmovil.rehapp.Utils.UserMethods;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhysiologicalParameterTherapy extends AppCompatActivity {

    private TextView title;

    private String physiologicalParameterAction;
    private String action;

    String json;
    private int therapySelectedId;
    private EditText editText;
    private TextView textView;
    private GridLayout grid;
    SharedPreferences sharedpreferences;

    ArrayList<PhysiologicalParameterViewModel> options= new ArrayList<PhysiologicalParameterViewModel>();

    private List<PhysiologicalParameterTherapyViewModel> physiologicalParameterViewModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physiological_parameter_therapy);

         title = findViewById(R.id.title);
         grid = findViewById(R.id.grid);

        recoverySendData();
        LoadData();

        options.add(new PhysiologicalParameterViewModel("FC"));

        options.add(new PhysiologicalParameterViewModel("Heart rate"));

        options.add(new PhysiologicalParameterViewModel("BorgD"));

        options.add(new PhysiologicalParameterViewModel("BorgE"));

        addPhysiologicalParametersView(options);


    }

    public void LoadData()
    {
        if(physiologicalParameterAction.equals(PreferencesData.PhysiologicalParameterTherapySesionIN))
        {
            title.setText(R.string.phisiologicalParametersIn);
        }else if(physiologicalParameterAction.equals(PreferencesData.PhysiologicalParameterTherapySesionOUT))
        {
            title.setText(R.string.phisiologicalParametersOut);
        }
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
                   addPhysiologicalParametersView(physiologicalParameters);

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
              getPhysiolocalParametersFromView();
              saveObjectTherapyMasterDetailViewModel();

            Toast.makeText(getApplicationContext(), PreferencesData.PhysiologicalParameterTherapySuccessMgs,   Toast.LENGTH_LONG).show();
            System.out.print("el objeto grabado es: ".concat(json));
            Intent intent = new Intent(PhysiologicalParameterTherapy.this, TherapyDetail.class);
            intent.putExtra(PreferencesData.TherapyAction,action);
            //intent.putExtra(PreferencesData.TherapySelected, therapySelected);
            startActivity(intent);
    }

    private void getPhysiolocalParametersFromView() {

        int childCount = grid.getChildCount();
        String valueEditText;
        String valueTextView;
        Object childEditText;
        Object childTextView;
        TextView textView;
        EditText editText;

        for(int i=1;i<childCount; i+=2)
        {
             childEditText = grid.getChildAt(i);
             childTextView = grid.getChildAt(i-1);

             textView=(TextView) childTextView;
             valueTextView=textView.getText().toString().trim();
             editText = (EditText)childEditText;

            valueEditText = editText.getText().toString().trim();
            physiologicalParameterViewModelList.add(new PhysiologicalParameterTherapyViewModel(0,getIdPhysiologicalParameter(valueTextView),0,valueEditText,physiologicalParameterAction));

        }

    }

    private void recoverySendData()
    {
        if( getIntent().getExtras()!=null)
        {
            Bundle extras = getIntent().getExtras();
            physiologicalParameterAction= extras.getString(PreferencesData.PhysiologicalParameterAction);
            action=extras.getString(PreferencesData.TherapyAction);
            therapySelectedId = (int) getIntent().getSerializableExtra(PreferencesData.TherapySelectedId);
        }
    }


    private void addPhysiologicalParametersView( ArrayList<PhysiologicalParameterViewModel> physiologicalParameters)
    {
        for(PhysiologicalParameterViewModel physiologicalParameterViewModel : physiologicalParameters)
        {

            textView = new TextView(PhysiologicalParameterTherapy.this);
            editText = new EditText(PhysiologicalParameterTherapy.this);

            textView.setText(physiologicalParameterViewModel.getPhysiological_parameter_name());
            editText.setEms(PreferencesData.PhysiologicalParameterTherapyValueSize);
            editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(PreferencesData.PhysiologicalParameterTherapyValueSize) });
            editText.setSingleLine(true);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editText.setGravity(Gravity.CENTER);
            grid.addView(textView);
            grid.addView(editText);
        }
    }


    private int getIdPhysiologicalParameter(String name)
    {
        for(PhysiologicalParameterViewModel physiologicalParameterTherapy: options )
        {
            if(physiologicalParameterTherapy.getPhysiological_parameter_name().equals(name)){
                return physiologicalParameterTherapy.getPhysiological_parameter_id();
            }
        }
        return 0;
    }

    private void getObjectTherapyMasterDetailViewModel()
    {
        Gson gson = new  Gson();
        sharedpreferences = getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);
        json= sharedpreferences.getString(PreferencesData.TherapyMasterDetailViewModel,"");
    }


    private void saveObjectTherapyMasterDetailViewModel()
    {
        Gson gson = new  Gson();
        json = gson.toJson(physiologicalParameterViewModelList);
        sharedpreferences = getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        if(physiologicalParameterAction.equals(PreferencesData.PhysiologicalParameterTherapySesionIN)) {
            editor.putString(PreferencesData.PhysiologicalParameterIn, json);
        }else
        {
            editor.putString(PreferencesData.PhysiologicalParameterOut, json);
        }
        editor.commit();
    }
}

