package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;


import com.rehapp.rehappmovil.rehapp.Models.PhysiologicalParameterTherapyViewModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PhysiologicalParameterTherapyApiService {

    @PUT("physiologicalParametersTherapy/savePhysiologicalParameter")
    Call<PhysiologicalParameterTherapyViewModel> savePhysiologicalParamsTherapy(@Body PhysiologicalParameterTherapyViewModel physioParam);

    @GET("physiologicalParametersTherapy/getPhysiologicalParameter/param/{paramId}/therapy/{therapyId}/action/{action}")
    Call<PhysiologicalParameterTherapyViewModel> getPhysiologicalParamsTherapy(@Path("paramId") String paramId, @Path("therapyId") int therapyId, @Path("action") String action);

}
