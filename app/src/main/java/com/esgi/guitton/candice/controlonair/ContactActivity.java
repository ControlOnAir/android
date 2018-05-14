package com.esgi.guitton.candice.controlonair;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.esgi.guitton.candice.controlonair.models.Contact;
import com.esgi.guitton.candice.controlonair.services.ContactService;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {

    ListView contacts;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        ListView contactsListView = findViewById(R.id.list_view_contact);
        ArrayList<Contact> contactList = ContactService.getContactList(getApplicationContext());
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1 ,contactList);
        contactsListView.setAdapter(adapter);
    }





}
