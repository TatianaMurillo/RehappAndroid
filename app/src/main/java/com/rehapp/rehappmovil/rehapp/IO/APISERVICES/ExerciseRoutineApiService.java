package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.ExerciseRoutinesViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ExerciseRoutineApiService {


    @GET("exerciseRoutines")
    Call<ArrayList<ExerciseRoutinesViewModel>> getExerciseRoutines();

    @GET("exerciseRoutines/{routineId}")
    Call<ExerciseRoutinesViewModel> getExerciseRoutine(@Path("routineId") String routineId);
}
