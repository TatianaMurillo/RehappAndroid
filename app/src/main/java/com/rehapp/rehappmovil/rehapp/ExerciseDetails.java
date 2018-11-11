package com.rehapp.rehappmovil.rehapp;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class ExerciseDetails extends AppCompatActivity  {


    ProgressDialog prgsDialog;
    VideoView videoView;
    int position;
    String videoUrl = "https://drive.google.com/drive/folders/13U9F_7WxEOdCWsmGeHlxUsJu6eZ-Ny2I/videoTati.mp4";
    MediaController mediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_exercise_details);


        videoView = findViewById(R.id.videoView);


        prgsDialog = new ProgressDialog(ExerciseDetails.this);

        prgsDialog.setMessage("Loading...");

        prgsDialog.setCancelable(false);


        prgsDialog.show();




        if (mediaController == null) {

            mediaController = new MediaController(ExerciseDetails.this);

        }



        try {


            mediaController.setAnchorView(videoView);
            Uri video = Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
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
