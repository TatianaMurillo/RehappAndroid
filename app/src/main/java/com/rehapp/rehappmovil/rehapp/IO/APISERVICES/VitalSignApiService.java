package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;


import com.rehapp.rehappmovil.rehapp.Models.PatientMedicalHistoryVitalSignViewModel;
import com.rehapp.rehappmovil.rehapp.Models.VitalSignViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface VitalSignApiService {


    @GET("vitalSigns")
    Call<ArrayList<VitalSignViewModel>> getVitalSigns();

    @GET("vitalSignsMedicalHistory/showByMedicalHistory/{medicalHistoryId}")
    Call<ArrayList<VitalSignViewModel>> getVitalSignsByMedicalHistory2(@Path("medicalHistoryId") String medicalHistoryId);

    @GET("vitalSignsMedicalHistory/showByMedicalHistory/{medicalHistoryId}")
    Call<ArrayList<PatientMedicalHistoryVitalSignViewModel>> getVitalSignsByMedicalHistory(@Path("medicalHistoryId") String medicalHistoryId);

    @POST("patientMedicalHistoryVitalSigns/saveVitalSignsInMedicalHistory/{medicalHistoryId}")
    Call<List<PatientMedicalHistoryVitalSignViewModel>> saveMedicalHistoryVitalSigns(@Body List<PatientMedicalHistoryVitalSignViewModel> patientMedicalHistoryVitalSign, @Path("medicalHistoryId") String medicalHistoryId);


}
