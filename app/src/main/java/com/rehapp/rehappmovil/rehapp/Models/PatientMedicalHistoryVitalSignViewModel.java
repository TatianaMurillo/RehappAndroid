package com.rehapp.rehappmovil.rehapp.Models;

public class PatientMedicalHistoryVitalSignViewModel {

    private String patient_medical_history_vital_sign_id;
    private String patient_vital_signs_value;
    private String   ptnt_mdcl_hstry_id;
    private String vital_sign_id;
    private String created_at;
    private String  updated_at;


    public String getPatient_vital_signs_value() {
        return patient_vital_signs_value;
    }

    public void setPatient_vital_signs_value(String patient_vital_signs_value) {
        this.patient_vital_signs_value = patient_vital_signs_value;
    }

    public String getPtnt_mdcl_hstry_id() {
        return ptnt_mdcl_hstry_id;
    }

    public void setPtnt_mdcl_hstry_id(String ptnt_mdcl_hstry_id) {
        this.ptnt_mdcl_hstry_id = ptnt_mdcl_hstry_id;
    }

    public String getVital_sign_id() {
        return vital_sign_id;
    }

    public void setVital_sign_id(String vital_sign_id) {
        this.vital_sign_id = vital_sign_id;
    }

    public String getPatient_vital_signs_id() {
        return patient_medical_history_vital_sign_id;
    }

    public void setPatient_vital_signs_id(String patient_vital_signs_id) {
        this.patient_medical_history_vital_sign_id = patient_vital_signs_id;
    }
}
