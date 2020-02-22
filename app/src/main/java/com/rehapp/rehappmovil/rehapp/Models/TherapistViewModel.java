package com.rehapp.rehappmovil.rehapp.Models;

import android.arch.lifecycle.ViewModel;

public class TherapistViewModel extends ViewModel {

        private int therapist_id;
        private String therapist_first_name;
        private String therapist_second_name;
        private String therapist_first_lastname;
        private String therapist_second_lastname;
        private String therapist_email;
        private String therapist_document;
        private int therapist_age;
        private int gender_id;
        private int document_type_id;
        private  int neighborhood_id;

    public TherapistViewModel() { }

    public TherapistViewModel(int therapist_id) {
        this.therapist_id = therapist_id;
    }

    public TherapistViewModel(int therapist_id, String therapist_first_name, String therapist_second_name, String therapist_first_lastname, String therapist_second_lastname, int therapist_age, int genderId, int documentTypeId, int neighborhoodId,String therapist_email) {
        this.therapist_id = therapist_id;
        this.therapist_first_name = therapist_first_name;
        this.therapist_second_name = therapist_second_name;
        this.therapist_first_lastname = therapist_first_lastname;
        this.therapist_second_lastname = therapist_second_lastname;
        this.therapist_age = therapist_age;
        this.gender_id = genderId;
        this.document_type_id = documentTypeId;
        this.neighborhood_id = neighborhoodId;
        this.therapist_email=therapist_email;
    }

    /**
     *
     * Getter
     */
        public int getTherapist_id() {
            return therapist_id;
        }
        public String getTherapist_first_name() {
            return therapist_first_name;
        }
        public String getTherapist_second_name() {
            return therapist_second_name;
        }

        public String getTherapist_first_lastname() {
            return therapist_first_lastname;
        }
        public String getTherapist_second_lastname() {
            return therapist_second_lastname;
        }
        public int getTherapist_age() {
            return therapist_age;
        }
        public int getGenderId() {
            return gender_id;
        }

        public int getDocumentTypeId() {
            return document_type_id;
        }
        public int getNeighborhoodId() {
            return neighborhood_id;
        }

        public String getTherapist_document() {
            return therapist_document;
        }

        public String getTherapist_email() {
            return therapist_email;
        }

    /**
     *
     * Setter
     */

        public void setTherapist_document(String therapist_document) {
        this.therapist_document = therapist_document;
    }

        public void setTherapist_id(int therapist_id) {
            this.therapist_id = therapist_id;
        }

        public void setTherapist_first_name(String therapist_first_name) {
            this.therapist_first_name = therapist_first_name;
        }

        public void setTherapist_second_name(String therapist_second_name) {
            this.therapist_second_name = therapist_second_name;
        }
        public void setTherapist_first_lastname(String therapist_first_lastname) {
            this.therapist_first_lastname = therapist_first_lastname;
        }

        public void setTherapist_second_lastname(String therapist_second_lastname) {
            this.therapist_second_lastname = therapist_second_lastname;
        }

        public void setTherapist_age(int therapist_age) {
            this.therapist_age = therapist_age;
        }

        public void setGender(int genderId) {
            this.gender_id = genderId;
        }

        public void setDocumentType(int documentTypeId) {
            this.document_type_id = documentTypeId;
        }

        public void setNeighborhood(int neighborhoodId) {
            this.neighborhood_id = neighborhoodId;
        }

        public void setTherapist_email(String therapist_email) {
            this.therapist_email = therapist_email;
        }
}
