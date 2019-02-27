package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.PatientViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PatientApiService {


    @GET("patients/showbyname/{document}")
    Call<PatientViewModel> getPatient(@Path("document") String document);

}
