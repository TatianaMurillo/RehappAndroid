package com.rehapp.rehappmovil.rehapp.Models;

public class QuestionariesViewModel {


    private String questionnaire_id;
    private String questionnaire_name;
    private String questionnaire_description;
    private String created_at;
    private String updated_at;

    public QuestionariesViewModel(){}

    public String getQuestionnaire_id() {
        return questionnaire_id;
    }

    public void setQuestionnaire_id(String questionnaire_id) {
        this.questionnaire_id = questionnaire_id;
    }

    public String getQuestionnaire_name() {
        return questionnaire_name;
    }

    public void setQuestionnaire_name(String questionnaire_name) {
        this.questionnaire_name = questionnaire_name;
    }

    public String getQuestionnaire_description() {
        return questionnaire_description;
    }

    public void setQuestionnaire_description(String questionnaire_description) {
        this.questionnaire_description = questionnaire_description;
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
