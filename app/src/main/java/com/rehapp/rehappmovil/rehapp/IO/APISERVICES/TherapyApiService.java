package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TherapyApiService {


    @GET("search?")
    Call<TherapyViewModel> getTherapies(
            @Query("query") String query,
            @Query("format") String format,
            @Query("id") String id);

}
