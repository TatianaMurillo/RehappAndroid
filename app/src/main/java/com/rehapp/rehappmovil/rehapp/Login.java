package com.rehapp.rehappmovil.rehapp;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.TherapistApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.UserApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapistViewModel;
import com.rehapp.rehappmovil.rehapp.Models.UserViewModel;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

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

        sharedpreferences=getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);

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
            login(user);
        }
    }

    @Override
    public void onBackPressed() {

        isFinish("Iniciar sesion.");

    }

    public void isFinish(String alertmessage) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(alertmessage)
                .setPositiveButton("De acuerdo.", dialogClickListener).show();

    }


    private void login(final UserViewModel user){
        Call<UserViewModel> call = UserApiAdapter.getApiService().login(user);
        call.enqueue(new Callback<UserViewModel>() {
            @Override
            public void onResponse(Call<UserViewModel> call, Response<UserViewModel> response) {


                if(response.isSuccessful()) {

                    userViewModel = response.body();

                    if(userViewModel.getCode()==200)
                    {
                        userViewModel.setName(userViewModel.getName());
                        storeStringSharepreferences(PreferencesData.userActive,"");
                        storeStringSharepreferences(PreferencesData.loginToken, userViewModel.getToken());
                        storeStringSharepreferences(PreferencesData.userActive, userViewModel.getName());
                        searchTherapist(user.getEmail());

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
    public  void searchTherapist(String email) {

        TherapistViewModel therapist= new TherapistViewModel();
        therapist.setTherapist_email(email);
        Call<TherapistViewModel> call = TherapistApiAdapter.getApiService().getTherapistByEmail(therapist);
        call.enqueue(new Callback<TherapistViewModel>() {
            @Override
            public void onResponse(Call<TherapistViewModel> call, Response<TherapistViewModel> response) {
                if(response.isSuccessful())
                {
                    TherapistViewModel therapist = response.body();
                    storeIntSharepreferences(PreferencesData.TherapistId, therapist.getTherapist_id());
                    storeStringSharepreferences(PreferencesData.TherapistName, therapist.getTherapist_first_name());
                    storeStringSharepreferences(PreferencesData.TherapistEmail, therapist.getTherapist_email());
                    Intent intent = new Intent(Login.this,MainActivity.class);
                    startActivity(intent);

                }else if(response.raw().code()==404) {
                       Toast.makeText(getApplicationContext(), PreferencesData.TherapistNotFoundPatient,Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<TherapistViewModel> call, Throwable t)
            {
                Toast.makeText(getApplicationContext(), PreferencesData.searchTherapistPatient +" "+ t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }



    public void registerUser(View view) {

        Intent intent = new Intent(Login.this,Register.class);
        startActivity(intent);
    }

    private void loadPreferences() {
        sharedpreferences=getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);

        String therapistEmail=sharedpreferences.getString(PreferencesData.TherapistEmailLogin,"");

        String therapistPassword=sharedpreferences.getString(PreferencesData.TherapistPasswordlLogin,"");

        etUser.setText(therapistEmail);
        etpassword.setText(therapistPassword);

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
