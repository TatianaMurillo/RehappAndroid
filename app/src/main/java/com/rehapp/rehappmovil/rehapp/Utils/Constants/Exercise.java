package com.rehapp.rehappmovil.rehapp.Utils.Constants;

public enum Exercise {

    DATA("1","","","","");

    String row;
    String exercise_routine_id;
    String exercise_routine_url;
    String exercise_routine_name;
    String exercise_routine_description;

    Exercise(String row,String exercise_routine_id, String exercise_routine_url, String exercise_routine_name, String exercise_routine_description) {
        this.row=row;
        this.exercise_routine_id = exercise_routine_id;
        this.exercise_routine_url = exercise_routine_url;
        this.exercise_routine_name = exercise_routine_name;
        this.exercise_routine_description = exercise_routine_description;
    }


    public String getRow()
    {
        return row;
    }

    public String getExercise_routine_id() {
        return exercise_routine_id;
    }

    public String getExercise_routine_url() {
        return exercise_routine_url;
    }

    public String getExercise_routine_name() {
        return exercise_routine_name;
    }

    public String getExercise_routine_description() {
        return exercise_routine_description;
    }
}
