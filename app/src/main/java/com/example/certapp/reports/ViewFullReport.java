package com.example.certapp.reports;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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

        TextView tvName = findViewById(R.id.tvRName);

        tvName.setText(model.getUserName());

        TextView tvTitle = findViewById(R.id.tvRTitle);
        tvTitle.setText(model.getTitle());

        TextView tvDateTime = findViewById(R.id.tvRDateTime);
        tvDateTime.setText(model.getTimedate());

        TextView tvIncidentType = findViewById(R.id.tvRIncidentType);
        tvIncidentType.setText(model.getTypeOfIncident());

        TextView tvHazmatType = findViewById(R.id.tvRHazmatType);
        tvHazmatType.setText(model.getHazmatType());

        TextView tvAddress = findViewById(R.id.tvRAddress);
        tvAddress.setText(model.getAddress());

        TextView tvState = findViewById(R.id.tvRState);
        tvState.setText(model.getState());

        TextView tvZipcode = findViewById(R.id.tvRZipcode);
        tvZipcode.setText(model.getZipcode());

        TextView tvCasualities = findViewById(R.id.tvRCasualities);
        tvCasualities.setText(model.getRed()+model.getBlack()+model.getGreen()+model.getYellow());


    }
}