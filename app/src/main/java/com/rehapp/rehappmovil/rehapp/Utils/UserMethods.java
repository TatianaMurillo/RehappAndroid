package com.rehapp.rehappmovil.rehapp.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;


import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.UserApiAdapter;
import com.rehapp.rehappmovil.rehapp.Login;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.UserViewModel;

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
    }

