package com.rehapp.rehappmovil.rehapp.Models;

import android.arch.lifecycle.ViewModel;

import com.google.gson.annotations.SerializedName;

public class PatientViewModel extends ViewModel {

        private float patient_id;
        private String patient_first_name;
        private String patient_second_name;
        private String patient_first_lastname;
        private String patient_second_lastname;
        private String patient_document;
        private float patient_age;
        private String patient_address;
        private String patient_mobile_number;
        private String patient_landline_phone;
        private String patient_additional_data;
        private float neighborhood_id;
        private float document_type_id;
        private float gender_id;
        private DocumentTypeViewModel Document_typeObject;
        private NeighborhoodViewModel neighborhoodViewModelObject;

        @SerializedName("error")
        private String patient_code_description;

        @SerializedName("code_error")
        private int patient_code;


        // Getter Methods

        public float getPatient_id() {
            return patient_id;
        }

        public String getPatient_first_name() {
            return patient_first_name;
        }

        public String getPatient_second_name() {
            return patient_second_name;
        }

        public String getPatient_first_lastname() {
            return patient_first_lastname;
        }

        public String getPatient_second_lastname() {
            return patient_second_lastname;
        }

        public String getPatient_document() {
            return patient_document;
        }

        public float getPatient_age() {
            return patient_age;
        }

        public String getPatient_address() {
            return patient_address;
        }

        public String getPatient_mobile_number() {
            return patient_mobile_number;
        }

        public String getPatient_landline_phone() {
            return patient_landline_phone;
        }

        public String getPatient_additional_data() {
            return patient_additional_data;
        }

        public float getNeighborhood_id() {
            return neighborhood_id;
        }

        public float getDocument_type_id() {
            return document_type_id;
        }

        public float getGender_id() {
            return gender_id;
        }

        public DocumentTypeViewModel getDocument_type() {
            return Document_typeObject;
        }

        public NeighborhoodViewModel getNeighborhood() {
            return neighborhoodViewModelObject;
        }

        // Setter Methods

        public void setPatient_id(float patient_id) {
            this.patient_id = patient_id;
        }

        public void setPatient_first_name(String patient_first_name) {
            this.patient_first_name = patient_first_name;
        }

        public void setPatient_second_name(String patient_second_name) {
            this.patient_second_name = patient_second_name;
        }

        public void setPatient_first_lastname(String patient_first_lastname) {
            this.patient_first_lastname = patient_first_lastname;
        }

        public void setPatient_second_lastname(String patient_second_lastname) {
            this.patient_second_lastname = patient_second_lastname;
        }

        public void setPatient_document(String patient_document) {
            this.patient_document = patient_document;
        }

        public void setPatient_age(float patient_age) {
            this.patient_age = patient_age;
        }

        public void setPatient_address(String patient_address) {
            this.patient_address = patient_address;
        }

        public void setPatient_mobile_number(String patient_mobile_number) {
            this.patient_mobile_number = patient_mobile_number;
        }

        public void setPatient_landline_phone(String patient_landline_phone) {
            this.patient_landline_phone = patient_landline_phone;
        }

        public void setPatient_additional_data(String patient_additional_data) {
            this.patient_additional_data = patient_additional_data;
        }

        public void setNeighborhood_id(float neighborhood_id) {
            this.neighborhood_id = neighborhood_id;
        }

        public void setDocument_type_id(float document_type_id) {
            this.document_type_id = document_type_id;
        }

        public void setGender_id(float gender_id) {
            this.gender_id = gender_id;
        }

        public void setDocument_type(DocumentTypeViewModel document_typeObject) {
            this.Document_typeObject = document_typeObject;
        }

        public void setNeighborhood(NeighborhoodViewModel neighborhoodViewModelObject) {
            this.neighborhoodViewModelObject = neighborhoodViewModelObject;
        }
    }


