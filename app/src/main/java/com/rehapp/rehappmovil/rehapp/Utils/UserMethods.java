package com.rehapp.rehappmovil.rehapp.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.DocumentTypeApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.TherapyApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.UserApiAdapter;
import com.rehapp.rehappmovil.rehapp.Login;
import com.rehapp.rehappmovil.rehapp.Models.DocumentTypeViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapistViewModel;
import com.rehapp.rehappmovil.rehapp.Models.TherapyViewModel;
import com.rehapp.rehappmovil.rehapp.Models.UserViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserMethods extends Activity {

   private  SharedPreferences sharedpreferences;

   public static UserMethods Do()
    {
        return new UserMethods();
    }

    public  void Logout(Context context){

       try {

           sharedpreferences = this.getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);

           SharedPreferences.Editor editor = sharedpreferences.edit();

           String token = sharedpreferences.getString(PreferencesData.loginToken, "");

            UserViewModel user = new UserViewModel(token);

           Call<UserViewModel> call = UserApiAdapter.getApiService().logout(user);

           call.enqueue(new Callback<UserViewModel>() {
               @Override
               public void onResponse(Call<UserViewModel> call, Response<UserViewModel> response) {

                   UserViewModel user = response.body();

                   if (user.getCode() == 200) {
                       Log.d("Exitoso", "Call1 Succeeded");
                   } else {
                       Log.d("Exitoso pero error", user.getError());
                   }
               }

               @Override
               public void onFailure(Call<UserViewModel> call, Throwable t) {
                   Log.d("Error en Onfailure", t.getMessage());
               }
           });

           editor.clear();
           editor.commit();
           finish();
       }catch(Exception ex)
       {
           Log.d("Error en logout", ex.getMessage());
       }
       }


       public static void saveTherapy(TherapyViewModel therapy, final Context context)
       {

        Call<TherapyViewModel> call = TherapyApiAdapter.getApiService().createTherapy(therapy);
        call.enqueue(new Callback<TherapyViewModel>() {
            @Override
            public void onResponse(Call<TherapyViewModel> call, Response<TherapyViewModel> response) {

                Toast.makeText(context, PreferencesData.therapyCreationFailedMsg, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<TherapyViewModel> call, Throwable t) {
                Toast.makeText(context, PreferencesData.therapyCreationSuccessMsg, Toast.LENGTH_LONG).show();

            }
        });

       }


    public static void getTherapy(TherapyViewModel therapy, final Context context)
    {

        Call<TherapyViewModel> call = TherapyApiAdapter.getApiService().getTherapy();
        call.enqueue(new Callback<TherapyViewModel>() {
            @Override
            public void onResponse(Call<TherapyViewModel> call, Response<TherapyViewModel> response) {

                Toast.makeText(context, PreferencesData.therapyCreationFailedMsg, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<TherapyViewModel> call, Throwable t) {
                Toast.makeText(context, PreferencesData.therapyCreationSuccessMsg, Toast.LENGTH_LONG).show();

            }
        });

    }

    public static UserMethods getInstance()
    {
        return  new UserMethods();
    }
    public  ArrayList<DocumentTypeViewModel> getDocumentTypes()
    {
        DocumentTypes documentTypes= new DocumentTypes();
        documentTypes.loadDocumentTypes();
        return documentTypes.getDocumentTypes();
    }

    class DocumentTypes
    {

        ArrayList<DocumentTypeViewModel> documentTypes;

        public void loadDocumentTypes()
        {

            Call<ArrayList<DocumentTypeViewModel>> call = DocumentTypeApiAdapter.getApiService().getDocumentTypes();
            call.enqueue(new Callback<ArrayList<DocumentTypeViewModel>>() {

                             @Override
                             public void onResponse(Call<ArrayList<DocumentTypeViewModel>> call, Response<ArrayList<DocumentTypeViewModel>> response) {
                                 if(response.isSuccessful())
                                 {
                                     documentTypes= response.body();
                                 }
                             }
                             @Override
                             public void onFailure(Call<ArrayList<DocumentTypeViewModel>> call, Throwable t) {
                                 Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

                             }
                         }
            );
        }

        public ArrayList<DocumentTypeViewModel> getDocumentTypes()
        {
            return documentTypes;
        }
    }




}

