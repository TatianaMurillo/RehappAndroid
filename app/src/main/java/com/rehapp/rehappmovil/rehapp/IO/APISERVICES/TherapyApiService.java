package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.*;

public interface TherapyApiService {

    @GET("therapies/showByPatient/{patientId}")
    Call<ArrayList<TherapyViewModel>> getTherapiesByPatient(@Path("patientId") String patientId);

    @GET("therapies/showById/{therapy_id}")
    Call<TherapyViewModel> getTherapy(@Path("therapy_id") String therapyId);

    @POST("therapies")
    Call<TherapyViewModel> createTherapyId(@Body TherapyViewModel therapy);

    @PUT("therapies")
    Call<TherapyViewModel> updateTherapy(@Body TherapyViewModel therapy);

    @PUT("therapies/{therapy_id}")
    Call<TherapyViewModel> updateTherapy(@Body TherapyViewModel therapy,@Path("therapy_id") String therapyId);

    @PUT("therapies/addAdditionalInformation/{therapy_id}")
    Call<TherapyViewModel> addTherapyAdditionalInformation(@Body TherapyViewModel therapy,@Path("therapy_id") String therapyId);

    @GET("therapies/getAdditionalInformation/{therapy_id}")
    Call<TherapyViewModel> getTherapyAdditionalInformation(@Path("therapy_id") String therapyId);

}
