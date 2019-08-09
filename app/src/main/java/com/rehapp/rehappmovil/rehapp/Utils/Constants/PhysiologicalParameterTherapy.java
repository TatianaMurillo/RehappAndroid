package com.rehapp.rehappmovil.rehapp.Utils.Constants;


import java.lang.String;


public enum PhysiologicalParameterTherapy {


    DATA(1,1,2,3,4,5);

    int row;
    int physio_param_thrpy_id;
    int physio_param_id;
    int therapy_id;
    int physio_param_thrpy_value;
    int physio_param_thrpy_in_out;

    PhysiologicalParameterTherapy(int row, int physio_param_thrpy_id, int physio_param_id, int therapy_id, int physio_param_thrpy_value, int physio_param_thrpy_in_out) {
        this.row = row;
        this.physio_param_thrpy_id = physio_param_thrpy_id;
        this.physio_param_id = physio_param_id;
        this.therapy_id = therapy_id;
        this.physio_param_thrpy_value = physio_param_thrpy_value;
        this.physio_param_thrpy_in_out = physio_param_thrpy_in_out;
    }

    public int getRow() {
        return row;
    }

    public int getPhysio_param_thrpy_id() {
        return physio_param_thrpy_id;
    }

    public int getPhysio_param_id() {
        return physio_param_id;
    }

    public int getTherapy_id() {
        return therapy_id;
    }

    public int getPhysio_param_thrpy_value() {
        return physio_param_thrpy_value;
    }

    public int getPhysio_param_thrpy_in_out() {
        return physio_param_thrpy_in_out;
    }
}
