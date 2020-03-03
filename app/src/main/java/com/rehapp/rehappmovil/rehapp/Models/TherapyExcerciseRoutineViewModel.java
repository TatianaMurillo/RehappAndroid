package com.rehapp.rehappmovil.rehapp.Models;

public class TherapyExcerciseRoutineViewModel {


    private int therapy_excercise_routine_id;
    private float therapy_excercise_routine_intensity;
    private float therapy_excercise_routine_duration;
    private float therapy_excercise_routine_speed;
    private String therapy_excercise_routine_observation;
    private int therapy_id;
    private int exercise_routine_id;
    private String conditions;
    private String preconditions;
    private float frequency;



    public TherapyExcerciseRoutineViewModel() { }

    public int getTherapyExcerciseRoutineId() {
        return therapy_excercise_routine_id;
    }
    public float getTherapyExcerciseRoutineIntensity() { return therapy_excercise_routine_intensity; }
    public float getTherapyExcerciseRoutineDuration() {
        return therapy_excercise_routine_duration;
    }
    public float getTherapyExcerciseRoutineSpeed() {
        return therapy_excercise_routine_speed;
    }
    public String getTherapyExcerciseRoutineObservation() { return therapy_excercise_routine_observation; }
    public int getTherapyId() {
        return therapy_id;
    }
    public int getExerciseRoutineId() {
        return exercise_routine_id;
    }
    public String getPreConditions() {
        return preconditions;
    }
    public float getFrequency() {
        return frequency;
    }

    public void setExerciseRoutineId(int exercise_routine_id) { this.exercise_routine_id = exercise_routine_id; }
    public void setTherapyId(int therapy_id) {
        this.therapy_id = therapy_id;
    }
    public void setTherapyExcerciseRoutineObservation(String therapy_excercise_routine_observation) { this.therapy_excercise_routine_observation = therapy_excercise_routine_observation; }
    public void setTherapy_excercise_routine_speed(float therapy_excercise_routine_speed) { this.therapy_excercise_routine_speed = therapy_excercise_routine_speed; }
    public void setTherapyExcerciseRoutineIntensity(float therapy_excercise_routine_intensity) { this.therapy_excercise_routine_intensity = therapy_excercise_routine_intensity; }
    public void setTherapyExcerciseRoutineDuration(float therapy_excercise_routine_duration) { this.therapy_excercise_routine_duration = therapy_excercise_routine_duration; }
    public void setConditions(String conditions) {
        this.conditions = conditions;
    }
    public void setPreconditions(String preconditions) {
        this.preconditions = preconditions;
    }
    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }
}
