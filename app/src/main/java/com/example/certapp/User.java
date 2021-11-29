package com.example.certapp;

public class User {

    public String fullName,phone,email;
    //this is android 
    public User()
    {

    }

    public User(String fullName,String Phone,String email)
    {
        this.email =email;
        this.fullName = fullName;
        this.phone = phone;
    }
}
