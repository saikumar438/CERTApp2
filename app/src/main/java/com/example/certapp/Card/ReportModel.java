package com.example.certapp.Card;

import java.io.Serializable;

public class ReportModel implements Serializable {

    private String userName;
    private String title;
    private String timedate;
    private String location;
    private String lattitude;
    private String longitude;
    private String description;
    private String typeOfIncident;
    private String impactLevel;
    private String structuralDamageImpact;
    private String red;
    private String green;
    private String yellow;
    private String black;
    private String hazmatType;
    private String incidentId;
    private String notes;
    private String address;
    private String imageURL;
    private String state;
    private String zipcode;
    private String updatedAt;

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public ReportModel() {
    }

    public ReportModel(String userName, String title, String timedate, String location, String description, String typeOfIncident, String impactLevel, String structuralDamageImpact, String red, String green, String yellow, String black, String hazmatType, String incidentId, String notes, String address, String imageURL, String state, String zipcode,String lat,String longitude, String updatedAt) {
        this.userName = userName;
        this.title = title;
        this.timedate = timedate;
        this.location = location;
        this.description = description;
        this.typeOfIncident = typeOfIncident;
        this.impactLevel = impactLevel;
        this.structuralDamageImpact = structuralDamageImpact;
        this.red = red;
        this.green = green;
        this.yellow = yellow;
        this.black = black;
        this.hazmatType = hazmatType;
        this.incidentId = incidentId;
        this.notes = notes;
        this.address = address;
        this.imageURL = imageURL;
        this.lattitude=lat;
        this.longitude=longitude;
        this.state = state;
        this.zipcode = zipcode;
        this.updatedAt = updatedAt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimedate() {
        return timedate;
    }

    public void setTimedate(String timedate) {
        this.timedate = timedate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeOfIncident() {
        return typeOfIncident;
    }

    public void setTypeOfIncident(String typeOfIncident) {
        this.typeOfIncident = typeOfIncident;
    }

    public String getImpactLevel() {
        return impactLevel;
    }

    public void setImpactLevel(String impactLevel) {
        this.impactLevel = impactLevel;
    }

    public String getStructuralDamageImpact() {
        return structuralDamageImpact;
    }

    public void setStructuralDamageImpact(String structuralDamageImpact) {
        this.structuralDamageImpact = structuralDamageImpact;
    }

    public String getRed() {
        return red;
    }

    public void setRed(String red) {
        this.red = red;
    }

    public String getGreen() {
        return green;
    }

    public void setGreen(String green) {
        this.green = green;
    }

    public String getYellow() {
        return yellow;
    }

    public void setYellow(String yellow) {
        this.yellow = yellow;
    }

    public String getBlack() {
        return black;
    }

    public void setBlack(String black) {
        this.black = black;
    }

    public String getHazmatType() {
        return hazmatType;
    }

    public void setHazmatType(String hazmatType) {
        this.hazmatType = hazmatType;
    }

    public String getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(String incidentId) {
        this.incidentId = incidentId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}

