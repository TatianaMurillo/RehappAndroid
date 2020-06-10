package com.rehapp.rehappmovil.rehapp.fragments;

import android.content.Context;
import android.content.Intent;
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
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.ExerciseRoutineApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.TherapyExerciseRoutineApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.ExerciseRoutinesViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapyExcerciseRoutineViewModel;
import com.rehapp.rehappmovil.rehapp.R;
import com.rehapp.rehappmovil.rehapp.Utils.ValidateInputs;
import com.rehapp.rehappmovil.rehapp.VideoAdapter;
import com.rehapp.rehappmovil.rehapp.YoutubeVideo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RoutineDetailFragment extends Fragment {


    SharedPreferences sharedpreferences;
    private Context mContext;
    View view;
    FragmentManager manager;
    int exerciseRoutineId;

    TextView tvFullScreenVideo;
    TextView tvExerciseVideo;
    EditText etExerciseSpeed;
    EditText etExerciseFrequent;
    EditText etExerciseIntensity;
    EditText etDuration;
    EditText etPreConditions;
    VideoAdapter adapter;
    EditText etObservations;
    WebView webView;

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
        view =inflater.inflate(R.layout.activity_routine_detail,container,false);
        sharedpreferences =mContext.getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);
        adapter=null;

        tvExerciseVideo=view.findViewById(R.id.tvExerciseVideo);
        etExerciseSpeed=view.findViewById(R.id.etExerciseSpeed);
        etExerciseFrequent=view.findViewById(R.id.etExerciseFrequent);
        etExerciseIntensity=view.findViewById(R.id.etExerciseIntensity);
        etPreConditions=view.findViewById(R.id.etPreConditions);
        etObservations=view.findViewById(R.id.etObservations);
        etDuration=view.findViewById(R.id.etDuration);
        webView=view.findViewById(R.id.webView);
        tvFullScreenVideo=view.findViewById(R.id.tvFullScreenVideo);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);

        tvFullScreenVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    redirectToVideo(sharedpreferences.getString(PreferencesData.ExerciseRoutineUrl,""));
                }
            }
        );
        recoverySendData();
        searchRoutineDetail();

        return view;
    }

    private void recoverySendData() {
        exerciseRoutineId=sharedpreferences.getInt(PreferencesData.ExerciseRoutineId,0);
    }

    private void searchRoutineDetail(){
        Call<ExerciseRoutinesViewModel> call = ExerciseRoutineApiAdapter.getApiService().getExerciseRoutine(exerciseRoutineId);
        call.enqueue(new Callback<ExerciseRoutinesViewModel>() {
            @Override
            public void onResponse(Call<ExerciseRoutinesViewModel> call, Response<ExerciseRoutinesViewModel> response) {

                if(response.isSuccessful()) {
                    ExerciseRoutinesViewModel exerciseRoutine=response.body();
                    storeStringSharepreferences(PreferencesData.ExerciseRoutineUrl,exerciseRoutine.getExercise_routine_url());
                    webView.loadData(setHtmlFromRoutineList(exerciseRoutine),"text/html","utf-8");
                    webView.setWebViewClient(new WebViewClient(){
                        @Override
                        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                            super.onReceivedError(view, request, error);

                            webView.loadUrl("file:///android_asset/error.html");
                        }
                    });
                    setDataToView(exerciseRoutine);
                }else{

                }
            }

            @Override
            public void onFailure(Call<ExerciseRoutinesViewModel> call, Throwable t) {

            }
        });
    }

    private String setHtmlFromRoutineList(ExerciseRoutinesViewModel exercise){
        String html;
        html=PreferencesData.html.replace(PreferencesData.htmlTagToReplace,exercise.getExercise_routine_url());
        return html;
    }


    private void setDataToView(ExerciseRoutinesViewModel excerciseRoutine){

        etExerciseSpeed.setText(String.valueOf(excerciseRoutine.getSpeed()));
        etExerciseFrequent.setText(String.valueOf(excerciseRoutine.getFrequency()));
        etExerciseIntensity.setText(String.valueOf(excerciseRoutine.getIntensity()));
        etDuration.setText(String.valueOf(excerciseRoutine.getDuration()));
        etPreConditions.setText(excerciseRoutine.getConditions());
        etObservations.setText(excerciseRoutine.getObservations());
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

    public void showHideItems(Menu menu) {
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

    private void redirectToVideo(String url){
        Bundle extras = new Bundle();
        extras.putString(PreferencesData.ExerciseRoutineUrl,url);
        Intent intent = new Intent(mContext, YoutubeVideo.class);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
