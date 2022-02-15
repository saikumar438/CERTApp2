package com.example.certapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.certapp.Card.ReportsActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText etEmail;
    EditText etPassword;
    TextView textView4;
    SharedPreferences preferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = findViewById(R.id.etLoginEmail);
        etPassword = findViewById(R.id.etLoginPassword);
        textView4  = findViewById(R.id.tv4);
        preferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);


        mAuth = FirebaseAuth.getInstance();

        String email = preferences.getString(KEY_EMAIL,null);
        String password = preferences.getString(KEY_PASSWORD,null);

        if(email != null)
        {
            etEmail.setText(email);
            etPassword.setText(password);
        }
    }

    public void login(View view)
    {
        Log.d("login button","In  login method");
        try {



            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();



            if(email.isEmpty())
            {
                etEmail.setError("Please Enter an email address");
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                etEmail.setError("Please Enter a valid email address");
            }
            if(password.isEmpty())
            {
                etPassword.setError("Please Enter a password");
            }

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(KEY_EMAIL,email);
            editor.putString(KEY_PASSWORD,password);
            editor.apply();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if(user.isEmailVerified())
                        {
                            Toast.makeText(MainActivity.this, "User Logged In Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, HomeScreen.class);
                                startActivity(intent);
                        }else
                        {
                            user.sendEmailVerification();
                            Toast.makeText(MainActivity.this, "Please Check your email for Verification ", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Log.e("In Login onComplete","Please check the on complete task ");
                        textView4.setText("Email or Password is incorrect");
                    }
                }
            });

        }
        catch (Exception e)
        {
            Log.d("Login","Catch block of login button");
        }

    }

    public void register(View view)
    {
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }

    public void forgotPassword(View view) {
        try {
            EditText etEmail = findViewById(R.id.etLoginEmail);
            String email = etEmail.getText().toString();
            Switch forgotPassword = findViewById(R.id.loginForgotPassword);

            if (email.isEmpty()) {
                etEmail.setError("Email Cannot be empty");
                Toast.makeText(MainActivity.this,"Please enter an email -- Email cannot be empty",Toast.LENGTH_LONG).show();
                forgotPassword.setChecked(false);
            }
            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Please Check your Email ", Toast.LENGTH_LONG).show();
                        forgotPassword.setChecked(false);

                    } else {
                        Toast.makeText(MainActivity.this, "Please Enter a Valid Email id", Toast.LENGTH_LONG).show();
                        forgotPassword.setChecked(false);
                    }
                }
            });
        }
        catch(Exception e)
        {
            Log.d("Forgot Password ","This exception is in Forgot password Catch Block "+e);
        }
    }
}