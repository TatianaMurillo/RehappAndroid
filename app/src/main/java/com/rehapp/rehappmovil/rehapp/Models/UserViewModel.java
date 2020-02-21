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

    private String therapist_document;
    private int document_type_id;

    private UserViewModel ActualUser;


    public UserViewModel() {
    }

    public UserViewModel(String email, String password) {

        this.email = email;
        this.password = password;

    }

    public UserViewModel(String email, String password, String name,String therapist_document,int document_type_id) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.therapist_document=therapist_document;
        this.document_type_id=document_type_id;
    }

    public UserViewModel(String token) {
        this.token=token;
    }

    /**
     *   getters
     */
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

            public String getEmail() {
                return email;
            }

            public String getPassword() {
                return password;
            }

            public String getToken() {
                return token;
            }

            public String getTherapist_document() {
                return therapist_document;
            }

            public int getDocument_type_id() {
                return document_type_id;
            }

    /**
     *   setters
     */

            public void setName(String name) {
                this.name = name;
            }


            public void setEmail(String email) {
                this.email = email;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public void setTherapist_document(String therapist_document) {
                this.therapist_document = therapist_document;
            }

            public void setDocument_type_id(int document_type_id) {
                this.document_type_id = document_type_id;
            }
}
