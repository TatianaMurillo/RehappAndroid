package com.rehapp.rehappmovil.rehapp.Models;

import android.arch.lifecycle.ViewModel;



import java.io.Serializable;
import java.util.List;

public class TherapyViewModel extends ViewModel implements Serializable {


    private int institution_id;
    private int patient_id;
    private int therapist_id;
    private int therapy_achieved_the_goal;
    private String therapy_additional_information;
    private int therapy_id;
    private int therapy_sequence;
    private double therapy_total_duration;
    List<PhysiologicalParameterTherapyViewModel> physiologicalParametersIn;
    List<PhysiologicalParameterTherapyViewModel>  physiologicalParametersOut;
    List<ExerciseRoutinesViewModel> exerciseRoutinesViewModelList;

    private String therapy_description;
    private String therapy_observation;
    private String therapy_date;
    private String therapy_time;
    private InstitutionViewModel institucion;
    private PatientViewModel patient;
    private TherapistViewModel therapistViewModel;
    private String created_at;
    private String updated_at;

    public TherapyViewModel(){}

    public TherapyViewModel(int institution_id, int patient_id, int therapist_id, int therapy_achieved_the_goal, String therapy_additional_information, int therapy_id, int therapy_sequence, double therapy_total_duration, String therapy_description, String therapy_observation, String therapy_date, String therapy_time, InstitutionViewModel institucion, PatientViewModel patient, TherapistViewModel therapistViewModel) {
        this.institution_id = institution_id;
        this.patient_id = patient_id;
        this.therapist_id = therapist_id;
        this.therapy_achieved_the_goal = therapy_achieved_the_goal;
        this.therapy_additional_information = therapy_additional_information;
        this.therapy_id = therapy_id;
        this.therapy_sequence = therapy_sequence;
        this.therapy_total_duration = therapy_total_duration;
        this.therapy_description = therapy_description;
        this.therapy_observation = therapy_observation;
        this.therapy_date = therapy_date;
        this.therapy_time = therapy_time;
        this.institucion = institucion;
        this.patient = patient;
        this.therapistViewModel = therapistViewModel;
    }

    public int getTherapy_id() {
        return therapy_id;
    }

    public void setTherapy_id(int therapy_id) {
        this.therapy_id = therapy_id;
    }

    public String getTherapy_description() {
        return therapy_description;
    }

    public void setTherapy_description(String therapy_description) {
        this.therapy_description = therapy_description;
    }

    public double getTherapy_total_duration() {
        return therapy_total_duration;
    }

    public void setTherapy_total_duration(double therapy_total_duration) {
        this.therapy_total_duration = therapy_total_duration;
    }

    public String getTherapy_observation() {
        return therapy_observation;
    }

    public void setTherapy_observation(String therapy_observation) {
        this.therapy_observation = therapy_observation;
    }

    public int getTherapy_sequence() {
        return therapy_sequence;
    }

    public void setTherapy_sequence(int therapy_sequence) {
        this.therapy_sequence = therapy_sequence;
    }

    public int isTherapy_achieved_the_goal() {
        return therapy_achieved_the_goal;
    }

    public void setTherapy_achieved_the_goal(int therapy_achieved_the_goal) {
        this.therapy_achieved_the_goal = therapy_achieved_the_goal;
    }

    public int getTherapist_id() {
        return therapist_id;
    }

    public void setTherapist_id(int therapist_id) {
        this.therapist_id = therapist_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getInstitution_id() {
        return institution_id;
    }

    public void setInstitution_id(int institution_id) {
        this.institution_id = institution_id;
    }

    public void setCreated_at(String created_at) {
         this.created_at=created_at;
    }

    public void setUpdated_at(String Updated_at) {
        this.updated_at=Updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getTherapy_date() { return therapy_date; }

    public void setTherapy_date(String therapy_date) {
        this.therapy_date = therapy_date;
    }

    public String getTherapy_time() {
        return therapy_time;
    }

    public void setTherapy_time(String therapy_time) {
        this.therapy_time = therapy_time;
    }

    public InstitutionViewModel getInstitucion() {
        return institucion;
    }

    public void setInstitucion(InstitutionViewModel institucion) {
        this.institucion = institucion;
    }

    public PatientViewModel getPatient() {
        return patient;
    }

    public void setPatient(PatientViewModel patient) {
        this.patient = patient;
    }

    public TherapistViewModel getTherapistViewModel() {
        return therapistViewModel;
    }

    public void setTherapistViewModel(TherapistViewModel therapistViewModel) {
        this.therapistViewModel = therapistViewModel;
    }

    public String getTherapy_additional_information() {
        return therapy_additional_information;
    }

    public void setTherapy_additional_information(String therapy_additional_information){
        this.therapy_additional_information = therapy_additional_information;
    }

    public List<PhysiologicalParameterTherapyViewModel> getPhysiologicalParametersIn() {
        return physiologicalParametersIn;
    }

    public void setPhysiologicalParametersIn(List<PhysiologicalParameterTherapyViewModel> physiologicalParametersIn) {
        this.physiologicalParametersIn = physiologicalParametersIn;
    }

    public List<PhysiologicalParameterTherapyViewModel> getPhysiologicalParametersOut() {
        return physiologicalParametersOut;
    }

    public void setPhysiologicalParametersOut(List<PhysiologicalParameterTherapyViewModel> physiologicalParametersOut) {
        this.physiologicalParametersOut = physiologicalParametersOut;
    }

    public List<ExerciseRoutinesViewModel> getExerciseRoutinesViewModelList() {
        return exerciseRoutinesViewModelList;
    }

    public void setExerciseRoutinesViewModelList(List<ExerciseRoutinesViewModel> exerciseRoutinesViewModelList) {
        this.exerciseRoutinesViewModelList = exerciseRoutinesViewModelList;
    }
}
