package com.example.certapp.reports;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.certapp.HomeScreen;
import com.example.certapp.MapsActivity;
import com.example.certapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ReportsMainActivity extends AppCompatActivity {
    public static final int TASK_REQ = 1;
    public static final int TASK_RES = 1;

    public static final int GOOD_RES = 2;

    public static final int TOG_RES = 2;

    public static final int FLO_RES = 2;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private DatabaseReference RootRef;
    private String userID,name;

    String[] ImpactLevel = {"Low", "Medium", "High"};

    String[] ImpactLevel1={"Low","Medium","High"};

    String[] hazmats = {"Solid","gas","Chemical","Oil Spill","Electricity","None"};

    int PICK_IMAGE_MULTIPLE_1 = 1;
    String imageEncoded1;
    List<String> imagesEncodedList1;
    private GridView gvGallery1;
    private GalleryAdapter galleryAdapter1;
    static String token;
    EditText title;
    EditText datetime;
    EditText description;
    EditText type;
    MaterialBetterSpinner impact;
    MaterialBetterSpinner impact1;
    EditText redCount;
    EditText greenCount;
    EditText yellowCount;
    EditText blackCount;
    MaterialBetterSpinner Hazmat;
    EditText Notes;
    String dateStr, timeStr;
    EditText address;
    EditText state;
    EditText zipCode;
    TextView dateTV;
    TextView timeTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainreport);

        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();
        fStore = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        title = findViewById(R.id.titleofreport);
        datetime = findViewById(R.id.dateandtime);
        address = findViewById(R.id.address);
        state = findViewById(R.id.state);
        zipCode = findViewById(R.id.zipCode);
        description = findViewById(R.id.description);
        type = findViewById(R.id.Type2);
        impact = findViewById(R.id.spinner1);
        impact1 = findViewById(R.id.spinner);
        redCount = findViewById(R.id.Count1);
        greenCount = findViewById(R.id.Count2);
        yellowCount = findViewById(R.id.Count3);
        blackCount = findViewById(R.id.Count4);
        Hazmat = findViewById(R.id.hazmatType);
        Notes = findViewById(R.id.Notes);
        //datetime.setText(dateStr+" "+timeStr);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, ImpactLevel);
        MaterialBetterSpinner betterSpinner = findViewById(R.id.spinner1);
        betterSpinner.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, ImpactLevel1);
        MaterialBetterSpinner betterSpinner1 = findViewById(R.id.spinner);
        betterSpinner1.setAdapter(arrayAdapter1);

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, hazmats);
        MaterialBetterSpinner betterSpinner2 = findViewById(R.id.hazmatType);
        betterSpinner2.setAdapter(arrayAdapter2);

        DocumentReference documentReference = fStore.collection("usersDB").document(userID);
//        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
//                name = documentSnapshot.getString("firstName");
////                System.out.println("this is system "+name);
//                Log.e("Name of the user ",name);
//
//                Toast.makeText(ReportsMainActivity.this, "Current user name "+name, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onSubmit(View v) {
        try {
            Intent resu=getIntent();
            String details=resu.getStringExtra("locationDetails");
            String[] detailsArr=details.split(",");
            System.out.println(details);
            DocumentReference documentReference = fStore.collection("reportsDB").document();

            Map<String, Object> jsonBody = new HashMap<>();
//            jsonBody.put("Location",location);
//            jsonBody.put("Severity",severity);
//            jsonBody.put("Incident_Type",incidentType);
//            jsonBody.put("User",name);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
//            RequestQueue requestQueue = Volley.newRequestQueue(this);
            Random rnd = new Random();
            int incID = 143 + rnd.nextInt(9999) + this.title.getText().toString().length();
//            JSONObject jsonBody = new JSONObject();
            jsonBody.put("title", this.title.getText().toString());
            jsonBody.put("userName", name);
            // Toast.makeText(getApplicationContext(),this.pwd.getText().toString()+"",Toast.LENGTH_SHORT).show();
            jsonBody.put("timedate", datetime.getText().toString());
            jsonBody.put("updatedAt", dtf.format(now));
            jsonBody.put("address", detailsArr[0]+detailsArr[1]);
            jsonBody.put("state",detailsArr[4]);
            jsonBody.put("zipCode",detailsArr[7]);
            jsonBody.put("latitude",detailsArr[5]);
            jsonBody.put("longitude",detailsArr[6]);
            jsonBody.put("description", this.description.getText().toString());
            jsonBody.put("typeOfIncident", this.type.getText().toString());
            jsonBody.put("impactLevel", String.valueOf(impact.getText().toString()));
            jsonBody.put("structuralDamageImpact", String.valueOf(impact1.getText().toString()));
            jsonBody.put("red", this.redCount.getText().toString());
            jsonBody.put("green", this.greenCount.getText().toString());
            jsonBody.put("yellow", this.yellowCount.getText().toString());
            jsonBody.put("black", this.blackCount.getText().toString());
            jsonBody.put("hazmatType", String.valueOf(Hazmat.getText().toString()));
            jsonBody.put("incidentId", "INC"+incID);
            jsonBody.put("notes", this.Notes.getText().toString());

            documentReference.set(jsonBody).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(ReportsMainActivity.this, "report details saved successfully", Toast.LENGTH_SHORT).show();
                        Log.e("Create Report",userID);
                    }else
                    {
                        Toast.makeText(ReportsMainActivity.this, "Error report details not saved ", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            Intent intent = new Intent(this, HomeScreen.class);
            startActivity(intent);


//            final String requestBody = jsonBody.toString();
//            final Intent tip_intent = new Intent(this, IncidentListResponse.class);
            //should be removed after fixing login issue
            //     startActivity(tip_intent);


//            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
//                @Override
//
//                public void onResponse(JSONObject response) {
//                    Log.i("VOLLEY", response.toString());
//                    // json = new JSONObject(jsonResult);
//                    try {
//                        token = response.getString("token");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    ;
//                    //  Intent ini = new Intent(this,TabsActivity.class)
//                    startActivity(tip_intent);
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e("VOLLEY", error.toString());
//
//                    Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
//
//                }
//            }) {
//                @Override
//                public String getBodyContentType() {
//                    return "application/json; charset=utf-8";
//
//                }
//
//                public Map<String, String> getHeaders() throws AuthFailureError {
//
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("Content-Type", "application/json; charset=UTF-8");
//                    params.put("Authorization", ReportsMainActivity.token);
//                    Log.e("token", ReportsMainActivity.token);
//
//                    return params;
//                }
//
//            };
//
//            requestQueue.add(stringRequest);
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public void getLocationAction(View v) {
        Intent intent = new Intent(ReportsMainActivity.this, MapsActivity.class);
        startActivityForResult(intent, 11);

    }

    public void getDisasterType(View v) {
        Intent disaster_ini = new Intent(this, ImagesActivity.class);
        startActivityForResult(disaster_ini, TASK_REQ);
    }

    public void selectImagesAction(View v){
        Button button = findViewById(R.id.choosefile);
        gvGallery1 = (GridView)findViewById(R.id.gv1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE_1);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent disasterInt) {
        super.onActivityResult(requestCode, resultCode, disasterInt);
        try {
            // When an Image is picked
            if (requestCode == PICK_IMAGE_MULTIPLE_1 && resultCode == RESULT_OK
                    && null != disasterInt) {
                // Get the Image from data

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imagesEncodedList1 = new ArrayList<String>();
                if (disasterInt.getData() != null) {

                    Uri mImageUri = disasterInt.getData();

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded1 = cursor.getString(columnIndex);

                    cursor.close();

                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                    mArrayUri.add(mImageUri);
                    galleryAdapter1 = new GalleryAdapter(getApplicationContext(), mArrayUri);
                    gvGallery1.setAdapter(galleryAdapter1);
                    gvGallery1.setVerticalSpacing(gvGallery1.getHorizontalSpacing());
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery1
                            .getLayoutParams();
                    mlp.setMargins(0, gvGallery1.getHorizontalSpacing(), 0, 0);

                } else {
                    if (disasterInt.getClipData() != null) {
                        ClipData mClipData = disasterInt.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded1 = cursor.getString(columnIndex);
                            imagesEncodedList1.add(imageEncoded1);
                            cursor.close();

                            galleryAdapter1 = new GalleryAdapter(getApplicationContext(), mArrayUri);
                            gvGallery1.setAdapter(galleryAdapter1);
                            gvGallery1.setVerticalSpacing(gvGallery1.getHorizontalSpacing());
                            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery1
                                    .getLayoutParams();
                            mlp.setMargins(0, gvGallery1.getHorizontalSpacing(), 0, 0);

                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }


        if (requestCode == TASK_REQ) {
            if (resultCode == TASK_RES) {
                String str = disasterInt.getStringExtra("name");
                EditText edt = findViewById(R.id.Type2);
                edt.setText(str);
            } else if (resultCode == GOOD_RES) {
                String str = disasterInt.getStringExtra("disaster");
                EditText edt = findViewById(R.id.Type2);
                edt.setText(str);
            } else if (resultCode == TOG_RES) {
                String str = disasterInt.getStringExtra("disaster");
                EditText edt = findViewById(R.id.Type2);
                edt.setText(str);
            } else if (resultCode == FLO_RES) {
                String str = disasterInt.getStringExtra("disaster");
                EditText edt = findViewById(R.id.Type2);
                edt.setText(str);
            }
        }
        if (requestCode == 11) {
            if (resultCode == 11) {
                String str = disasterInt.getStringExtra("locationDetails");
                String[] detailsArr=str.split(",");
                address.setText(detailsArr[0]+detailsArr[1]);

                state.setText(detailsArr[4]);
                zipCode.setText(detailsArr[7]);
            }
        }
    }


    private int mYear, mMonth, mDay, mHour, mMinute;

    public void dateClick(View v) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dateStr = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        //dateTV.setText((monthOfYear + 1)+"-"+dayOfMonth  + "-" + year);
                        datetime.setText((monthOfYear + 1) + "-" + dayOfMonth + "-" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void timeClick(View v) {

        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        datetime.setText(datetime.getText().toString()+ " "+mHour + ":" + mMinute);
//        // Launch Time Picker Dialog
//        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
//                new TimePickerDialog.OnTimeSetListener() {
//
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay,
//                                          int minute) {
//                        timeStr = hourOfDay + ":" + minute;
//                        //timeTV.setText(hourOfDay + ":" + minute);
//                        datetime.setText(datetime.getText().toString()+ " "+hourOfDay + ":" + minute);
//                    }
//                }, mHour, mMinute, true);
//        timePickerDialog.show();
    }

}


