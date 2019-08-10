package com.rehapp.rehappmovil.rehapp.Models;

public class GenderViewModel {

    private int gender_id;
    private String gender_description;
    private String gender_name;

    public GenderViewModel(int gender_id, String gender_description, String gender_name) {
        this.gender_id = gender_id;
        this.gender_description = gender_description;
        this.gender_name = gender_name;
    }

    public int getGender_id() {
        return gender_id;
    }

    public void setGender_id(int gender_id) {
        this.gender_id = gender_id;
    }

    public String getGender_description() {
        return gender_description;
    }

    public void setGender_description(String gender_description) {
        this.gender_description = gender_description;
    }

    public String getGender_name() {
        return gender_name;
    }

    public void setGender_name(String gender_name) {
        this.gender_name = gender_name;
    }
}
