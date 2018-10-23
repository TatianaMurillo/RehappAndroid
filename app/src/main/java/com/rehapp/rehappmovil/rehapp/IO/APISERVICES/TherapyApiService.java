package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.Therapy;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TherapyApiService {


    @GET("query={query}&format={format}&id={id}")
    Call<ArrayList<Therapy>> getTherapies(
            @Query("query") String query,
            @Query("format") String format,
            @Query("id") String id);

}
