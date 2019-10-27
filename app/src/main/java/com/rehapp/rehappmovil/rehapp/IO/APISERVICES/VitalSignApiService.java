package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.MedicalHistoryVitalSignPatientViewModel;
import com.rehapp.rehappmovil.rehapp.Models.VitalSignViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VitalSignApiService {


    @GET("vitalSigns")
    Call<ArrayList<VitalSignViewModel>> getVitalSigns();

    @GET("vitalSignsMedicalHistory/showByMedicalHistory/{medicalhistory}")
    Call<ArrayList<MedicalHistoryVitalSignPatientViewModel>> getVitalSignsByMedicalHistory(@Path("medicalhistory") String medicalhistory);

}
