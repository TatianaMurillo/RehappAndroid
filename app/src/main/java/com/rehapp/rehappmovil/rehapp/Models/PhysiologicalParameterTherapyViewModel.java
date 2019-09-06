package com.rehapp.rehappmovil.rehapp.Models;

import android.content.ContentValues;

import com.rehapp.rehappmovil.rehapp.Utils.TherapyDBHelperEntry;
import static com.rehapp.rehappmovil.rehapp.Utils.TherapyDBHelperEntry.PhysiologicalParameterTherapyEntry;
public class PhysiologicalParameterTherapyViewModel {



    private int physio_param_thrpy_id;
    private int physio_param_id;
    private int therapy_id;
    private String physio_param_thrpy_value;
    private String physio_param_thrpy_in_out;

    public PhysiologicalParameterTherapyViewModel(int physio_param_thrpy_id, int physio_param_id, int therapy_id, String physio_param_thrpy_value, String physio_param_thrpy_in_out) {
        this.physio_param_thrpy_id = physio_param_thrpy_id;
        this.physio_param_id = physio_param_id;
        this.therapy_id = therapy_id;
        this.physio_param_thrpy_value = physio_param_thrpy_value;
        this.physio_param_thrpy_in_out = physio_param_thrpy_in_out;
    }

    public int getPhysio_param_thrpy_id() {
        return physio_param_thrpy_id;
    }

    public void setPhysio_param_thrpy_id(int physio_param_thrpy_id) {
        this.physio_param_thrpy_id = physio_param_thrpy_id;
    }

    public int getPhysio_param_id() {
        return physio_param_id;
    }

    public void setPhysio_param_id(int physio_param_id) {
        this.physio_param_id = physio_param_id;
    }

    public int getTherapy_id() {
        return therapy_id;
    }

    public void setTherapy_id(int therapy_id) {
        this.therapy_id = therapy_id;
    }

    public String getPhysio_param_thrpy_value() {
        return physio_param_thrpy_value;
    }

    public void setPhysio_param_thrpy_value(String physio_param_thrpy_value) {
        this.physio_param_thrpy_value = physio_param_thrpy_value;
    }

    public String getPhysio_param_thrpy_in_out() {
        return physio_param_thrpy_in_out;
    }

    public void setPhysio_param_thrpy_in_out(String physio_param_thrpy_in_out) {
        this.physio_param_thrpy_in_out = physio_param_thrpy_in_out;
    }

    public static ContentValues toContentValues(PhysiologicalParameterTherapyViewModel physiologicalParameterTherapyViewModel) {
        ContentValues values = new ContentValues();
        values.put(PhysiologicalParameterTherapyEntry.PHYSIO_PARAM_ID, physiologicalParameterTherapyViewModel.getPhysio_param_id());
        values.put(PhysiologicalParameterTherapyEntry.PHYSIO_PARAM_THERAPY_ID, physiologicalParameterTherapyViewModel.getPhysio_param_thrpy_id());
        values.put(PhysiologicalParameterTherapyEntry.THERAPY_ID, physiologicalParameterTherapyViewModel.getTherapy_id());
        values.put(PhysiologicalParameterTherapyEntry.THERAPY_VALUE, physiologicalParameterTherapyViewModel.getPhysio_param_thrpy_value());
        return values;
    }
}
