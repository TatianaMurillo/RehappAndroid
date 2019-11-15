package com.rehapp.rehappmovil.rehapp;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.TextView;

public class TherapyExcerciseRoutine extends AppCompatActivity {

    TextView tvExerciseVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapy_exercise_detail);

        tvExerciseVideo=findViewById(R.id.tvExerciseVideo);

        tvExerciseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                watchVideo();
            }
        });

    }

    public void watchVideo() {

        Intent intent = new Intent(TherapyExcerciseRoutine.this, YoutubeVideo.class);
        startActivity(intent);

    }
}
