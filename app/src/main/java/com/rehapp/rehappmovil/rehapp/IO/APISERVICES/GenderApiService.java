package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.GenderViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GenderApiService {


    @GET("genders")
    Call<ArrayList<GenderViewModel>> getGenders();

}
