package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.TherapyExcerciseRoutineDetailViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyExcerciseRoutineViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TherapyExerciseRoutineApiService {


    @GET("therapyExerciseRoutines/showbyTherapy/{therapyId}")
    Call<ArrayList<TherapyExcerciseRoutineViewModel>> getTherapyExerciseRoutines(@Path("therapyId") String therapyId);

    @GET("therapyExerciseRoutines/showRoutine/therapy/{therapyId}/routine/{routineId}/views/{views}")
    Call<TherapyExcerciseRoutineViewModel> getTherapyExerciseRoutine(@Path("therapyId") String therapyId,@Path("routineId") String routineId,@Path("views") String views);

    @GET("exerciseRoutineDetail/getTherapyDetailRoutine/list/{list}/detail/{routineDetailId}")
    Call<TherapyExcerciseRoutineDetailViewModel> getTherapyDetailRoutine(@Path("list") String listName, @Path("routineDetailId") int routineDetailId);


    @POST("therapyExerciseRoutines/saveRoutines/{therapyId}")
    Call<List<TherapyExcerciseRoutineViewModel>> saveTherapyExerciseRoutines(@Body List<TherapyExcerciseRoutineViewModel> therapyExcerciseRoutines, @Path("therapyId") String therapyId);

    @POST("therapyExerciseRoutines/updateRoutine/{therapyId}")
    Call<TherapyExcerciseRoutineViewModel> updateRoutine(@Body TherapyExcerciseRoutineViewModel therapyExcerciseRoutine, @Path("therapyId") String therapyId);


    @PUT("exerciseRoutineDetail/createOrUpdateTherapyDetail/{therapyRoutineDetailId}")
    Call<TherapyExcerciseRoutineDetailViewModel> updateRoutineDetail(@Body TherapyExcerciseRoutineDetailViewModel therapyExcerciseRoutineDetail, @Path("therapyRoutineDetailId") String therapyRoutineDetailId);


    @PUT("therapyExerciseRoutines/updateDurationFromTherapyExerciseRoutine/{therapyId}")
    Call<TherapyViewModel> updateTherapyDuration(@Path("therapyId") String therapyId);

}
