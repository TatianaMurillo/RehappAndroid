package com.rehapp.rehappmovil.rehapp.Models;

import java.util.ArrayList;
import java.util.List;

public class TherapyMasterDetailViewModel extends TherapyViewModel {


    private String action;
    private TherapistViewModel therapist;
    private PatientViewModel patient;
    private InstitutionViewModel institution;
    private List<List<PhysiologicalParameterTherapyViewModel>> physiologicalParameterViewModel;
    private static TherapyMasterDetailViewModel instance;

    public TherapyMasterDetailViewModel(int institution_id, int patient_id, int therapist_id, int therapy_achieved_the_goal, String therapy_additional_information, int therapy_id, int therapy_sequence, double therapy_total_duration, String therapy_description, String therapy_observation, String therapy_date, String therapy_time, InstitutionViewModel institucion, PatientViewModel patient, TherapistViewModel therapistViewModel, String action, TherapistViewModel therapist, PatientViewModel patient1, InstitutionViewModel institution, List<List<PhysiologicalParameterTherapyViewModel>> physiologicalParameterViewModel) {
        super(institution_id, patient_id, therapist_id, therapy_achieved_the_goal, therapy_additional_information, therapy_id, therapy_sequence, therapy_total_duration, therapy_description, therapy_observation, therapy_date, therapy_time, institucion, patient, therapistViewModel);
        this.action = action;
        this.therapist = therapist;
        this.patient = patient1;
        this.institution = institution;
        this.physiologicalParameterViewModel = physiologicalParameterViewModel;
    }
    public TherapyMasterDetailViewModel(){}

    public static TherapyMasterDetailViewModel getInstance()
    {
        if(instance ==null)
        {
        }

        return instance;
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

    public List<List<PhysiologicalParameterTherapyViewModel>> getPhysiologicalParameterViewModel() {
        return physiologicalParameterViewModel;
    }

    public void setPhysiologicalParameterViewModel(List<List<PhysiologicalParameterTherapyViewModel>> physiologicalParameterViewModel) {
        this.physiologicalParameterViewModel = physiologicalParameterViewModel;
    }
}

