package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;


import com.rehapp.rehappmovil.rehapp.Models.PatientMedicalHistoryQuestionaireOptionViewModel;
import com.rehapp.rehappmovil.rehapp.Models.QuestionaryOptionViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface QuestionariesApiService {


    @GET("questionnaireOptions/getOptionsByQuestionaryId")
    Call<ArrayList<QuestionaryOptionViewModel>> getQuestionariesOptions();

    @GET("patientMedicalHistoryQuestionnaireOptions/getOptionsByMedicalHistoryId/{medicalHistoryId}")
    Call<ArrayList<PatientMedicalHistoryQuestionaireOptionViewModel>> getPatienHistoryMedicalQuestionariesOptions(@Path("medicalHistoryId") String medicalHistoryId);

    @POST("patientMedicalHistoryQuestionnaireOptions/saveQuestionairesOptionsInMedicalHistory/{medicalHistoryId}")
    Call<List<PatientMedicalHistoryQuestionaireOptionViewModel>> saveQuestionairesOptionsInMedicalHistory(@Body List<PatientMedicalHistoryQuestionaireOptionViewModel> patientQuestionaireOptions, @Path("medicalHistoryId") String medicalHistoryId);

}
