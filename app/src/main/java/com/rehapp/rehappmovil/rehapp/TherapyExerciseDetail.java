package com.rehapp.rehappmovil.rehapp;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;

public class TherapyExerciseDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapy_exercise_detail);






    }

    public void watchVideo(View view) {

        Intent intent = new Intent(TherapyExerciseDetail.this,ExerciseVideo.class);
        startActivity(intent);

    }
}
