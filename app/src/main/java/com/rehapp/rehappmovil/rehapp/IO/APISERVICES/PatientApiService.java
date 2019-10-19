package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.PatientViewModel;
import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PatientApiService {


    @GET("patients/showbyname/{document}")
    Call<PatientViewModel> getPatient(@Path("document") String document);

    @POST("patients")
    Call<PatientViewModel> createPatient(@Body PatientViewModel patient);

    @PUT("patients/{patientId}")
    Call<PatientViewModel> updatePatient(@Body PatientViewModel patient,@Path("patientId") String document);

}
