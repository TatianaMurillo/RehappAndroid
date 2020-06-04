package com.rehapp.rehappmovil.rehapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.PhysiologicalParameterTherapyApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.PhysiologicalParameterTherapyViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.QuestionaryOptionViewModel;
import com.rehapp.rehappmovil.rehapp.R;
import com.rehapp.rehappmovil.rehapp.Utils.DataValidation;
import com.rehapp.rehappmovil.rehapp.Utils.ValidateInputs;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhysiologicalParameterTherapyDetailFragment extends Fragment {

    private TextView tvOptionTitle;
    private String physiologicalParameterAction;
    private Context mContext;
    View view;
    private int therapyId;
    private String paramId;
    EditText etObservations;
    EditText etValue;
    TableLayout tlCategory;
    TableLayout tlValue;
    TextView tvUnitOfMeasure;
    Spinner spnCategories;
    String categorySelectedId;
    int indexCategorySelectedId=-1;;

    FragmentManager manager;
    TextView tvTitle;
    private SharedPreferences sharedpreferences;
    ArrayList<String> optionNames= new ArrayList<>();
    ArrayList<QuestionaryOptionViewModel> options= new ArrayList<>();
    PhysiologicalParameterTherapyViewModel paramDetail;


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
        view =inflater.inflate(R.layout.activity_physiological_parameter_therapy_detail,container,false);
        sharedpreferences=mContext.getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);
        tvTitle=view.findViewById(R.id.tvTitle);
        tvOptionTitle = view.findViewById(R.id.tvOptionTitle);
        etObservations=view.findViewById(R.id.etObservations);
        etValue=view.findViewById(R.id.etValue);
        tlCategory=view.findViewById(R.id.tlCategory);
        tlValue=view.findViewById(R.id.tlValue);
        tvUnitOfMeasure=view.findViewById(R.id.tvUnitOfMeasure);
        spnCategories=view.findViewById(R.id.spnCategories);
        recoverySendData();
        loadPhysiologicalParameterTherapy();

        spnCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                /**se le resta  dos porque se esta agregando una opción por defecto al spinner cuando se  llena**/
                int selectedOption=position-1;
                if(selectedOption>-1) {
                    categorySelectedId = options.get(selectedOption).getQuestionnaire_option_id();
                }
                indexCategorySelectedId = selectedOption;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        return view;
    }

    private void recoverySendData() {
        if( getArguments()!=null)
        {
            Bundle extras = getArguments();
            tvOptionTitle.setText(extras.getString(PreferencesData.ParameterName));
            paramId=extras.getString(PreferencesData.ParameterId);

        }
        therapyId=sharedpreferences.getInt(PreferencesData.TherapyId,0);
        physiologicalParameterAction=sharedpreferences.getString(PreferencesData.PhysiologicalParameterAction,"");
        setTitle(physiologicalParameterAction);

    }

    private void setTitle(String action){
        String title=action.equals(PreferencesData.PhysiologicalParameterTherapySesionOUT)?getResources().getString(R.string.phisiologicalParametersOut):getResources().getString(R.string.phisiologicalParametersIn);
        tvTitle.setText(title);
    }


    public void showHideItems(Menu menu) {
        MenuItem item;
        item = menu.findItem(R.id.save);
        item.setVisible(true);
        item = menu.findItem(R.id.create_therapy);
        item.setVisible(false);

    }

    private void goToParameterList(){
        loadFragment(new PhysiologicalParameterTherapyFragment());
    }

    public void loadFragment(Fragment fragment){
        manager.beginTransaction().replace(R.id.content,fragment).commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        showHideItems(menu);
    }

    public void loadPhysiologicalParameterTherapy(){

        Call<PhysiologicalParameterTherapyViewModel> call = PhysiologicalParameterTherapyApiAdapter.getApiService().getPhysiologicalParamsTherapy(paramId,therapyId,physiologicalParameterAction);
        call.enqueue(new Callback<PhysiologicalParameterTherapyViewModel>() {
            @Override
            public void onResponse(Call<PhysiologicalParameterTherapyViewModel> call, Response<PhysiologicalParameterTherapyViewModel> response) {
                if (response.isSuccessful()) {
                    paramDetail=response.body();
                    String value=paramDetail.getPhysio_param_thrpy_value()!=null?paramDetail.getPhysio_param_thrpy_value():"";
                    String unitOfMeasue=paramDetail.getUnitOfMeasure()!=null?paramDetail.getUnitOfMeasure():"";
                    String observation=paramDetail.getObservations()!=null?paramDetail.getObservations():"";
                    etValue.setText(value);
                    tvUnitOfMeasure.setText(unitOfMeasue);
                    etObservations.setText(observation);
                    options = paramDetail.getOptions();
                    loadOptions();
                }else{
                    Toast.makeText(mContext,response.raw().message(),Toast.LENGTH_LONG).show();
                    goToParameterList();
                }
            }

            @Override
            public void onFailure(Call<PhysiologicalParameterTherapyViewModel> call, Throwable t) {
                String msg = PreferencesData.PhysiologicalParameterTherapyDetailLoadDataError.concat(" ").concat(t.getMessage());
                Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show();
                goToParameterList();
            }
        });
    }

    public void loadOptions(){

        if(options.size()>0){
            optionNames.add(getResources().getString(R.string.NonSelected));
            for(QuestionaryOptionViewModel option : options) {
                optionNames.add(option.getOption_name());
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mContext,android.R.layout.simple_list_item_1,optionNames);
            spnCategories.setAdapter(arrayAdapter);
            selectOption(paramDetail.getQuestionnaire_option_id());
            tlCategory.setVisibility(View.VISIBLE);
        }
    }

    private void selectOption(String questionnaireOptionId){
        int indexOfOption=-1;
        for(QuestionaryOptionViewModel option : options)
        {
            if (option.getQuestionnaire_option_id().equals(questionnaireOptionId)) {
                /**se le suma  uno porque se esta agregando una opciòn por defecto al spinner cuando se  llena**/
                indexOfOption = options.indexOf(option)+1;
            }
        }
        if (indexOfOption != -1) {
            spnCategories.setSelection(indexOfOption);
        } else {
            /**se selecciona al menos la opcion que se crea por defecto**/
            spnCategories.setSelection(0);
        }
    }

    public void savePhysiologicalParameterTherapy(){

        List<Object> rpta = validateInputData();
        boolean checked = Boolean.parseBoolean(rpta.get(0).toString());
        String  msg = rpta.get(1).toString();
        if(checked) {
            PhysiologicalParameterTherapyViewModel object=  getParamDetailFromView();

            Call<PhysiologicalParameterTherapyViewModel> call = PhysiologicalParameterTherapyApiAdapter.getApiService().savePhysiologicalParamsTherapy(object);
            call.enqueue(new Callback<PhysiologicalParameterTherapyViewModel>() {
                @Override
                public void onResponse(Call<PhysiologicalParameterTherapyViewModel> call, Response<PhysiologicalParameterTherapyViewModel> response) {
                    Toast.makeText(mContext, PreferencesData.PhysiologicalParameterTherapySuccessMgs,Toast.LENGTH_LONG).show();
                    goToParameterList();
                }

                @Override
                public void onFailure(Call<PhysiologicalParameterTherapyViewModel> call, Throwable t) {
                    Toast.makeText(mContext, PreferencesData.PhysiologicalParameterTherapyDataMgsError,Toast.LENGTH_LONG).show();
                    goToParameterList();
                }
            });
        }else{
            Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
        }



    }

    private PhysiologicalParameterTherapyViewModel getParamDetailFromView() {

        PhysiologicalParameterTherapyViewModel param = new PhysiologicalParameterTherapyViewModel();

        param.setPhysio_param_id(Integer.parseInt(paramId));
        param.setTherapy_id(therapyId);
        param.setPhysio_param_thrpy_value(etValue.getText().toString());
        param.setPhysio_param_thrpy_in_out(physiologicalParameterAction);
        param.setObservations(etObservations.getText().toString());

        if(indexCategorySelectedId>-1){
            param.setQuestionnaire_option_id(categorySelectedId);
        }

        return param;
    }

    private List<Object> validateInputData() {

        ArrayList<DataValidation>  list= new ArrayList<>();

        list.add(new DataValidation(etObservations.getText().toString(),"Observaciones.").textMaxLength(1000));
        list.add(new DataValidation(etValue.getText().toString(),"Valor de parametro.").textMaxLength(5));

        list.add(new DataValidation(paramId,"Identificador de parámetro.").noEmptyValue().notZeroValue().selectedValue());
        list.add(new DataValidation(String.valueOf(therapyId),"Identificador de terapia.").noEmptyValue().notZeroValue().selectedValue());
        list.add(new DataValidation(physiologicalParameterAction,"Parámetro entrada/salida").noEmptyValue().textMaxLength(3));


        return  ValidateInputs.validate().ValidateDataObject(list);
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
