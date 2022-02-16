package com.example.certapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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
    private EditText etFullName,etEmail,etPhoneNumber,etPassword,etAddress,etQualification;
    private Spinner spinner ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();
        fStore = FirebaseFirestore.getInstance();

        etFullName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhoneNumber =findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etAddress =findViewById(R.id.etAddress);
        spinner = (Spinner) findViewById(R.id.etQualification);
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.support_simple_spinner_dropdown_item,R.array.qualifications_array){
            @Override
            public boolean isEnabled(int position){
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
        spinner.setOnItemSelectedListener(this);
    }


    public void register(View view)
    {
        System.out.println("Out side try method");
        try {
            System.out.println("In try method register");
            String name = etFullName.getText().toString();

            String email = etEmail.getText().toString();
            String phoneNumber = etPhoneNumber.getText().toString();
            String password = etPassword.getText().toString();
            String address = etAddress.getText().toString();

            String qualification = spinner.getSelectedItem().toString();

            System.out.println("inside try method ");
            if(name.isEmpty())
            {
                etFullName.setError("Please Enter your Full Name");
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
                etPhoneNumber.setError("Please enter a Phone Number");
            }
            if(phoneNumber.length() != 10)
            {
                etPhoneNumber.setError("Phone Number must be 10 digits ");
            }

            if(password.isEmpty())
            {
                etPassword.setError("Please Enter a password");
            }
            if(password.length() < 8)
            {
                etPassword.setError("Password Length must be >= 8");
            }
            if(address.isEmpty())
            {
                etAddress.setError("Please Enter your Full Name");
            }
            if(qualification.isEmpty())
            {
                etQualification.setError("Please Enter the Email");
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                System.out.println("Task is successful ");
                                User user1 = new User("Saikumar","Mylavarapu",qualification,"6605281481","Maryville","Missouri","1215 w 16th street ","64468","1231231231","none","01/15/2022","sainallapati55@gmail.com");
                                String mUid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                                FirebaseDatabase.getInstance().getReference("usersDB")
                                        .child(mUid)
                                        .setValue(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            System.out.println("Task is successful inside");
                                            userID = mAuth.getCurrentUser().getUid();
                                            RootRef.child("usersDB").child(userID).setValue("");
                                            DocumentReference documentReference = fStore.collection("usersDB").document(userID);
                                            Map<String,Object> user = new HashMap<>();
                                            user.put("firstName",user1.getFirstName());
                                            user.put("lastName",user1.getLastName());
                                            user.put("emailAddress",user1.getEmailAddress());
                                            user.put("contactNumber",user1.getContactNumber());
                                            user.put("city",user1.getCity());
                                            user.put("state",user1.getState());
                                            user.put("streetAddress",user1.getStreetAddress());
                                            user.put("zipCode",user1.getZipCode());
                                            user.put("verifyPassword",user1.getVerifyPassword());
                                            user.put("others",user1.getOthers());
                                            user.put("createdAt",user1.getCreatedAt());
                                            user.put("password","1231231231");


                                            user.put("qualification",user1.getQualification());
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
        catch(Exception e)
        {
            Log.d("Register","Catch in Register class ");
        }

    }

}

