package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.MedicalHistoryVitalSignPatientViewModel;
import com.rehapp.rehappmovil.rehapp.Models.QuestionariesViewModel;
import com.rehapp.rehappmovil.rehapp.Models.VitalSignViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface QuestionariesApiService {


    @GET("vitalSigns")
    Call<ArrayList<QuestionariesViewModel>> getQuestionaries();

    @GET("vitalSignsMedicalHistory/showByMedicalHistory/{medicalhistory}")
    Call<ArrayList<MedicalHistoryVitalSignPatientViewModel>> getQuestionatiesOptions(@Path("medicalhistory") String medicalhistory);

}
