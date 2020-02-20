package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.DocumentTypeViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DocumentTypeApiService {


    @GET("documentTypes")
    Call<ArrayList<DocumentTypeViewModel>> getDocumentTypes();
}
