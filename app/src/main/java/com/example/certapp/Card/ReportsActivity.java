package com.example.certapp.Card;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.certapp.ProfileManagement;
import com.example.certapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ReportsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ReportsAdapter reportsAdapter;
    private ArrayList<ReportModel> reportsArrayList;
//    private FirebaseFirestore db;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance().getReference("report");
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
                Toast.makeText(getApplicationContext(),"Position = "+position+"\n Item = "+data.getUser(),Toast.LENGTH_SHORT).show();
            }
        });
        createDataForCards();

    }

    private void createDataForCards(){


        fStore.collection("report").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.e("TAG", document.getId() + " => " + document.getData());
                        System.out.println("The user value is :"+document.getString("User"));
                        ReportModel r=new ReportModel();
            r.setUser(document.getString("User"));

            r.setIncident_Type(document.getString("Incident Type"));
            r.setSeverity(document.getString("Severity"));
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