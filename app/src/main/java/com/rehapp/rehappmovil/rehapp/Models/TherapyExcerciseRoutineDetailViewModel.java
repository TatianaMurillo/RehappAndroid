package com.rehapp.rehappmovil.rehapp.Models;

import java.util.ArrayList;

public class TherapyExcerciseRoutineDetailViewModel {


    private int therapy_exercise_routine_detail_id;

    private String  therapy_exercise_routine_value;
    private String  therapy_exercise_routine_option_id;
    private String  therapy_exercise_routine_observation;
    private String  therapy_exercise_routine_preconditions;
    private String  therapy_exercise_routine_id;

    private String  questionnaireId;
    private String  questionnaireName;
    private String  unit_of_measure_name;
    private ArrayList<QuestionaryOptionViewModel> options = new ArrayList<>();


    public TherapyExcerciseRoutineDetailViewModel() { }

    public int getTherapy_exercise_routine_detail_id() {
        return therapy_exercise_routine_detail_id;
    }

    public void setTherapy_exercise_routine_detail_id(int therapy_exercise_routine_detail_id) {
        this.therapy_exercise_routine_detail_id = therapy_exercise_routine_detail_id;
    }

    public String getTherapy_exercise_routine_value() {
        return therapy_exercise_routine_value;
    }

    public void setTherapy_exercise_routine_value(String therapy_exercise_routine_value) {
        this.therapy_exercise_routine_value = therapy_exercise_routine_value;
    }

    public String getTherapy_exercise_routine_option_id() {
        return therapy_exercise_routine_option_id;
    }

    public void setTherapy_exercise_routine_option_id(String therapy_exercise_routine_option_id) {
        this.therapy_exercise_routine_option_id = therapy_exercise_routine_option_id;
    }

    public String getTherapy_exercise_routine_observation() {
        return therapy_exercise_routine_observation;
    }

    public void setTherapy_exercise_routine_observation(String therapy_exercise_routine_observation) {
        this.therapy_exercise_routine_observation = therapy_exercise_routine_observation;
    }

    public String getTherapy_exercise_routine_preconditions() {
        return therapy_exercise_routine_preconditions;
    }

    public void setTherapy_exercise_routine_preconditions(String therapy_exercise_routine_preconditions) {
        this.therapy_exercise_routine_preconditions = therapy_exercise_routine_preconditions;
    }

    public String getTherapy_exercise_routine_id() {
        return therapy_exercise_routine_id;
    }

    public void setTherapy_exercise_routine_id(String therapy_exercise_routine_id) {
        this.therapy_exercise_routine_id = therapy_exercise_routine_id;
    }

    public String getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(String questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public String getUnit_of_measure_name() {
        return unit_of_measure_name;
    }

    public void setUnit_of_measure_name(String unit_of_measure_name) {
        this.unit_of_measure_name = unit_of_measure_name;
    }

    public ArrayList<QuestionaryOptionViewModel> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<QuestionaryOptionViewModel> options) {
        this.options = options;
    }

    public String getQuestionnaireName() {
        return questionnaireName;
    }

    public void setQuestionnaireName(String questionnaireName) {
        this.questionnaireName = questionnaireName;
    }
}
