package com.example.certapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import javax.net.ssl.SSLEngineResult;

public class SecondActivity extends AppCompatActivity {

    EditText editText;
    TextView textView1,textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        try {
            Intent intent = getIntent();

            editText = findViewById(R.id.edit_text);
//        textView1 = findViewById(R.id.textView);
            textView2 = findViewById(R.id.text_view2);

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

    public void logout(View view)
    {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }

}