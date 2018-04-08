package com.esgi.guitton.candice.controlonair;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MessageActivity extends AppCompatActivity {
    android.support.design.widget.FloatingActionButton create_message_button;

public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
     create_message_button = findViewById(R.id.create_message_button);

/*
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference messagesReference = database.getReference("message");

       // Contact contact = new Contact("Candice", "0625936281");
        //Message firstMessage = new Message(contact, "");



        //messagesReference.push().setValue(firstMessage);

        ListenerOnMessage listener = new ListenerOnMessage();
        // Read from the database
       // messagesReference.addChildEventListener(listener);
*/



    create_message_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MessageActivity.this, SendMessageActivity.class);
            startActivity(intent);
        }
    });




    }




}




