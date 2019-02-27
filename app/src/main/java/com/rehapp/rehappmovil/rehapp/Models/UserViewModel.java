package com.rehapp.rehappmovil.rehapp.Models;

import android.app.Application;
import android.arch.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {

    private String response;
    private String error;
    private int code;
    private int id;
    private String name;
    private String email;
    private String password;
    private String token;

    private UserViewModel ActualUser;


    public UserViewModel() {
    }

    public UserViewModel(String email, String password) {

        this.email = email;
        this.password = password;

    }

    public UserViewModel(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public UserViewModel(String token) {
        this.token=token;
    }

    public String getResponse() {
        return response;
    }

    public String getError() {
        return error;
    }

    public int getCode() {
        return code;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }




}
