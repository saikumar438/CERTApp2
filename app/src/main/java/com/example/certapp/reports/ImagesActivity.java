package com.example.certapp.reports;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.certapp.R;

public class ImagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
    }

    public void onTornado(View v){
        Intent in = new Intent();
        in.putExtra("name","Tornado");
        setResult(ReportsMainActivity.TASK_RES,in);

        finish();
    }

    public void onStorm(View v){
        Intent in = new Intent();
        in.putExtra("disaster","Storm");
        setResult(ReportsMainActivity.GOOD_RES,in);

        finish();
    }

    public void onCyclone(View v){
        Intent in = new Intent();
        in.putExtra("disaster","Cyclone");
        setResult(ReportsMainActivity.TOG_RES,in);

        finish();
    }
    public void onFlood(View v){
        Intent in = new Intent();
        in.putExtra("disaster","Flood");
        setResult(ReportsMainActivity.FLO_RES,in);

        finish();
    }

}
