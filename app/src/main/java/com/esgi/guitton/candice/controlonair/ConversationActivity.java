package com.esgi.guitton.candice.controlonair;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.esgi.guitton.candice.controlonair.adapter.ConversationAdapter;
import com.esgi.guitton.candice.controlonair.models.Contact;
import com.esgi.guitton.candice.controlonair.models.Conversation;
import com.esgi.guitton.candice.controlonair.services.MessageService;

import java.util.ArrayList;

public class ConversationActivity extends AppCompatActivity implements ConversationAdapter.OnConversationClickListener {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);


        MessageService.getAllSms(this);

        ListView listView = findViewById(R.id.list_view_conversation);
        ArrayList<Conversation> conversations = MessageService.getConversations(this);
        ConversationAdapter adapter = new ConversationAdapter(this, conversations, this);
        listView.setAdapter(adapter);

/*
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference messagesReference = database.getReference("message");

       // Contact app_contact = new Contact("Candice", "0625936281");
        //Message firstMessage = new Message(app_contact, "");



        //messagesReference.push().setValue(firstMessage);

        ListenerOnMessage listener = new ListenerOnMessage();
        // Read from the database
       // messagesReference.addChildEventListener(listener);
*/

    }


    @Override
    public void onConversationClicked(long id) {
        Toast.makeText(this, "salut, t'as cliqué sur la conv " + id, Toast.LENGTH_SHORT).show();
    }
}




