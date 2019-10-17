package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.PatientViewModel;
import com.rehapp.rehappmovil.rehapp.Models.VitalSignViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface VitalSignApiService {


    @GET("vitalSigns")
    Call<List<VitalSignViewModel>> getVitalSigns();

}
