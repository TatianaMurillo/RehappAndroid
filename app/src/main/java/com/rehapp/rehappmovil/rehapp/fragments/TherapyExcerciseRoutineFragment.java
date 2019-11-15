package com.rehapp.rehappmovil.rehapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.R;
import com.rehapp.rehappmovil.rehapp.YoutubeVideo;

public class TherapyExcerciseRoutineFragment extends Fragment {


    private Context mContext;
    View view;
    FragmentManager manager;
    private SharedPreferences sharedpreferences;
    TextView tvExerciseVideo;

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
        view = inflater.inflate(R.layout.activity_therapy_exercise_detail, container, false);
        sharedpreferences = mContext.getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);

        tvExerciseVideo=view.findViewById(R.id.tvExerciseVideo);

        tvExerciseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                watchVideo();
            }
        });
        return  view;
    }

    public void watchVideo() {

        Intent intent = new Intent(mContext, YoutubeVideo.class);
        startActivity(intent);

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
        }



        return super.onOptionsItemSelected(item);
    }

    public void showHideItems(Menu menu) {
        MenuItem item;

        String action=sharedpreferences.getString(PreferencesData.TherapyAction,"");

        if(action.equals("DETAIL")) {
            item = menu.findItem(R.id.create_therapy);
            item.setVisible(false);
        }else
        {
            item = menu.findItem(R.id.save);
            item.setVisible(false);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }
}
