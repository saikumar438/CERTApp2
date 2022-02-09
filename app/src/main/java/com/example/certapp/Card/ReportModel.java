package com.example.certapp.Card;

public class ReportModel {

    private String User;
    private String Severity;
    private String Incident_Type;

    public ReportModel(){

    }

    public ReportModel(String user, String severity, String incident_Type) {
        User = user;
        Severity = severity;
        Incident_Type = incident_Type;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getSeverity() {
        return Severity;
    }

    public void setSeverity(String severity) {
        Severity = severity;
    }

    public String getIncident_Type() {
        return Incident_Type;
    }

    public void setIncident_Type(String incident_Type) {
        Incident_Type = incident_Type;
    }
}

