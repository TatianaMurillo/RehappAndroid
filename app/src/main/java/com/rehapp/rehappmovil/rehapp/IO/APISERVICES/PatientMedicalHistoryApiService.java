package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.DiseaseViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PatientMedicalHistoryViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyExcerciseRoutineViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PatientMedicalHistoryApiService {

    @GET("medicalHistories/{idMedicalHistory}")
    Call<PatientMedicalHistoryViewModel> getMedicalHistory(@Path("idMedicalHistory") String idMedicalHistory);


    @GET("medicalHistories/showByPatient/{patientId}")
    Call<ArrayList<PatientMedicalHistoryViewModel>> getMedicalHistoriesByPatient(@Path("patientId") String patientId);

    @POST("historyDiseases/saveDiseases/{idHistoryDisease}")
    Call<List<DiseaseViewModel>> saveDiseases(@Body List<DiseaseViewModel> diseases, @Path("idHistoryDisease") String idHistoryDisease);


    @POST("medicalHistories/updateOrCreate/{idMedicalHistory}")
    Call<PatientMedicalHistoryViewModel> createOrUpdateMedicalHistory(@Body PatientMedicalHistoryViewModel medicalHistory, @Path("idMedicalHistory") String idMedicalHistory);

}
