package com.rehapp.rehappmovil.rehapp.Utils.Constants;

public enum Exercise {

    DATA(2,"*",0,1,2,3);

    int rowId;
    int exercise_routine_id;
    String exerciseObjectSeparator;
    int exercise_routine_url;
    int exercise_routine_name;
    int exercise_routine_description;

    Exercise(int rowId,String exerciseObjectSeparator, int exercise_routine_id, int exercise_routine_url, int exercise_routine_name, int exercise_routine_description) {
        this.rowId = rowId;
        this.exerciseObjectSeparator=exerciseObjectSeparator;
        this.exercise_routine_id = exercise_routine_id;
        this.exercise_routine_url = exercise_routine_url;
        this.exercise_routine_name = exercise_routine_name;
        this.exercise_routine_description = exercise_routine_description;
    }

    public int getRowId() {
        return rowId;
    }

    public int getExercise_routine_id() {
        return exercise_routine_id;
    }

    public int getExercise_routine_url() {
        return exercise_routine_url;
    }

    public int getExercise_routine_name() {
        return exercise_routine_name;
    }

    public int getExercise_routine_description() {
        return exercise_routine_description;
    }

    public String getExerciseObjectSeparator() {
        return exerciseObjectSeparator;
    }
}
