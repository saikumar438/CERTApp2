package com.example.certapp.Card;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.certapp.HomeScreen;
import com.example.certapp.ProfileManagement;
import com.example.certapp.R;
import com.example.certapp.reports.ViewFullReport;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ReportsActivity extends AppCompatActivity  {
    private RecyclerView recyclerView;
    private ReportsAdapter reportsAdapter;
    private ArrayList<ReportModel> reportsArrayList;
//    private FirebaseFirestore db;

    public QueryDocumentSnapshot reportDocument;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    DatabaseReference database;
    HashMap<Integer,String> map = new HashMap<>();
    ArrayList<QueryDocumentSnapshot> list = new ArrayList<>();
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance().getReference("reportsDB");
        initializeCardView();


    }

    private void initializeCardView(){
        recyclerView=findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reportsArrayList=new ArrayList<>();
        reportsAdapter=new ReportsAdapter(this,reportsArrayList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(reportsAdapter);
        reportsAdapter.setOnItemClickListener(new ClickListener<ReportModel>(){
            @Override
            public void onClick(View view, ReportModel data, int position) {

                Intent viewReport=new Intent(ReportsActivity.this, ViewFullReport.class);
                viewReport.putExtra("reportData", (Serializable) data);
                startActivity(viewReport);

            }
        });
        createDataForCards();

    }

    private void createDataForCards(){


        fStore.collection("reportsDB").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        reportDocument = document;
                        list.add(document);
                        map.put(count++,document.getId());
                        System.out.println("Value of the document is :- "+document);
                        Log.e("TAG", document.getId() + " => " + document.getData());
                        System.out.println("The user value is :"+document.getString("User"));
                        ReportModel r=new ReportModel();
                        r.setUserName(document.getString("userName"));
                        r.setTitle(document.getString("title"));
                        r.setTimedate(document.getString("timedate"));
                        r.setLocation(document.getString("location"));
                        r.setDescription(document.getString("description"));
                        r.setTypeOfIncident(document.getString("typeOfIncident"));
                        r.setImpactLevel(document.getString("impactLevel"));
                        r.setStructuralDamageImpact(document.getString("structuralDamageImpact"));
                        r.setRed(document.getString("red"));
                        r.setGreen(document.getString("green"));
                        r.setYellow(document.getString("yellow"));
                        r.setBlack(document.getString("black"));
                        r.setHazmatType(document.getString("hazmatType"));
                        r.setIncidentId(document.getString("incidentId"));
                        r.setNotes(document.getString("notes"));
                        r.setAddress(document.getString("address"));
                        r.setImageURL(document.getString("imageURL"));
                        r.setLattitude(document.getString("latitude"));
                        r.setLongitude(document.getString("longitude"));
                        r.setState(document.getString("state"));
                        r.setZipcode(document.getString("zipcode"));
                        r.setUpdatedAt(document.getString("updatedAt"));
                        reportsArrayList.add(r);
                        reportsAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.e("TAG", "Error getting documents: ", task.getException());
                }
            }
        });





    }
}