package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.PatientMedicalHistoryViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PatientMedicalHistoryApiService {

    @GET("medicalHistories/showByPatient/{patientId}")
    Call<ArrayList<PatientMedicalHistoryViewModel>> getMedicalHistoriesByPatient(@Path("patientId") String patientId);

    @GET("therapies/showById/{therapy_id}")
    Call<TherapyViewModel> getTherapy(@Path("therapy_id") String therapyId);

    @POST("therapies")
    Call<TherapyViewModel> createTherapyId(@Body TherapyViewModel therapy);

    @PUT("therapies")
    Call<TherapyViewModel> updateTherapy(@Body TherapyViewModel therapy);

    @PUT("therapies/{therapy_id}")
    Call<TherapyViewModel> updateTherapy(@Body TherapyViewModel therapy, @Path("therapy_id") String therapyId);

}
