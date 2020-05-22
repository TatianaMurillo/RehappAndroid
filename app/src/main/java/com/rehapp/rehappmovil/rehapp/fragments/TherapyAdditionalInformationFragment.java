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
import android.widget.TextView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.TherapyApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;
import com.rehapp.rehappmovil.rehapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TherapyAdditionalInformationFragment extends Fragment {

    EditText etAdditionalInformacion;
    TextView titleAdditionalInformation;
    private String therapyId;
    private Context mContext;
    View view;
    FragmentManager manager;

    private SharedPreferences sharedpreferences;

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
        view =inflater.inflate(R.layout.activity_therapy_additional_information_dialog,container,false);
        sharedpreferences=mContext.getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);

        etAdditionalInformacion = view.findViewById(R.id.etAdditionalInformacion);
        titleAdditionalInformation=view.findViewById(R.id.titleAdditionalInformation);
        recoverySendData();
        LoadData();
        setTitle();

        return view;
    }

    private void recoverySendData() {

        therapyId=String.valueOf(sharedpreferences.getInt(PreferencesData.TherapyId,0));
    }


    public void LoadData() {

        getAdditionalInformation();
    }

    private void getAdditionalInformation(){
        Call<TherapyViewModel> call = TherapyApiAdapter.getApiService().getTherapyAdditionalInformation(therapyId);
        call.enqueue(new Callback<TherapyViewModel>() {
            @Override
            public void onResponse(Call<TherapyViewModel> call, Response<TherapyViewModel> response) {
                if(response.isSuccessful()){
                    TherapyViewModel therapy=response.body();
                    etAdditionalInformacion.setText(therapy.getTherapy_observation()!=null?therapy.getTherapy_observation():"");
                }
            }
            @Override
            public void onFailure(Call<TherapyViewModel> call, Throwable t) {
                String error = "Error "+t.getMessage();
                Toast.makeText(mContext,  error, Toast.LENGTH_LONG).show();

            }
        });
    }

    public void addAdditionalInformation(){
        TherapyViewModel therapy = new TherapyViewModel();
        therapy.setTherapy_observation(etAdditionalInformacion.getText().toString());
        Call<TherapyViewModel> call = TherapyApiAdapter.getApiService().addTherapyAdditionalInformation(therapy,therapyId);
        call.enqueue(new Callback<TherapyViewModel>() {
            @Override
            public void onResponse(Call<TherapyViewModel> call, Response<TherapyViewModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(mContext, PreferencesData.therapyAddAdditionalInformationSuccessMsg, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(mContext, PreferencesData.therapyAddAdditionalInformationFailedMsg, Toast.LENGTH_LONG).show();
                }
                goToTherapy();

            }
            @Override
            public void onFailure(Call<TherapyViewModel> call, Throwable t) {
                String error = "Error "+t.getMessage();
                Toast.makeText(mContext,  error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setTitle() {
        titleAdditionalInformation.setText(getResources().getString(R.string.additionalInfo));
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
                addAdditionalInformation();
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
