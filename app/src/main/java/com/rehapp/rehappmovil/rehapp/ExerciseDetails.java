package com.rehapp.rehappmovil.rehapp;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.VideoView;

public class ExerciseDetails extends AppCompatActivity implements View.OnClickListener{


    ProgressDialog prgsDialog;
    VideoView videoView;
    ImageButton ibtnPlayPause;

    //String videoUrl="https://drive.google.com/drive/folders/13U9F_7WxEOdCWsmGeHlxUsJu6eZ-Ny2I/videoTati.mp4";
    String videoUrl="https://drive.google.com/file/d/1dNW-X1UOpEacd5hnhfVhP9K0weQMYx5I/view?usp=sharing";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);


        videoView = findViewById(R.id.videoView);
        ibtnPlayPause = findViewById(R.id.ibtnPlayPause);
        ibtnPlayPause.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        prgsDialog = new ProgressDialog(ExerciseDetails.this);
        prgsDialog.setMessage("Cargando...");
        prgsDialog.setCanceledOnTouchOutside(false);
        prgsDialog.show();

        try {
            if(videoView.isPlaying()) {
                Uri uri = Uri.parse(videoUrl);
                videoView.setVideoURI(uri);
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        ibtnPlayPause.setImageResource(R.mipmap.ic_play);
                    }
                });
            }else
            {
                videoView.pause();
                ibtnPlayPause.setImageResource(R.mipmap.ic_play);
            }
        }catch (Exception ex)
        {

        }
            videoView.requestFocus();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    prgsDialog.dismiss();
                    mp.setLooping(true);
                    videoView.start();
                    ibtnPlayPause.setImageResource(R.mipmap.ic_pause);

                }
            });

    }
}
