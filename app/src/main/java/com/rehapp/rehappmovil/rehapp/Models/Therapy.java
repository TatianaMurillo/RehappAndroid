package com.rehapp.rehappmovil.rehapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.rehapp.rehappmovil.rehapp.IO.RESPONSES.TherapyResponses.RequestTherapyResponse;
import com.rehapp.rehappmovil.rehapp.IO.RESPONSES.TherapyResponses.ResultListTherapyResponse;


import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

public class Therapy implements Parcelable {

private int Therapy_id;
private int Therapist_id;
private int Patient_id;
private int Therapy_institution_id;
private String Therapy_description;
private String Therapy_date;
private String Therapy_time;
private double Therapy_total_duration;
private String Therapy_observation;
private int Therapy_sequence;
private boolean Therapy_achieved_the_goal;


    public Therapy() {
    }

    public Therapy(int therapy_id, int therapist_id, int patient_id, int therapy_institution_id, String therapy_description, String therapy_date, String therapy_time, double therapy_total_duration, String therapy_observation, int therapy_sequence, boolean therapy_achieved_the_goal) {
        Therapy_id = therapy_id;
        Therapist_id = therapist_id;
        Patient_id = patient_id;
        Therapy_institution_id = therapy_institution_id;
        Therapy_description = therapy_description;
        Therapy_date = therapy_date;
        Therapy_time = therapy_time;
        Therapy_total_duration = therapy_total_duration;
        Therapy_observation = therapy_observation;
        Therapy_sequence = therapy_sequence;
        Therapy_achieved_the_goal = therapy_achieved_the_goal;
    }

    protected Therapy(Parcel in) {
        Therapy_id = in.readInt();
        Therapist_id = in.readInt();
        Patient_id = in.readInt();
        Therapy_institution_id = in.readInt();
        Therapy_description = in.readString();
        Therapy_date = in.readString();
        Therapy_time = in.readString();
        Therapy_total_duration = in.readDouble();
        Therapy_observation = in.readString();
        Therapy_sequence = in.readInt();
        Therapy_achieved_the_goal = in.readByte() != 0;
    }

    public static final Creator<Therapy> CREATOR = new Creator<Therapy>() {
        @Override
        public Therapy createFromParcel(Parcel in) {
            return new Therapy(in);
        }

        @Override
        public Therapy[] newArray(int size) {
            return new Therapy[size];
        }
    };

    public int getTherapy_id() {
        return Therapy_id;
    }

    public void setTherapy_id(int therapy_id) {
        Therapy_id = therapy_id;
    }

    public int getTherapist_id() {
        return Therapist_id;
    }

    public void setTherapist_id(int therapist_id) {
        Therapist_id = therapist_id;
    }

    public int getPatient_id() {
        return Patient_id;
    }

    public void setPatient_id(int patient_id) {
        Patient_id = patient_id;
    }

    public int getTherapy_institution_id() {
        return Therapy_institution_id;
    }

    public void setTherapy_institution_id(int therapy_institution_id) {
        Therapy_institution_id = therapy_institution_id;
    }

    public String getTherapy_description() {
        return Therapy_description;
    }

    public void setTherapy_description(String therapy_description) {
        Therapy_description = therapy_description;
    }

    public String getTherapy_date() {
        return Therapy_date;
    }

    public void setTherapy_date(String therapy_date) {
        Therapy_date = therapy_date;
    }

    public String getTherapy_time() {
        return Therapy_time;
    }

    public void setTherapy_time(String therapy_time) {
        Therapy_time = therapy_time;
    }

    public double getTherapy_total_duration() {
        return Therapy_total_duration;
    }

    public void setTherapy_total_duration(double therapy_total_duration) {
        Therapy_total_duration = therapy_total_duration;
    }

    public String getTherapy_observation() {
        return Therapy_observation;
    }

    public void setTherapy_observation(String therapy_observation) {
        Therapy_observation = therapy_observation;
    }

    public int getTherapy_sequence() {
        return Therapy_sequence;
    }

    public void setTherapy_sequence(int therapy_sequence) {
        Therapy_sequence = therapy_sequence;
    }

    public boolean isTherapy_achieved_the_goal() {
        return Therapy_achieved_the_goal;
    }

    public void setTherapy_achieved_the_goal(boolean therapy_achieved_the_goal) {
        Therapy_achieved_the_goal = therapy_achieved_the_goal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Therapy_id);
        dest.writeInt(Therapist_id);
        dest.writeInt(Patient_id);
        dest.writeInt(Therapy_institution_id);
        dest.writeString(Therapy_description);
        dest.writeString(Therapy_date);
        dest.writeString(Therapy_time);
        dest.writeDouble(Therapy_total_duration);
        dest.writeString(Therapy_observation);
        dest.writeInt(Therapy_sequence);
        dest.writeByte((byte) (Therapy_achieved_the_goal ? 1 : 0));
    }
}
