package com.example.certapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

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

    public void logout(View view)
    {
        Toast.makeText(this, "User logged out successfully", Toast.LENGTH_LONG).show();
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);


    }

}