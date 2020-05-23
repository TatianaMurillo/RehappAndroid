package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.PhysiologicalParameterViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PhysiologicalParameterApiService {


    @GET("physiologicalParameters/getPhysiologicalParameters/therapy/141/option/IN")
    Call<ArrayList<PhysiologicalParameterViewModel>> getPhysiologicalParams();

}
