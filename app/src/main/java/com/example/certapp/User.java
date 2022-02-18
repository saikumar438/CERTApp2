package com.example.certapp;

public class User {

    public String firstName,lastName,qualification,contactNumber,city,state,streetAddress,zipCode,password,verifyPassword,others,emailAddress;
    public User()
    {

    }

    public User(String firstName, String lastName, String qualification, String contactNumber, String city, String state, String streetAddress, String zipCode,String password, String verifyPassword, String others, String emailAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.qualification = qualification;
        this.contactNumber = contactNumber;
        this.city = city;
        this.password=password;
        this.state = state;
        this.streetAddress = streetAddress;
        this.zipCode = zipCode;
        this.verifyPassword = verifyPassword;
        this.others = others;
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }


    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
