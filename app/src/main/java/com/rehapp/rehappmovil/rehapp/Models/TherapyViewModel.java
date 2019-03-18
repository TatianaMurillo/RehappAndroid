package com.rehapp.rehappmovil.rehapp.Models;

import android.arch.lifecycle.ViewModel;


import java.io.Serializable;
import java.util.ArrayList;

public class TherapyViewModel extends ViewModel implements Serializable {

    private int therapy_id;
    private String therapy_description;
    private double therapy_total_duration;
    private String therapy_observation;
    private int therapy_sequence;
    private boolean therapy_achieved_the_goal;
    private int therapist_id;
    private int patient_id;
    private int institution_id;
    private String created_at;
    private String updated_at;

    public TherapyViewModel(int therapy_id, String therapy_description, double therapy_total_duration, String therapy_observation, int therapy_sequence, boolean therapy_achieved_the_goal, int therapist_id, int patient_id, int institution_id) {
        this.therapy_id = therapy_id;
        this.therapy_description = therapy_description;
        this.therapy_total_duration = therapy_total_duration;
        this.therapy_observation = therapy_observation;
        this.therapy_sequence = therapy_sequence;
        this.therapy_achieved_the_goal = therapy_achieved_the_goal;
        this.therapist_id = therapist_id;
        this.patient_id = patient_id;
        this.institution_id = institution_id;
    }

    public TherapyViewModel() {
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

    public boolean isTherapy_achieved_the_goal() {
        return therapy_achieved_the_goal;
    }

    public void setTherapy_achieved_the_goal(boolean therapy_achieved_the_goal) {
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
}
