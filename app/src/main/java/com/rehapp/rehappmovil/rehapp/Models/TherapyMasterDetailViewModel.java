package com.rehapp.rehappmovil.rehapp.Models;

import java.util.ArrayList;

public class TherapyMasterDetailViewModel extends TherapyViewModel {


    private String action;
    private TherapistViewModel therapist;
    private PatientViewModel patient;
    private InstitutionViewModel institution;
    private ArrayList<PhysiologicalParameterTherapyViewModel>[] physiologicalParameterViewModel;

    public TherapyMasterDetailViewModel(int therapy_id, String therapy_description, double therapy_total_duration, String therapy_observation, int therapy_sequence, boolean therapy_achieved_the_goal, int therapist_id, int patient_id, int institution_id, String action, TherapistViewModel therapist, PatientViewModel patient, InstitutionViewModel institution, ArrayList<PhysiologicalParameterTherapyViewModel>[] physiologicalParameterViewModel) {
        super(therapy_id, therapy_description, therapy_total_duration, therapy_observation, therapy_sequence, therapy_achieved_the_goal, therapist_id, patient_id, institution_id);
        this.action = action;
        this.therapist = therapist;
        this.patient = patient;
        this.institution = institution;
        this.physiologicalParameterViewModel = physiologicalParameterViewModel;
    }


    public TherapyMasterDetailViewModel() {
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public TherapistViewModel getTherapist() {
        return therapist;
    }

    public void setTherapist(TherapistViewModel therapist) {
        this.therapist = therapist;
    }

    public PatientViewModel getPatient() {
        return patient;
    }

    public void setPatient(PatientViewModel patient) {
        this.patient = patient;
    }

    public InstitutionViewModel getInstitution() {
        return institution;
    }

    public void setInstitution(InstitutionViewModel institution) {
        this.institution = institution;
    }

    public ArrayList<PhysiologicalParameterTherapyViewModel>[] getPhysiologicalParameterViewModel() {
        return physiologicalParameterViewModel;
    }

    public void setPhysiologicalParameterViewModel(ArrayList<PhysiologicalParameterTherapyViewModel>[] physiologicalParameterViewModel) {
        this.physiologicalParameterViewModel = physiologicalParameterViewModel;
    }
}

