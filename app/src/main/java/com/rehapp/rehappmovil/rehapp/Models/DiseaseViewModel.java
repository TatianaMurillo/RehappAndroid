package com.rehapp.rehappmovil.rehapp.Models;

public class DiseaseViewModel {

    private String disease_id;
    private String disease_name;
    private String disease_description;
    private String patient_medical_history_id;
    private String ptnt_mdcl_hstry_id;
    private String patient_disease_is_base;
    boolean isSelected;
    private String created_at;
    private String  updated_at;

    public DiseaseViewModel(){}
    public DiseaseViewModel(boolean isSelected, String disease_name) {
        this.isSelected = isSelected;
        this.disease_name = disease_name;
    }

    public String getDisease_id() {
        return disease_id;
    }

    public void setDisease_id(String disease_id) {
        this.disease_id = disease_id;
    }

    public String getDisease_name() {
        return disease_name;
    }

    public void setDisease_name(String disease_name) {
        this.disease_name = disease_name;
    }

    public String getDisease_description() {
        return disease_description;
    }

    public void setDisease_description(String disease_description) {
        this.disease_description = disease_description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getPatient_medical_history_id() {
        return patient_medical_history_id;
    }

    public void setPatient_medical_history_id(String patient_medical_history_id) {
        this.patient_medical_history_id = patient_medical_history_id;
    }

    public String getPatient_disease_is_base() {
        return patient_disease_is_base;
    }

    public void setPatient_disease_is_base(String patient_disease_is_base) {
        this.patient_disease_is_base = patient_disease_is_base;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getPtnt_mdcl_hstry_id() {
        return ptnt_mdcl_hstry_id;
    }

    public void setPtnt_mdcl_hstry_id(String ptnt_mdcl_hstry_id) {
        this.ptnt_mdcl_hstry_id = ptnt_mdcl_hstry_id;
    }
}
