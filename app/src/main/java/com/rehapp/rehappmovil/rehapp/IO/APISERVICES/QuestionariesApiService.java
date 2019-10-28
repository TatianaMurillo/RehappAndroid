package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.MedicalHistoryVitalSignPatientViewModel;
import com.rehapp.rehappmovil.rehapp.Models.QuestionariesViewModel;
import com.rehapp.rehappmovil.rehapp.Models.QuestionaryOptionViewModel;
import com.rehapp.rehappmovil.rehapp.Models.VitalSignViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface QuestionariesApiService {


    @GET("vitalSigns")
    Call<ArrayList<QuestionariesViewModel>> getQuestionaries();

    @GET("questionnaireOptions/getOptionsByQuestionaryId")
    Call<ArrayList<QuestionaryOptionViewModel>> getQuestionariesOptions();

}
