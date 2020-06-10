package com.rehapp.rehappmovil.rehapp.Models;

public class ExerciseRoutinesViewModel implements Comparable<ExerciseRoutinesViewModel>{

    private int exercise_routine_id;
    private String exercise_routine_url;
    private String exercise_routine_name;
    private String exercise_routine_description;
    private String conditions;
    private String preconditions;
    private String html;
    private float frequency;
    private float duration;
    private float speed;
    private float intensity;
    private String observations;
    boolean isSelected;
    private String therapy_id;

    public ExerciseRoutinesViewModel() { }

    public ExerciseRoutinesViewModel(String html) {
        this.html=html;
    }

    public ExerciseRoutinesViewModel(boolean isSelected, String exerciseName) {
        this.isSelected = isSelected;
        this.exercise_routine_name = exerciseName;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public void setPreconditions(String preconditions) {
        this.preconditions = preconditions;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setExerciseName(String exerciseName) {
        this.exercise_routine_name = exerciseName;
    }

    public void setExercise_routine_description(String exercise_routine_description) {
        this.exercise_routine_description = exercise_routine_description;
    }
    public void setExerciseUrl(String exerciseUrl) {
        this.exercise_routine_url = exerciseUrl;
    }

    public void setExercise_routine_id(int exercise_routine_id) {
        this.exercise_routine_id = exercise_routine_id;
    }

    public void setExercise_routine_url(String exercise_routine_url) {
        this.exercise_routine_url = exercise_routine_url;
    }

    public void setExercise_routine_name(String exercise_routine_name) {
        this.exercise_routine_name = exercise_routine_name;
    }

    public void setTherapy_id(String therapy_id) {
        this.therapy_id = therapy_id;
    }

    public String getHtml() {
        return html;
    }

    public String getConditions() {
        return conditions;
    }

    public String getPreconditions() {
        return preconditions;
    }

    public float getFrequency() {
        return frequency;
    }

    public float getDuration() {
        return duration;
    }

    public float getSpeed() {
        return speed;
    }

    public float getIntensity() {
        return intensity;
    }

    public String getObservations() {
        return observations;
    }

    public int getExercise_routine_id() {
        return exercise_routine_id;
    }

    public String getExercise_routine_description() {
        return exercise_routine_description;
    }

    public String getExercise_routine_name() {
        return exercise_routine_name;
    }

    public String getExerciseName() {
        return exercise_routine_name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public String getExerciseUrl() {
        return exercise_routine_url;
    }

    public String getExercise_routine_url() {
        return exercise_routine_url;
    }

    public String getTherapy_id() {
        return therapy_id;
    }

    @Override
    public int compareTo(ExerciseRoutinesViewModel o) {
        if (o.isSelected)
            // give node higher preference
            return 100;
        else
            // give node lower preference
            return -1;
    }
}
