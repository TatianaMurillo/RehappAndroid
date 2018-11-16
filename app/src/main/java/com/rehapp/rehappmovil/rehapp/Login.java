package com.rehapp.rehappmovil.rehapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText etUser;
    private  EditText etpassword;
    private Button btnLogin;
    private TextView tvForgotPassword;
    private TextView tvRegister;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUser=findViewById(R.id.etUser);
        etpassword=findViewById(R.id.etpassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword= findViewById(R.id.tvForgotPassword);
        tvRegister= findViewById(R.id.tvRegister);





    }

    public void login(View view) {

        String user=etUser.getText().toString().trim();
        String password=etpassword.getText().toString().trim();
        if(user.equals("") || password.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Por favor ingrese los datos para iniciar sesión ",   Toast.LENGTH_LONG).show();

        }else
        {
            if(user.equals("yuly") && password.equals("1234"))
            {
                Intent intent = new Intent(Login.this,SearchCreatePatient.class);
                intent.putExtra( "userActive",etUser.getText().toString());
                startActivity(intent);
            }else
            {
                Toast.makeText(getApplicationContext(), "Los datos ingresados no son válidos.",   Toast.LENGTH_LONG).show();

            }
        }

    }
}
