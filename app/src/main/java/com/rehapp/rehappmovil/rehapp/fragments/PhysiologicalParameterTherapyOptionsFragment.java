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
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.PhysiologicalParameterApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.QuestionaryOptionViewModel;
import com.rehapp.rehappmovil.rehapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhysiologicalParameterTherapyOptionsFragment extends Fragment {

    private TextView tvObservations;
    private TextView tvOptionTitle;

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
        view =inflater.inflate(R.layout.activity_physiological_parameter_therapy_options,container,false);
        sharedpreferences=mContext.getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);

        tvOptionTitle = view.findViewById(R.id.tvOptionTitle);
        tvObservations=view.findViewById(R.id.tvObservations);
        recoverySendData();
        LoadData();

        return view;
    }

    private void recoverySendData() {


    }

    private void setTitle(String action){
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
                }
            }

            @Override
            public void onFailure(Call<ArrayList<QuestionaryOptionViewModel>> call, Throwable t) {
                String msg=PreferencesData.PhysiologicalParameterTherapyDataListFailed + " " +t.getMessage();
                Toast.makeText(mContext, msg , Toast.LENGTH_LONG).show();
            }
        });
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
