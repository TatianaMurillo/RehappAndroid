package com.rehapp.rehappmovil.rehapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.VitalSignApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.PhysiologicalParameterViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.VitalSignViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VitalSigns extends AppCompatActivity {

    private GridLayout grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vital_signs);


        grid = findViewById(R.id.grid);

        loadVitalSigns();

    }

    private void loadVitalSigns() {
        Call<List<VitalSignViewModel>> call = VitalSignApiAdapter.getApiService().getVitalSigns();
        call.enqueue(new Callback<List<VitalSignViewModel>>() {
            @Override
            public void onResponse(Call<List<VitalSignViewModel>> call, Response<List<VitalSignViewModel>> response) {
                if (response.isSuccessful()) {
                    List<VitalSignViewModel> vitalSigns = response.body();

                    addVitalSignsToView(vitalSigns);
                }
            }

            @Override
            public void onFailure(Call<List<VitalSignViewModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), PreferencesData.vitalSignsLoadFailed, Toast.LENGTH_LONG).show();
            }
        });
    }


    private void addVitalSignsToView( List<VitalSignViewModel> vitalSigns) {
        EditText editText;
        TextView textView;

        for(VitalSignViewModel vitalSign : vitalSigns)
        {

            textView = new TextView(getApplicationContext());
            editText = new EditText(getApplicationContext());

            textView.setText(vitalSign.getVital_sign_name());
            editText.setEms(PreferencesData.VitalSignsTherapyValueSize);
            editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(PreferencesData.VitalSignsTherapyValueSize) });
            editText.setSingleLine(true);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            //editText.setGravity(Gravity.CENTER);
            grid.addView(textView);
            grid.addView(editText);
        }
    }

    private  void setVitalSignsToView(String vitalSignName,String VitalSignValue) {

        int childCount = grid.getChildCount();
        String valueTextView;
        Object childEditText;
        Object childTextView;
        TextView textView;
        EditText editText;

        for (int i = 1; i < childCount; i += 2) {
            childEditText = grid.getChildAt(i);
            childTextView = grid.getChildAt(i - 1);

            textView = (TextView) childTextView;
            valueTextView = textView.getText().toString().trim();
            editText = (EditText) childEditText;

            if(vitalSignName.equals(valueTextView)) {
                editText.setText(VitalSignValue);
            }
        }
    }

}
