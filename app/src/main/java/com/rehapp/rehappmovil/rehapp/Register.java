package com.rehapp.rehappmovil.rehapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.DocumentTypeApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.UserApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.DocumentTypeViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapistViewModel;
import com.rehapp.rehappmovil.rehapp.Models.UserViewModel;
import com.rehapp.rehappmovil.rehapp.Utils.Constants.Therapist;
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
    EditText etTherapistDocument;
    Spinner spnTherapistDocumentType;
    int documentTypeSelectedId=-1,indexDocumentTypeSelected=-1;
    ArrayList<DocumentTypeViewModel> documentTypes =new ArrayList<>();
    ArrayList<String> documentTypeNames= new ArrayList<>();

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
         etTherapistDocument=findViewById(R.id.etTherapistDocument);
         spnTherapistDocumentType=findViewById(R.id.spnTherapistDocumentType);
        loadData();
        spnTherapistDocumentType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                documentTypeSelectedId = documentTypes.get(position).getDocument_type_id();
                indexDocumentTypeSelected=position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

    }

    private void loadData(){
        loadDocumentTypes();
    }

    private void loadDocumentTypes()
    {
        Call<ArrayList<DocumentTypeViewModel>> call = DocumentTypeApiAdapter.getApiService().getDocumentTypes();
        call.enqueue(new Callback<ArrayList<DocumentTypeViewModel>>() {

                         @Override
                         public void onResponse(Call<ArrayList<DocumentTypeViewModel>> call, Response<ArrayList<DocumentTypeViewModel>> response) {
                             if(response.isSuccessful())
                             {
                                 documentTypes= response.body();
                                 for (DocumentTypeViewModel documentTypeViewModel : documentTypes) {
                                     documentTypeNames.add(documentTypeViewModel.getDocument_type_name());
                                 }
                                 ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, documentTypeNames);
                                 spnTherapistDocumentType.setAdapter(arrayAdapter);

                             }
                         }
                         @Override
                         public void onFailure(Call<ArrayList<DocumentTypeViewModel>> call, Throwable t) {
                             t.printStackTrace();
                             Toast.makeText(getApplicationContext(), PreferencesData.listDocumentTypesFailed, Toast.LENGTH_LONG).show();
                         }
                     }
        );
    }


    public void register(View view) {

        String name, email,confirmEmail, password, confirmPassword,therapistDocument;
        int therapistDocumentTypeId=documentTypeSelectedId;

        therapistDocument=etTherapistDocument.getText().toString();
        name = etName.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        confirmEmail = etConfirmarEmail.getText().toString().trim();
        password = etUserPassword.getText().toString().trim();
        confirmPassword = etConfirmPassword.getText().toString().trim();
        ArrayList<String> data =new ArrayList<>(Arrays.asList(therapistDocument,name,email,confirmEmail,password,confirmPassword));
        ArrayList<Integer> dataInteger =new ArrayList<>(Arrays.asList(therapistDocumentTypeId));

        if(!ValidateInputs.validate().ValidateStringsAndIntegers(data,dataInteger)){

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

            Call<TherapistViewModel> call = UserApiAdapter.getApiService().newUSer(newUser);
            call.enqueue(new Callback<TherapistViewModel>() {

                @Override
                public void onResponse (Call < TherapistViewModel > call, Response < TherapistViewModel > response)
                {
                    if (response.isSuccessful()) {
                            TherapistViewModel therapist=response.body();
                            storeIntSharepreferences(PreferencesData.TherapistId, therapist.getTherapist_id());
                            login(newUser);
                    } else {
                            Toast.makeText(getApplicationContext(), PreferencesData.registerUserFailedMgs, Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure (Call < TherapistViewModel > call, Throwable t){
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

