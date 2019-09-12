package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.*;

public interface TherapyApiService {

    @GET("documentTypes")
    Call<TherapyViewModel> getTherapy();

    @POST("users/new")
    Call<TherapyViewModel> createTherapy(@Body TherapyViewModel therapy);


}
