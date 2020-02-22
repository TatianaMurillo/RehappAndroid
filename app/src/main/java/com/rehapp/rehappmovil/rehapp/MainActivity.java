package com.rehapp.rehappmovil.rehapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rehapp.rehappmovil.rehapp.IO.APIADAPTERS.UserApiAdapter;
import com.rehapp.rehappmovil.rehapp.Models.PreferencesData;
import com.rehapp.rehappmovil.rehapp.Models.UserViewModel;
import com.rehapp.rehappmovil.rehapp.fragments.HistoryTherapiesPatientFragment;
import com.rehapp.rehappmovil.rehapp.fragments.MedicalHistoriesPatientFragment;
import com.rehapp.rehappmovil.rehapp.fragments.MedicalHistoryDetailFragment;
import com.rehapp.rehappmovil.rehapp.fragments.SearchCreatePatientFragment;
import com.rehapp.rehappmovil.rehapp.fragments.SearchPatientFragment;
import com.rehapp.rehappmovil.rehapp.fragments.TherapistFragment;
import com.rehapp.rehappmovil.rehapp.fragments.TherapyDetailFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences sharedpreferences;
    ImageView imageViewCircle;
    TextView tvTherapistName;
    TextView tvTherapistEmail;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sharedpreferences = getSharedPreferences(PreferencesData.PreferenceFileName, Context.MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        view= LayoutInflater.from(this).inflate(R.layout.nav_header_main,null);
        navigationView.addHeaderView(view);
        imageViewCircle=view.findViewById(R.id.imageViewCircle);
        tvTherapistName=view.findViewById(R.id.tvTherapistName);;
        tvTherapistEmail=view.findViewById(R.id.tvTherapistEmail);;
        imageViewCircle.setImageResource(R.drawable.profile);

        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        recoveryData();
        loadData();
    }

    private void loadData(){
        loadFragment(new SearchCreatePatientFragment());
    }

    private void recoveryData(){
        String therapistName = sharedpreferences.getString(PreferencesData.TherapistName,"");
        String therapistEmail = sharedpreferences.getString(PreferencesData.TherapistEmail,"");

        tvTherapistName.setText(therapistName);
        tvTherapistEmail.setText(therapistEmail);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            IsFinish("¿Cerrar sesion?");
        }
    }

    public void IsFinish(String alertmessage) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        logout();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(alertmessage)
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    private void logout(){
        String token = sharedpreferences.getString(PreferencesData.loginToken,"");
        if(!"".equals(token)){
            UserViewModel user = new UserViewModel();
            user.setToken(token);

            Call<UserViewModel> call = UserApiAdapter.getApiService().logout(user);
            call.enqueue(new Callback<UserViewModel>() {
                @Override
                public void onResponse(Call<UserViewModel> call, Response<UserViewModel> response) {
                    if(response.isSuccessful()){
                        Intent intent = new Intent(MainActivity.this,Login.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(), response.raw().message(),Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<UserViewModel> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), PreferencesData.LogoutError,Toast.LENGTH_LONG).show();
                }
            });
        }else{
            Toast.makeText(getApplicationContext(), PreferencesData.LogoutToken,Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        showHideItems(menu);
        return true;
    }

    public void showHideItems(Menu menu)
    {
        MenuItem item;
        item= menu.findItem(R.id.create_therapy);
        item.setVisible(false);
        item= menu.findItem(R.id.save);
        item.setVisible(false);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.therapies:
                checkHistoryTherapiesPatientFragment();
                break;
            case R.id.therapy_detail:
                checkTherapyDetailFragment();
                break;
            case R.id.medical_histories:
                checkMedicalHistoriesFragment();
                break;
            case R.id.medical_history_detail:
                checkMedicalHistoryDetailFragment();
                break;
            case R.id.menu_exercises:
                loadFragment(null);
                break;
            case R.id.seach_create_patient:
                loadFragment(new SearchCreatePatientFragment());
                break;
            case R.id.search_patient:
                checkSearchPatient();
                break;
            case R.id.therapist:
                checkTherapistFragment();
                break;
            case R.id.logout:
                logout();
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void checkTherapistFragment(){
        int therapistId=sharedpreferences.getInt(PreferencesData.TherapistId,0);
        if(therapistId==0){
            loadFragment(new SearchCreatePatientFragment());
            Toast.makeText(getApplicationContext(), PreferencesData.TherapistFragment,Toast.LENGTH_LONG).show();
        }else{
            loadFragment(new TherapistFragment());
        }
    }

    private void checkTherapyDetailFragment(){
        String therapySelectedId= String.valueOf(sharedpreferences.getInt(PreferencesData.TherapyId,0));
        String patientId=sharedpreferences.getString(PreferencesData.PatientId,"");
        if(patientId.equals("")){
            loadFragment(new SearchCreatePatientFragment());
            Toast.makeText(getApplicationContext(), PreferencesData.SearchCreatePatientFragment,Toast.LENGTH_LONG).show();
        } else if(therapySelectedId.equals("0")){
                loadFragment(new HistoryTherapiesPatientFragment());
                Toast.makeText(getApplicationContext(), PreferencesData.HistoryTherapiesPatientFragment,Toast.LENGTH_LONG).show();
        }else{
                loadFragment(new TherapyDetailFragment());
        }
    }

    private void checkHistoryTherapiesPatientFragment(){
        String patientId=sharedpreferences.getString(PreferencesData.PatientId,"");
        if(patientId.equals("")){
            loadFragment(new SearchCreatePatientFragment());
            Toast.makeText(getApplicationContext(), PreferencesData.SearchCreatePatientFragment,Toast.LENGTH_LONG).show();
        }else{
            loadFragment(new HistoryTherapiesPatientFragment());
        }
    }

    private void checkMedicalHistoriesFragment(){
        String patientId=sharedpreferences.getString(PreferencesData.PatientId,"");
        if(patientId.equals("")){
            loadFragment(new SearchCreatePatientFragment());
            Toast.makeText(getApplicationContext(), PreferencesData.SearchCreatePatientFragment,Toast.LENGTH_LONG).show();
        }else{
            loadFragment(new MedicalHistoriesPatientFragment());
        }
    }

    private void checkMedicalHistoryDetailFragment(){

            String documentPatient=sharedpreferences.getString(PreferencesData.PatientDocument,"");
            String medicalHistory=sharedpreferences.getString(PreferencesData.MedicalHistorySelectedId,"");
            if(documentPatient.equals("")){
                loadFragment(new SearchCreatePatientFragment());
                Toast.makeText(getApplicationContext(), PreferencesData.SearchCreatePatientFragment,Toast.LENGTH_LONG).show();
            }else if (medicalHistory.equals("")){
                loadFragment(new MedicalHistoriesPatientFragment());
                Toast.makeText(getApplicationContext(), PreferencesData.MedicalHistoriesPatientFragment,Toast.LENGTH_LONG).show();
            }else{
                loadFragment(new MedicalHistoryDetailFragment());
            }

    }
    private void checkSearchPatient(){
        String documentPatient=sharedpreferences.getString(PreferencesData.PatientDocument,"");
        int documentTypePatientId=Integer.parseInt(sharedpreferences.getString(PreferencesData.PatientTpoDocument,"0"));

        if(documentPatient.equals("") || documentTypePatientId==0){
            loadFragment(new SearchCreatePatientFragment());
            Toast.makeText(getApplicationContext(), PreferencesData.SearchCreatePatientFragment,Toast.LENGTH_LONG).show();
        }else{
            loadFragment(new SearchPatientFragment());
        }
    }

    public void loadFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content,fragment).commit();
    }
}
