package com.rehapp.rehappmovil.rehapp.Models;

public class PatientMedicalHistoryDiseaseViewModel {

   private String disease_id;
   private String ptnt_mdcl_hstry_id;
   private String patient_disease_is_base;
   private String ptnt_mdcl_hstry_disease_id;

   public PatientMedicalHistoryDiseaseViewModel() { }

    public String getDisease_id() {
        return disease_id;
    }

    public void setDisease_id(String disease_id) {
        this.disease_id = disease_id;
    }

    public String getPtnt_mdcl_hstry_id() {
        return ptnt_mdcl_hstry_id;
    }

    public void setPtnt_mdcl_hstry_id(String ptnt_mdcl_hstry_id) {
        this.ptnt_mdcl_hstry_id = ptnt_mdcl_hstry_id;
    }

    public String getPatient_disease_is_base() {
        return patient_disease_is_base;
    }

    public void setPatient_disease_is_base(String patient_disease_is_base) {
        this.patient_disease_is_base = patient_disease_is_base;
    }

    public String getPtnt_mdcl_hstry_disease_id() {
        return ptnt_mdcl_hstry_disease_id;
    }

    public void setPtnt_mdcl_hstry_disease_id(String ptnt_mdcl_hstry_disease_id) {
        this.ptnt_mdcl_hstry_disease_id = ptnt_mdcl_hstry_disease_id;
    }
}
