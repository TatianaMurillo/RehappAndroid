package com.rehapp.rehappmovil.rehapp.Utils.Constants;

public enum PhysiologicalParameterTherapy {


    DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_IN(6,0,1,2,3,4,":"),
    DATA_PHYSIOLOGICAL_PARAMETER_THERAPY_OUT(7,0,1,2,3,4,":");

    int rowId;
    String physiologicalParameterSeparator;
    int physio_param_thrpy_id;
    int physio_param_id;
    int therapy_id;
    int physio_param_thrpy_value;
    int physio_param_thrpy_in_out;

    PhysiologicalParameterTherapy(int rowId, int physio_param_thrpy_id, int physio_param_id, int therapy_id, int physio_param_thrpy_value, int physio_param_thrpy_in_out,String physiologicalParameterSeparator ) {
        this.rowId = rowId;
        this.physio_param_thrpy_id = physio_param_thrpy_id;
        this.physio_param_id = physio_param_id;
        this.therapy_id = therapy_id;
        this.physio_param_thrpy_value = physio_param_thrpy_value;
        this.physio_param_thrpy_in_out = physio_param_thrpy_in_out;
        this.physiologicalParameterSeparator=physiologicalParameterSeparator;
    }

    public int getRowId() {
        return rowId;
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

    public String getPhysiologicalParameterSeparator() {
        return physiologicalParameterSeparator;
    }
}
