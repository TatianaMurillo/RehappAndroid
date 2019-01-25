package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.DocumentType;
import com.rehapp.rehappmovil.rehapp.Models.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApiService {


    @POST("login")
    Call <User> login(@Body User user);



}
