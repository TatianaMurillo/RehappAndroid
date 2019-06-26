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


    public TherapyMasterDetailViewModel()
    {
    }


    public static TherapyMasterDetailViewModel getInstance()
    {
        if(instance ==null)
        {
            instance = new TherapyMasterDetailViewModel();
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

