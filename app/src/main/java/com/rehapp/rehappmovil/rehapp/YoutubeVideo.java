package com.rehapp.rehappmovil.rehapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;


import static com.rehapp.rehappmovil.rehapp.Models.PlayerConfig.API_KEY;

public class YoutubeVideo extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    YouTubePlayerView youtubePlayerView;
    private SharedPreferences sharedpreferences;
    String routineUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_video);
        sharedpreferences=getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);
        recoverySendData();
        youtubePlayerView= findViewById(R.id.youtubePlayerView);
        youtubePlayerView.initialize(API_KEY,this);

    }


    private void recoverySendData()
    {
        Bundle extras =getIntent().getExtras();
        if( extras!=null)
        {
            routineUrl =extras.getString(PreferencesData.ExerciseRoutineUrl);
            storeStringSharepreferences(PreferencesData.ExerciseRoutineUrl, routineUrl);
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean fueRestaurado) {
        String routineUrl=sharedpreferences.getString(PreferencesData.ExerciseRoutineUrl,"");
        if(!fueRestaurado)
        {
            youTubePlayer.cueVideo(routineUrl);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {


        if(youTubeInitializationResult.isUserRecoverableError())
        {
            youTubeInitializationResult.getErrorDialog(this,1).show();
        }else
        {
            String error= "Error al inicializar Youtube" + youTubeInitializationResult.toString();
            Toast.makeText(getApplication(), error, Toast.LENGTH_LONG).show();
        }

    }

    protected void onActivityResult(int requestCode,int resultCode, Intent data )
    {
        if(resultCode==1)
        {
                getYoutubePlayerProvider().initialize(API_KEY,this);
        }
    }

    protected  YouTubePlayer.Provider getYoutubePlayerProvider()
    {
        return youtubePlayerView;
    }


    private  void storeStringSharepreferences(String key, String value){

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();

    }
}
