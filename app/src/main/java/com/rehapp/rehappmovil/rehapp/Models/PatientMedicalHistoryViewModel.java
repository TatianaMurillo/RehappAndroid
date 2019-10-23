package com.rehapp.rehappmovil.rehapp.Models;

public class PatientMedicalHistoryViewModel {

    private String ptnt_mdcl_hstry_id;
    private String patient_id;
    private String  ptnt_mdcl_hstry_addtnl_info;
    private String  ptnt_mdcl_hstry_name;
    private String created_at;
    private String updated_at;

    public PatientMedicalHistoryViewModel() {
    }

    public String getPtnt_mdcl_hstry_id() {
        return ptnt_mdcl_hstry_id;
    }

    public void setPtnt_mdcl_hstry_id(String ptnt_mdcl_hstry_id) {
        this.ptnt_mdcl_hstry_id = ptnt_mdcl_hstry_id;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getPtnt_mdcl_hstry_addtnl_info() {
        return ptnt_mdcl_hstry_addtnl_info;
    }

    public void setPtnt_mdcl_hstry_addtnl_info(String ptnt_mdcl_hstry_addtnl_info) {
        this.ptnt_mdcl_hstry_addtnl_info = ptnt_mdcl_hstry_addtnl_info;
    }

    public String getPtnt_mdcl_hstry_name() {
        return ptnt_mdcl_hstry_name;
    }

    public void setPtnt_mdcl_hstry_name(String ptnt_mdcl_hstry_name) {
        this.ptnt_mdcl_hstry_name = ptnt_mdcl_hstry_name;
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
}
