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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.PhysiologicalParameterApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.PhysiologicalParameterTherapyApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.OptionViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PhysiologicalParameterTherapyViewModel;
import com.rehapp.rehappmovil.rehapp.Models.QuestionaryOptionViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhysiologicalParameterTherapyFragment extends Fragment {

    private EditText editText;
    private TextView textView;
    private TextView linkToDetail;
    private Spinner spnOptions;
    private TextView tvTitle;
    private GridLayout grid;
    private GridLayout gridWithCategories;
    private String physiologicalParameterAction;
    private int therapyId;
    private String questionnaireOptionId;
    private int indexQuestionnaireOptionSelected;

    ArrayList<String> optionsNames;

    private Context mContext;
    View view;
    FragmentManager manager;

    private SharedPreferences sharedpreferences;

    ArrayList<QuestionaryOptionViewModel> options= new ArrayList<>();


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
        gridWithCategories=view.findViewById(R.id.gridWithCategories);
        tvTitle=view.findViewById(R.id.tvTitle);
        recoverySendData();
        LoadData();

        return view;
    }

    private void recoverySendData() {

        therapyId=sharedpreferences.getInt(PreferencesData.TherapyId,0);
        physiologicalParameterAction=sharedpreferences.getString(PreferencesData.PhysiologicalParameterAction,"");
        setTitle(physiologicalParameterAction);
    }

    private void setTitle(String action){
        String title=action.equals(PreferencesData.PhysiologicalParameterTherapySesionOUT)?getResources().getString(R.string.phisiologicalParametersOut):getResources().getString(R.string.phisiologicalParametersIn);
        tvTitle.setText(title);
    }

    public void LoadData() {
        loadPhysiologicalParameters();
    }

    private void loadPhysiologicalParameters() {
        Call<ArrayList<QuestionaryOptionViewModel>> call = PhysiologicalParameterApiAdapter.getApiService().getPhysiologicalParams();
        call.enqueue(new Callback<ArrayList<QuestionaryOptionViewModel>>() {
            @Override
            public void onResponse(Call<ArrayList<QuestionaryOptionViewModel>> call, Response<ArrayList<QuestionaryOptionViewModel>> response) {
                if (response.isSuccessful()) {
                    options = response.body();
                    addPhysiologicalParameters(options);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<QuestionaryOptionViewModel>> call, Throwable t) {
                String msg=PreferencesData.PhysiologicalParameterTherapyDataListFailed + " " +t.getMessage();
                Toast.makeText(mContext, msg , Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addPhysiologicalParametersTherapyToView(final ArrayList<QuestionaryOptionViewModel> physiologicalParameters) {

        Call<List<PhysiologicalParameterTherapyViewModel>> call = PhysiologicalParameterTherapyApiAdapter.getApiService().getPhysiologicalParamsTherapy(therapyId, physiologicalParameterAction);
        call.enqueue(new Callback<List<PhysiologicalParameterTherapyViewModel>>() {
            @Override
            public void onResponse(Call<List<PhysiologicalParameterTherapyViewModel>> call, Response<List<PhysiologicalParameterTherapyViewModel>> response) {
                if (response.isSuccessful()) {
                    List<PhysiologicalParameterTherapyViewModel> lPhysiologicalParametersTherapy = response.body();

                    for (PhysiologicalParameterTherapyViewModel physiologicalParameterTherapy : lPhysiologicalParametersTherapy) {
                        for (QuestionaryOptionViewModel physiologicalParameter : physiologicalParameters) {
                            if (Integer.parseInt(physiologicalParameter.getQuestionnaire_id()) == physiologicalParameterTherapy.getPhysio_param_id()) {
                                setPhysiolocalParametersToView(physiologicalParameter.getQuestionnaire_id(), physiologicalParameterTherapy.getPhysio_param_thrpy_value());
                            }
                        }
                    }

                } else if (response.raw().code() == 404) {
                    Toast.makeText(mContext, PreferencesData.therapyDetailPhysiologicalParameterTherapyEmptyListMsg, Toast.LENGTH_LONG).show();

                }
            }
            @Override
            public void onFailure(Call<List<PhysiologicalParameterTherapyViewModel>> call, Throwable t) {
                Toast.makeText(mContext, PreferencesData.therapyDetailPhysiologicalParameterTherapyEmptyListMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addPhysiologicalParameters(ArrayList<QuestionaryOptionViewModel> questionnaires){
        for (QuestionaryOptionViewModel questionnaire : questionnaires) {

            if(questionnaire.getOptions().size()>0){
                addPhysiologicalParametersWithCategoryView(questionnaire.getQuestionnaire_name(),questionnaire.getOptions());
            }else{
                addPhysiologicalParametersView(questionnaire.getQuestionnaire_name());
            }
        }
    }


    private void addPhysiologicalParametersView(String questionnaireName) {

                linkToDetail = new TextView(mContext);
                textView = new TextView(mContext);
                editText = new EditText(mContext);
                linkToDetail = new TextView(mContext);
                linkToDetail.setText("Ver observaciones.");
                textView.setText(questionnaireName);
                editText.setEms(PreferencesData.PhysiologicalParameterTherapyValueSize);
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(PreferencesData.PhysiologicalParameterTherapyValueSize)});
                editText.setSingleLine(true);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                editText.setGravity(Gravity.CENTER);
                linkToDetail.setGravity(Gravity.CENTER_VERTICAL);
                textView.setGravity(Gravity.CENTER_VERTICAL);

                grid.addView(textView);
                grid.addView(editText);
                grid.addView(linkToDetail);

    }

    private void addPhysiologicalParametersWithCategoryView(String questionnaireName,final ArrayList<OptionViewModel> optionsToShow) {

            textView = new TextView(mContext);
            editText = new EditText(mContext);
            linkToDetail = new TextView(mContext);
            spnOptions = new Spinner(mContext);
            spnOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    /**se le resta  uno porque se esta agregando una opción por defecto al spinner cuando se  llena**/
                    int selectedOption=position-1;
                    if(selectedOption>-1) {
                        questionnaireOptionId = options.get(selectedOption).getQuestionnaire_option_id();
                    }
                    indexQuestionnaireOptionSelected = selectedOption;
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            linkToDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadFragment(new PhysiologicalParameterTherapyOptionsFragment());
                }
            });
            spnOptions.setLayoutParams(new Spinner.LayoutParams(Spinner.LayoutParams.WRAP_CONTENT, Spinner.LayoutParams.WRAP_CONTENT));
            textView.setText(questionnaireName);
            linkToDetail.setText("Ver observaciones.");

            editText.setEms(PreferencesData.PhysiologicalParameterTherapyValueSize);
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(PreferencesData.PhysiologicalParameterTherapyValueSize)});
            editText.setSingleLine(true);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

            editText.setGravity(Gravity.CENTER);
            linkToDetail.setGravity(Gravity.CENTER_VERTICAL);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            spnOptions.setGravity(Gravity.CENTER_VERTICAL);

            loadOptions(optionsToShow);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, optionsNames);
            spnOptions.setAdapter(arrayAdapter);

            gridWithCategories.addView(textView);
            gridWithCategories.addView(editText);
            gridWithCategories.addView(spnOptions);
            gridWithCategories.addView(linkToDetail);

    }


    private void loadOptions(ArrayList<OptionViewModel> options){
        optionsNames= new ArrayList<>();
        optionsNames.add(getResources().getString(R.string.OptionAnswerSelected));
        for (OptionViewModel option:options) {
            optionsNames.add(option.getOption_name());
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
        for (QuestionaryOptionViewModel physiologicalParameterTherapy : options) {
            if (physiologicalParameterTherapy.getQuestionnaire_name().equals(name)) {
                return Integer.parseInt(physiologicalParameterTherapy.getQuestionnaire_option_id());
            }
        }
        return 0;
    }

    public void showHideItems(Menu menu) {
        MenuItem item;
        item = menu.findItem(R.id.save);
        item.setVisible(true);
        item = menu.findItem(R.id.create_therapy);
        item.setVisible(false);

    }

    private void goToTherapy(){
        loadFragment(new TherapyDetailFragment());
    }

    public void loadFragment(Fragment fragment){
        manager.beginTransaction().replace(R.id.content,fragment).commit();
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }



}
