package com.rehapp.rehappmovil.rehapp.Models;

public class ExerciseRoutinesViewModel {

    private int exercise_routine_id;
    private String exercise_routine_url;
    private String exercise_routine_name;
    private String exercise_routine_description;
    boolean isSelected;

    public ExerciseRoutinesViewModel(boolean isSelected, String exerciseName) {
        this.isSelected = isSelected;
        this.exercise_routine_name = exerciseName;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getExerciseName() {
        return exercise_routine_name;
    }

    public void setExerciseName(String exerciseName) {
        this.exercise_routine_name = exerciseName;
    }

    public String getExerciseUrl() {
        return exercise_routine_url;
    }

    public void setExerciseUrl(String exerciseUrl) {
        this.exercise_routine_url = exerciseUrl;
    }

    public int getExercise_routine_id() {
        return exercise_routine_id;
    }

    public void setExercise_routine_id(int exercise_routine_id) {
        this.exercise_routine_id = exercise_routine_id;
    }

    public String getExercise_routine_url() {
        return exercise_routine_url;
    }

    public void setExercise_routine_url(String exercise_routine_url) {
        this.exercise_routine_url = exercise_routine_url;
    }

    public String getExercise_routine_name() {
        return exercise_routine_name;
    }

    public void setExercise_routine_name(String exercise_routine_name) {
        this.exercise_routine_name = exercise_routine_name;
    }

    public String getExercise_routine_description() {
        return exercise_routine_description;
    }

    public void setExercise_routine_description(String exercise_routine_description) {
        this.exercise_routine_description = exercise_routine_description;
    }
}
