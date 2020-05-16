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
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.GenderApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.NeighborhoodApiAdapter;
import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.UserApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.DocumentTypeViewModel;
import com.rehapp.rehappmovil.rehapp.Models.GenderViewModel;
import com.rehapp.rehappmovil.rehapp.Models.NeighborhoodViewModel;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.TherapistViewModel;
import com.rehapp.rehappmovil.rehapp.Models.UserViewModel;
import com.rehapp.rehappmovil.rehapp.Utils.DataValidation;
import com.rehapp.rehappmovil.rehapp.Utils.UtilMethods;
import com.rehapp.rehappmovil.rehapp.Utils.ValidateInputs;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    int genderSelectedId=-1;
    int neighborhoodSelectedId=-1;
    ArrayList<DocumentTypeViewModel> documentTypes =new ArrayList<>();
    ArrayList<String> documentTypeNames= new ArrayList<>();

    Button btnRegister;
    private SharedPreferences sharedpreferences;
    private UserViewModel userViewModel;
    ArrayList<GenderViewModel> genders =new ArrayList<>();
    ArrayList<NeighborhoodViewModel> neighborhoods =new ArrayList<>();


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
                /**se le resta  uno porque se esta agregando una opción por defecto al spinner cuando se  llena**/
                int selectedOption=position-1;
                if(selectedOption>-1) {
                    documentTypeSelectedId = documentTypes.get(selectedOption).getDocument_type_id();
                }
                indexDocumentTypeSelected = selectedOption;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

    }

    private void loadData(){
        loadGenders();
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
                                 documentTypeNames.add(getResources().getString(R.string.DocumentTypeNonSelected));
                                 for (DocumentTypeViewModel documentTypeViewModel : documentTypes) {
                                     documentTypeNames.add(documentTypeViewModel.getDocument_type_name().concat(" - ").concat(documentTypeViewModel.getDocument_type_description()));
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


    private void loadGenders() {
        Call<ArrayList<GenderViewModel>> call = GenderApiAdapter.getApiService().getGenders();
        call.enqueue(new Callback<ArrayList<GenderViewModel>>() {

                         @Override
                         public void onResponse(Call<ArrayList<GenderViewModel>> call, Response<ArrayList<GenderViewModel>> response) {
                             if(response.isSuccessful())
                             {
                                 genders= response.body();
                                 if(genders.size()>0) {
                                     genderSelectedId = genders.get(UtilMethods.randomValue(genders.size())).getGender_id();
                                     loadNeigborhoods();
                                 }
                             }
                         }
                         @Override
                         public void onFailure(Call<ArrayList<GenderViewModel>> call, Throwable t) {
                             t.printStackTrace();
                             Toast.makeText(getApplicationContext(), PreferencesData.FailedToLoadSomeResources, Toast.LENGTH_LONG).show();
                         }
                     }
        );
    }

    private void loadNeigborhoods() {
        Call<ArrayList<NeighborhoodViewModel>> call = NeighborhoodApiAdapter.getApiService().getNeighborhoods();
        call.enqueue(new Callback<ArrayList<NeighborhoodViewModel>>() {

                         @Override
                         public void onResponse(Call<ArrayList<NeighborhoodViewModel>> call, Response<ArrayList<NeighborhoodViewModel>> response) {
                             if(response.isSuccessful()) {
                                 neighborhoods= response.body();
                                 if(neighborhoods.size()>0) {
                                     neighborhoodSelectedId = neighborhoods.get(UtilMethods.randomValue(neighborhoods.size())).getNeighborhood_id();
                                     loadDocumentTypes();
                                 }
                             }
                         }
                         @Override
                         public void onFailure(Call<ArrayList<NeighborhoodViewModel>> call, Throwable t) {
                             t.printStackTrace();
                             Toast.makeText(getApplicationContext(), PreferencesData.FailedToLoadSomeResources, Toast.LENGTH_LONG).show();
                         }
                     }
        );
    }

    public void register(View view) {

        String name, email,confirmEmail, password, confirmPassword,therapistDocument;
        int therapistDocumentTypeId=documentTypeSelectedId;
        int therapistGenderId=documentTypeSelectedId;
        int therapistNeighborhoodId=documentTypeSelectedId;

        therapistDocument=etTherapistDocument.getText().toString();
        name = etName.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        confirmEmail = etConfirmarEmail.getText().toString().trim();
        password = etUserPassword.getText().toString().trim();
        confirmPassword = etConfirmPassword.getText().toString().trim();
        List<DataValidation> dataToValidate = new ArrayList<>();

        dataToValidate.add(new DataValidation(name,"Nombre usuario").noEmptyValue().textMaxLength(30));
        dataToValidate.add(new DataValidation(email,"Correo electrónico").noEmptyValue().textMaxLength(30).textMinLength(11));
        dataToValidate.add(new DataValidation(String.valueOf(therapistDocumentTypeId),"Tipo de documento").selectedValue());
        dataToValidate.add(new DataValidation(String.valueOf(therapistGenderId),"Género").selectedValue());
        dataToValidate.add(new DataValidation(String.valueOf(therapistNeighborhoodId),"Barrio").selectedValue());
        dataToValidate.add(new DataValidation(therapistDocument,"Documento").noEmptyValue().textMaxLength(30));
        dataToValidate.add(new DataValidation(password,"Contraseña").noEmptyValue().textMaxLength(100).textMinLength(11));


        List<Object> rpta =ValidateInputs.validate().ValidateDataObject(dataToValidate);
        boolean checked = Boolean.parseBoolean(rpta.get(0).toString());
        String  msg = rpta.get(1).toString();


        if (!email.equals(confirmEmail)) {
            Toast.makeText(getApplicationContext(), PreferencesData.registerUserEmailDataMgs, Toast.LENGTH_LONG).show();
        }else if (!password.equals(confirmPassword)) {
            Toast.makeText(getApplicationContext(), PreferencesData.registerUserPasswordDataMgs, Toast.LENGTH_LONG).show();
        }else if(!emailValidation(email)){
            Toast.makeText(getApplicationContext(), PreferencesData.registerUserEmailBadPatternDataMgs, Toast.LENGTH_LONG).show();
        }else if(checked) {
                UserViewModel user = new UserViewModel(email, password, name, therapistDocument, documentTypeSelectedId,genderSelectedId,neighborhoodSelectedId);
                registerUser(user);
        }else{
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        }
    }
    public boolean emailValidation(String email) {
            String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
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
                            storeStringSharepreferences(PreferencesData.TherapistName, therapist.getTherapist_first_name());
                            storeStringSharepreferences(PreferencesData.TherapistEmail, therapist.getTherapist_email());
                            login(newUser);
                    } else if(response.raw().code()==409){
                            Toast.makeText(getApplicationContext(), PreferencesData.registerExistedUserMgs, Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getApplicationContext(), PreferencesData.registerUserFailedMgs, Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure (Call < TherapistViewModel > call, Throwable t){
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
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

