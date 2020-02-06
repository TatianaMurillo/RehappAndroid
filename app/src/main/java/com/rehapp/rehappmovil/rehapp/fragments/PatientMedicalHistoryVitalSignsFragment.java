package com.rehapp.rehappmovil.rehapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.VitalSignApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.PatientMedicalHistoryVitalSignViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.VitalSignViewModel;
import com.rehapp.rehappmovil.rehapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientMedicalHistoryVitalSignsFragment extends Fragment {

    GridLayout grid;
    String medicalHistorySelectedId;
    ArrayList<VitalSignViewModel> vitalSigns;
    View view;
    FragmentManager manager;
    private SharedPreferences sharedpreferences;
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        manager = this.getFragmentManager();
        view =inflater.inflate(R.layout.activity_vital_sign_patient,container,false);
        sharedpreferences=mContext.getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);

        grid = view.findViewById(R.id.grid);

        recoverySendData();
        loadVitalSigns();


        return view;
    }

    private void loadVitalSigns() {
        Call<ArrayList<VitalSignViewModel>> call = VitalSignApiAdapter.getApiService().getVitalSignsByMedicalHistory2(medicalHistorySelectedId);
        call.enqueue(new Callback<ArrayList<VitalSignViewModel>>() {
            @Override
            public void onResponse(Call<ArrayList<VitalSignViewModel>> call, Response<ArrayList<VitalSignViewModel>> response) {
                if (response.isSuccessful()) {
                   vitalSigns = response.body();
                    addVitalSignsToView(vitalSigns);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<VitalSignViewModel>> call, Throwable t) {
                Toast.makeText(mContext, PreferencesData.vitalSignsLoadFailed, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void recoverySendData() {
        medicalHistorySelectedId =sharedpreferences.getString(PreferencesData.MedicalHistorySelectedId,"");
    }


    /**
     *
     * Una vez  consultados los signos vitales de la base de datos,
     * Estos se cargan a la vista.
     *
     */

    private void addVitalSignsToView( ArrayList<VitalSignViewModel> vitalSigns) {
        EditText editText;
        TextView textView;

        for(VitalSignViewModel vitalSign : vitalSigns)
        {

            textView = new TextView(mContext);
            editText = new EditText(mContext);

            textView.setText(vitalSign.getVital_sign_name());
            editText.setEms(PreferencesData.VitalSignsTherapyValueSize);
            editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(PreferencesData.VitalSignsTherapyValueSize) });
            editText.setSingleLine(true);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editText.setGravity(Gravity.CENTER);
            if(vitalSign.getVital_sign_value()!=null){
                editText.setText(vitalSign.getVital_sign_value());
            }
            grid.addView(textView);
            grid.addView(editText);
        }
    }


    /**
     *
     * Se obtienen los signos vitales de la vista para guardarlos en la BD
     *
     */
    private List<PatientMedicalHistoryVitalSignViewModel> getVitalSignsFromView() {

        List<PatientMedicalHistoryVitalSignViewModel> data = new ArrayList<>();
        PatientMedicalHistoryVitalSignViewModel patientMedicalHistoryQuestionaireOption;
        int childCount = grid.getChildCount();
        String valueEditText;
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
            valueEditText = editText.getText().toString().trim();

            patientMedicalHistoryQuestionaireOption=  new PatientMedicalHistoryVitalSignViewModel();
            patientMedicalHistoryQuestionaireOption.setPtnt_mdcl_hstry_id(medicalHistorySelectedId);
            patientMedicalHistoryQuestionaireOption.setVital_sign_id(getIdVitalSign(valueTextView));
            patientMedicalHistoryQuestionaireOption.setPatient_vital_signs_value(valueEditText);
            data.add(patientMedicalHistoryQuestionaireOption);

        }

        return data;
    }

    /**
     *
     * Guardar signos vitales de la historia  medica
     *
     */
    private void saveMedicalHistoryVitalSigns() {

        List<PatientMedicalHistoryVitalSignViewModel> data = getVitalSignsFromView();

        Call<List<PatientMedicalHistoryVitalSignViewModel>> call = VitalSignApiAdapter.getApiService().saveMedicalHistoryVitalSigns(data,medicalHistorySelectedId);
        call.enqueue(new Callback<List<PatientMedicalHistoryVitalSignViewModel>>() {
            @Override
            public void onResponse(Call<List<PatientMedicalHistoryVitalSignViewModel>> call, Response<List<PatientMedicalHistoryVitalSignViewModel>> response) {
                if (response.isSuccessful()) {
                    String mgs = response.body().size() + " " + PreferencesData.MedicalHistoryVitalSignsSuccessMgs;
                    Toast.makeText(mContext, mgs, Toast.LENGTH_LONG).show();
                    goToMedicalHistoryDetail();
                }else if(response.raw().code()!=200)
                {
                    Toast.makeText(mContext, response.raw().message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<PatientMedicalHistoryVitalSignViewModel>> call, Throwable t) {
                String msg = PreferencesData.MedicalHistoryVitalSignsDataMgsError+"\n"+t.getMessage();
                Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
                goToMedicalHistoryDetail();

            }
        });
    }


    /**
     *
     * Eventos
     *
     */

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        showHideItems(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.save:
                saveMedicalHistoryVitalSigns();
                break;
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }


    /**
     *
     * Metodos utiles
     *
     */
    private String getIdVitalSign(String name) {
        for (VitalSignViewModel vitalSign : vitalSigns) {
            if (vitalSign.getVital_sign_name().equals(name)) {
                return vitalSign.getVital_sign_id();
            }
        }
        return "";
    }

    private void goToMedicalHistoryDetail(){
        MedicalHistoryDetailFragment fragment = new MedicalHistoryDetailFragment();
        loadFragment(fragment);
    }

    public void showHideItems(Menu menu) {
        MenuItem item;
        item = menu.findItem(R.id.save);
        item.setVisible(true);
        item = menu.findItem(R.id.create_therapy);
        item.setVisible(false);

    }

    public void loadFragment(Fragment fragment){
        manager.beginTransaction().replace(R.id.content,fragment).commit();
    }





}
