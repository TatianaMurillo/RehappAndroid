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
import android.widget.TextView;

import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.QuestionaryOptionViewModel;
import com.rehapp.rehappmovil.rehapp.R;

import java.util.ArrayList;

public class PhysiologicalParameterTherapyDetailFragment extends Fragment {

    private TextView tvObservations;
    private TextView tvOptionTitle;
    private String physiologicalParameterAction;
    private Context mContext;
    View view;
    private int therapyId;
    FragmentManager manager;
    TextView tvTitle;
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
        view =inflater.inflate(R.layout.activity_physiological_parameter_therapy_detail,container,false);
        sharedpreferences=mContext.getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);
        tvTitle=view.findViewById(R.id.tvTitle);
        tvOptionTitle = view.findViewById(R.id.tvOptionTitle);
        tvObservations=view.findViewById(R.id.tvObservations);
        recoverySendData();

        return view;
    }

    private void recoverySendData() {
        if( getArguments()!=null)
        {
            Bundle extras = getArguments();
            tvOptionTitle.setText(extras.getString(PreferencesData.ParameterName));

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
