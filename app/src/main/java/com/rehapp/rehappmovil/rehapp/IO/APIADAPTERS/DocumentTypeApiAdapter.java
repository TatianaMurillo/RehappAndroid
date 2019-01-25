package com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS;

import com.rehapp.rehappmovil.rehapp.IO.APISERVICES.DocumentTypeApiService;
import com.rehapp.rehappmovil.rehapp.Utils.GlobalApplication;
import com.rehapp.rehappmovil.rehapp.Utils.Helper;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DocumentTypeApiAdapter {

    private static DocumentTypeApiService API_SERVICE;

    public static DocumentTypeApiService getApiService() {

        // Creamos un interceptor y le indicamos el log level a usar
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Asociamos el interceptor a las peticiones
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        String baseUrl ="http://192.168.0.28/terappmovil/public/api/";
        //Helper.getConfigValue(GlobalApplication.getAppContext(),"api_url");
        //"http://192.168.0.28/terappmovil/public/api/";

        if (API_SERVICE == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build()) // <-- usamos el log level
                    .build();
            API_SERVICE = retrofit.create(DocumentTypeApiService.class);
        }

        return API_SERVICE;


    }
}
