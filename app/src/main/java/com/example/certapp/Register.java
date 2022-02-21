package com.example.certapp;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        parent.getItemAtPosition(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback

    }
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private DatabaseReference RootRef;
    private String userID;
    private EditText etFirstName,etLastName,etEmail,etPhone,etPassword,etVerifyPassword,etAddress,etCity,etState,etZipCode,etOthers;
    private Spinner spinner ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();
        fStore = FirebaseFirestore.getInstance();
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhone =findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etVerifyPassword =findViewById(R.id.etVerifyPassword);
        etAddress =findViewById(R.id.etAddress);
        etCity = findViewById(R.id.etCity);
        etState = findViewById(R.id.etState);
        etZipCode = findViewById(R.id.etZipCode);
        spinner = (Spinner) findViewById(R.id.etQualification);
        etOthers=findViewById(R.id.etOthers);

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.support_simple_spinner_dropdown_item,R.array.qualifications_array){
            @Override
            public boolean isEnabled(int position){
                if(position == 6)
                {
                    etOthers.setVisibility(View.VISIBLE);
                }
                if(position == 0)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 6)
                {
                    etOthers.setVisibility(View.VISIBLE);
                }
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.qualifications_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 12)
                {
                    etOthers.setVisibility(View.VISIBLE);
                }
                else
                {
                    etOthers.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void register(View view)
    {
        System.out.println("Out side try method");
        try {
            System.out.println("In try method register");
            String name = etFirstName.getText().toString();
            String lname = etLastName.getText().toString();
            String email = etEmail.getText().toString();
            String phoneNumber = etPhone.getText().toString();
            String address = etAddress.getText().toString();
            String city = etCity.getText().toString();
            String state = etState.getText().toString();
            String zipCode = etZipCode.getText().toString();
            String password = etPassword.getText().toString();
            String verifyPassword = etVerifyPassword.getText().toString();

            String qualification = spinner.getSelectedItem().toString();


            if(qualification=="Others"){

                etOthers.setVisibility(View.VISIBLE);
                System.out.println("Nothing");
            }
            System.out.println("inside try method ");
            if(name.isEmpty())
            {
                etFirstName.setError("Please Enter your Full Name");
            }
            if(email.isEmpty())
            {
                etEmail.setError("Please Enter the Email");
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                etEmail.setError("Please Enter a valid email address");
            }
            if(phoneNumber.isEmpty())
            {
                etPhone.setError("Please enter a Phone Number");
            }
            if(phoneNumber.length() != 10)
            {
                etPhone.setError("Phone Number must be 10 digits ");
            }

            if(password.isEmpty())
            {
                etPassword.setError("Please Enter a password");
            }
            if(password.length() < 8)
            {
                etPassword.setError("Password Length must be >= 8");
            }
            if(verifyPassword.isEmpty())
            {
                etVerifyPassword.setError("Please Enter a password");
            }
            if(verifyPassword.length() < 8)
            {
                etVerifyPassword.setError("Password Length must be >= 8");
            }
            if(address.isEmpty())
            {
                etAddress.setError("Please Enter your Full Name");
            }
            if(password.equals(verifyPassword)){



                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    System.out.println("Task is successful ");
                                    User user1 = new User(name,lname,qualification,phoneNumber,city,state,address,zipCode,password,verifyPassword,"none",email);
                                    String mUid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                                    FirebaseDatabase.getInstance().getReference("usersDB")
                                            .child(mUid)
                                            .setValue(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @RequiresApi(api = Build.VERSION_CODES.O)
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                System.out.println("Task is successful inside");
                                                userID = mAuth.getCurrentUser().getUid();
                                                RootRef.child("usersDB").child(userID).setValue("");
                                                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                                                LocalDateTime now = LocalDateTime.now();
                                                DocumentReference documentReference = fStore.collection("usersDB").document(userID);
                                                Map<String,Object> user = new HashMap<>();
                                                user.put("firstName",user1.getFirstName());
                                                user.put("lastName",user1.getLastName());
                                                user.put("emailAddress",user1.getEmailAddress());
                                                user.put("contactNumber",user1.getContactNumber());
                                                user.put("city",user1.getCity());
                                                user.put("qualification",user1.getQualification());
                                                user.put("state",user1.getState());
                                                user.put("streetAddress",user1.getStreetAddress());
                                                user.put("zipCode",user1.getZipCode());
                                                user.put("password",user1.getPassword());
                                                user.put("verifyPassword",user1.getVerifyPassword());
                                                user.put("others",user1.getOthers());
                                                user.put("createdAt",dtf.format(now));

                                                documentReference.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()) {
                                                            Log.e("User details ", "User details have registered successfully ");
                                                        }else
                                                        {
                                                            Log.e("User not registered ","User not registered in real time database ");
                                                        }
                                                    }
                                                });
                                                Toast.makeText(Register.this, "User have registered Successfully", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(Register.this, MainActivity.class);
                                                startActivity(intent);
                                            } else {
                                                Log.e("Registration", "Registration not in task ");
                                                Toast.makeText(Register.this, "User registration was not successful", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                } else {
                                    Log.d("in Task ", "Not registered", task.getException());
                                    Toast.makeText(Register.this, "User registration was not successful In database Task", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
            else
            {
                Snackbar.make(findViewById(R.id.myCoordinatorLayout), "Passwords Should Match",
                        Snackbar.LENGTH_SHORT)
                        .show();
                etVerifyPassword.setError("Passwords Should match ");
            }




        }
        catch(Exception e)
        {
            Log.d("Register","Catch in Register class ");
        }

    }

}

