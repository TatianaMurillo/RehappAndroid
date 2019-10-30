package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.PatientMedicalHistoryViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PatientMedicalHistoryApiService {

    @GET("medicalHistories/showByPatient/{patientId}")
    Call<ArrayList<PatientMedicalHistoryViewModel>> getMedicalHistoriesByPatient(@Path("patientId") String patientId);


}
