package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.DocumentType;
import com.rehapp.rehappmovil.rehapp.Models.UserViewModel;
import com.rehapp.rehappmovil.rehapp.Models.UserViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApiService {


    @POST("login")
    Call <UserViewModel> login(@Body UserViewModel user);

    @POST("register")
    Call <UserViewModel> newUSer(@Body UserViewModel user);

    @POST("logout")
    Call <UserViewModel> logout(@Body UserViewModel user);



}
