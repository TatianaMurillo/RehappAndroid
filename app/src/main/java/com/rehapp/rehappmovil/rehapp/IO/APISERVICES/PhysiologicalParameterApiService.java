package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.DocumentTypeViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PhysiologicalParameterViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PhysiologicalParameterApiService {


    @GET("physiologicalParameters")
    Call<ArrayList<PhysiologicalParameterViewModel>> getPhysiologicalParams();

}
