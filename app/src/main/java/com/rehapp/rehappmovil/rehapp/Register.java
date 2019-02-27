package com.rehapp.rehappmovil.rehapp;

import android.content.Intent;
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

public class Register extends AppCompatActivity  implements Callback<UserViewModel> {


    EditText etName;
    EditText etEmail;
    EditText etConfirmarEmail;
    EditText etPassword;
    EditText etConfirmPassword;
    Button btnRegister;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


         etName= findViewById(R.id.etName);
         etEmail= findViewById(R.id.etEmail);
         etConfirmarEmail= findViewById(R.id.etConfirmarEmail);
         etPassword= findViewById(R.id.etpassword);
         etConfirmPassword= findViewById(R.id.etConfirmPassword);
         btnRegister= findViewById(R.id.btnRegister);

    }


    public void register(View view) {

        String name, email,confirmEmail, password, confirmPassword;

        name = etName.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        confirmEmail = etConfirmarEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        confirmPassword = etConfirmPassword.getText().toString().trim();
        ArrayList<String> data =new ArrayList<String>(Arrays.asList(name,email,confirmEmail,password,confirmPassword));

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

                Call<UserViewModel> call = UserApiAdapter.getApiService().newUSer(user);
                call.enqueue(this);
            }

        }


    @Override
    public void onResponse(Call<UserViewModel> call, Response<UserViewModel> response) {

        if(response.isSuccessful()) {

            UserViewModel user = response.body();

            if(user.getCode()==200)
            {
                Intent intent = new Intent(this,Login.class);
                startActivity(intent);

            }else
            {
                Toast.makeText(getApplicationContext(), user.getError(),   Toast.LENGTH_LONG).show();

            }


        }


    }

    @Override
    public void onFailure(Call<UserViewModel> call, Throwable t) {
        Toast.makeText(getApplicationContext(), t.getMessage(),   Toast.LENGTH_LONG).show();

    }
}

