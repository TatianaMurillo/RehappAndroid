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
            Uri video = Uri.parse("https://orangelo.fruithosted.net/dl/n/So692rrOZOfQCKxr/onklkmbpsokcqaco/Xk15YkHY7MemKDp0P_mOgT5dKLqEuI2vKO9LkTNWN3UiwgaVGlw1xZOp_mP3vvSBC4GDBKedMvCv0x0aCbW_dti9dbmZj4xVmdqDJIBDcrQJhDWiETME6JgPxBOBzrqxeAi6OUNo_WsOhAtWjWI196LfCjaDpVn2McgJbTAzCC48MOTDCMai_fa1K6n-PoEBntN0dowWW2b2eR9qgDuD5sgJRpK9UdwWlM8iq6hO0_LYrZd7z7clY_e4sPayVOJyETrBrF75Hvq2CYZhF4eF0PkttNdG3R6piX-xW18GIN6wzUSIkY6jeheizfKmlNJ7haul9kr7zW2Z0yQI0KD1UpAKLfapnVbNLbbr-4gMXgnVqSyMVZNaZ2lEv_js2SQss1Z3yMRtze-R19PwwIU04h64GHoKLeIcsTXOqOBjPMvg9Eb4s_vyMNL_iuRycELR/VID-20181204-WA0027.mp4");
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
