package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.TherapyExcerciseRoutineViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TherapyExerciseRoutineApiService {


    @GET("therapyExerciseRoutines/showbyTherapy/{therapyId}")
    Call<ArrayList<TherapyExcerciseRoutineViewModel>> getTherapyExerciseRoutines(@Path("therapyId") String therapyId);


}
