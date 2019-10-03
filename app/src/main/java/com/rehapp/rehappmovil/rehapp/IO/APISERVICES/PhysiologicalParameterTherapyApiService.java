package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.PhysiologicalParameterTherapyViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PhysiologicalParameterTherapyApiService {


    @POST("physiologicalParametersTherapy/savePhysiologicalParametersTherapyIn/{id}")
    Call<List<PhysiologicalParameterTherapyViewModel>> savePhysiologicalParamsTherapyIn(@Body List<PhysiologicalParameterTherapyViewModel>  physiologicalParameterTherapy, @Path("id") int id);

    @POST("physiologicalParametersTherapy/savePhysiologicalParametersTherapyOut/{id}")
    Call<List<PhysiologicalParameterTherapyViewModel>> savePhysiologicalParamsTherapyOut(@Body List<PhysiologicalParameterTherapyViewModel>  physiologicalParameterTherapy, @Path("id") int id);

    @GET("physiologicalParametersTherapy/getPhysiologicalParametersTherapyIn/{id}")
    Call<List<PhysiologicalParameterTherapyViewModel>> getPhysiologicalParamsTherapyIn(@Body List<PhysiologicalParameterTherapyViewModel>  physiologicalParameterTherapy, @Path("id") int id);

    @GET("physiologicalParametersTherapy/getPhysiologicalParametersTherapyOut/{id}")
    Call<List<PhysiologicalParameterTherapyViewModel>> getPhysiologicalParamsTherapyOut(@Body List<PhysiologicalParameterTherapyViewModel>  physiologicalParameterTherapy, @Path("id") int id);



}
