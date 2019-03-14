package com.rehapp.rehappmovil.rehapp.Models;

import android.arch.lifecycle.ViewModel;

public class SearchCreatePatientViewModel extends ViewModel {

private String activeUser;

public SearchCreatePatientViewModel()
{

}


    public String getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(String activeUser) {
        this.activeUser = activeUser;
    }
}
