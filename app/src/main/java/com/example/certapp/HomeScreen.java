package com.example.certapp;

import androidx.annotation.NavigationRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.certapp.Card.ReportsActivity;
import com.example.certapp.reports.ReportsMainActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        dl=(DrawerLayout)findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this,dl,R.string.Open,R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);

        dl.addDrawerListener(abdt);
        abdt.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(this);
        nav_view.bringToFront();
        Log.e("navItem","info "+nav_view.getMenu().getItem(0).getItemId());
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                Log.e("Printing Id ", String.valueOf(id));
                if(id == R.id.myprofile)
                {

//                    Toast.makeText(HomeScreen.this,"Clicked on MyProfile",Toast.LENGTH_SHORT).show();
                    Toast.makeText(HomeScreen.this, "MyProfile", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(HomeScreen.this,ProfileManagement.class);
                    startActivity(intent);

                }

                else if(id == R.id.chat)
                {
                    Toast.makeText(HomeScreen.this,"Clicked on Chat",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(HomeScreen.this,ChatActivity.class);
                    startActivity(intent);
                }
                else if(id == R.id.whatsapp)
                {
                    Toast.makeText(HomeScreen.this,"Clicked on whatsapp",Toast.LENGTH_LONG).show();

//                    Intent intent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
//
//                    intent.setPackage("com.whatsapp");
//                    Log.e("Intent for ", String.valueOf(intent));
//                    if(intent != null)
//                    {
//                        startActivity(intent);
//                    }
//                    Intent intent = new Intent(HomeScreen.this,ChatActivity.class);
//                    startActivity(intent);

                        PackageManager pm = getPackageManager();
                    Intent appIntent = pm.getLaunchIntentForPackage("com.whatsapp");//change app package name
                    Log.e("Intent for ", String.valueOf(appIntent));
                    if(appIntent != null)
                        startActivity(appIntent);
                    else {
                        Toast.makeText(HomeScreen.this,"App not installed ",Toast.LENGTH_LONG).show();
                        //App not installed !
                    }


                }
                else if(id == R.id.logout)
                {
                    Intent intent = new Intent(HomeScreen.this,MainActivity.class);
                    startActivity(intent);

                }
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public void createReport(View view)
    {
        System.out.println("button pressed create report");
        Intent intent = new Intent(this, ReportsMainActivity.class);
        startActivity(intent);
    }

    public void reports(View view)
    {
        Intent intent = new Intent(HomeScreen.this, ReportsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}