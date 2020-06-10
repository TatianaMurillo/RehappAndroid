package com.rehapp.rehappmovil.rehapp.Models;

import java.util.ArrayList;

public class TherapyExcerciseRoutineViewModel {


    private int therapy_excercise_routine_id;
    private float therapy_excercise_routine_duration;
    private String therapy_excercise_routine_observation;
    private int therapy_id;
    private int exercise_routine_id;
    private String preconditions;
    private ArrayList<QuestionaryOptionViewModel> options= new ArrayList<>();



    public TherapyExcerciseRoutineViewModel() { }

    public int getTherapyExcerciseRoutineId() {
        return therapy_excercise_routine_id;
    }
    public float getTherapyExcerciseRoutineDuration() {
        return therapy_excercise_routine_duration;
    }
    public String getTherapyExcerciseRoutineObservation() { return therapy_excercise_routine_observation; }
    public int getTherapyId() {
        return therapy_id;
    }
    public String getPreConditions() {
        return preconditions;
    }

    public void setExerciseRoutineId(int exercise_routine_id) { this.exercise_routine_id = exercise_routine_id; }
    public void setTherapyId(int therapy_id) {
        this.therapy_id = therapy_id;
    }
    public void setTherapyExcerciseRoutineObservation(String therapy_excercise_routine_observation) { this.therapy_excercise_routine_observation = therapy_excercise_routine_observation; }
    public void setTherapyExcerciseRoutineDuration(float therapy_excercise_routine_duration) { this.therapy_excercise_routine_duration = therapy_excercise_routine_duration; }
    public void setPreconditions(String preconditions) {
        this.preconditions = preconditions;
    }

    public ArrayList<QuestionaryOptionViewModel> getOptions() {
        return options;
    }
}
