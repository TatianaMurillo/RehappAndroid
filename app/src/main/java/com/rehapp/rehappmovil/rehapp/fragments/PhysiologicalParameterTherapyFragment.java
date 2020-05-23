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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.PhysiologicalParameterApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.PhysiologicalParameterViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.PhysiologicalParameterAdapter;
import com.rehapp.rehappmovil.rehapp.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhysiologicalParameterTherapyFragment extends Fragment {


    private String physiologicalParameterAction;
    private int therapyId;
    private ListView lvPhysiologicalParameter;
    private Context mContext;
    View view;
    TextView tvTitle;
    FragmentManager manager;
    PhysiologicalParameterAdapter adapter;
    private SharedPreferences sharedpreferences;

    ArrayList<PhysiologicalParameterViewModel> parameters= new ArrayList<>();


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
        view =inflater.inflate(R.layout.list_physiological_parameters,container,false);
        sharedpreferences=mContext.getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);
        tvTitle=view.findViewById(R.id.tvTitle);
        lvPhysiologicalParameter=view.findViewById(R.id.lvPhysiologicalParameter);
        adapter=null;
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
                    parameters = response.body();
                    adapter = new PhysiologicalParameterAdapter(getActivity(),parameters,getFragmentManager());
                    lvPhysiologicalParameter.setAdapter(adapter);

                    lvPhysiologicalParameter.setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            PhysiologicalParameterTherapyDetailFragment fragment=new PhysiologicalParameterTherapyDetailFragment();
                            Bundle extras = new Bundle();
                            extras.putString(PreferencesData.ParameterName, parameters.get(position).getPhysiological_parameter_name());
                            fragment.setArguments(extras);
                            loadFragment(fragment);
                        }
                    });

                } else {
                    Toast.makeText(mContext, PreferencesData.therapyDetailExerciseRoutineListMsg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PhysiologicalParameterViewModel>> call, Throwable t) {
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
