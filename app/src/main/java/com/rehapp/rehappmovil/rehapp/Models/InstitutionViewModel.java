package com.rehapp.rehappmovil.rehapp.Models;

public class InstitutionViewModel {

    private int institution_id;
    private String institution_name;
    private String institution_additional_info;


    public InstitutionViewModel(int institution_id, String institution_name, String institution_additional_info)
    {
        this.institution_id=institution_id;
        this.institution_name=institution_name;
        this.institution_additional_info=institution_additional_info;
    }

    public int getInstitution_id() {
        return institution_id;
    }

    public void setInstitution_id(int institution_id) {
        this.institution_id = institution_id;
    }

    public String getInstitution_name() {
        return institution_name;
    }

    public void setInstitution_name(String institution_name) {
        this.institution_name = institution_name;
    }

    public String getInstitution_additional_info() {
        return institution_additional_info;
    }

    public void setInstitution_additional_info(String institution_additional_info) {
        this.institution_additional_info = institution_additional_info;
    }
}
