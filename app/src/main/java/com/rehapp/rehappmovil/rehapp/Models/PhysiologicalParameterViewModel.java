package com.rehapp.rehappmovil.rehapp.Models;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;


public class PhysiologicalParameterViewModel extends ViewModel {

    private String physiological_parameter_name;
    private String questionnaire_id;
    private String physiological_parameter_id;
    private String unit_of_measure_name;
    private ArrayList<PhysiologicalParameterTherapyViewModel> therapy_value = new ArrayList<>();
    private ArrayList<OptionViewModel> option_selected= new ArrayList<>();


    public String getPhysiological_parameter_name() {
        return physiological_parameter_name;
    }

    public void setPhysiological_parameter_name(String physiological_parameter_name) {
        this.physiological_parameter_name = physiological_parameter_name;
    }

    public String getQuestionnaire_id() {
        return questionnaire_id;
    }

    public void setQuestionnaire_id(String questionnaire_id) {
        this.questionnaire_id = questionnaire_id;
    }

    public String getPhysiological_parameter_id() {
        return physiological_parameter_id;
    }

    public void setPhysiological_parameter_id(String physiological_parameter_id) {
        this.physiological_parameter_id = physiological_parameter_id;
    }

    public String getUnit_of_measure_name() {
        return unit_of_measure_name;
    }

    public void setUnit_of_measure_name(String unit_of_measure_name) {
        this.unit_of_measure_name = unit_of_measure_name;
    }

    public ArrayList<PhysiologicalParameterTherapyViewModel> getTherapy_value() {
        return therapy_value;
    }

    public void setTherapy_value(ArrayList<PhysiologicalParameterTherapyViewModel> therapy_value) {
        this.therapy_value = therapy_value;
    }

    public ArrayList<OptionViewModel> getOption_name() {
        return option_selected;
    }

    public void setOption_name(ArrayList<OptionViewModel> option_name) {
        this.option_selected = option_name;
    }

}
