package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.PhysiologicalParameterViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PhysiologicalParameterApiService {


    @GET("physiologicalParameters/getPhysiologicalParameters/therapy/{therapyId}/action/{action}")
    Call<ArrayList<PhysiologicalParameterViewModel>> getPhysiologicalParams(@Path("therapyId") int therapyId,@Path("action") String action);

}
