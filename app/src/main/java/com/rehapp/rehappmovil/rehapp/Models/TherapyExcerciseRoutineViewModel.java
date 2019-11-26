package com.rehapp.rehappmovil.rehapp.Models;

public class TherapyExcerciseRoutineViewModel {


    private int therapy_excercise_routine_id;
    private float therapy_excercise_routine_intensity;
    private float therapy_excercise_routine_duration;
    private float therapy_excercise_routine_frequent;
    private float therapy_excercise_routine_speed;
    private String therapy_excercise_routine_observation;
    private String therapy_excercise_routine_conditions;
    private int therapy_id;
    private int exercise_routine_id;

    public TherapyExcerciseRoutineViewModel() { }

    public float getTherapy_excercise_routine_frequent() {
        return therapy_excercise_routine_frequent;
    }

    public void setTherapy_excercise_routine_frequent(float therapy_excercise_routine_frequent) {
        this.therapy_excercise_routine_frequent = therapy_excercise_routine_frequent;
    }

    public int getTherapyExcerciseRoutineId() {
        return therapy_excercise_routine_id;
    }

    public void setTherapyExcerciseRoutineId(int therapy_excercise_routine_id) {
        this.therapy_excercise_routine_id = therapy_excercise_routine_id;
    }

    public float getTherapyExcerciseRoutineIntensity() {
        return therapy_excercise_routine_intensity;
    }

    public void setTherapyExcerciseRoutineIntensity(float therapy_excercise_routine_intensity) {
        this.therapy_excercise_routine_intensity = therapy_excercise_routine_intensity;
    }

    public float getTherapyExcerciseRoutineDuration() {
        return therapy_excercise_routine_duration;
    }

    public void setTherapyExcerciseRoutineDuration(float therapy_excercise_routine_duration) {
        this.therapy_excercise_routine_duration = therapy_excercise_routine_duration;
    }

    public float getTherapyExcerciseRoutineSpeed() {
        return therapy_excercise_routine_speed;
    }

    public void setTherapy_excercise_routine_speed(float therapy_excercise_routine_speed) {
        this.therapy_excercise_routine_speed = therapy_excercise_routine_speed;
    }

    public String getTherapyExcerciseRoutineObservation() {
        return therapy_excercise_routine_observation;
    }

    public void setTherapyExcerciseRoutineObservation(String therapy_excercise_routine_observation) {
        this.therapy_excercise_routine_observation = therapy_excercise_routine_observation;
    }

    public int getTherapyId() {
        return therapy_id;
    }

    public void setTherapyId(int therapy_id) {
        this.therapy_id = therapy_id;
    }

    public int getExerciseRoutineId() {
        return exercise_routine_id;
    }

    public void setExerciseRoutineId(int exercise_routine_id) {
        this.exercise_routine_id = exercise_routine_id;
    }
}
