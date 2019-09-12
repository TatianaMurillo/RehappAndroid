package com.rehapp.rehappmovil.rehapp.Models;

public class TherapyExercise {

    boolean isSelected;
    String exerciseName;
    String exerciseUrl;

    public TherapyExercise(boolean isSelected, String exerciseName) {
        this.isSelected = isSelected;
        this.exerciseName = exerciseName;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExerciseUrl() {
        return exerciseUrl;
    }

    public void setExerciseUrl(String exerciseUrl) {
        this.exerciseUrl = exerciseUrl;
    }
}
