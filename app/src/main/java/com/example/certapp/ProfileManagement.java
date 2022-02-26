package com.example.certapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.certapp.Card.ReportsActivity;
import com.example.certapp.reports.ReportsMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfileManagement extends AppCompatActivity {

    TextView fName,lName,addressp,phoneNumber,qualification,email;
    String userId;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_management);
        fName = findViewById(R.id.fName);
        lName = findViewById(R.id.lName);
        addressp = findViewById(R.id.addressProfile);
        phoneNumber = findViewById(R.id.phoneNumber);
        qualification = findViewById(R.id.qualification);
        email = findViewById(R.id.email);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        Log.e("User ID ",userId);

        DocumentReference documentReference = fStore.collection("usersDB").document(userId);
        documentReference.addSnapshotListener(ProfileManagement.this, new EventListener<DocumentSnapshot>() {
            @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                fName.setText(documentSnapshot.getString("firstName"));
                lName.setText(documentSnapshot.getString("lastName"));
                System.out.println("this is system "+fName.getText());
                Log.e("Name", String.valueOf(fName));
                email.setText(documentSnapshot.getString("emailAddress"));
                qualification.setText(documentSnapshot.getString("qualification"));
                addressp.setText(documentSnapshot.getString("streetAddress"));
                phoneNumber.setText(documentSnapshot.getString("contactNumber"));
            }
        });
    }

    public void back(View view)
    {
        Intent intent = new Intent(ProfileManagement.this,HomeScreen.class);
        startActivity(intent);
    }

    public void updateProfile(View view)
    {
//        Intent intent = new Intent(ProfileManagement.this, ReportsActivity.class);
//        startActivity(intent);
        Toast.makeText(ProfileManagement.this, "Profile updated", Toast.LENGTH_SHORT).show();
    }


}