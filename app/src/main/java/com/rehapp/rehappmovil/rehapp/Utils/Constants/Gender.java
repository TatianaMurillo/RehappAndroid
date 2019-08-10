package com.rehapp.rehappmovil.rehapp.Utils.Constants;

public enum Gender {

    DATA(3,"*",0,1,2);

    int rowId;
    String genderObjectSeparator;
    int gender_id;
    int gender_description;
    int gender_name;

    Gender(int rowId,String genderObjectSeparator, int gender_id, int gender_description, int gender_name) {
        this.rowId = rowId;
        this.gender_id = gender_id;
        this.gender_description = gender_description;
        this.gender_name = gender_name;
        this.genderObjectSeparator=genderObjectSeparator;
    }

    public int getRowId() {
        return rowId;
    }

    public int getGender_id() {
        return gender_id;
    }

    public int getGender_description() {
        return gender_description;
    }

    public int getGender_name() {
        return gender_name;
    }

    public String getGenderObjectSeparator() {
        return genderObjectSeparator;
    }
}
