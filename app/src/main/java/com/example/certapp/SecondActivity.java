package com.example.certapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.SSLEngineResult;

public class SecondActivity extends AppCompatActivity {


    EditText editText;
    TextView textView1,textView2,address;
    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    private String userID,name,finalLoc;
    //private FirebaseFirestore fStore;
    private DatabaseReference RootRef;
    String laneAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        try {
            mAuth = FirebaseAuth.getInstance();
            Intent intent = getIntent();
            fStore = FirebaseFirestore.getInstance();
            editText = findViewById(R.id.edit_text);
//        textView1 = findViewById(R.id.textView);
            textView2 = findViewById(R.id.text_view2);
            address = findViewById(R.id.fullAddress);
            userID = mAuth.getCurrentUser().getUid();
            Log.e("Create Report",userID);
            Places.initialize(getApplicationContext(), "AIzaSyAdjioNdUzNH-aApWJfa2PPB6zro6O6LTA");

            editText.setFocusable(false);
            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
                    Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(SecondActivity.this);

                    startActivityForResult(intent, 100);
                }
            });

            DocumentReference documentReference = fStore.collection("user").document(userID);
//            documentReference.addSnapshotListener(SecondActivity.this, new EventListener<DocumentSnapshot>() {
//                @Override
//                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
//                    name = documentSnapshot.getString("FullName");
//                    System.out.println("this is system "+name);
//                    Log.e("Name of the user ",name);
//
//                    Toast.makeText(SecondActivity.this, "Current user name "+name, Toast.LENGTH_SHORT).show();
//
//                }
//            });



        }catch (Exception e) {
            Log.e("Address 1 ","This is in catch block address");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == 100 && resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                editText.setText(place.getAddress());
//                textView1.setText(String.format("Locality Name is :- ", place.getName()));
                textView2.setText(String.valueOf(place.getLatLng()));
                String temp = String.valueOf(place.getLatLng());
                Log.e("in Temporary",temp);
                String arr[] = temp.split(",");
                String latitude = arr[0].substring(10,arr[0].length());
                String longitude = arr[1].substring(0,(arr[1].length()-1));
                Log.e("Array",arr[0]);
                Log.e("Array",arr[1]);
                Log.e("Latitude",latitude);
                Log.e("Longitude",longitude);

                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());

                addresses = geocoder.getFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), 1);
                laneAddress = addresses.get(0).getAddressLine(0);
//                String city = addresses.get(0).getLocality();
//                String state = addresses.get(0).getAdminArea();
//                String zip = addresses.get(0).getPostalCode();
//                String country = addresses.get(0).getCountryName();
                finalLoc=addresses.get(0).getAddressLine(0)+","+
                        addresses.get(0).getAdminArea() + "," +
                        latitude+","+longitude+","+addresses.get(0).getPostalCode();
                Log.e("Final Address",finalLoc);
                address.setText(String.valueOf(laneAddress));
                Log.e("Address", String.valueOf(addresses));
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);

                Toast.makeText(getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {

            Log.e("Address 2 ","In catch block address 2"+e);
        }
    }

    public void submit(View view)
    {

        String incidentType = editText.getText().toString();

        String location = textView2.getText().toString();


        DocumentReference documentReference = fStore.collection("report").document();

        Map<String,Object> report = new HashMap<>();
        report.put("Location",location);

        report.put("Incident_Type",incidentType);
        report.put("User",name);

//        documentReference.set(report).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()) {
//                    Toast.makeText(SecondActivity.this, "report details saved successfully", Toast.LENGTH_SHORT).show();
//                    Log.e("Create Report",userID);
//                }else
//                {
//                    Toast.makeText(SecondActivity.this, "Error report details not saved ", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        Intent intent = new Intent(this,HomeScreen.class);
        startActivity(intent);

    }

    public void backToIncidentReport(View v){
        Intent in = new Intent();
        in.putExtra("locationDetails",finalLoc);
        setResult(45,in);

        finish();
    }


}