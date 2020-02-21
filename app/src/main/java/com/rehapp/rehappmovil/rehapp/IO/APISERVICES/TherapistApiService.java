package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.TherapistViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TherapistApiService {


    @GET("therapists")
    Call<ArrayList<TherapistViewModel>> getTherapists();

    @POST("therapists/getTherapistByEmail")
    Call<TherapistViewModel> getTherapistByEmail(@Body TherapistViewModel therapist);

    @GET("therapists/{therapistId}")
    Call<TherapistViewModel> getTherapist(@Path("therapistId") String therapistId);

    @PUT("therapists/{therapistId}")
    Call<TherapistViewModel> updateTherapist(@Body TherapistViewModel therapist, @Path("therapistId") String therapistId);

}
