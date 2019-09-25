package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.PatientViewModel;
import com.rehapp.rehappmovil.rehapp.Models.UserViewModel;

import java.util.ArrayList;

import retrofit2.Call;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PatientApiService {


    @GET("patients/showbyname/{document}")
    Call<PatientViewModel> getPatient(@Path("document") String document);

    @FormUrlEncoded
    @POST("patients")
    Call<PatientViewModel> savePatient(
                                       @Field("patient_first_name") String patient_first_name,
                                       @Field("patient_second_name") String patient_second_name,
                                       @Field("patient_first_lastname") String patient_first_lastname,
                                       @Field("patient_second_lastname") String patient_second_lastname,
                                       @Field("patient_document") String patient_document,
                                       @Field("patient_age") int patient_age,
                                       @Field("patient_address") String patient_address,
                                       @Field("patient_mobile_number") String patient_mobile_number,
                                       @Field("patient_landline_phone") String patient_landline_phone,
                                       @Field("patient_additional_data") String patient_additional_data,
                                       @Field("document_type_id") int document_type_id,
                                       @Field("gender_id") int gender_id,
                                       @Field("neighborhood_id") int neighborhood_id);

}
