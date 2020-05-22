package com.rehapp.rehappmovil.rehapp.Models;

import android.arch.lifecycle.ViewModel;


public class PhysiologicalParameterViewModel extends ViewModel {

    private int physiological_parameter_id;
    private String physiological_parameter_name;
    private String physiological_parameter_description;
    private String questionnaire_id;



    public PhysiologicalParameterViewModel(String physiological_parameter_name) {
    this.physiological_parameter_name=physiological_parameter_name;
    }

    public PhysiologicalParameterViewModel() {
    }

    public int getPhysiological_parameter_id() {
        return physiological_parameter_id;
    }

    public void setPhysiological_parameter_id(int physiological_parameter_id) {
        this.physiological_parameter_id = physiological_parameter_id;
    }

    public String getPhysiological_parameter_name() {
        return physiological_parameter_name;
    }

    public void setPhysiological_parameter_name(String physiological_parameter_name) {
        this.physiological_parameter_name = physiological_parameter_name;
    }

    public String getPhysiological_parameter_description() {
        return physiological_parameter_description;
    }

    public void setPhysiological_parameter_description(String physiological_parameter_description) {
        this.physiological_parameter_description = physiological_parameter_description;
    }

    public String getQuestionnaire_id() {
        return questionnaire_id;
    }
}
