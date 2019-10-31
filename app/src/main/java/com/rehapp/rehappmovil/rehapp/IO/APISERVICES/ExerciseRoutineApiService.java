package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.ExerciseRoutinesViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ExerciseRoutineApiService {


    @GET("exerciseRoutines")
    Call<ArrayList<ExerciseRoutinesViewModel>> getExerciseRoutines();

}
