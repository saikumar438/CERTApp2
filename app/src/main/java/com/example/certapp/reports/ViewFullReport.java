package com.example.certapp.reports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.certapp.Card.ReportModel;
import com.example.certapp.R;

public class ViewFullReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_full_report);
        Intent intent=getIntent();
        ReportModel model= (ReportModel) intent.getSerializableExtra("reportData");
        Log.e("Loction",model.getLattitude());

    }
}