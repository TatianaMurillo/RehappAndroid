package com.rehapp.rehappmovil.rehapp.Models;

public class VitalSignViewModel {

    private String vital_sign_id;
    private String vital_sign_name;
    private String vital_sign_description;
    private String patient_vital_signs_value;
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

    public String getVital_sign_id() {
        return vital_sign_id;
    }

    public void setVital_sign_id(String vital_sign_id) {
        this.vital_sign_id = vital_sign_id;
    }

    public String getVital_sign_value() {
        return patient_vital_signs_value;
    }

    public void setVital_sign_value(String vital_sign_value) {
        this.patient_vital_signs_value = vital_sign_value;
    }
}
