package com.example.covid.model;

public class StateModels {
    private String state_name;
    private String confirm_cases;

    public StateModels() {
    }

    public StateModels(String state_name, String confirm_cases) {
        this.state_name = state_name;
        this.confirm_cases = confirm_cases;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getConfirm_cases() {
        return confirm_cases;
    }

    public void setConfirm_cases(String confirm_cases) {
        this.confirm_cases = confirm_cases;
    }
}
