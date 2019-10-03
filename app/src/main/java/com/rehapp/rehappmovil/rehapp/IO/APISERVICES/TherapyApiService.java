package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.*;

public interface TherapyApiService {

    @GET("therapies")
    Call<ArrayList<TherapyViewModel>> getTherapies();

    @GET("therapies/showById/{therapy_id}")
    Call<TherapyViewModel> getTherapy(@Path("therapy_id") String therapyId);

    @POST("therapies")
    Call<TherapyViewModel> createTherapyId(@Body TherapyViewModel therapy);

    @PUT("therapies")
    Call<TherapyViewModel> updateTherapy(@Body TherapyViewModel therapy);

    @PUT("therapies/{therapy_id}")
    Call<TherapyViewModel> updateTherapy(@Body TherapyViewModel therapy,@Path("therapy_id") String therapyId);

}
