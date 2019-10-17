package com.rehapp.rehappmovil.rehapp.Models;

public class VitalSignViewModel {

    private String vital_sign_name;
    private String vital_sign_description;
    private String created_at;
    private String  updated_at;

    public String getVital_sign_name() {
        return vital_sign_name;
    }

    public void setVital_sign_name(String vital_sign_name) {
        this.vital_sign_name = vital_sign_name;
    }

    public String getVital_sign_description() {
        return vital_sign_description;
    }

    public void setVital_sign_description(String vital_sign_description) {
        this.vital_sign_description = vital_sign_description;
    }
}
