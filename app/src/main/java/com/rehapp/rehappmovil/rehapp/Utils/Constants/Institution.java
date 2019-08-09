package com.rehapp.rehappmovil.rehapp.Utils.Constants;

public enum Institution {

    DATA(0,1,2,3);

    int row;
    int institution_id;
    int institution_name;
    int institution_additional_info;

    Institution(int row, int institution_id, int institution_name, int institution_additional_info) {
        this.row=row;
        this.institution_id = institution_id;
        this.institution_name = institution_name;
        this.institution_additional_info = institution_additional_info;
    }

    public int getRow() {
        return row;
    }

    public int getInstitution_id() {
        return institution_id;
    }

    public int getInstitution_name() {
        return institution_name;
    }

    public int getInstitution_additional_info() {
        return institution_additional_info;
    }
}
