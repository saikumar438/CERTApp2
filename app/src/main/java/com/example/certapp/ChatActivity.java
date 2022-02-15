package com.example.certapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ChatActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private ViewPager myViewPager;
    private TabLayout myTabLayout;
    private TabsAccessorAdaptor myTabsAccessorAdaptor;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private DatabaseReference RootRef;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        myViewPager = (ViewPager) findViewById(R.id.main_tabs_pager);
        myTabsAccessorAdaptor = new TabsAccessorAdaptor(getSupportFragmentManager());
        myViewPager.setAdapter(myTabsAccessorAdaptor);


        myTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        myTabLayout.setupWithViewPager(myViewPager);

        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();
        fStore = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        Log.e("User ID ",userID);

//        DocumentReference documentReference = fStore.collection("user").document(userID);
//        documentReference.addSnapshotListener(ChatActivity.this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
//                String name = documentSnapshot.getString("FullName");
//                System.out.println("this is system "+name);
//                Log.e("Name of the user ",name);
//
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.back)
        {
            Intent intent = new Intent(ChatActivity.this,HomeScreen.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.create_group)
        {
            RequestNewGroup();
        }

        return true;

    }

    private void RequestNewGroup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ChatActivity.this);

        builder.setTitle("Enter Group Name :-");
        
        final EditText groupNameField = new EditText(ChatActivity.this);
        groupNameField.setHint("eg :- Missouri Rescue Team");
        builder.setView(groupNameField);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String groupName = groupNameField.getText().toString();

                if(TextUtils.isEmpty(groupName))
                {
                    Toast.makeText(ChatActivity.this, "Please Enter a Group Name", Toast.LENGTH_SHORT).show();

                }
                else
                {
                        CreateNewGroup(groupName);
                }

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        builder.show();
    }

    private void CreateNewGroup(String groupName) {

        RootRef.child("Groups").child(groupName).setValue("")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(ChatActivity.this,groupName+" group is created Successfully",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Log.e("Chat Activity ","In chat activity group creation");
                            Toast.makeText(ChatActivity.this,"Group creation was not successful ",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}