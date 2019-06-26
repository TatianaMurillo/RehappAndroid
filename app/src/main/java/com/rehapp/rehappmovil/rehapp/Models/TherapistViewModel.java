package com.rehapp.rehappmovil.rehapp.Models;

import android.arch.lifecycle.ViewModel;

public class TherapistViewModel extends ViewModel {

        private int therapist_id;
        private String therapist_first_name;
        private String therapist_second_name;
        private String therapist_first_lastname;
        private String therapist_second_lastname;
        private int therapist_age;
        private GenderViewModel gender;
        private DocumentTypeViewModel documentType;
        private  NeighborhoodViewModel neighborhood;


    public TherapistViewModel(int therapist_id) {
        this.therapist_id = therapist_id;
    }

    public TherapistViewModel(int therapist_id, String therapist_first_name, String therapist_second_name, String therapist_first_lastname, String therapist_second_lastname, int therapist_age, GenderViewModel gender, DocumentTypeViewModel documentType, NeighborhoodViewModel neighborhood) {
        this.therapist_id = therapist_id;
        this.therapist_first_name = therapist_first_name;
        this.therapist_second_name = therapist_second_name;
        this.therapist_first_lastname = therapist_first_lastname;
        this.therapist_second_lastname = therapist_second_lastname;
        this.therapist_age = therapist_age;
        this.gender = gender;
        this.documentType = documentType;
        this.neighborhood = neighborhood;
    }

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

    public GenderViewModel getGender() {
        return gender;
    }

    public void setGender(GenderViewModel gender) {
        this.gender = gender;
    }

    public DocumentTypeViewModel getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentTypeViewModel documentType) {
        this.documentType = documentType;
    }

    public NeighborhoodViewModel getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(NeighborhoodViewModel neighborhood) {
        this.neighborhood = neighborhood;
    }
}
