package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.DiseaseViewModel;
import com.rehapp.rehappmovil.rehapp.Models.ExerciseRoutinesViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DiseaseApiService {


    @GET("diseases")
    Call<ArrayList<DiseaseViewModel>> getDiseases();

    @GET("medicalHistories/showDiseaseByMedicalHistory/{medicalHistoryId}")
    Call<ArrayList<DiseaseViewModel>> getDiseasesByMedicalHistory(@Path("medicalHistoryId") String medicalHistoryId);
}
