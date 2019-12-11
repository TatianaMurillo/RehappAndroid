package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.TherapyExcerciseRoutineViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TherapyExerciseRoutineApiService {


    @GET("therapyExerciseRoutines/showbyTherapy/{therapyId}")
    Call<ArrayList<TherapyExcerciseRoutineViewModel>> getTherapyExerciseRoutines(@Path("therapyId") String therapyId);

    @GET("therapyExerciseRoutines/showRoutineByTherapy/{therapyId}/{routineId}")
    Call<TherapyExcerciseRoutineViewModel> getTherapyExerciseRoutine(@Path("therapyId") String therapyId,@Path("routineId") String routineId);


    @POST("therapyExerciseRoutines/saveRoutines/{therapyId}")
    Call<List<TherapyExcerciseRoutineViewModel>> saveTherapyExerciseRoutines(@Body List<TherapyExcerciseRoutineViewModel> therapyExcerciseRoutines, @Path("therapyId") String therapyId);


    @POST("therapyExerciseRoutines/saveRoutine/{therapyId}")
    Call<TherapyExcerciseRoutineViewModel> saveTherapyExerciseRoutine(@Body TherapyExcerciseRoutineViewModel therapyExcerciseRoutines, @Path("therapyId") String therapyId);


}
