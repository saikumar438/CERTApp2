package com.example.certapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private String userID;
    private EditText etFullName,etEmail,etPhoneNumber,etPassword,etAddress,etQualification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        etFullName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhoneNumber =findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etAddress =findViewById(R.id.etAddress);
        etQualification = findViewById(R.id.etQualification);
    }


    public void register(View view)
    {
        try {

            String name = etFullName.getText().toString();
            String email = etEmail.getText().toString();
            String phoneNumber = etPhoneNumber.getText().toString();
            String password = etPassword.getText().toString();
            String address = etAddress.getText().toString();
            String qualification = etQualification.getText().toString();


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
                                System.out.println("Task is succesful ");
                                User user = new User(name, phoneNumber, email);
                                String mUid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(mUid)
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            System.out.println("Task is succesful inside");
                                            userID = mAuth.getCurrentUser().getUid();
                                            DocumentReference documentReference = fStore.collection("user").document(userID);
                                            Map<String,Object> user = new HashMap<>();
                                            user.put("FullName",name);
                                            user.put("Email",email);
                                            user.put("PhoneNumber",phoneNumber);
                                            user.put("Address",address);
                                            user.put("Qualification",qualification);
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

