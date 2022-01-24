package com.example.certapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfileManagement extends AppCompatActivity {

    TextView fName,address,phoneNumber,qualification,email;
    String userId;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_management);
        fName = findViewById(R.id.fName);
        address = findViewById(R.id.address);
        phoneNumber = findViewById(R.id.phoneNumber);
        qualification = findViewById(R.id.qualification);
        email = findViewById(R.id.email);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        Log.e("User ID ",userId);

        DocumentReference documentReference = fStore.collection("user").document(userId);
        documentReference.addSnapshotListener(ProfileManagement.this, new EventListener<DocumentSnapshot>() {
            @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                fName.setText(documentSnapshot.getString("FullName"));
                System.out.println("this is system "+fName.getText());
                email.setText(documentSnapshot.getString("Email"));
                qualification.setText(documentSnapshot.getString("Qualification"));
                address.setText(documentSnapshot.getString("Address"));
                phoneNumber.setText(documentSnapshot.getString("PhoneNumber"));
            }
        });
    }


}