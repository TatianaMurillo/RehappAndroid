package com.rehapp.rehappmovil.rehapp.Models;

public class PatientMedicalHistoryQuestionaireOptionViewModel {

    private String ptnt_mdcl_hstry_id;
    private String questionnaire_id;
    private String option_id;
    private String option_name;
    private  String option_description;


    public String getPtnt_mdcl_hstry_id() {
        return ptnt_mdcl_hstry_id;
    }

    public void setPtnt_mdcl_hstry_id(String ptnt_mdcl_hstry_id) {
        this.ptnt_mdcl_hstry_id = ptnt_mdcl_hstry_id;
    }

    public String getQuestionnaire_id() {
        return questionnaire_id;
    }

    public void setQuestionnaire_id(String questionnaire_id) {
        this.questionnaire_id = questionnaire_id;
    }

    public String getOption_id() {
        return this.option_id;
    }

    public void setOption_id(String option_id) {
        this.option_id = option_id;
    }

    public String getOption_name() {
        return option_name;
    }

    public void setOption_name(String option_name) {
        this.option_name = option_name;
    }

    public String getOption_description() {
        return option_description;
    }

    public void setOption_description(String option_description) {
        this.option_description = option_description;
    }



}
