package com.example.covid.model;

public class DistModel {
    private String dist_name;
    private String state_name;
    private String confirm_cases;

    public DistModel() {
    }

    public DistModel(String dist_name, String state_name, String confirm_cases) {
        this.dist_name = dist_name;
        this.state_name = state_name;
        this.confirm_cases = confirm_cases;
    }

    public String getDist_name() {
        return dist_name;
    }

    public String getState_name() {
        return state_name;
    }

    public String getConfirm_cases() {
        return confirm_cases;
    }

    public void setDist_name(String dist_name) {
        this.dist_name = dist_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public void setConfirm_cases(String confirm_cases) {
        this.confirm_cases = confirm_cases;
    }
}