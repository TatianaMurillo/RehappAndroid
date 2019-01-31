package com.rehapp.rehappmovil.rehapp.Models;

public class User {

    private String response;
    private String error;
    private int code;
    private int id;
    private String name;
    private String email;
    private String password;
    private String token;

    private User ActualUser;


    public User(String email, String password) {

        this.email = email;
        this.password = password;

    }

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User(String token) {
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
