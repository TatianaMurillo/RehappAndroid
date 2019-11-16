package com.rehapp.rehappmovil.rehapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.DocumentTypeApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.UserApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.UserViewModel;
import com.rehapp.rehappmovil.rehapp.Utils.ValidateInputs;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {


    EditText etName;
    EditText etEmail;
    EditText etConfirmarEmail;
    EditText etUserPassword;
    EditText etConfirmPassword;
    Button btnRegister;
    private SharedPreferences sharedpreferences;
    private UserViewModel userViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        sharedpreferences=getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);

         etName= findViewById(R.id.etName);
         etEmail= findViewById(R.id.etEmail);
         etConfirmarEmail= findViewById(R.id.etConfirmarEmail);
         etUserPassword= findViewById(R.id.etUserPassword);
         etConfirmPassword= findViewById(R.id.etConfirmUserPassword);
         btnRegister= findViewById(R.id.btnRegister);

    }


    public void register(View view) {

        String name, email,confirmEmail, password, confirmPassword;

        name = etName.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        confirmEmail = etConfirmarEmail.getText().toString().trim();
        password = etUserPassword.getText().toString().trim();
        confirmPassword = etConfirmPassword.getText().toString().trim();
        ArrayList<String> data =new ArrayList<>(Arrays.asList(name,email,confirmEmail,password,confirmPassword));

        if(!ValidateInputs.validate().ValidateString(data)){


            Toast.makeText(getApplicationContext(),PreferencesData.registerUserDataMgs,Toast.LENGTH_LONG).show();

        }else if(!email.equals(confirmEmail))
            {
                Toast.makeText(getApplicationContext(),PreferencesData.registerUserEmailDataMgs,Toast.LENGTH_LONG).show();

            }else if(!password.equals(confirmPassword))
            {

                Toast.makeText(getApplicationContext(),PreferencesData.registerUserPasswordDataMgs,Toast.LENGTH_LONG).show();

            }else
            {
                UserViewModel user = new UserViewModel(email, password, name);
                registerUser(user);
            }

        }

        private void registerUser(final UserViewModel newUser) {

            Call<UserViewModel> call = UserApiAdapter.getApiService().newUSer(newUser);
            call.enqueue(new Callback<UserViewModel>() {

            @Override
            public void onResponse (Call < UserViewModel > call, Response < UserViewModel > response)
            {

                if (response.isSuccessful()) {
                        login(newUser);
                } else {
                        Toast.makeText(getApplicationContext(), PreferencesData.registerUserFailedMgs, Toast.LENGTH_LONG).show();

                }
            }

                @Override
                public void onFailure (Call < UserViewModel > call, Throwable t){
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }



    private void login(UserViewModel user){
        Call<UserViewModel> call = UserApiAdapter.getApiService().login(user);
        call.enqueue(new Callback<UserViewModel>() {
            @Override
            public void onResponse(Call<UserViewModel> call, Response<UserViewModel> response) {


                if(response.isSuccessful()) {

                    userViewModel = response.body();

                    if(userViewModel.getCode()==200)
                    {
                        userViewModel.setName(userViewModel.getName());

                        storeStringSharepreferences(PreferencesData.loginToken, userViewModel.getToken());
                        storeStringSharepreferences(PreferencesData.userActive, userViewModel.getName());
                        storeIntSharepreferences(PreferencesData.userActiveId, userViewModel.getId());

                        Intent intent = new Intent(Register.this, MainActivity.class);
                        startActivity(intent);
                    }else
                    {
                        Toast.makeText(getApplicationContext(), userViewModel.getError(),   Toast.LENGTH_LONG).show();

                    }
                }
            }
            @Override
            public void onFailure(Call<UserViewModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), PreferencesData.loginFailureMsg,   Toast.LENGTH_LONG).show();
            }
        });
    }

    private  void storeStringSharepreferences(String key, String value){

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.commit();

    }

    private  void storeIntSharepreferences(String key, int value){

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(key, value);
        editor.commit();

    }
}

