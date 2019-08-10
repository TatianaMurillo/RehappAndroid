package com.rehapp.rehappmovil.rehapp.Utils.Constants;

public enum Therapist {

    DATA(8,0, 1, 2, 3, 4, 5, 6, 7, 8);


    int rowId;
    int therapist_id;
    int therapist_first_name;
    int therapist_second_name;
    int therapist_first_lastname;
    int therapist_second_lastname;
    int therapist_age;
    int gender;
    int documentType;
    int neighborhood;

    Therapist(int rowId, int therapist_id, int therapist_first_name, int therapist_second_name, int therapist_first_lastname, int therapist_second_lastname, int therapist_age, int gender, int documentType, int neighborhood) {
        this.rowId = rowId;
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

    public int getRowId() {
        return rowId;
    }

    public int getTherapist_id() {
        return therapist_id;
    }

    public int getTherapist_first_name() {
        return therapist_first_name;
    }

    public int getTherapist_second_name() {
        return therapist_second_name;
    }

    public int getTherapist_first_lastname() {
        return therapist_first_lastname;
    }

    public int getTherapist_second_lastname() {
        return therapist_second_lastname;
    }

    public int getTherapist_age() {
        return therapist_age;
    }

    public int getGender() {
        return gender;
    }

    public int getDocumentType() {
        return documentType;
    }

    public int getNeighborhood() {
        return neighborhood;
    }
}
