package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.DocumentType;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PatientApiService {


    @GET("patient")
    Call<ArrayList<DocumentType>> getDocumentTypes();

}
