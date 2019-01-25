package com.rehapp.rehappmovil.rehapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import static com.rehapp.rehappmovil.rehapp.Models.PlayerConfig.API_KEY;

public class YoutubeVideo extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    YouTubePlayerView youtubePlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_video);

        youtubePlayerView= findViewById(R.id.youtubePlayerView);
        youtubePlayerView.initialize(API_KEY,this);



    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean fueRestaurado) {

        if(!fueRestaurado)
        {
            youTubePlayer.cueVideo("-SLZBgcMzek");
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
}
