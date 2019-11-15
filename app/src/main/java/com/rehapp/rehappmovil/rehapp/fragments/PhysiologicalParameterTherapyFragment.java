package com.rehapp.rehappmovil.rehapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
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

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.PhysiologicalParameterApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.PhysiologicalParameterTherapyApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.PhysiologicalParameterTherapyViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PhysiologicalParameterViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.R;
import com.rehapp.rehappmovil.rehapp.TherapyDetail;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhysiologicalParameterTherapyFragment extends Fragment {

    private EditText editText;
    private TextView textView;
    private GridLayout grid;
    private String physiologicalParameterAction;
    private int therapyId;
    Dialog d;

    private Context mContext;
    View view;
    FragmentManager manager;

    private SharedPreferences sharedpreferences;

    ArrayList<PhysiologicalParameterViewModel> options= new ArrayList<>();


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
        view =inflater.inflate(R.layout.activity_physiological_parameter_therapy,container,false);
        sharedpreferences=mContext.getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);

        grid = view.findViewById(R.id.grid);

        recoverySendData();
        LoadData();

        return view;
    }

    private void recoverySendData() {

        therapyId=sharedpreferences.getInt(PreferencesData.TherapyId,0);
        physiologicalParameterAction=sharedpreferences.getString(PreferencesData.PhysiologicalParameterAction,"");
    }

    public void LoadData() {
        loadPhysiologicalParameters();
    }

    private void loadPhysiologicalParameters() {
        Call<ArrayList<PhysiologicalParameterViewModel>> call = PhysiologicalParameterApiAdapter.getApiService().getPhysiologicalParams();
        call.enqueue(new Callback<ArrayList<PhysiologicalParameterViewModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PhysiologicalParameterViewModel>> call, Response<ArrayList<PhysiologicalParameterViewModel>> response) {
                if (response.isSuccessful()) {
                    options = response.body();
                    addPhysiologicalParametersView(options);
                    addPhysiologicalParametersTherapyToView(options);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PhysiologicalParameterViewModel>> call, Throwable t) {
                String msg=PreferencesData.PhysiologicalParameterTherapyDataListFailed + " " +t.getMessage();
                Toast.makeText(mContext, msg , Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addPhysiologicalParametersTherapyToView(final ArrayList<PhysiologicalParameterViewModel> physiologicalParameters) {

        Call<List<PhysiologicalParameterTherapyViewModel>> call = PhysiologicalParameterTherapyApiAdapter.getApiService().getPhysiologicalParamsTherapy(therapyId, physiologicalParameterAction);
        call.enqueue(new Callback<List<PhysiologicalParameterTherapyViewModel>>() {
            @Override
            public void onResponse(Call<List<PhysiologicalParameterTherapyViewModel>> call, Response<List<PhysiologicalParameterTherapyViewModel>> response) {
                if (response.isSuccessful()) {
                    List<PhysiologicalParameterTherapyViewModel> lPhysiologicalParametersTherapy = response.body();

                    for (PhysiologicalParameterTherapyViewModel physiologicalParameterTherapy : lPhysiologicalParametersTherapy) {
                        for (PhysiologicalParameterViewModel physiologicalParameter : physiologicalParameters) {
                            if (physiologicalParameter.getPhysiological_parameter_id() == physiologicalParameterTherapy.getPhysio_param_id()) {
                                setPhysiolocalParametersToView(physiologicalParameter.getPhysiological_parameter_name(), physiologicalParameterTherapy.getPhysio_param_thrpy_value());
                            }
                        }
                    }

                } else if (response.raw().code() == 404) {
                    System.out.println("No se encontraron parametros fisiologicos para la terapia.");
                }
            }

            @Override
            public void onFailure(Call<List<PhysiologicalParameterTherapyViewModel>> call, Throwable t) {
                Toast.makeText(mContext, PreferencesData.therapyDetailPhysiologicalParameterTherapyEmptyListMsg, Toast.LENGTH_LONG).show();
            }
        });


    }

    private void addPhysiologicalParametersView(ArrayList<PhysiologicalParameterViewModel> physiologicalParameters) {
        for (PhysiologicalParameterViewModel physiologicalParameterViewModel : physiologicalParameters) {

            textView = new TextView(mContext);
            editText = new EditText(mContext);

            textView.setText(physiologicalParameterViewModel.getPhysiological_parameter_name());
            editText.setEms(PreferencesData.PhysiologicalParameterTherapyValueSize);
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(PreferencesData.PhysiologicalParameterTherapyValueSize)});
            editText.setSingleLine(true);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            editText.setGravity(Gravity.CENTER);
            grid.addView(textView);
            grid.addView(editText);
        }
    }

    private void setPhysiolocalParametersToView(String physiologicalParameterName, String physiologicalParameterValue) {

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

            if (physiologicalParameterName.equals(valueTextView)) {
                editText.setText(physiologicalParameterValue);
            }
        }

    }

    private List<PhysiologicalParameterTherapyViewModel> setPhysiolocalParametersFromView() {

        List<PhysiologicalParameterTherapyViewModel> data = new ArrayList<>();

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
            data.add(new PhysiologicalParameterTherapyViewModel(0, getIdPhysiologicalParameter(valueTextView), therapyId, valueEditText, physiologicalParameterAction));

        }

        return data;

    }

    private void savePhysiologicalParameterTherapy() {

        List<PhysiologicalParameterTherapyViewModel> data = setPhysiolocalParametersFromView();

        Call<List<PhysiologicalParameterTherapyViewModel>> call = PhysiologicalParameterTherapyApiAdapter.getApiService().savePhysiologicalParamsTherapy(data, therapyId, physiologicalParameterAction);
        call.enqueue(new Callback<List<PhysiologicalParameterTherapyViewModel>>() {
            @Override
            public void onResponse(Call<List<PhysiologicalParameterTherapyViewModel>> call, Response<List<PhysiologicalParameterTherapyViewModel>> response) {
                if (response.isSuccessful()) {
                    String mgs = response.body().size() + " " + PreferencesData.PhysiologicalParameterTherapySuccessMgs;
                    Toast.makeText(mContext, mgs, Toast.LENGTH_LONG).show();
                    goToTherapy();
                }
            }

            @Override
            public void onFailure(Call<List<PhysiologicalParameterTherapyViewModel>> call, Throwable t) {
                String msg = PreferencesData.PhysiologicalParameterTherapyDataMgsError+"\n"+t.getMessage();
                Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
                goToTherapy();

            }
        });
    }

    private int getIdPhysiologicalParameter(String name) {
        for (PhysiologicalParameterViewModel physiologicalParameterTherapy : options) {
            if (physiologicalParameterTherapy.getPhysiological_parameter_name().equals(name)) {
                return physiologicalParameterTherapy.getPhysiological_parameter_id();
            }
        }
        return 0;
    }

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
                savePhysiologicalParameterTherapy();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void showHideItems(Menu menu) {
        MenuItem item;
        item = menu.findItem(R.id.save);
        item.setVisible(true);
        item = menu.findItem(R.id.create_therapy);
        item.setVisible(false);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }


    private void goToTherapy(){
        loadFragment(new TherapyDetailFragment());
    }

    public void loadFragment(Fragment fragment){
        manager.beginTransaction().replace(R.id.content,fragment).commit();
    }


}
