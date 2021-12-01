package com.example.certapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    public void createReport(View view)
    {
        System.out.println("button pressed create report");
        Intent intent = new Intent(this,SecondActivity.class);
        startActivity(intent);
    }

}