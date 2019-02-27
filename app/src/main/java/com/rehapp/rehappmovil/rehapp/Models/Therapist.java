package com.rehapp.rehappmovil.rehapp.Models;

import android.arch.lifecycle.ViewModel;

public class Therapist extends ViewModel {

        private int therapist_id;
        private String therapist_first_name;
        private String therapist_second_name;
        private String therapist_first_lastname;
        private String therapist_second_lastname;
        private int therapist_age;
        private int gender_id;
        private int document_type_id;
        private int neighborhood_id;

    public int getTherapist_id() {
        return therapist_id;
    }

    public void setTherapist_id(int therapist_id) {
        this.therapist_id = therapist_id;
    }

    public String getTherapist_first_name() {
        return therapist_first_name;
    }

    public void setTherapist_first_name(String therapist_first_name) {
        this.therapist_first_name = therapist_first_name;
    }

    public String getTherapist_second_name() {
        return therapist_second_name;
    }

    public void setTherapist_second_name(String therapist_second_name) {
        this.therapist_second_name = therapist_second_name;
    }

    public String getTherapist_first_lastname() {
        return therapist_first_lastname;
    }

    public void setTherapist_first_lastname(String therapist_first_lastname) {
        this.therapist_first_lastname = therapist_first_lastname;
    }

    public String getTherapist_second_lastname() {
        return therapist_second_lastname;
    }

    public void setTherapist_second_lastname(String therapist_second_lastname) {
        this.therapist_second_lastname = therapist_second_lastname;
    }

    public int getTherapist_age() {
        return therapist_age;
    }

    public void setTherapist_age(int therapist_age) {
        this.therapist_age = therapist_age;
    }

    public int getGender_id() {
        return gender_id;
    }

    public void setGender_id(int gender_id) {
        this.gender_id = gender_id;
    }

    public int getDocument_type_id() {
        return document_type_id;
    }

    public void setDocument_type_id(int document_type_id) {
        this.document_type_id = document_type_id;
    }

    public int getNeighborhood_id() {
        return neighborhood_id;
    }

    public void setNeighborhood_id(int neighborhood_id) {
        this.neighborhood_id = neighborhood_id;
    }
}
