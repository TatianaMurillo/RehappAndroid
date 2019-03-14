package com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS;

import com.rehapp.rehappmovil.rehapp.IO.APISERVICES.InstitutionApiService;
import com.rehapp.rehappmovil.rehapp.IO.APISERVICES.TherapistApiService;
import com.rehapp.rehappmovil.rehapp.Utils.GlobalApplication;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InstitutionApiAdapter {

    private static InstitutionApiService API_SERVICE;

    public static InstitutionApiService getApiService() {


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        String baseUrl =GlobalApplication.url;

        if (API_SERVICE == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
            API_SERVICE = retrofit.create(InstitutionApiService.class);
        }

        return API_SERVICE;


    }
}
