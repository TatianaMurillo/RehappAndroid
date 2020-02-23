package com.rehapp.rehappmovil.rehapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.ExerciseRoutineApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.ExerciseRoutinesViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.R;
import com.rehapp.rehappmovil.rehapp.VideoAdapter;
import com.rehapp.rehappmovil.rehapp.YoutubeVideo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RoutinesFragment extends Fragment implements VideoAdapter.OnVideoClickListener{

    RecyclerView recyclerView;
    SharedPreferences sharedpreferences;
    private Context mContext;
    View view;
    FragmentManager manager;
    VideoAdapter adapter;
    private ArrayList<ExerciseRoutinesViewModel> exercises = new ArrayList<>();

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
        view =inflater.inflate(R.layout.list_routines,container,false);
        sharedpreferences =mContext.getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);
        adapter=null;

        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        listExercisesRoutines();
        return view;
    }

    private void listExercisesRoutines()
    {
            Call<ArrayList<ExerciseRoutinesViewModel>> call = ExerciseRoutineApiAdapter.getApiService().getExerciseRoutines();
            call.enqueue(new Callback<ArrayList<ExerciseRoutinesViewModel>>() {
                @Override
                public void onResponse(Call<ArrayList<ExerciseRoutinesViewModel>> call, Response<ArrayList<ExerciseRoutinesViewModel>> response) {
                    if (response.isSuccessful()) {
                        exercises = response.body();
                        setHtmlFromRoutineList(exercises);
                        adapter = new VideoAdapter(getActivity(), exercises, getFragmentManager(), RoutinesFragment.this);
                        recyclerView.setAdapter(adapter);

                    } else {
                        Toast.makeText(mContext, PreferencesData.therapyDetailExerciseRoutineListMsg, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<ExerciseRoutinesViewModel>> call, Throwable t) {
                    Toast.makeText(mContext, PreferencesData.therapyDetailExerciseRoutineListMsg.concat(t.getMessage()), Toast.LENGTH_LONG).show();
                }
            });
        }


    private void setHtmlFromRoutineList(ArrayList<ExerciseRoutinesViewModel> exercisesParam){
        String html;
        for (ExerciseRoutinesViewModel exercise:exercisesParam ) {
            html=PreferencesData.html.replace(PreferencesData.htmlTagToReplace,exercise.getExercise_routine_url());
            exercises.get(exercises.indexOf(exercise)).setHtml(html);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        showHideItems(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case  R.id.save:
                break;
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    public void showHideItems(Menu menu)
    {
        MenuItem item;
        item= menu.findItem(R.id.create_therapy);
        item.setVisible(false);
        item= menu.findItem(R.id.save);
        item.setVisible(false);
    }


    public void loadFragment(Fragment fragment){
        manager.beginTransaction().replace(R.id.content,fragment).commit();
    }

    private  void storeStringSharepreferences(String key, String value){

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();

    }
    private  void storeIntSharepreferences(String key, int value){

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(key, value);
        editor.commit();

    }

    @Override
    public void onVideoClick(int position,int option) {
        switch (option){
            case 1:
                    int routineId= exercises.get(position).getExercise_routine_id();
                    storeIntSharepreferences(PreferencesData.ExerciseRoutineId, routineId);
                    loadFragment(new RoutineDetailFragment());
                break;
            case 2:
                redirectToVideo(exercises.get(position).getExerciseUrl());
                break;
        }
    }

    private void redirectToVideo(String url){
        Bundle extras = new Bundle();
        extras.putString(PreferencesData.ExerciseRoutineUrl,url);
        Intent intent = new Intent(mContext, YoutubeVideo.class);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
