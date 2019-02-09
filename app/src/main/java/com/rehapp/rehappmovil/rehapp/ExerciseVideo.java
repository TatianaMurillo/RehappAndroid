package com.rehapp.rehappmovil.rehapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

public class ExerciseVideo extends Activity {


    ProgressDialog prgsDialog;
    VideoView videoView;
    int position;
    String videoUrl = "https://drive.google.com/drive/folders/13U9F_7WxEOdCWsmGeHlxUsJu6eZ-Ny2I/videoTati.mp4";
    MediaController mediaController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_video);








        videoView = findViewById(R.id.videoView);


        prgsDialog = new ProgressDialog(ExerciseVideo.this);

        prgsDialog.setMessage("Loading...");

        prgsDialog.setCancelable(false);


        prgsDialog.show();




        if (mediaController == null) {

            mediaController = new MediaController(ExerciseVideo.this);

        }



        try {


            mediaController.setAnchorView(videoView);
            Uri video = Uri.parse("https://drive.google.com/file/d/1AXhwwXLnYw0ZoXcypDeiyawuoGDe2_TF/view?fbclid=IwAR1aTWcyOgV_N2_3VPDCWCVwnI7e7SjxWOn_HLObVRXNDoK4CE_5pm0OlaQ");
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(video);

        }catch (Exception ex)
        {
            Log.e("Error", ex.getMessage());
            ex.printStackTrace();

        }

        videoView.requestFocus();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                prgsDialog.dismiss();

                videoView.seekTo(position);

                if (position == 0) {

                    videoView.start();
                } else {

                    videoView.pause();

                }

            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putInt("pos",videoView.getCurrentPosition());

        videoView.pause();

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        position = savedInstanceState.getInt("pos");
        videoView.seekTo(position);


    }
}
