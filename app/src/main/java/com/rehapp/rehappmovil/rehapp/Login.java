package com.rehapp.rehappmovil.rehapp;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.UserApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.UserViewModel;
import com.rehapp.rehappmovil.rehapp.Utils.ReadCSVFile;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements Callback<UserViewModel>{

    private EditText etUser;
    private  EditText etpassword;
    private Button btnLogin;
    private TextView tvForgotPassword;
    private TextView tvRegister;



    SharedPreferences sharedpreferences;
    private UserViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUser=findViewById(R.id.etUser);
        etpassword=findViewById(R.id.etpassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword= findViewById(R.id.tvForgotPassword);
        tvRegister= findViewById(R.id.tvRegister);

        loadPreferences();
        userViewModel= ViewModelProviders.of(this).get(UserViewModel.class);
    }


    public void login(View view) {


        String username=etUser.getText().toString().trim();
        String password=etpassword.getText().toString().trim();
        if(username.equals("") || password.equals(""))
        {
            Toast.makeText(getApplicationContext(), PreferencesData.loginIncorrectDataMgs,   Toast.LENGTH_LONG).show();

        }else
        {

            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(PreferencesData.TherapistEmailLogin, username);
            editor.putString(PreferencesData.TherapistPasswordlLogin, password);
            editor.commit();


            UserViewModel user = new UserViewModel(username,password);
            Call<UserViewModel> call = UserApiAdapter.getApiService().login(user);
            call.enqueue(this);
        }
    }

    @Override
    public void onResponse(Call<UserViewModel> call, Response<UserViewModel> response) {


        if(response.isSuccessful()) {

            userViewModel = response.body();

            if(userViewModel.getCode()==200)
            {
                userViewModel.setName(userViewModel.getName().toString());
                Intent intent = new Intent(Login.this,SearchCreatePatient.class);
                intent.putExtra( PreferencesData.userActive,userViewModel.getName().toString());

                sharedpreferences=getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(PreferencesData.loginToken, userViewModel.getToken());
                editor.commit();

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

    public void registerUser(View view) {


        Intent intent = new Intent(Login.this,Register.class);

        startActivity(intent);



    }

    private void loadPreferences() {
        sharedpreferences=getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);

        String therapistEmail=sharedpreferences.getString(PreferencesData.TherapistEmailLogin,"").toString();

        String therapistPassword=sharedpreferences.getString(PreferencesData.TherapistPasswordlLogin,"").toString();

        etUser.setText(therapistEmail);
        etpassword.setText(therapistPassword);

    }



}
