package com.rehapp.rehappmovil.rehapp;

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
import com.rehapp.rehappmovil.rehapp.Models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements Callback<User>{

    private EditText etUser;
    private  EditText etpassword;
    private Button btnLogin;
    private TextView tvForgotPassword;
    private TextView tvRegister;


    public static final String MyPREFERENCES = PreferencesData.loginKey;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUser=findViewById(R.id.etUser);
        etpassword=findViewById(R.id.etpassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword= findViewById(R.id.tvForgotPassword);
        tvRegister= findViewById(R.id.tvRegister);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


    }

    public void login(View view) {


        String username=etUser.getText().toString().trim();
        String password=etpassword.getText().toString().trim();
        if(username.equals("") || password.equals(""))
        {
            Toast.makeText(getApplicationContext(), PreferencesData.loginIncorrectDataMgs,   Toast.LENGTH_LONG).show();

        }else
        {

            User user = new User(username,password);
            Call<User> call = UserApiAdapter.getApiService().login(user);
            call.enqueue(this);
        }
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {

        if(response.isSuccessful()) {

            User user = response.body();

            if(user.getCode()==200)
            {
                Intent intent = new Intent(Login.this,SearchCreatePatient.class);
                intent.putExtra( PreferencesData.userActive,user.getName().toString());

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString(PreferencesData.loginToken, user.getToken());
                editor.commit();

                startActivity(intent);

            }else
            {
                Toast.makeText(getApplicationContext(), user.getError(),   Toast.LENGTH_LONG).show();

            }


        }


    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        Toast.makeText(getApplicationContext(), PreferencesData.loginFailureMsg,   Toast.LENGTH_LONG).show();

    }

    public void registerUser(View view) {


        Intent intent = new Intent(Login.this,Register.class);

        startActivity(intent);



    }
}
