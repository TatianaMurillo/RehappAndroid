package com.rehapp.rehappmovil.rehapp.IO.APISERVICES;

import com.rehapp.rehappmovil.rehapp.Models.InstitutionViewModel;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface InstitutionApiService {


    @GET("institutions")
    Call<ArrayList<InstitutionViewModel>> getInstitutions();
}
