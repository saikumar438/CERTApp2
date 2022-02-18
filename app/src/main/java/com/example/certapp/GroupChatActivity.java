package com.example.certapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

public class GroupChatActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageButton SendMessageButton;
    private EditText userMessageInput;
    private ScrollView mScrollView;
    private TextView displayTextMessages;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private DatabaseReference RootRef,GroupNameRef,GroupMessageKeyRef;
    private String userID,currentDate,currentTime,name;

    private String currentGroupName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        currentGroupName = getIntent().getExtras().get("groupName").toString();
        Toast.makeText(GroupChatActivity.this, currentGroupName, Toast.LENGTH_SHORT).show();

        mAuth = FirebaseAuth.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference().child("Users");
        GroupNameRef = FirebaseDatabase.getInstance().getReference().child("Groups").child(currentGroupName);
        fStore = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        Log.e("User ID ",userID);

        DocumentReference documentReference = fStore.collection("usersDB").document(userID);
        documentReference.addSnapshotListener(GroupChatActivity.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                name = documentSnapshot.getString("FullName");


                Toast.makeText(GroupChatActivity.this, "Current user name "+name, Toast.LENGTH_SHORT).show();

            }
        });
        InitializeFields();

        GetUserInfo();

        SendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveMessageInfoToDatabase();

                userMessageInput.setText("");

                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

    }



    private void GetUserInfo() {

        RootRef.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    userID = snapshot.child("fullName").getValue().toString();


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        GroupNameRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists())
                {
                    DisplayMessages(snapshot);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                DisplayMessages(snapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void InitializeFields() {



        SendMessageButton=(ImageButton) findViewById(R.id.send_message_button);
        userMessageInput=(EditText) findViewById(R.id.input_group_message);
        displayTextMessages=(TextView) findViewById(R.id.group_chat_text_display);
        mScrollView=(ScrollView) findViewById(R.id.my_scroll_view);
    }

    private void SaveMessageInfoToDatabase() {

    String message = userMessageInput.getText().toString();
    String messageKey = GroupNameRef.push().getKey();

    if(TextUtils.isEmpty(message))
    {
        Toast.makeText(GroupChatActivity.this, "Please Write a message", Toast.LENGTH_SHORT).show();
    }
    else
    {
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd,yyyy");
        currentDate = currentDateFormat.format(calForDate.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm a");
        currentTime = currentTimeFormat.format(calForTime.getTime());

        HashMap<String,Object> groupMessageKey = new HashMap<>();

        GroupNameRef.updateChildren(groupMessageKey);

        GroupMessageKeyRef = GroupNameRef.child(messageKey);
        HashMap<String,Object> messageInfoMap = new HashMap<>();
        messageInfoMap.put("name",name);
        messageInfoMap.put("message",message);
        messageInfoMap.put("date",currentDate);
        messageInfoMap.put("time",currentTime);

        GroupMessageKeyRef.updateChildren(messageInfoMap);

    }

    }


    private void DisplayMessages(DataSnapshot snapshot) {
        Iterator iterator = snapshot.getChildren().iterator();

        while(iterator.hasNext())
        {
            String chatDate = (String) ((DataSnapshot)iterator.next()).getValue() ;
            String chatMessage = (String) ((DataSnapshot)iterator.next()).getValue() ;
            String chatName = (String) ((DataSnapshot)iterator.next()).getValue() ;
//            String chatTime = (String) ((DataSnapshot)iterator.next()).getValue() ;

            displayTextMessages.append(chatName + " :\n"+chatMessage+" \n"+"    "+chatDate+" \n\n\n");

            mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
    }

}